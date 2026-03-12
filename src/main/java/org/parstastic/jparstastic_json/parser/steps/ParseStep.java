package org.parstastic.jparstastic_json.parser.steps;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParser;

import java.util.Optional;
import java.util.function.Function;

public class ParseStep<J extends JsonParticle> extends JsonParsingStep {
    private final JsonParticleParser<J> parser;
    private final Function<J, JsonParsingStep> nextCreator;

    public ParseStep(final JsonParticleParser<J> parser, final Function<J, JsonParsingStep> nextCreator) {
        super();
        this.parser = parser;
        this.nextCreator = nextCreator;
    }

    @Override
    public Optional<JsonParsingResult.JsonParsingResultError> execute(final JsonParsingProcess parsingProcess) {
        if (this.parser.canParse(new JsonParsingProcess(parsingProcess))) {
            try {
                final JsonParsingResult<J> result = this.parser.parse(parsingProcess);
                return this.nextCreator.apply(result.getValue()).execute(parsingProcess);
            } catch (final JsonParsingResult.JsonParsingResultNoSuchElementException e) {
                return Optional.of(new JsonParsingResult.JsonParsingResultError(
                        "An error occurred during usage of the parser.",
                        parsingProcess
                ));
            }
        } else {
            return Optional.of(new JsonParsingResult.JsonParsingResultError(
                    "The given parser cannot parse the JsonParsingProcess.",
                    parsingProcess
            ));
        }
    }
}
