package org.parstastic.jparstastic_json.parser.steps;

public abstract class LoopStep extends JsonParsingStep {
    protected final JsonParsingStep instruction;

    protected LoopStep(final JsonParsingStep instruction) {
        super();
        this.instruction = instruction;
    }
}
