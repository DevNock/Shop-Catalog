package com.google.shopcatalog;

import android.util.Log;

import com.google.shopcatalog.model.ModelCategoryOffers;
import com.google.shopcatalog.model.ModelOffer;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * Created by Sergey on 11.03.2016.
 */
public class XMLParser {

    private static final String XML_CATEGORY = "category";
    private static final String XML_ID = "id";
    private static final String XML_OFFER = "offer";

    private static final String XML_NAME = "name";
    private static final String XML_PRICE = "price";
    private static final String XML_DESCRIPTION = "description";
    private static final String XML_PICTURE = "picture";
    private static final String XML_CATEGORY_ID = "categoryId";
    private static final String XML_PARAM = "param";
    private static final String XML_WEIGHT = "Вес";

    private static final String TAG = XMLParser.class.getSimpleName();

    public static ArrayList<ModelCategoryOffers> parseItems(ArrayList<ModelCategoryOffers> items,
                                                            String xmlString) {
        try {
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(xmlString)));
            doc.getDocumentElement().normalize();
            Log.d(TAG, "Root element :"
                    + doc.getDocumentElement().getNodeName());
            // Parsing categories of items
            NodeList nodeList = doc.getElementsByTagName(XML_CATEGORY);
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node nNode = nodeList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    int id =  Integer.parseInt(eElement.getAttribute(XML_ID));
                    String name = eElement.getTextContent();
                    items.add(new ModelCategoryOffers(id, name));
                }
            }
            // Parsing each of items
            nodeList = doc.getElementsByTagName(XML_OFFER);
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                ModelOffer offer;
                Node nNode = nodeList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    offer = new ModelOffer(Integer.parseInt(eElement.getAttribute(XML_ID)));
                    offer.setCategoryId(Integer.parseInt(eElement
                            .getElementsByTagName(XML_CATEGORY_ID)
                            .item(0)
                            .getTextContent()));
                    offer.setName(eElement
                            .getElementsByTagName(XML_NAME)
                            .item(0)
                            .getTextContent());
                    offer.setPrice(eElement
                            .getElementsByTagName(XML_PRICE)
                            .item(0)
                            .getTextContent());
                    offer.setDescription(eElement
                            .getElementsByTagName(XML_DESCRIPTION)
                            .item(0)
                            .getTextContent());
                    offer.setUrlPhoto(eElement
                            .getElementsByTagName(XML_PICTURE)
                            .item(0)
                            .getTextContent());
                    NodeList listParam = eElement.getElementsByTagName(XML_PARAM);
                    for (int i = 0; i < listParam.getLength(); i++) {
                        Node nodeParam = listParam.item(i);
                        if (nodeParam.getNodeType() == Node.ELEMENT_NODE) {
                            Element elParam = (Element) nodeParam;
                            if (elParam.getAttribute(XML_NAME).equals(XML_WEIGHT)) {
                                offer.setWeight(elParam.getTextContent());
                            }
                        }
                    }
                    for (ModelCategoryOffers categoryItems : items) {
                        if (categoryItems.getId() == offer.getCategoryId()) {
                            categoryItems.addOffer(offer);
                            break;
                        }
                    }
                }
            }
            return items;
        } catch (Exception e) {
            Log.e(TAG, "Failed to parse items: " + e.getMessage());
            return null;
        }
    }
}
