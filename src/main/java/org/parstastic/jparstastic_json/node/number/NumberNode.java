package org.parstastic.jparstastic_json.node.number;

import org.parstastic.jparstastic_json.node.JsonNode;
import org.parstastic.jparstastic_json.node.StringifyOptions;

/**
 * This class represents a <code>JSON</code> number node.
 * An example for such a node is {@code 18.8}.
 */
public class NumberNode extends JsonNode {
    public enum NumberNodeExponentSignSymbol {
        BLANK(1),
        PLUS(1),
        MINUS(-1);

        private final int value;

        NumberNodeExponentSignSymbol(final int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    /**
     * Character delimiter between the integer and decimal parts of the <code>JSON</code> number node.
     */
    public static final char DECIMAL_DELIMITER = '.';
    public static final char EXPONENT_SYMBOL = 'e';
    public static final char EXPONENT_SYMBOL_CAPITALIZED = 'E';

    /**
     * Numeric value of the <code>JSON</code> number node
     */
    private final Number value;
    private final boolean hasExponent;
    private final boolean isExponentCapitalized;
    private final NumberNodeExponentSignSymbol exponentSign;
    private final long exponent;

    /**
     * Creates a {@link NumberNode} object with the given {@code value} as numeric value.
     *
     * @param value numeric value of the <code>JSON</code> number node
     */
    public NumberNode(final Number value) throws IllegalArgumentException {
        this(value, false, false, null, 0);
    }

    public NumberNode(final Number value,
                      final boolean isExponentCapitalized,
                      final NumberNodeExponentSignSymbol exponentSign,
                      final long exponent) throws IllegalArgumentException {
        this(value, true, isExponentCapitalized, exponentSign, exponent);
    }

    private NumberNode(final Number value,
                       final boolean hasExponent,
                       final boolean isExponentCapitalized,
                       final NumberNodeExponentSignSymbol exponentSign,
                       final long exponent) throws IllegalArgumentException {
        super();

        if (value == null || exponent < 0) {
            throw new IllegalArgumentException();
        }

        this.value = value;
        this.hasExponent = hasExponent;
        this.isExponentCapitalized = isExponentCapitalized;
        this.exponentSign = exponentSign;
        this.exponent = exponent;
    }

    @Override
    public String stringify(final StringifyOptions options) {
        return options.getIndentation() + this.value.toString();
    }

    public double getNumericValue() {
        if (this.hasExponent) {
            return Math.pow(this.value.doubleValue(), (double) this.exponentSign.value * this.exponent);
        } else {
            return this.value.doubleValue();
        }
    }
}
