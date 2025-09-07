package org.parstastic.jparstastic_json.node.number;

import org.parstastic.jparstastic_json.node.builders.JsonNodeBuilder;
import org.parstastic.jparstastic_json.parser.JsonParser;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonNumberNodeException;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * This class is responsible for parsing <code>JSON</code> strings into {@link NumberNode} objects.
 */
public class NumberNodeBuilder extends JsonNodeBuilder<NumberNode, InvalidJsonNumberNodeException> {
    /**
     * {@link List} of all characters before {@link NumberNode#DECIMAL_DELIMITER}
     */
    private final List<Character> beforeDelimiter;
    /**
     * {@link List} of all characters after {@link NumberNode#DECIMAL_DELIMITER}
     */
    private final List<Character> afterDelimiter;

    /**
     * Creates a {@link NumberNodeBuilder} object with given {@link JsonParser}.
     *
     * @param jsonParser {@link JsonParser} that can be invoked for parsing
     */
    public NumberNodeBuilder(final JsonParser jsonParser) {
        super(jsonParser);
        this.beforeDelimiter = new LinkedList<>();
        this.afterDelimiter = new LinkedList<>();
    }

    /**
     * {@inheritDoc}
     *
     * This {@link NumberNodeBuilder} can parse a <code>JSON</code> {@link String}
     * if index is within <code>JSON</code> {@link String} and the character at index is numeric.
     */
    @Override
    public boolean canParseJson(final String json, final AtomicInteger index) {
        if (index.get() < json.length()) {
            final char c = json.charAt(index.get());
            return Character.isDigit(c);
        }
        return false;
    }

    @Override
    public NumberNode parseJson(final String json, final AtomicInteger index) throws InvalidJsonNumberNodeException {
        if (!canParseJson(json, index)) {
            throw new InvalidJsonNumberNodeException();
        }

        if (iterateNumbers(json, index, this.beforeDelimiter) && iterateNumbers(json, index, this.afterDelimiter)) {
            throw new InvalidJsonNumberNodeException();
        }

        return createNode();
    }

    /**
     * Iterates over all characters in <code>JSON</code> {@link String} starting from {@code index}
     * until there are none left or a non-numeric character is found.
     * Any numeric characters will be added to {@code listToFill}.
     *
     * @param json <code>JSON</code> {@link String} to iterate
     * @param index the index to start iteration from
     * @param listToFill {@link List} of characters to fill
     * @return {@code true} if {@link NumberNode#DECIMAL_DELIMITER} was found,
     *         {@code false} otherwise
     */
    private boolean iterateNumbers(final String json, final AtomicInteger index, final List<Character> listToFill) {
        while (index.get() < json.length()) {
            final char c = json.charAt(index.get());

            if (Character.isDigit(c)) {
                listToFill.add(c);
                index.incrementAndGet();
            } else if (c == NumberNode.DECIMAL_DELIMITER) {
                index.incrementAndGet();
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    /**
     * Creates a {@link NumberNode} object after parsing {@link #beforeDelimiter} and {@link #afterDelimiter} into a numeric value.
     *
     * @return a {@link NumberNode} object with numeric value
     */
    private NumberNode createNode() {
        final Number number;
        if (this.afterDelimiter.isEmpty()) {
            number = parseInt();
        } else {
            number = parseDouble();
        }
        return new NumberNode(number);
    }

    /**
     * Parses the characters in {@link #beforeDelimiter} into an {@link Integer}.
     *
     * @return parsed {@link Integer}
     */
    private Integer parseInt() {
        final String string = joinCharList(this.beforeDelimiter);
        return Integer.parseInt(string);
    }

    /**
     * Parses the characters in {@link #beforeDelimiter} and {@link #afterDelimiter} into a {@link Double}.
     *
     * @return parsed {@link Double}
     */
    private Double parseDouble() {
        final String before = joinCharList(this.beforeDelimiter);
        final String after = joinCharList(this.afterDelimiter);
        final String string = before + NumberNode.DECIMAL_DELIMITER + after;
        return Double.parseDouble(string);
    }

    /**
     * Joins the characters in given {@link Character} {@link List} into a {@link String}.
     *
     * @param charList {@link Character} {@link List} to join
     * @return joined {@link String}
     */
    private String joinCharList(final List<Character> charList) {
        return charList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
