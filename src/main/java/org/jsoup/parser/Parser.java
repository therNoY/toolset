package org.jsoup.parser;

/**
 * 解析resp 需要的解析器
 */
public abstract class Parser {

    static JsonParser jsonParser = new JsonParser();

    public static JsonParser jsonParse() {
        return jsonParser;
    }


    /**
     * Create a new XML xmlParser. This xmlParser assumes no knowledge of the incoming tags and does not treat it as HTML,
     * rather creates a simple tree directly from the input.
     * @return a new simple XML xmlParser.
     */
    public static XmlParser xmlParser() {
        return new XmlParser(new XmlTreeBuilder());
    }
}
