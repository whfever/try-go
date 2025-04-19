package org.example.imitate.mybatis.parsing;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;

public class XPathParser {
    private final Document document;
    private final XPath xpath;

    public XPathParser(InputStream inputStream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(inputStream));
            xpath = XPathFactory.newInstance().newXPath();
        } catch (Exception e) {
            throw new RuntimeException("Error creating XPathParser", e);
        }
    }

    public Object evaluate(String expression, javax.xml.namespace.QName returnType) {
        try {
            return xpath.evaluate(expression, document, returnType);
        } catch (Exception e) {
            throw new RuntimeException("Error evaluating XPath expression: " + expression, e);
        }
    }
} 