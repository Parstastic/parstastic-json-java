package org.parstastic.jparstastic_json.parser;

import org.parstastic.jparstastic_json.node.JsonParticle;

public class JsonParsingResult<T extends JsonParticle> {
    public record JsonParsingResultError(String message, JsonParsingProcess parsingProcess) {

    }

    public static class JsonParsingResultNoSuchElementException extends Exception {

    }

    public static <T extends JsonParticle> JsonParsingResult<T> value(final T value) {
        return new JsonParsingResult<>(value);
    }

    public static <T extends JsonParticle> JsonParsingResult<T> error(final String message,
                                                                      final JsonParsingProcess parsingProcess) {
        return error(new JsonParsingResultError(message, parsingProcess));
    }

    public static <T extends JsonParticle> JsonParsingResult<T> error(final JsonParsingResultError error) {
        return new JsonParsingResult<>(error);
    }

    private final T value;
    private final JsonParsingResultError error;

    private JsonParsingResult(final T value) {
        this(value, null);
    }

    private JsonParsingResult(final JsonParsingResultError error) {
        this(null, error);
    }

    private JsonParsingResult(final T value, final JsonParsingResultError error) {
        super();
        this.value = value;
        this.error = error;
    }

    public boolean hasValue() {
        return this.value != null;
    }

    public T getValue() throws JsonParsingResultNoSuchElementException {
        return get(this.value);
    }

    public boolean hasError() {
        return this.error != null;
    }

    public JsonParsingResultError getError() throws JsonParsingResultNoSuchElementException {
        return get(this.error);
    }

    private <R> R get(final R field) throws JsonParsingResultNoSuchElementException {
        if (field == null) {
            throw new JsonParsingResultNoSuchElementException();
        } else {
            return field;
        }
    }
}
