package org.parstastic.jparstastic_json.parser;

import org.parstastic.jparstastic_json.node.JsonValue;
import org.parstastic.jparstastic_json.parser.parsers.JsonValueParser;

public class FullStringJsonParser extends JsonValueParser {
    @Override
    public JsonParsingResult<JsonValue> parse(final JsonParsingProcess parsingProcess) {
        final JsonParsingResult<JsonValue> result = super.parse(parsingProcess);
        if (result.hasError() || parsingProcess.isFinished()) {
            return result;
        } else {
            return JsonParsingResult.error("The JSON String is not fully parsed.", parsingProcess);
        }
    }
}
