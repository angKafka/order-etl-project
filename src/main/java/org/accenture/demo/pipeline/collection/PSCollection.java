package org.accenture.demo.pipeline.collection;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.values.PCollection;

import static org.accenture.demo.utilities.LogMessages.READING_MESSAGES_PUBSUB;

public class PSCollection {
    public static PCollection<PubsubMessage> pubsubMessagePCollection(Pipeline pipeline, String topic) {
        return pipeline.apply(
                READING_MESSAGES_PUBSUB,
                PubsubIO.readMessagesWithAttributesAndMessageId().fromTopic(topic)
        );
    }
}
