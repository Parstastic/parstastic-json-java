package org.parstastic.jparstastic_json.parser.parsers.nodes;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.Whitespace;
import org.parstastic.jparstastic_json.node.nodes.ContainerNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParser;
import org.parstastic.jparstastic_json.parser.parsers.WhitespaceParser;
import org.parstastic.jparstastic_json.parser.steps.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class ContainerNodeParser<P extends JsonParticle, J extends ContainerNode<P>> extends JsonNodeParser<J> {
    protected Whitespace whitespace;
    protected final List<P> elements;

    protected ContainerNodeParser() {
        super();
        this.elements = new LinkedList<>();
    }

    @Override
    public boolean canParse(final JsonParsingProcess parsingProcess) {
        return parsingProcess.isAtChar(getStartDelimiter());
    }

    @Override
    protected JsonParsingStep getStep() {
        return new BlockStep(
                new ParseCharacterStep(getStartDelimiter()),
                createContentParser(),
                new ParseCharacterStep(getEndDelimiter())
        );
    }

    protected JsonParsingStep createContentParser() {
        return new ParseStep<>(
                new WhitespaceParser(),
                w -> new OrStep(
                        Map.of(
                                p -> !p.isAtChar(getEndDelimiter()),
                                createElementsParser(w)
                        ),
                        new ExportStep(() -> {
                            this.whitespace = w;
                            return true;
                        })
                )
        );
    }

    protected JsonParsingStep createElementsParser(final Whitespace whitespace) {
        return new BlockStep(
                new ParseStep<>(
                        getElementParser(whitespace),
                        element -> new ExportStep(() -> this.elements.add(element))
                ),
                new WhileLoopStep(
                        new BlockStep(
                                new ParseCharacterStep(c -> true),
                                new StepCreationStep(() -> new ParseStep<>(
                                        getElementParser(),
                                        element -> new ExportStep(() -> this.elements.add(element))
                                ))
                        ),
                        p -> p.isAtChar(getElementDelimiter())
                )
        );
    }

    protected abstract char getStartDelimiter();

    protected abstract char getEndDelimiter();

    protected abstract char getElementDelimiter();

    protected abstract JsonParticleParser<P> getElementParser();

    protected abstract JsonParticleParser<P> getElementParser(final Whitespace whitespace);

    protected final J create() throws JsonParticleInstantiationException {
        return this.whitespace != null ?
                create(this.whitespace) :
                create(this.elements);
    }

    protected abstract J create(final Whitespace whitespace) throws JsonParticleInstantiationException;

    protected abstract J create(final List<P> elements) throws JsonParticleInstantiationException;
}
