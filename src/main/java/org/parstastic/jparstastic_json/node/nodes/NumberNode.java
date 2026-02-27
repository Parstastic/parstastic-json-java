package org.parstastic.jparstastic_json.node.nodes;

import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.StringifyOptions;

/**
 * This class represents a <code>JSON</code> number node.
 * An example for such a node is {@code 18.8}.
 */
public class NumberNode extends JsonNode {
    public enum NumberNodeExponentSignSymbol {
        BLANK("", 1),
        PLUS("+", 1),
        MINUS("-", -1);

        private final String symbol;
        private final int value;

        NumberNodeExponentSignSymbol(final String symbol, final int value) {
            this.symbol = symbol;
            this.value = value;
        }

        public String getSymbol() {
            return this.symbol;
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
    public NumberNode(final Number value) throws JsonParticleInstantiationException {
        this(value, false, false, null, 0);
    }

    public NumberNode(final Number value,
                      final boolean isExponentCapitalized,
                      final NumberNodeExponentSignSymbol exponentSign,
                      final long exponent)
            throws JsonParticleInstantiationException {
        this(value, true, isExponentCapitalized, exponentSign, exponent);
    }

    private NumberNode(final Number value,
                       final boolean hasExponent,
                       final boolean isExponentCapitalized,
                       final NumberNodeExponentSignSymbol exponentSign,
                       final long exponent)
            throws JsonParticleInstantiationException {
        super();

        validateNotNullOrThrowInstantiationException(value, "value");

        if (exponent < 0) {
            throwInstantiationException("Exponent may not be negative.");
        }

        if (hasExponent && exponentSign == null) {
            throwInstantiationException("If an exponent is present, \"exponentSign\" may not be null.");
        }

        this.value = value;
        this.hasExponent = hasExponent;
        this.isExponentCapitalized = isExponentCapitalized;
        this.exponentSign = exponentSign;
        this.exponent = exponent;
    }

    @Override
    public String stringify(final StringifyOptions options) {
        return this.value.toString();
    }

    public double getNumericValue() {
        if (this.hasExponent) {
            return Math.pow(this.value.doubleValue(), (double) this.exponentSign.value * this.exponent);
        } else {
            return this.value.doubleValue();
        }
    }
}
