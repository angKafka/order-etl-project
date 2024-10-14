package org.accenture.demo.gcp.output;

import org.accenture.demo.factory.OrderEntityFactory;
import org.accenture.demo.gcp.input.GCPPubSubJSONToXML;
import org.accenture.demo.modal.Order;
import org.accenture.demo.utilities.LogMessages;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import com.google.cloud.datastore.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GCPPubSubMessages extends DoFn<List<Order>, KV<String, Order>> {
    private static final Logger LOG = Logger.getLogger(GCPPubSubMessages.class.getName());
    @ProcessElement
    public void processElement(ProcessContext c) {
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        OrderEntityFactory orderEntityFactory = new OrderEntityFactory(datastore);
        List<Order> orders = c.element();

        if (orders == null) {
            LOG.log(Level.WARNING, LogMessages.ORDER_LIST_IS_NULL);
            return;
        }


        try{
            for (Order order : orders) {
                String orderID = UUID.randomUUID().toString();
                Entity orderEntity = orderEntityFactory.createOrderEntity(
                        orderID,
                        order.getStatus(),
                        order.getProduct_id(),
                        order.getProduct_name(),
                        order.getCustomer_id(),
                        order.getCreated_at()
                );

                datastore.put(orderEntity);

                c.output(KV.of(orderID, order));
            }
            String projectId = "traindev-gcp";
            String orderCode = "O1";
            GCPPubSubJSONToXML.fetchDataForPrinting(projectId, orderCode);

        }catch(DatastoreException dse){
            LOG.log(Level.SEVERE, LogMessages.ERROR_SAVING_ORDER_ENTITY, dse);
        }catch (Exception e){
            LOG.log(Level.SEVERE,"Unexpected error processing PubSub message: {0}", e);
        }
    }
}
