package org.parstastic.jparstastic_json.parser.parsers.nodes;

import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.JsonValue;
import org.parstastic.jparstastic_json.node.Whitespace;
import org.parstastic.jparstastic_json.node.nodes.ObjectNode;
import org.parstastic.jparstastic_json.node.nodes.StringNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParser;
import org.parstastic.jparstastic_json.parser.parsers.JsonValueParser;
import org.parstastic.jparstastic_json.parser.parsers.WhitespaceParser;
import org.parstastic.jparstastic_json.parser.steps.*;

import java.util.List;

import static org.parstastic.jparstastic_json.node.nodes.ObjectNode.ObjectNodeProperty;

public class ObjectNodeParser extends ContainerNodeParser<ObjectNodeProperty, ObjectNode> {
    public static class ObjectNodePropertyParser extends JsonParticleParser<ObjectNodeProperty> {
        private Whitespace leadingWhitespace;
        private StringNode key;
        private Whitespace trailingWhitespace;
        private JsonValue value;

        public ObjectNodePropertyParser() {
            this(null);
        }

        public ObjectNodePropertyParser(final Whitespace leadingWhitespace) {
            super();
            this.leadingWhitespace = leadingWhitespace;
        }

        @Override
        public boolean canParse(final JsonParsingProcess parsingProcess) {
            return new WhitespaceParser().canParse(parsingProcess);
        }

        @Override
        protected JsonParsingStep getStep() {
            return new BlockStep(
                    new ParseStep<>(
                            new WhitespaceParser(this.leadingWhitespace),
                            w -> new ExportStep(() -> {
                                this.leadingWhitespace = w;
                                return true;
                            })
                    ),
                    new ParseStep<>(
                            new StringNodeParser(),
                            s -> new ExportStep(() -> {
                                this.key = s;
                                return true;
                            })
                    ),
                    new ParseStep<>(
                            new WhitespaceParser(),
                            w -> new ExportStep(() -> {
                                this.trailingWhitespace = w;
                                return true;
                            })
                    ),
                    new ParseCharacterStep(ObjectNodeProperty.KEY_VALUE_DELIMITER),
                    new ParseStep<>(
                            new JsonValueParser(),
                            v -> new ExportStep(() -> {
                                this.value = v;
                                return true;
                            })
                    )
            );
        }

        @Override
        protected ObjectNodeProperty create() throws JsonParticleInstantiationException {
            return new ObjectNodeProperty(
                    this.leadingWhitespace,
                    this.key,
                    this.trailingWhitespace,
                    this.value
            );
        }
    }

    public ObjectNodeParser() {
        super();
    }

    @Override
    protected char getStartDelimiter() {
        return ObjectNode.DELIMITER_START;
    }

    @Override
    protected char getEndDelimiter() {
        return ObjectNode.DELIMITER_END;
    }

    @Override
    protected char getElementDelimiter() {
        return ObjectNode.DELIMITER_ELEMENTS;
    }

    @Override
    protected JsonParticleParser<ObjectNodeProperty> getElementParser() {
        return new ObjectNodeParser.ObjectNodePropertyParser();
    }

    @Override
    protected JsonParticleParser<ObjectNodeProperty> getElementParser(final Whitespace whitespace) {
        return new ObjectNodeParser.ObjectNodePropertyParser(whitespace);
    }

    @Override
    protected ObjectNode create(final Whitespace whitespace) throws JsonParticleInstantiationException {
        return new ObjectNode(whitespace);
    }

    @Override
    protected ObjectNode create(final List<ObjectNodeProperty> elements) throws JsonParticleInstantiationException {
        return new ObjectNode(elements);
    }
}
