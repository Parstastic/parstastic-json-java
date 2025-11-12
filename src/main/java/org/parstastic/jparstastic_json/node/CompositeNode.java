package org.parstastic.jparstastic_json.node;

import java.util.List;

public abstract class CompositeNode<P extends JsonParticle> extends JsonNode {
    /**
     * {@link List} containing all the elements of the <code>JSON</code> node.
     */
    private final List<P> elements;

    protected CompositeNode(final List<P> elements) {
        super();
        this.elements = elements;
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
