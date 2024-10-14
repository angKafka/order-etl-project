package org.accenture.demo.utilities;

public interface LogMessages {
    String READING_MESSAGES_PUBSUB = "Reading messages from Google Cloud PubSub";
    String NO_MESSAGES_FOUND_PUBSUB = "Received null PubsubMessage.";
    String TRANSFORMING_INPUT_JSON_TO_XML = "Transforming JSON into XML";
    String LOAD_XML_TO_GCP_DATASTORE = "Loading converted XML to Google Cloud Datastore";

    String ORDER_LIST_IS_NULL = "Received an empty order list";
    String ERROR_SAVING_ORDER_ENTITY = "Error saving order entity";
}
