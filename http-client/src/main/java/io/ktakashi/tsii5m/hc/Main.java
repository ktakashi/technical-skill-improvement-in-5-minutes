package io.ktakashi.tsii5m.hc;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String... args) throws Exception {
        var node = request();
        System.out.println(node);
    }

    public static List<String> request() throws IOException, InterruptedException, ParserConfigurationException, SAXException, XPathExpressionException {
        var httpRequest = HttpRequest.newBuilder().GET().uri(URI.create("https://httpbin.org/xml")).build();
        var client = HttpClient.newHttpClient();
        var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
        var document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(response.body());
        var xPath = XPathFactory.newInstance().newXPath();
        var nodeList = (NodeList)xPath.compile("/slideshow/slide/title").evaluate(document, XPathConstants.NODESET);
        var result = new ArrayList<String>();
        for (var i = 0; i < nodeList.getLength(); i++) {
            var node = nodeList.item(i);
            result.add(node.getTextContent());
        }
        return result;
    }
}
