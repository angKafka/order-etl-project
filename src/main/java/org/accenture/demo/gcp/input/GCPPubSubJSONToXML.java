package org.accenture.demo.gcp.input;

import com.google.cloud.datastore.*;
import org.accenture.demo.modal.Order;
import org.accenture.demo.utilities.LoggerUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.ArrayList;
import java.util.List;

public class GCPPubSubJSONToXML {
    public static void fetchDataForPrinting(String projectId, String orderCode){
        try{
            Datastore datastore = DatastoreOptions.newBuilder().setProjectId(projectId).build().getService();


            //Creating the query for User Event Kind
            Query<Entity> query = Query.newEntityQueryBuilder()
                    .setKind("Order")
                    .setFilter(StructuredQuery.PropertyFilter.eq("orderCode", orderCode)).build();


            //Fetching Entities
            QueryResults<Entity> results = datastore.run(query);


            //Convert QueryResult to List
            List<Entity> entities = new ArrayList<>();
            while(results.hasNext()){
                entities.add(results.next());
            }

            //Create a new XML Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder document = factory.newDocumentBuilder();
            Document doc = document.newDocument();

            //Creating the root Element
            Element root = doc.createElement("Order");
            doc.appendChild(root);

            for(Entity entity : entities){
                Order order = new Order.OrderBuilder(
                        entity.getString("order_id"),
                        entity.getString("status"),
                        entity.getString("product_id"),
                        entity.getString("product_name"),
                        entity.getString("customer_id"),
                        entity.getString("created_at")
                ).build();

                //Creating User element
                Element userElement = doc.createElement("Order");
                root.appendChild(userElement);

                //Add user details as child element
                createChildElement(doc, userElement, "order_id", order.getOrder_id());
                createChildElement(doc, userElement, "status", order.getStatus());
                createChildElement(doc, userElement, "product_id", order.getProduct_id());
                createChildElement(doc, userElement, "product_name", order.getProduct_name());
                createChildElement(doc, userElement, "customer_id", order.getCustomer_id());
                createChildElement(doc, userElement, "created_at", order.getCreated_at());
            }

            //Print the XML to the console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            LoggerUtil.logError(e.getMessage());
        }
    }

    public static void createChildElement(Document doc, Element parentElement, String tagName, String value){
        Element childElement = doc.createElement(tagName);
        childElement.setTextContent(value);
        parentElement.appendChild(childElement);
    }
}
