package org.parstastic.jparstastic_json.node;

import java.util.function.Consumer;

public class StringifyOptions {
    public static final boolean DEFAULT_DO_LINE_BREAKS = true;
    public static final char DEFAULT_INDENTATION_CHAR = '\t';
    public static final int DEFAULT_NUMBER_OF_INDENTATIONS_PER_LEVEL = 1;
    public static final int DEFAULT_STARTING_INDENTATION_LEVEL = 0;
    public static final boolean DEFAULT_SKIP_FIRST_INDENTATION = false;

    public static final StringifyOptions DEFAULT_STRINGIFY_OPTIONS = new StringifyOptions(
            DEFAULT_DO_LINE_BREAKS,
            DEFAULT_INDENTATION_CHAR,
            DEFAULT_NUMBER_OF_INDENTATIONS_PER_LEVEL,
            DEFAULT_STARTING_INDENTATION_LEVEL,
            DEFAULT_SKIP_FIRST_INDENTATION
    );

    private final boolean doLineBreaks;
    private final char indentationChar;
    private final int numberOfIndentationsPerLevel;
    private int indentationLevel;
    private boolean skipFirstIndentation;

    private StringifyOptions(final boolean doLineBreaks,
                             final char indentationChar,
                             final int numberOfIndentationsPerLevel,
                             final int indentationLevel,
                             final boolean skipFirstIndentation) {
        super();
        this.doLineBreaks = doLineBreaks;
        this.indentationChar = indentationChar;
        this.numberOfIndentationsPerLevel = numberOfIndentationsPerLevel;
        this.indentationLevel = indentationLevel;
        this.skipFirstIndentation = skipFirstIndentation;
    }

    private StringifyOptions(final StringifyOptions options) {
        this(
                options.doLineBreaks,
                options.indentationChar,
                options.numberOfIndentationsPerLevel,
                options.indentationLevel,
                options.skipFirstIndentation
        );
    }

    public StringifyOptions withIncreasedIndentationLevel() {
        return update(newOptions -> newOptions.indentationLevel++);
    }

    public StringifyOptions skipFirstIndentation() {
        return update(newOptions -> newOptions.skipFirstIndentation = true);
    }

    private StringifyOptions update(final Consumer<StringifyOptions> updateFunction) {
        final StringifyOptions newOptions = new StringifyOptions(this);
        updateFunction.accept(newOptions);
        return newOptions;
    }

    public String getLineBreak() {
        if (this.doLineBreaks) {
            return "\n";
        } else {
            return "";
        }
    }

    public String getIndentation() {
        if (this.skipFirstIndentation) {
            this.skipFirstIndentation = false;
            return "";
        } else {
            return String.valueOf(this.indentationChar).repeat(this.numberOfIndentationsPerLevel * this.indentationLevel);
        }
    }
}
