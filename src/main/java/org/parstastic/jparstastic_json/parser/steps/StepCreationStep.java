package org.parstastic.jparstastic_json.parser.steps;

import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;

import java.util.Optional;
import java.util.function.Supplier;

public class StepCreationStep extends JsonParsingStep {
    private final Supplier<JsonParsingStep> stepCreator;

    public StepCreationStep(final Supplier<JsonParsingStep> stepCreator) {
        super();
        this.stepCreator = stepCreator;
    }

    @Override
    public Optional<JsonParsingResult.JsonParsingResultError> execute(final JsonParsingProcess parsingProcess) {
        return stepCreator.get().execute(parsingProcess);
    }
}
