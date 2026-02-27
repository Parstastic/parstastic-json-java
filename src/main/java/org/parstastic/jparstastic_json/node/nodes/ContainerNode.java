package org.parstastic.jparstastic_json.node.nodes;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.StringifyOptions;
import org.parstastic.jparstastic_json.node.Whitespace;

import java.util.List;

public abstract class ContainerNode<P extends JsonParticle> extends JsonNode {
    protected final Whitespace whitespace;
    /**
     * {@link List} containing all the elements of the <code>JSON</code> node.
     */
    protected final List<P> elements;

    private ContainerNode(final Whitespace whitespace, final List<P> elements)
            throws JsonParticleInstantiationException {
        super();

        if ((whitespace == null && elements == null) || (whitespace != null && elements != null)) {
            throwInstantiationException(
                    "Either the given whitespace or the given elements must be \"null\", not both or neither."
            );
        }

        this.whitespace = whitespace;
        this.elements = elements;
    }

    protected ContainerNode(final Whitespace whitespace) throws JsonParticleInstantiationException {
        this(whitespace, null);
    }

    protected ContainerNode(final List<P> elements) throws JsonParticleInstantiationException {
        this(null, elements);
    }

    @Override
    public String stringify(final StringifyOptions options) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(options.getIndentation()).append(getDelimiterStart()).append(options.getLineBreak());
        for (int i = 0; i < this.elements.size(); i++) {
            stringBuilder.append(this.elements.get(i).stringify(options.withIncreasedIndentationLevel()));
            if (i < this.elements.size() - 1) {
                stringBuilder.append(getDelimiterElements());
            }
            stringBuilder.append(options.getLineBreak());
        }
        stringBuilder.append(options.getIndentation()).append(getDelimiterEnd());
        return stringBuilder.toString();
    }

    public abstract char getDelimiterStart();

    public abstract char getDelimiterEnd();

    public abstract char getDelimiterElements();
}
