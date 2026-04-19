package org.parstastic.jparstastic_json.parser.parsers.nodes;

import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.nodes.BooleanNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.steps.*;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BooleanNodeParser extends JsonNodeParser<BooleanNode> {
    private Boolean value;

    public BooleanNodeParser() {
        super();
    }

    @Override
    public boolean canParse(final JsonParsingProcess parsingProcess) {
        return createValueStream()
                .anyMatch(v -> parsingProcess.startsWith(v.toString()));
    }

    private Stream<Boolean> createValueStream() {
        return Stream.of(Boolean.TRUE, Boolean.FALSE);
    }

    @Override
    protected JsonParsingStep getStep() {
        return OrStep.elseError(
                createParsersMap()
        );
    }

    private Map<Predicate<JsonParsingProcess>, JsonParsingStep> createParsersMap() {
        return createValueStream()
                .map(this::createEntry)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<Predicate<JsonParsingProcess>, JsonParsingStep> createEntry(final Boolean b) {
        final String stringValue = b.toString();
        return new AbstractMap.SimpleEntry<>(
                p -> p.startsWith(stringValue),
                new BlockStep(
                        new ForLoopStep(
                                new ParseCharacterStep(c -> true),
                                stringValue.length()
                        ),
                        new ExportStep(() -> {
                            this.value = b;
                            return true;
                        })
                )
        );
    }

    @Override
    protected BooleanNode create() throws JsonParticleInstantiationException {
        return BooleanNode.VALUES.stream()
                .filter(b -> b.hasValue(this.value))
                .findFirst()
                .orElseThrow(() -> new JsonParticleInstantiationException("There was no matching BooleanNode found."));
    }
}
