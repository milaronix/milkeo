package com.example.milaronix.milkeo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by milaronix on 31/05/15.
 */
public class ParsingClass  extends DefaultHandler {
    ArrayList<String> pin = new ArrayList<String>();
    ArrayList<String> data = new ArrayList<String>();

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equalsIgnoreCase("data")) {
            tempStore = "";
        } else if (localName.equalsIgnoreCase("descripition")) {
            tempStore = "";
        }else{
            tempStore = "";
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        super.endElement(uri, localName, qName);
        if (localName.equalsIgnoreCase("data")) {
            data.add(tempStore);
        } else if (localName.equalsIgnoreCase("description")) {
            pin.add(tempStore);
        }
        tempStore = "";
    }

    private String tempStore = "";

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        tempStore += new String(ch, start, length);
    }
}
