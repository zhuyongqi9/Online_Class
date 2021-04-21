package pri.kirin.onlineclass.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;

/**
 * 微信支付工具类 ,map => xml, xml => map, 生成签名
 */
public class WechatPayUtils {
    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            WechatPayUtils.getLogger().warn("Invalid XML, can not convert to map. Error message: {}. XML content: {}", ex.getMessage(), strXML);
            throw ex;
        }

    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }


    /**
     * 生成微信签名
     * @return
     */
    public static String createSign(SortedMap<String,String> parms,String key){
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String,String >> iterator = parms.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String ,String> entry = iterator.next();
            String k = entry.getKey();
            String v = entry.getValue();
            if(v != null && v != "" && k != "sign" && k != "key"){
                stringBuilder.append(k + "=" + v + "&");
            }
        }

        stringBuilder.append("key=").append(key);
        String sign = CommonUtils.geneMD5(stringBuilder.toString()).toUpperCase();
        return sign;
    }


    public static boolean checkSign(SortedMap<String,String> parms,String key){
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String,String >> iterator = parms.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String ,String> entry = iterator.next();
            String k = entry.getKey();
            String v = entry.getValue();
            if(v != null && v != "" && k != "sign" && k != "key"){
                stringBuilder.append(k + "=" + v + "&");
            }
        }

        stringBuilder.append("key=").append(key);
        String sign = CommonUtils.geneMD5(stringBuilder.toString()).toUpperCase();

        String wechatPaySign = parms.get("sign").toUpperCase();

        return sign.equals(wechatPaySign);
    }


    public static SortedMap<String,String> getSortedMap(Map<String,String> map){
        SortedMap<String,String> sortedMap = new TreeMap<>();
        Iterator<String> it = map.keySet().iterator();

        while (it.hasNext()){
            String key = it.next();
            String value = map.get(key);
            String temp = "";
            if(value!= null) temp=value.trim();
            sortedMap.put(key,temp);
        }
        return sortedMap;
    }

    public static boolean isCorrectedSign(SortedMap<String,String> parms,String key){
        String sign = createSign(parms,key);
        String wechatPaySign = parms.get("sign").toUpperCase();
        return sign.equals(wechatPaySign);
    }
    /**
     * 获得日志
     * @return
     */
    public static Logger getLogger() {
        Logger logger = LoggerFactory.getLogger("wxpay java sdk");
        return logger;
    }

}
