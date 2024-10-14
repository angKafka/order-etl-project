package org.accenture.demo.pipeline.transformer;

import org.accenture.demo.modal.Order;
import org.accenture.demo.pipeline.extractor.PSPayloadExtractor;
import org.apache.beam.sdk.coders.ListCoder;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import static org.accenture.demo.utilities.LogMessages.TRANSFORMING_INPUT_JSON_TO_XML;
import java.util.List;

public class PSTransform {
    public static PCollection<List<Order>> transformOrderData(PCollection<PubsubMessage> messagePCollection) {
        return messagePCollection.apply(
                TRANSFORMING_INPUT_JSON_TO_XML,
                ParDo.of(new PSPayloadExtractor())
        ).setCoder(ListCoder.of(SerializableCoder.of(Order.class)));
    }
}
