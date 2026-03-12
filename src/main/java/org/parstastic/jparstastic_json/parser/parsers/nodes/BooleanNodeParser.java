package org.parstastic.jparstastic_json.parser.parsers.nodes;

import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.nodes.BooleanNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.steps.*;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BooleanNodeParser extends JsonNodeParser<BooleanNode> {
    private BooleanNode.BooleanValue value;

    public BooleanNodeParser() {
        super();
    }

    @Override
    public boolean canParse(final JsonParsingProcess parsingProcess) {
        return Arrays.stream(BooleanNode.BooleanValue.values())
                .anyMatch(v -> parsingProcess.startsWith(v.toString()));
    }

    @Override
    protected JsonParsingStep getStep() {
        return OrStep.elseError(
                createParsersMap()
        );
    }

    private Map<JsonParsingStep, Predicate<JsonParsingProcess>> createParsersMap() {
        return Arrays.stream(BooleanNode.BooleanValue.values())
                .map(this::createEntry)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<JsonParsingStep, Predicate<JsonParsingProcess>> createEntry(final BooleanNode.BooleanValue b) {
        final String stringValue = b.toString();
        return new AbstractMap.SimpleEntry<>(
                new BlockStep(
                        new ForLoopStep(
                                new ParseCharacterStep(c -> true),
                                stringValue.length()
                        ),
                        new ExportStep(() -> {
                            this.value = b;
                            return true;
                        })
                ),
                p -> p.startsWith(stringValue)
        );
    }

    @Override
    protected BooleanNode create() throws JsonParticleInstantiationException {
        return BooleanNode.VALUES.stream()
                .filter(b -> b.hasValue(this.value))
                .findFirst()
                .orElseThrow(() -> new JsonParticleInstantiationException("There was no matching BooleanNode found."));
    }
}
