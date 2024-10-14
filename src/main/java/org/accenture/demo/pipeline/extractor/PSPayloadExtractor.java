package org.accenture.demo.pipeline.extractor;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.accenture.demo.exception.PubSubExceptions;
import org.accenture.demo.modal.Order;
import org.accenture.demo.statergy.OrderExtractionStrategy;
import org.accenture.demo.statergy.concrete.GsonOrderExtractionStrategy;
import org.accenture.demo.utilities.LogMessages;
import org.accenture.demo.utilities.LoggerUtil;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.DoFn;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PSPayloadExtractor extends DoFn<PubsubMessage, List<Order>> {
    private final static OrderExtractionStrategy orderExtractionStrategy = new GsonOrderExtractionStrategy(new Gson());


    @ProcessElement
    public void processElement(ProcessContext c) {
        PubsubMessage message = c.element();

        if(message == null){
            LoggerUtil.logError(LogMessages.NO_MESSAGES_FOUND_PUBSUB);
            throw new PubSubExceptions(LogMessages.NO_MESSAGES_FOUND_PUBSUB);
        }

        String payloadMessage = new String(message.getPayload(), StandardCharsets.UTF_8);
        LoggerUtil.logInfo("Received payload: " + payloadMessage);

        try{
            List<Order> orderList = extractOrdersFromPubsubMessages(payloadMessage);
            c.output(orderList);
        }catch (Exception e){
            LoggerUtil.logError("Error processing PubSub message: " + e.getMessage());
        }
    }


    private static List<Order> extractOrdersFromPubsubMessages(String message) {
        JsonObject jsonObject = new Gson().fromJson(message, JsonObject.class);
        JsonArray ordersArray = jsonObject.getAsJsonArray("Order");

        List<Order> orders = new ArrayList<>();
        for (JsonElement orderElement : ordersArray) {
            Order order = orderExtractionStrategy.extractOrder(orderElement.getAsJsonObject());
            if (order != null) {
                orders.add(order);
            }
        }
        return orders;
    }
}
