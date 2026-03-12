package org.parstastic.jparstastic_json.parser.steps;

import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class OrStep extends JsonParsingStep {
    public static OrStep elseError(final Map<JsonParsingStep, Predicate<JsonParsingProcess>> ifSteps) {
        return new OrStep(
                ifSteps,
                new ExportStep(() -> false)
        );
    }

    public static OrStep elseSuccess(final Map<JsonParsingStep, Predicate<JsonParsingProcess>> ifSteps) {
        return new OrStep(
                ifSteps,
                new ExportStep(() -> true)
        );
    }

    private final Map<JsonParsingStep, Predicate<JsonParsingProcess>> ifSteps;
    private final JsonParsingStep elseStep;

    public OrStep(final Map<JsonParsingStep, Predicate<JsonParsingProcess>> ifSteps, final JsonParsingStep elseStep) {
        super();
        this.ifSteps = ifSteps;
        this.elseStep = elseStep;
    }

    @Override
    public Optional<JsonParsingResult.JsonParsingResultError> execute(final JsonParsingProcess parsingProcess) {
        for (final Map.Entry<JsonParsingStep, Predicate<JsonParsingProcess>> ifStep : ifSteps.entrySet()) {
            if (ifStep.getValue().test(new JsonParsingProcess(parsingProcess))) {
                return ifStep.getKey().execute(parsingProcess);
            }
        }
        return this.elseStep.execute(parsingProcess);
    }
}
