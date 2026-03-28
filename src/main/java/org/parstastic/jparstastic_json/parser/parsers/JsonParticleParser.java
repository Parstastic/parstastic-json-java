package org.parstastic.jparstastic_json.parser.parsers;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;
import org.parstastic.jparstastic_json.parser.steps.JsonParsingStep;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class JsonParticleParser<J extends JsonParticle> {
    protected JsonParticleParser() {
        super();
    }

    public final boolean canParse(final String json) {
        return canParse(new JsonParsingProcess(json));
    }

    public abstract boolean canParse(final JsonParsingProcess parsingProcess);

    public final JsonParsingResult<J> parse(final String json) {
        return parse(new JsonParsingProcess(json));
    }

    public JsonParsingResult<J> parse(final JsonParsingProcess parsingProcess) {
        final JsonParsingStep step = getStep();
        final Optional<JsonParsingResult.JsonParsingResultError> result = step.execute(parsingProcess);
        if (result.isEmpty()) {
            try {
                return JsonParsingResult.value(create());
            } catch (final JsonParticleInstantiationException e) {
                return JsonParsingResult.error("An exception occurred during instantiation.", parsingProcess);
            }
        } else {
            return JsonParsingResult.error(result.get());
        }
    }

    protected abstract JsonParsingStep getStep();

    protected abstract J create() throws JsonParticleInstantiationException;

    protected String joinCharacters(final List<Character> characters) {
        return characters.stream()
                .map(Object::toString)
                .collect(Collectors.joining());
    }
}
