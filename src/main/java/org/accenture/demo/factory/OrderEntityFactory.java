package org.accenture.demo.factory;
import com.google.cloud.datastore.*;


public class OrderEntityFactory {
    private final Datastore datastore;
    private static final String ENTITY_KIND = "Order";

    public OrderEntityFactory(Datastore datastore) {
        this.datastore = datastore;
    }

    public Entity createOrderEntity(String orderId, String status, String productId, String productName, String customerId, String createdAt) {
        Key key = datastore.newKeyFactory().setKind(ENTITY_KIND).newKey(orderId);

        return Entity.newBuilder(key)
                .set("order_id", orderId)
                .set("status", status)
                .set("product_id", productId)
                .set("product_name", productName)
                .set("customer_id", customerId)
                .set("created_at", createdAt)
                .build();
    }
}
