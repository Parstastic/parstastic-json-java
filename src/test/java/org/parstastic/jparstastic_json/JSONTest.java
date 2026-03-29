package org.parstastic.jparstastic_json;

import org.junit.jupiter.api.Test;
import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.JsonValue;
import org.parstastic.jparstastic_json.node.Whitespace;
import org.parstastic.jparstastic_json.node.nodes.StringNode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JSONTest {
    @Test
    void stringify() throws JsonParticleInstantiationException {
        assertThat(JSON.stringify(new StringNode("test")))
                .isEqualTo("\"test\"");
    }

    @Test
    void parse_success() throws JsonParticleInstantiationException {
        assertThat(JSON.parse("\"test\""))
                .usingRecursiveComparison()
                .isEqualTo(new JsonValue(
                        new Whitespace(""),
                        new StringNode("test"),
                        new Whitespace("")
                ));
    }

    @Test
    void parse_failure() {
        assertThatThrownBy(() -> JSON.parse("\"test"))
                .isInstanceOf(RuntimeException.class);
    }
}