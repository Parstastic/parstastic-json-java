package org.parstastic.jparstastic_json.node.number;

import org.parstastic.jparstastic_json.node.builders.JsonNodeBuilder;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonNumberNodeException;

import java.util.LinkedList;
import java.util.List;
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
     * Creates a {@link NumberNodeBuilder} object.
     */
    public NumberNodeBuilder() {
        super();
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
    public boolean canParseJson(final JsonParsingProcess parsingProcess) {
        return parsingProcess.isCharValid(Character::isDigit);
    }

    @Override
    public NumberNode parseJson(final JsonParsingProcess parsingProcess) throws InvalidJsonNumberNodeException {
        if (!canParseJson(parsingProcess)) {
            throw new InvalidJsonNumberNodeException();
        }

        if (iterateNumbers(parsingProcess, this.beforeDelimiter) && iterateNumbers(parsingProcess, this.afterDelimiter)) {
            throw new InvalidJsonNumberNodeException();
        }

        return createNode();
    }

    /**
     * Iterates over all characters in <code>JSON</code> {@link String} starting from {@code index}
     * until there are none left or a non-numeric character is found.
     * Any numeric characters will be added to {@code listToFill}.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process to iterate
     * @param listToFill {@link List} of characters to fill
     * @return {@code true} if {@link NumberNode#DECIMAL_DELIMITER} was found,
     *         {@code false} otherwise
     */
    private boolean iterateNumbers(final JsonParsingProcess parsingProcess, final List<Character> listToFill) {
        while (parsingProcess.isIndexInJson()) {
            final char c = parsingProcess.getChar();

            if (Character.isDigit(c)) {
                listToFill.add(c);
                parsingProcess.incrementIndex();
            } else if (c == NumberNode.DECIMAL_DELIMITER) {
                parsingProcess.incrementIndex();
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
