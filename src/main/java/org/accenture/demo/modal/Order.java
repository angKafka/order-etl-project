package org.accenture.demo.modal;

import java.io.Serializable;

public class Order implements Serializable {
    private final String order_id;
    private final String status;
    private final String product_id;
    private final String product_name;
    private final String customer_id;
    private final String created_at;

    public Order(OrderBuilder builder) {
        this.order_id = builder.order_id;
        this.status = builder.status;
        this.product_id = builder.product_id;
        this.product_name = builder.product_name;
        this.customer_id = builder.customer_id;
        this.created_at = builder.created_at;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getStatus() {
        return status;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }


    public String getCustomer_id() {
        return customer_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public static class OrderBuilder {
        private String order_id;
        private String status;
        private String product_id;
        private String product_name;
        private String customer_id;
        private String created_at;

        public OrderBuilder(String order_id, String status, String product_id,
                            String product_name
        ,String customer_id, String created_at) {
            this.order_id = order_id;
            this.status = status;
            this.product_id = product_id;
            this.product_name = product_name;
            this.customer_id = customer_id;
            this.created_at = created_at;
        }

        public OrderBuilder order_id(String order_id) {
            this.order_id = order_id;
            return this;
        }
        public OrderBuilder status(String status) {
            this.status = status;
            return this;
        }
        public OrderBuilder product_id(String product_id) {
            this.product_id = product_id;
            return this;
        }
        public OrderBuilder product_name(String product_name) {
            this.product_name = product_name;
            return this;
        }
        public OrderBuilder customer_id(String customer_id) {
            this.customer_id = customer_id;
            return this;
        }

        public OrderBuilder created_at(String created_at) {
            this.created_at = created_at;
            return this;
        }



        public Order build(){
            return new Order(this);
        }
    }
}
