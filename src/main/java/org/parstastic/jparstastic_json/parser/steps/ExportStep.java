package org.parstastic.jparstastic_json.parser.steps;

import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;

import java.util.Optional;
import java.util.function.BooleanSupplier;

public class ExportStep extends JsonParsingStep {
    private final BooleanSupplier exporter;

    public ExportStep(final BooleanSupplier exporter) {
        super();
        this.exporter = exporter;
    }

    @Override
    public Optional<JsonParsingResult.JsonParsingResultError> execute(final JsonParsingProcess parsingProcess) {
        if (this.exporter.getAsBoolean()) {
            return Optional.empty();
        } else {
            return Optional.of(new JsonParsingResult.JsonParsingResultError("Exporting failed", parsingProcess));
        }
    }
}
