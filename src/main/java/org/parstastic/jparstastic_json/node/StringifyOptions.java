package org.parstastic.jparstastic_json.node;

import org.parstastic.jparstastic_json.node.nodes.ArrayNode;
import org.parstastic.jparstastic_json.node.nodes.ContainerNode;
import org.parstastic.jparstastic_json.node.nodes.ObjectNode;

import java.util.function.Function;

public class StringifyOptions {
    private record WhitespaceStringifyOptions(Function<StringifyOptions, Whitespace> whitespaceCreator) {
        public Whitespace toWhitespace(final StringifyOptions stringifyOptions) {
            return this.whitespaceCreator.apply(stringifyOptions);
        }
    }

    private record JsonValueStringifyOptions<W>(
            W leadingWhitespaceStringifyOptions,
            W trailingWhitespaceStringifyOptions
    ) {

    }

    private abstract static class ContainerNodeStringifyOptions<W> {
        protected final W whitespaceStringifyOptions;
        protected final JsonValueStringifyOptions<W> jsonValueStringifyOptions;
        protected final W jsonValueLastElementTrailingWhitespaceStringifyOptions;

        private ContainerNodeStringifyOptions(
                final W whitespaceStringifyOptions,
                final JsonValueStringifyOptions<W> jsonValueStringifyOptions,
                final W jsonValueLastElementTrailingWhitespaceStringifyOptions
        ) {
            super();
            this.whitespaceStringifyOptions = whitespaceStringifyOptions;
            this.jsonValueStringifyOptions = jsonValueStringifyOptions;
            this.jsonValueLastElementTrailingWhitespaceStringifyOptions = jsonValueLastElementTrailingWhitespaceStringifyOptions;
        }
    }

    private static class ArrayNodeStringifyOptions<W> extends ContainerNodeStringifyOptions<W> {
        private ArrayNodeStringifyOptions(
                final W whitespaceStringifyOptions,
                final JsonValueStringifyOptions<W> jsonValueStringifyOptions,
                final W jsonValueLastElementTrailingWhitespaceStringifyOptions
        ) {
            super(
                    whitespaceStringifyOptions,
                    jsonValueStringifyOptions,
                    jsonValueLastElementTrailingWhitespaceStringifyOptions
            );
        }
    }

    private static class ObjectNodeStringifyOptions<W> extends ContainerNodeStringifyOptions<W> {
        private final W objectNodePropertyLeadingWhitespaceStringifyOptions;
        private final W objectNodePropertyTrailingWhitespaceStringifyOptions;

        private ObjectNodeStringifyOptions(
                final W whitespaceStringifyOptions,
                final JsonValueStringifyOptions<W> jsonValueStringifyOptions,
                final W jsonValueLastElementTrailingWhitespaceStringifyOptions,
                final W objectNodePropertyLeadingWhitespaceStringifyOptions,
                final W objectNodePropertyTrailingWhitespaceStringifyOptions
        ) {
            super(
                    whitespaceStringifyOptions,
                    jsonValueStringifyOptions,
                    jsonValueLastElementTrailingWhitespaceStringifyOptions
            );
            this.objectNodePropertyLeadingWhitespaceStringifyOptions = objectNodePropertyLeadingWhitespaceStringifyOptions;
            this.objectNodePropertyTrailingWhitespaceStringifyOptions = objectNodePropertyTrailingWhitespaceStringifyOptions;
        }
    }

    public static final String DEFAULT_INDENTATION = "    ";

    private static final WhitespaceStringifyOptions DEFAULT_WHITESPACE_STRINGIFY_OPTIONS = new WhitespaceStringifyOptions(
            options -> null
    );
    private static final WhitespaceStringifyOptions LINE_BREAK_AND_INDENTATION_WHITESPACE_STRINGIFY_OPTIONS = new WhitespaceStringifyOptions(
            options -> {
                try {
                    return new Whitespace("\n" + DEFAULT_INDENTATION.repeat(Math.max(0, options.indentationLevel)));
                } catch (final JsonParticleInstantiationException e) {
                    return null;
                }
            }
    );
    private static final WhitespaceStringifyOptions LINE_BREAK_AND_INDENTATION_REDUCED_WHITESPACE_STRINGIFY_OPTIONS = new WhitespaceStringifyOptions(
            options -> {
                try {
                    return new Whitespace("\n" + DEFAULT_INDENTATION.repeat(Math.max(0, options.indentationLevel - 1)));
                } catch (final JsonParticleInstantiationException e) {
                    return null;
                }
            }
    );
    private static final WhitespaceStringifyOptions ONE_SPACE_WHITESPACE_STRINGIFY_OPTIONS = new WhitespaceStringifyOptions(
            options -> {
                try {
                    return new Whitespace(" ");
                } catch (final JsonParticleInstantiationException e) {
                    return null;
                }
            }
    );
    private static final WhitespaceStringifyOptions MINIMAL_WHITESPACE_STRINGIFY_OPTIONS = new WhitespaceStringifyOptions(
            options -> {
                try {
                    return new Whitespace("");
                } catch (final JsonParticleInstantiationException e) {
                    return null;
                }
            }
    );

    private static final JsonValueStringifyOptions<WhitespaceStringifyOptions> DEFAULT_JSON_VALUE_STRINGIFY_OPTIONS = new JsonValueStringifyOptions<>(
            DEFAULT_WHITESPACE_STRINGIFY_OPTIONS,
            DEFAULT_WHITESPACE_STRINGIFY_OPTIONS
    );
    private static final JsonValueStringifyOptions<WhitespaceStringifyOptions> PRETTY_JSON_VALUE_STRINGIFY_OPTIONS = new JsonValueStringifyOptions<>(
            MINIMAL_WHITESPACE_STRINGIFY_OPTIONS,
            MINIMAL_WHITESPACE_STRINGIFY_OPTIONS
    );
    private static final JsonValueStringifyOptions<WhitespaceStringifyOptions> MINIMAL_JSON_VALUE_STRINGIFY_OPTIONS = new JsonValueStringifyOptions<>(
            MINIMAL_WHITESPACE_STRINGIFY_OPTIONS,
            MINIMAL_WHITESPACE_STRINGIFY_OPTIONS
    );

    private static final ArrayNodeStringifyOptions<WhitespaceStringifyOptions> DEFAULT_ARRAY_NODE_STRINGIFY_OPTIONS = new ArrayNodeStringifyOptions<>(
            DEFAULT_WHITESPACE_STRINGIFY_OPTIONS,
            DEFAULT_JSON_VALUE_STRINGIFY_OPTIONS,
            DEFAULT_WHITESPACE_STRINGIFY_OPTIONS
    );
    private static final ArrayNodeStringifyOptions<WhitespaceStringifyOptions> PRETTY_ARRAY_NODE_STRINGIFY_OPTIONS = new ArrayNodeStringifyOptions<>(
            MINIMAL_WHITESPACE_STRINGIFY_OPTIONS,
            new JsonValueStringifyOptions<>(
                    LINE_BREAK_AND_INDENTATION_WHITESPACE_STRINGIFY_OPTIONS,
                    MINIMAL_WHITESPACE_STRINGIFY_OPTIONS
            ),
            LINE_BREAK_AND_INDENTATION_REDUCED_WHITESPACE_STRINGIFY_OPTIONS
    );
    private static final ArrayNodeStringifyOptions<WhitespaceStringifyOptions> MINIMAL_ARRAY_NODE_STRINGIFY_OPTIONS = new ArrayNodeStringifyOptions<>(
            MINIMAL_WHITESPACE_STRINGIFY_OPTIONS,
            MINIMAL_JSON_VALUE_STRINGIFY_OPTIONS,
            MINIMAL_WHITESPACE_STRINGIFY_OPTIONS
    );

    private static final ObjectNodeStringifyOptions<WhitespaceStringifyOptions> DEFAULT_OBJECT_NODE_STRINGIFY_OPTIONS = new ObjectNodeStringifyOptions<>(
            DEFAULT_WHITESPACE_STRINGIFY_OPTIONS,
            DEFAULT_JSON_VALUE_STRINGIFY_OPTIONS,
            DEFAULT_WHITESPACE_STRINGIFY_OPTIONS,
            DEFAULT_WHITESPACE_STRINGIFY_OPTIONS,
            DEFAULT_WHITESPACE_STRINGIFY_OPTIONS
    );
    private static final ObjectNodeStringifyOptions<WhitespaceStringifyOptions> PRETTY_OBJECT_NODE_STRINGIFY_OPTIONS = new ObjectNodeStringifyOptions<>(
            MINIMAL_WHITESPACE_STRINGIFY_OPTIONS,
            new JsonValueStringifyOptions<>(
                    ONE_SPACE_WHITESPACE_STRINGIFY_OPTIONS,
                    MINIMAL_WHITESPACE_STRINGIFY_OPTIONS
            ),
            LINE_BREAK_AND_INDENTATION_REDUCED_WHITESPACE_STRINGIFY_OPTIONS,
            LINE_BREAK_AND_INDENTATION_WHITESPACE_STRINGIFY_OPTIONS,
            MINIMAL_WHITESPACE_STRINGIFY_OPTIONS
    );
    private static final ObjectNodeStringifyOptions<WhitespaceStringifyOptions> MINIMAL_OBJECT_NODE_STRINGIFY_OPTIONS = new ObjectNodeStringifyOptions<>(
            MINIMAL_WHITESPACE_STRINGIFY_OPTIONS,
            MINIMAL_JSON_VALUE_STRINGIFY_OPTIONS,
            MINIMAL_WHITESPACE_STRINGIFY_OPTIONS,
            MINIMAL_WHITESPACE_STRINGIFY_OPTIONS,
            MINIMAL_WHITESPACE_STRINGIFY_OPTIONS
    );

    public static final StringifyOptions DEFAULT_STRINGIFY_OPTIONS = new StringifyOptions(
            DEFAULT_JSON_VALUE_STRINGIFY_OPTIONS,
            DEFAULT_ARRAY_NODE_STRINGIFY_OPTIONS,
            DEFAULT_OBJECT_NODE_STRINGIFY_OPTIONS
    );
    public static final StringifyOptions PRETTY_STRINGIFY_OPTIONS = new StringifyOptions(
            PRETTY_JSON_VALUE_STRINGIFY_OPTIONS,
            PRETTY_ARRAY_NODE_STRINGIFY_OPTIONS,
            PRETTY_OBJECT_NODE_STRINGIFY_OPTIONS
    );
    public static final StringifyOptions MINIMAL_STRINGIFY_OPTIONS = new StringifyOptions(
            MINIMAL_JSON_VALUE_STRINGIFY_OPTIONS,
            MINIMAL_ARRAY_NODE_STRINGIFY_OPTIONS,
            MINIMAL_OBJECT_NODE_STRINGIFY_OPTIONS
    );


    private final ContainerNode<?> containerNode;
    private final boolean isLastElement;
    private final int indentationLevel;

    private final JsonValueStringifyOptions<WhitespaceStringifyOptions> jsonValueStringifyOptions;
    private final ArrayNodeStringifyOptions<WhitespaceStringifyOptions> arrayNodeStringifyOptions;
    private final ObjectNodeStringifyOptions<WhitespaceStringifyOptions> objectNodeStringifyOptions;

    private final JsonValueStringifyOptions<Whitespace> jsonValueStringifyOptionsConfigured;
    private final ArrayNodeStringifyOptions<Whitespace> arrayNodeStringifyOptionsConfigured;
    private final ObjectNodeStringifyOptions<Whitespace> objectNodeStringifyOptionsConfigured;

    private StringifyOptions(
            final JsonValueStringifyOptions<WhitespaceStringifyOptions> jsonValueStringifyOptions,
            final ArrayNodeStringifyOptions<WhitespaceStringifyOptions> arrayNodeStringifyOptions,
            final ObjectNodeStringifyOptions<WhitespaceStringifyOptions> objectNodeStringifyOptions
    ) {
        this(
                null,
                false,
                0,
                jsonValueStringifyOptions,
                arrayNodeStringifyOptions,
                objectNodeStringifyOptions
        );
    }

    private StringifyOptions(
            final ContainerNode<?> containerNode,
            final boolean isLastElement,
            final int indentationLevel,
            final JsonValueStringifyOptions<WhitespaceStringifyOptions> jsonValueStringifyOptions,
            final ArrayNodeStringifyOptions<WhitespaceStringifyOptions> arrayNodeStringifyOptions,
            final ObjectNodeStringifyOptions<WhitespaceStringifyOptions> objectNodeStringifyOptions
    ) {
        super();

        this.containerNode = containerNode;
        this.isLastElement = isLastElement;
        this.indentationLevel = indentationLevel;

        this.jsonValueStringifyOptions = jsonValueStringifyOptions;
        this.arrayNodeStringifyOptions = arrayNodeStringifyOptions;
        this.objectNodeStringifyOptions = objectNodeStringifyOptions;

        this.jsonValueStringifyOptionsConfigured = new JsonValueStringifyOptions<>(
                this.jsonValueStringifyOptions.leadingWhitespaceStringifyOptions.toWhitespace(this),
                this.jsonValueStringifyOptions.trailingWhitespaceStringifyOptions.toWhitespace(this)
        );
        this.arrayNodeStringifyOptionsConfigured = new ArrayNodeStringifyOptions<>(
                this.arrayNodeStringifyOptions.whitespaceStringifyOptions.toWhitespace(this),
                new JsonValueStringifyOptions<>(
                        this.arrayNodeStringifyOptions.jsonValueStringifyOptions.leadingWhitespaceStringifyOptions.toWhitespace(this),
                        this.arrayNodeStringifyOptions.jsonValueStringifyOptions.trailingWhitespaceStringifyOptions.toWhitespace(this)
                ),
                this.arrayNodeStringifyOptions.jsonValueLastElementTrailingWhitespaceStringifyOptions.toWhitespace(this)
        );
        this.objectNodeStringifyOptionsConfigured = new ObjectNodeStringifyOptions<>(
                this.objectNodeStringifyOptions.whitespaceStringifyOptions.toWhitespace(this),
                new JsonValueStringifyOptions<>(
                        this.objectNodeStringifyOptions.jsonValueStringifyOptions.leadingWhitespaceStringifyOptions.toWhitespace(this),
                        this.objectNodeStringifyOptions.jsonValueStringifyOptions.trailingWhitespaceStringifyOptions.toWhitespace(this)
                ),
                this.objectNodeStringifyOptions.jsonValueLastElementTrailingWhitespaceStringifyOptions.toWhitespace(this),
                this.objectNodeStringifyOptions.objectNodePropertyLeadingWhitespaceStringifyOptions.toWhitespace(this),
                this.objectNodeStringifyOptions.objectNodePropertyTrailingWhitespaceStringifyOptions.toWhitespace(this)
        );
    }

    public StringifyOptions forContainerNode(final ContainerNode<?> containerNode) {
        return forContainerNode(containerNode, false);
    }

    public StringifyOptions forContainerNode(final ContainerNode<?> containerNode, final boolean isLastElement) {
        return new StringifyOptions(
                containerNode,
                isLastElement,
                this.indentationLevel + 1,
                this.jsonValueStringifyOptions,
                this.arrayNodeStringifyOptions,
                this.objectNodeStringifyOptions
        );
    }

    public Whitespace getContainerNodeWhitespace(final Whitespace whitespace) {
        final Whitespace w = getContainerNodeWhitespace();
        if (w != null) {
            return w;
        } else {
            return whitespace;
        }
    }

    private Whitespace getContainerNodeWhitespace() {
        if (this.containerNode instanceof ArrayNode) {
            return this.arrayNodeStringifyOptionsConfigured.whitespaceStringifyOptions;
        } else if (this.containerNode instanceof ObjectNode) {
            return this.objectNodeStringifyOptionsConfigured.whitespaceStringifyOptions;
        } else {
            return null;
        }
    }

    public Whitespace getJsonValueLeadingWhitespace(final Whitespace whitespace) {
        final Whitespace w = getJsonValueLeadingWhitespace();
        if (w != null) {
            return w;
        } else {
            return whitespace;
        }
    }

    private Whitespace getJsonValueLeadingWhitespace() {
        if (this.containerNode instanceof ArrayNode) {
            return this.arrayNodeStringifyOptionsConfigured.jsonValueStringifyOptions.leadingWhitespaceStringifyOptions;
        } else if (this.containerNode instanceof ObjectNode) {
            return this.objectNodeStringifyOptionsConfigured.jsonValueStringifyOptions.leadingWhitespaceStringifyOptions;
        } else {
            return this.jsonValueStringifyOptionsConfigured.leadingWhitespaceStringifyOptions;
        }
    }

    public Whitespace getJsonValueTrailingWhitespace(final Whitespace whitespace) {
        final Whitespace w = getJsonValueTrailingWhitespace();
        if (w != null) {
            return w;
        } else {
            return whitespace;
        }
    }

    private Whitespace getJsonValueTrailingWhitespace() {
        if (this.containerNode instanceof ArrayNode) {
            if (this.isLastElement) {
                return this.arrayNodeStringifyOptionsConfigured.jsonValueLastElementTrailingWhitespaceStringifyOptions;
            } else {
                return this.arrayNodeStringifyOptionsConfigured.jsonValueStringifyOptions.trailingWhitespaceStringifyOptions;
            }
        } else if (this.containerNode instanceof ObjectNode) {
            if (this.isLastElement) {
                return this.objectNodeStringifyOptionsConfigured.jsonValueLastElementTrailingWhitespaceStringifyOptions;
            } else {
                return this.objectNodeStringifyOptionsConfigured.jsonValueStringifyOptions.trailingWhitespaceStringifyOptions;
            }
        } else {
            return this.jsonValueStringifyOptionsConfigured.trailingWhitespaceStringifyOptions;
        }
    }

    public Whitespace getObjectNodePropertyLeadingWhitespace(final Whitespace whitespace) {
        final Whitespace w = this.objectNodeStringifyOptionsConfigured.objectNodePropertyLeadingWhitespaceStringifyOptions;
        if (w != null) {
            return w;
        } else {
            return whitespace;
        }
    }

    public Whitespace getObjectNodePropertyTrailingWhitespace(final Whitespace whitespace) {
        final Whitespace w = this.objectNodeStringifyOptionsConfigured.objectNodePropertyTrailingWhitespaceStringifyOptions;
        if (w != null) {
            return w;
        } else {
            return whitespace;
        }
    }
}
