package org.parstastic.jparstastic_json.parser;

import java.util.function.Predicate;

/**
 * This class encapsulates all information about an ongoing <code>JSON</code> {@link String} parsing process.
 */
public class JsonParsingProcess {
    /**
     * <code>JSON</code> {@link String} to be parsed
     */
    private final String json;
    /**
     * Current index in the <code>JSON</code> {@link String}
     */
    private int index;

    /**
     * Creates a {@link JsonParsingProcess} object with a given <code>JSON</code> {@link String} and initializes {@link #index} to {@code 0}.
     *
     * @param json <code>JSON</code> {@link String}
     */
    JsonParsingProcess(final String json) {
        this.json = json;
        this.index = 0;
    }

    /**
     * Increments {@link #index} by one.
     */
    public void incrementIndex() {
        this.index++;
    }

    /**
     * Returns the character at index {@link #index} in <code>JSON</code> {@link String} {@link #json}.
     *
     * @return character at index {@link #index} in <code>JSON</code> {@link String} {@link #json}
     * @throws IndexOutOfBoundsException if {@link #index} is outside <code>JSON</code> {@link String} {@link #json}
     */
    public char getChar() throws IndexOutOfBoundsException {
        if (isIndexInJson()) {
            return this.json.charAt(this.index);
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Checks whether the character at index {@link #index} in <code>JSON</code> {@link String} {@link #json} is given character {@code c}.
     *
     * @param c character to check equality with character at index {@link #index} in <code>JSON</code> {@link String} {@link #json}
     * @return {@code true} if {@code c} and character at index {@link #index} in <code>JSON</code> {@link String} {@link #json} are equal,
     *         {@code false} otherwise or if {@link #index} is outside <code>JSON</code> {@link String} {@link #json}
     */
    public boolean isAtChar(final char c) {
        if (isIndexInJson()) {
            return c == this.json.charAt(this.index);
        }
        return false;
    }

    /**
     * Applies a given {@link Predicate} to the character at index {@link #index} in <code>JSON</code> {@link String} {@link #json}.
     *
     * @param validationFunction {@link Predicate} to apply to the character at index {@link #index} in <code>JSON</code> {@link String} {@link #json}
     * @return result of applying the given {@link Predicate} to the character at index {@link #index} in <code>JSON</code> {@link String} {@link #json},
     *         {@code false} if {@link #index} is outside <code>JSON</code> {@link String} {@link #json}
     */
    public boolean isCharValid(final Predicate<Character> validationFunction) {
        if (isIndexInJson()) {
            return validationFunction.test(this.json.charAt(this.index));
        }
        return false;
    }

    /**
     * Checks whether {@link #index} currently is inside the boundaries of <code>JSON</code> {@link String} {@link #json}.
     *
     * @return {@code true} if {@link #index} is less than length of {@link #json},
     *         {@code false} otherwise
     */
    public boolean isIndexInJson() {
        return this.index < this.json.length();
    }

    /**
     * Checks whether <code>JSON</code> {@link String} {@link #json} starts with given {@link String} {@code string},
     * starting from index {@link #index} in <code>JSON</code> {@link String} {@link #json}.
     *
     * @param string {@link String} to compare starting position with
     * @return {@code true} if <code>JSON</code> {@link String} {@link #json} at index {@link #index} starts with given {@link String} {@code string},
     *         {@code false} otherwise
     */
    public boolean startsWith(final String string) {
        return this.json.startsWith(string, this.index);
    }

    /**
     * Checks whether the parsing process is finished by comparing {@link #index} to length of <code>JSON</code> {@link String} {@link #json}.
     *
     * @return {@code true} if {@link #index} is equal to length of <code>JSON</code> {@link String} {@link #json},
     *         {@code false} otherwise
     */
    public boolean isFinished() {
        return this.index == this.json.length();
    }
}
