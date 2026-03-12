package org.parstastic.jparstastic_json.parser.steps;

import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;

import java.util.Optional;

public class BlockStep extends JsonParsingStep {
    private final JsonParsingStep[] instructions;

    public BlockStep(final JsonParsingStep... instructions) {
        super();
        this.instructions = instructions;
    }

    @Override
    public Optional<JsonParsingResult.JsonParsingResultError> execute(final JsonParsingProcess parsingProcess) {
        for (final JsonParsingStep instruction : this.instructions) {
            final Optional<JsonParsingResult.JsonParsingResultError> result = instruction.execute(parsingProcess);
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.empty();
    }
}
