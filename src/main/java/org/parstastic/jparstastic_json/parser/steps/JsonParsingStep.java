package org.parstastic.jparstastic_json.parser.steps;

import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;

import java.util.Optional;

public abstract class JsonParsingStep {
    protected JsonParsingStep() {
        super();
    }

    public abstract Optional<JsonParsingResult.JsonParsingResultError> execute(final JsonParsingProcess parsingProcess);
}
