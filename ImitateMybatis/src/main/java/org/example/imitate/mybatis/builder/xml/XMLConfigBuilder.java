package org.example.imitate.mybatis.builder.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.example.imitate.mybatis.config.Configuration;
import org.example.imitate.mybatis.mapping.MappedStatement;
import org.example.imitate.mybatis.mapping.MappedStatement.SqlCommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.imitate.mybatis.parsing.XPathParser;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {
    private static final Logger logger = LoggerFactory.getLogger(XMLConfigBuilder.class);
    private final Configuration configuration;
    private final XPathParser parser;

    public XMLConfigBuilder(InputStream inputStream) {
        this.configuration = new Configuration();
        this.parser = new XPathParser(inputStream);
    }

    public Configuration parse() {
        try {
            // 解析数据源配置
            Node dataSourceNode = (Node) parser.evaluate("/configuration/environments/environment/dataSource", XPathConstants.NODE);
            Properties props = new Properties();
            NodeList propertyNodes = dataSourceNode.getChildNodes();
            for (int i = 0; i < propertyNodes.getLength(); i++) {
                Node propertyNode = propertyNodes.item(i);
                if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                    String name = propertyNode.getAttributes().getNamedItem("name").getNodeValue();
                    String value = propertyNode.getAttributes().getNamedItem("value").getNodeValue();
                    props.setProperty(name, value);
                }
            }
            configuration.setDriver(props.getProperty("driver"));
            configuration.setUrl(props.getProperty("url"));
            configuration.setUsername(props.getProperty("username"));
            configuration.setPassword(props.getProperty("password"));

            // 解析Mapper配置
            NodeList mapperNodes = (NodeList) parser.evaluate("/configuration/mappers/mapper", XPathConstants.NODESET);
            for (int i = 0; i < mapperNodes.getLength(); i++) {
                Node mapperNode = mapperNodes.item(i);
                String resource = mapperNode.getAttributes().getNamedItem("resource").getNodeValue();
                logger.info("Parsing mapper XML: {}", resource);
                parseMapper(resource);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing XML configuration", e);
        }
        return configuration;
    }

    private void parseMapper(String resource) {
        try {
            // 从类路径加载资源
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
            if (inputStream == null) {
                throw new RuntimeException("Could not find resource: " + resource);
            }
            
            XPathParser parser = new XPathParser(inputStream);
            
            // 解析SQL语句
            NodeList sqlNodes = (NodeList) parser.evaluate("/mapper/*[self::select or self::insert or self::update or self::delete]", XPathConstants.NODESET);
            logger.info("Found {} SQL nodes in mapper XML: {}", sqlNodes.getLength(), resource);
            
            for (int i = 0; i < sqlNodes.getLength(); i++) {
                Node sqlNode = sqlNodes.item(i);
                String id = sqlNode.getAttributes().getNamedItem("id").getNodeValue();
                String resultType = sqlNode.getAttributes().getNamedItem("resultType") != null ? 
                    sqlNode.getAttributes().getNamedItem("resultType").getNodeValue() : null;
                String resultMap = sqlNode.getAttributes().getNamedItem("resultMap") != null ? 
                    sqlNode.getAttributes().getNamedItem("resultMap").getNodeValue() : null;
                String sql = sqlNode.getTextContent().trim();
                
                logger.info("Parsing SQL node - id: {}, resultType: {}, resultMap: {}, sql: {}", 
                    id, resultType, resultMap, sql);
                
                Class<?> resultTypeClass = null;
                if (resultType != null) {
                    resultTypeClass = Class.forName(resultType);
                }
                
                SqlCommandType commandType = getSqlCommandType(sqlNode.getNodeName());
                MappedStatement mappedStatement = new MappedStatement(id, sql, resultTypeClass, commandType);
                configuration.addMappedStatement(id, mappedStatement);
                logger.info("Added mapped statement for id: {}", id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing mapper XML: " + resource, e);
        }
    }

    private SqlCommandType getSqlCommandType(String elementName) {
        switch (elementName.toUpperCase()) {
            case "SELECT":
                return SqlCommandType.SELECT;
            case "INSERT":
                return SqlCommandType.INSERT;
            case "UPDATE":
                return SqlCommandType.UPDATE;
            case "DELETE":
                return SqlCommandType.DELETE;
            default:
                throw new RuntimeException("Unknown SQL command type: " + elementName);
        }
    }

    private Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.error("Class not found: " + className, e);
            throw new RuntimeException("Class not found: " + className, e);
        }
    }
} 