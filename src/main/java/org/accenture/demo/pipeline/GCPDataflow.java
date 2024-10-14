package org.accenture.demo.pipeline;

import lombok.extern.slf4j.Slf4j;
import org.accenture.demo.modal.Order;
import org.accenture.demo.pipeline.collection.PSCollection;
import org.accenture.demo.pipeline.load.PSDatastore;
import org.accenture.demo.pipeline.transformer.PSTransform;
import org.accenture.demo.utilities.ETLPipeline;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.values.PCollection;

import java.util.List;

import static org.accenture.demo.utilities.GCPPubSubConstraints.TOPIC_ID;
import static org.accenture.demo.utilities.LogMessages.READING_MESSAGES_PUBSUB;

@Slf4j
public class GCPDataflow implements ETLPipeline {

    @Override
    public void etlPipeline(PipelineOptions options) {
        Pipeline pipeline = Pipeline.create(options);

        /*
        * @param messageCollection used for storing the collection
        */
        log.info(READING_MESSAGES_PUBSUB);
        PCollection<PubsubMessage> messageCollection = PSCollection.pubsubMessagePCollection(pipeline, TOPIC_ID);

        PCollection<List<Order>> extractedOrderList = PSTransform.transformOrderData(messageCollection);


        PSDatastore.loadToStore(extractedOrderList);

        pipeline.run();
    }
}
