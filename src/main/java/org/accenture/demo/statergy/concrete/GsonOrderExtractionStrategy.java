package org.accenture.demo.statergy.concrete;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.accenture.demo.modal.Order;
import org.accenture.demo.statergy.OrderExtractionStrategy;

public class GsonOrderExtractionStrategy implements OrderExtractionStrategy {

    private final Gson gson;

    public GsonOrderExtractionStrategy(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Order extractOrder(JsonObject orderObj) {
        try {
            return gson.fromJson(orderObj, Order.class);
        } catch (JsonSyntaxException e) {
            System.err.println("Failed to deserialize Order: " + e.getMessage());
            return null;
        }
    }
}
