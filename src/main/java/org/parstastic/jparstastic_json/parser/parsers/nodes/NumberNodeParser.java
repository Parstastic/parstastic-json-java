package org.parstastic.jparstastic_json.parser.parsers.nodes;

import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.nodes.NumberNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.steps.*;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class NumberNodeParser extends JsonNodeParser<NumberNode> {
    private static final Predicate<Character> IS_DIGIT_ZERO = c -> c == '0';
    private static final Predicate<Character> IS_DIGIT_ONE_TO_NINE = c -> c >= '1' && c <= '9';
    private static final Predicate<Character> IS_DIGIT = c -> IS_DIGIT_ZERO.test(c) || IS_DIGIT_ONE_TO_NINE.test(c);

    private final List<Character> value;
    private boolean isExponentCapitalized;
    private NumberNode.NumberNodeExponentSignSymbol exponentSign;
    private final List<Character> exponent;

    public NumberNodeParser() {
        super();
        this.value = new LinkedList<>();
        this.exponent = new LinkedList<>();
    }

    @Override
    public boolean canParse(final JsonParsingProcess parsingProcess) {
        return parsingProcess.isCharValid(c -> c == NumberNode.NEGATIVE_NUMBER_PREFIX || IS_DIGIT.test(c));
    }

    @Override
    protected JsonParsingStep getStep() {
        return new BlockStep(
                createBaseSignParser(),
                createBaseParser(),
                createFractionParser(),
                createExponentParser()
        );
    }

    private JsonParsingStep createBaseSignParser() {
        return OrStep.elseSuccess(
                Map.of(
                        parsingProcess -> parsingProcess.isAtChar(NumberNode.NEGATIVE_NUMBER_PREFIX),
                        new ParseCharacterStep(this.value::add)
                )
        );
    }

    private JsonParsingStep createBaseParser() {
        return OrStep.elseError(
                Map.of(
                        parsingProcess -> parsingProcess.isCharValid(IS_DIGIT_ZERO),
                        new ParseCharacterStep(this.value::add),
                        parsingProcess -> parsingProcess.isCharValid(IS_DIGIT_ONE_TO_NINE),
                        new BlockStep(
                                new ParseCharacterStep(this.value::add),
                                new WhileLoopStep(
                                        new ParseCharacterStep(this.value::add),
                                        parsingProcess -> parsingProcess.isCharValid(IS_DIGIT)
                                )
                        )
                )
        );
    }

    private JsonParsingStep createFractionParser() {
        return OrStep.elseSuccess(
                Map.of(
                        parsingProcess -> parsingProcess.isAtChar(NumberNode.DECIMAL_DELIMITER),
                        new BlockStep(
                                new ParseCharacterStep(this.value::add),
                                createWhileLoopWithAtLeastOneIterationParser(this.value)
                        )
                )
        );
    }

    private JsonParsingStep createWhileLoopWithAtLeastOneIterationParser(final List<Character> characters) {
        return new BlockStep(
                new ValidateCharacterStep(IS_DIGIT),
                new ParseCharacterStep(characters::add),
                new WhileLoopStep(
                        new ParseCharacterStep(characters::add),
                        parsingProcess -> parsingProcess.isCharValid(IS_DIGIT)
                )
        );
    }

    private JsonParsingStep createExponentParser() {
        return OrStep.elseSuccess(
                Map.of(
                        p -> p.isCharValid(
                                c -> c == NumberNode.EXPONENT_SYMBOL || c == NumberNode.EXPONENT_SYMBOL_CAPITALIZED
                        ),
                        new BlockStep(
                                new ParseCharacterStep(c -> {
                                    this.isExponentCapitalized = Character.isUpperCase(c);
                                    return true;
                                }),
                                createExponentSignParser(),
                                createWhileLoopWithAtLeastOneIterationParser(this.exponent)
                        )
                )
        );
    }

    private JsonParsingStep createExponentSignParser() {
        return new OrStep(
                Map.ofEntries(
                        createExponentSignSymbolParserEntry(NumberNode.NumberNodeExponentSignSymbol.MINUS),
                        createExponentSignSymbolParserEntry(NumberNode.NumberNodeExponentSignSymbol.PLUS)
                ),
                new ExportStep(() -> {
                    this.exponentSign = NumberNode.NumberNodeExponentSignSymbol.BLANK;
                    return true;
                })
        );
    }

    private Map.Entry<Predicate<JsonParsingProcess>, JsonParsingStep> createExponentSignSymbolParserEntry(
            final NumberNode.NumberNodeExponentSignSymbol exponentSignSymbol
    ) {
        return new AbstractMap.SimpleEntry<>(
                p -> p.startsWith(exponentSignSymbol.getSymbol()),
                new ParseCharacterStep(c -> {
                    this.exponentSign = exponentSignSymbol;
                    return true;
                })
        );
    }

    @Override
    protected NumberNode create() throws JsonParticleInstantiationException {
        final String s = joinCharacters(this.value);
        final Number number;
        if (this.value.contains(NumberNode.DECIMAL_DELIMITER)) {
            number = Double.parseDouble(s);
        } else {
            number = Long.parseLong(s);
        }

        if (this.exponent.isEmpty()) {
            return new NumberNode(number);
        } else {
            final long exponentValue = Long.parseLong(joinCharacters(this.exponent));
            return new NumberNode(number, this.isExponentCapitalized, this.exponentSign, exponentValue);
        }
    }
}
