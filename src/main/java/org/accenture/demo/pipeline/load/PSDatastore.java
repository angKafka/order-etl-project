package org.accenture.demo.pipeline.load;

import org.accenture.demo.gcp.output.GCPPubSubMessages;
import org.accenture.demo.modal.Order;
import org.accenture.demo.utilities.LogMessages;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import java.util.List;

public class PSDatastore {
    public static String loadToStore(PCollection<List<Order>> inputCollection) {
         inputCollection.apply(
                LogMessages.LOAD_XML_TO_GCP_DATASTORE,
                ParDo.of(new GCPPubSubMessages()));

         return "Loaded in Google Cloud Datastore";
    }
}
