package org.parstastic.jparstastic_json.parser.steps;

import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class OrStep extends JsonParsingStep {
    public static OrStep elseError(final Map<Predicate<JsonParsingProcess>, JsonParsingStep> ifSteps) {
        return new OrStep(
                ifSteps,
                new ExportStep(() -> false)
        );
    }

    public static OrStep elseSuccess(final Map<Predicate<JsonParsingProcess>, JsonParsingStep> ifSteps) {
        return new OrStep(
                ifSteps,
                new ExportStep(() -> true)
        );
    }

    private final Map<Predicate<JsonParsingProcess>, JsonParsingStep> ifSteps;
    private final JsonParsingStep elseStep;

    public OrStep(final Map<Predicate<JsonParsingProcess>, JsonParsingStep> ifSteps, final JsonParsingStep elseStep) {
        super();
        this.ifSteps = ifSteps;
        this.elseStep = elseStep;
    }

    @Override
    public Optional<JsonParsingResult.JsonParsingResultError> execute(final JsonParsingProcess parsingProcess) {
        for (final Map.Entry<Predicate<JsonParsingProcess>, JsonParsingStep> ifStep : ifSteps.entrySet()) {
            if (ifStep.getKey().test(new JsonParsingProcess(parsingProcess))) {
                return ifStep.getValue().execute(parsingProcess);
            }
        }
        return this.elseStep.execute(parsingProcess);
    }
}
