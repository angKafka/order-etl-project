package org.accenture.demo.statergy;

import com.google.gson.JsonObject;
import org.accenture.demo.modal.Order;


public interface OrderExtractionStrategy {
    Order extractOrder(JsonObject orderObj);
}
