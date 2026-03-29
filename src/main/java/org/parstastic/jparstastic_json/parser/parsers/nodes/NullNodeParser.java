package org.parstastic.jparstastic_json.parser.parsers.nodes;

import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.nodes.NullNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.steps.BlockStep;
import org.parstastic.jparstastic_json.parser.steps.JsonParsingStep;
import org.parstastic.jparstastic_json.parser.steps.ParseCharacterStep;

import java.util.List;

public class NullNodeParser extends JsonNodeParser<NullNode> {
    @Override
    public boolean canParse(final JsonParsingProcess parsingProcess) {
        return parsingProcess.startsWith(NullNode.STRING_VALUE);
    }

    @Override
    protected JsonParsingStep getStep() {
        return new BlockStep(
                createValidation()
        );
    }

    private List<ParseCharacterStep> createValidation() {
        return NullNode.STRING_VALUE.chars()
                .mapToObj(i -> new ParseCharacterStep((char) i))
                .toList();
    }

    @Override
    protected NullNode create() throws JsonParticleInstantiationException {
        return NullNode.NULL_NODE;
    }
}
