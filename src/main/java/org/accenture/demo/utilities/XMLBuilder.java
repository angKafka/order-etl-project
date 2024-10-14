package org.accenture.demo.utilities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XMLBuilder {
    private static final Logger LOGGER = Logger.getLogger(XMLBuilder.class.getName());

    private final Document document;
    private final Element rootElement;

    /**
     * Constructor that initializes the XML document with a root element.
     *
     * @param rootTagName Name of the root element.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created.
     */
    public XMLBuilder(String rootTagName) throws ParserConfigurationException {
        this.document = createNewDocument();
        this.rootElement = document.createElement(rootTagName);
        document.appendChild(rootElement);
        LOGGER.log(Level.INFO, "Root element '{0}' created and appended to the document.", rootTagName);
    }

    /**
     * Adds a child element to the root element.
     *
     * @param tagName The name of the child element.
     * @param value   The text content of the child element.
     */
    public void addChildToRoot(String tagName, String value) {
        Element childElement = document.createElement(tagName);
        childElement.setTextContent(value);
        rootElement.appendChild(childElement);
        LOGGER.log(Level.INFO, "Added child element '{0}' with value '{1}' to root.", new Object[]{tagName, value});
    }

    /**
     * Adds a child element to a specified parent element.
     *
     * @param parent  The parent element.
     * @param tagName The name of the child element.
     * @param value   The text content of the child element.
     */
    public void addChildToElement(Element parent, String tagName, String value) {
        if (parent == null) {
            LOGGER.log(Level.WARNING, "Parent element is null. Cannot add child element '{0}'.", tagName);
            return;
        }
        Element childElement = document.createElement(tagName);
        childElement.setTextContent(value);
        parent.appendChild(childElement);
        LOGGER.log(Level.INFO, "Added child element '{0}' with value '{1}' to parent '{2}'.",
                new Object[]{tagName, value, parent.getTagName()});
    }

    /**
     * Creates a new child element under the root element.
     *
     * @param tagName The name of the new element.
     * @return The created element.
     */
    public Element createAndAppendElementToRoot(String tagName) {
        Element newElement = document.createElement(tagName);
        rootElement.appendChild(newElement);
        LOGGER.log(Level.INFO, "Created and appended new element '{0}' to root.", tagName);
        return newElement;
    }

    /**
     * Gets the constructed XML document.
     *
     * @return The XML document.
     */
    public Document getDocument() {
        LOGGER.log(Level.INFO, "Returning the constructed XML document.");
        return document;
    }

    /**
     * Creates a new Document instance.
     *
     * @return A new XML Document.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created.
     */
    private Document createNewDocument() throws ParserConfigurationException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            LOGGER.log(Level.INFO, "DocumentBuilder created successfully.");
            return builder.newDocument();
        } catch (ParserConfigurationException e) {
            LOGGER.log(Level.SEVERE, "Error while creating DocumentBuilder: {0}", e.getMessage());
            throw e;
        }
    }
}
