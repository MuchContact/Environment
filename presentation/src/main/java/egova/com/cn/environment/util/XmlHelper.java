package egova.com.cn.environment.util;

import android.util.Log;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import cn.com.im.basetlibrary.util.DateUtil;

public class XmlHelper {
	/** 日志打印的Tag,便于过滤日志. */
	private static final String TAG = "[XmlHelper]";
	private static final Pattern P1_= Pattern.compile(SpecialChar.Egova_1.toString(), Pattern.LITERAL);
	private static final Pattern P2_= Pattern.compile(SpecialChar.Egova_2.toString(), Pattern.LITERAL);
	private static final Pattern P3_= Pattern.compile(SpecialChar.Egova_3.toString(), Pattern.LITERAL);
	private static final Pattern P4_= Pattern.compile(SpecialChar.Egova_4.toString(), Pattern.LITERAL);
	private static final Pattern P5_= Pattern.compile(SpecialChar.Egova_5.toString(), Pattern.LITERAL);
	private static final Pattern P6_= Pattern.compile(SpecialChar.Egova_6.toString(), Pattern.LITERAL);
	private static final Pattern P7_= Pattern.compile(SpecialChar.Egova_7.toString(), Pattern.LITERAL);
	private static final Pattern P8_= Pattern.compile(SpecialChar.Egova_8.toString(), Pattern.LITERAL);
	private static final Pattern P9_= Pattern.compile(SpecialChar.Egova_9.toString(), Pattern.LITERAL);
	
	private static final Pattern P1= Pattern.compile("&", Pattern.LITERAL);
	private static final Pattern P2= Pattern.compile("<", Pattern.LITERAL);
	private static final Pattern P3= Pattern.compile(">", Pattern.LITERAL);
	private static final Pattern P4= Pattern.compile("\"", Pattern.LITERAL);
	private static final Pattern P5= Pattern.compile("'", Pattern.LITERAL);
	private static final Pattern P6= Pattern.compile(";", Pattern.LITERAL);
	private static final Pattern P7= Pattern.compile(",", Pattern.LITERAL);
	private static final Pattern P8= Pattern.compile("(", Pattern.LITERAL);
	private static final Pattern P9= Pattern.compile(")", Pattern.LITERAL);

	public static String getNodeContent(String xml, String nodeName) {
		return "<?xml version=\"1.0\" encoding=\"gb2312\"?>\n"
				+ xml.substring(
						xml.indexOf("<" + nodeName + ">"),
						xml.indexOf("</" + nodeName + ">") + 3
								+ nodeName.length());
	}

	/**
	 * 从Element里取得标签名为tagName的节点的值
	 * 
	 * @param ele
	 * @param tagName
	 * @return 节点值
	 */
	public static String getNodeValue(Element ele, String tagName) {
		NodeList nlField = ele.getElementsByTagName(tagName);
		if (nlField != null && nlField.getLength() > 0
				&& nlField.item(0).hasChildNodes()) {
			return ParseEnityToChar(nlField.item(0).getFirstChild()
					.getNodeValue());
		} else {
			return "";
		}
	}
	
    
	/**
	 * 从Element里取得标签名为tagName的节点的值
	 * 
	 * @param ele
	 * @param tagName
	 * @param defaultValue
	 * @return 节点值
	 */
	public static double getNodeDouble(Element ele, String tagName,
									   double defaultValue) {
		String strValue = getNodeValue(ele, tagName);
		if (!"".equals(strValue)) {
			try {
				return Double.parseDouble(strValue);
			} catch (NumberFormatException e) {
				Log.e(TAG, "getNodeDouble[" + strValue + "]");
			}
		}
		return defaultValue;
	}

	/**
	 * 从Element里取得标签名为tagName的节点的值
	 * 
	 * @param ele
	 * @param tagName
	 * @param defaultValue
	 * @return 节点值
	 */
	public static float getNodeFloat(Element ele, String tagName,
									 float defaultValue) {
		String strValue = getNodeValue(ele, tagName);
		if (!"".equals(strValue)) {
			try {
				return Float.parseFloat(strValue);
			} catch (NumberFormatException e) {
				Log.e(TAG, "getNodeFloat[" + strValue + "]");
			}
		}
		return defaultValue;
	}

	/**
	 * 从Element里取得标签名为tagName的节点的值
	 * 
	 * @param ele
	 * @param tagName
	 * @param defaultValue
	 * @return 节点值
	 */
	public static int getNodeInt(Element ele, String tagName, int defaultValue) {
		String strValue = getNodeValue(ele, tagName);
		if (!"".equals(strValue)) {
			try {
				return Integer.parseInt(strValue);
			} catch (NumberFormatException e) {
				Log.e(TAG, "getNodeFloat[" + strValue + "]");
			}
		}
		return defaultValue;
	}

	/**
	 * 从Element里取得标签名为tagName的节点的值
	 * 
	 * @param ele
	 * @param tagName
	 * @param defaultValue
	 * @return 节点值
	 */
	public static Date getNodeDate(Element ele, String tagName,
								   Date defaultValue) {
		Date result = defaultValue;
		String strValue = getNodeValue(ele, tagName);
		Date d = DateUtil.parse(strValue,DateUtil.FORMAT_YMDHMS);
		if(null !=d){
			return d;
		}
//		if (strValue != null && !"".equals(strValue)) {
//			SimpleDateFormat format = new SimpleDateFormat(
//					Format.DATA_FORMAT_YMDHMS_EN.toString());
//			try {
//				result = format.parse(strValue);
//			} catch (ParseException ex) {
//				Log.e(TAG, "getNodeDate[" + strValue + "]");
//				result = defaultValue;
//			}
//		}
		return result;
	}
	
	/**
	 * 解析strValue
	 * 
	 * @param strValue
	 * @param defautValue 默认值
	 * @return
	 */
	public static int getInt(String strValue, int defautValue) {
		try {
			return Integer.parseInt(strValue);
		} catch (NumberFormatException e) {
			Log.e(TAG, "getNodeDouble[" + strValue + "]");
		}
		
		return defautValue;
	}

	/**
	 * 解析strValue
	 * 
	 * @param strValue
	 * @param defautValue 默认值
	 * @return
	 */
	public static Date getDate(String strValue, Date defautValue) {
		Date d = DateUtil.parse(strValue, DateUtil.FORMAT_YMDHMS);
		if(null !=d){
			return d;
		}
//		SimpleDateFormat format = new SimpleDateFormat(Format.DATA_FORMAT_YMDHMS_EN.toString());
//		try {
//			return format.parse(strValue);
//		} catch (ParseException ex) {
//			Log.e(TAG, "getNodeDate[" + strValue + "]");
//		}
		
		return defautValue;
	}

	/**
	 * 解析strValue
	 * 
	 * @param strValue
	 * @param defautValue 默认值
	 * @return
	 */
	public static Double getDouble(String strValue, double defautValue) {
		try {
			return Double.parseDouble(strValue);
		} catch (NumberFormatException e) {
			Log.e(TAG, "getNodeDouble[" + strValue + "]");
		}
		
		return defautValue;
	}

	/**
	 * 处理特殊字符(恢复转义，类似解密过程):Entity to Character
	 * 
	 * @param strXml
	 * @return
	 */
	public static String ParseEnityToChar(String strXml) {
		if (StringUtils.isEmpty(strXml) || !strXml.contains("*#egova_")) {
			return strXml;
		}

		strXml = P1_.matcher(strXml).replaceAll(Matcher.quoteReplacement("&"));
		strXml = P2_.matcher(strXml).replaceAll(Matcher.quoteReplacement("<"));
		strXml = P3_.matcher(strXml).replaceAll(Matcher.quoteReplacement(">"));
		strXml = P4_.matcher(strXml).replaceAll(Matcher.quoteReplacement("\""));
		strXml = P5_.matcher(strXml).replaceAll(Matcher.quoteReplacement("'"));
		
		return strXml;
	}
	
	/**
     * 处理特殊字符 处理Xml的特殊字符：“&”,“<”,“>”,“"”,“'”
     * @param strXml
     * @return
     */
    public static String ParseCharToEnity(String strXml){
        if(strXml == "" || null == strXml){
            return "";
        }
        
        strXml = P1.matcher(strXml).replaceAll(Matcher.quoteReplacement(SpecialChar.Egova_1.toString()));
        strXml = P1.matcher(strXml).replaceAll(Matcher.quoteReplacement(SpecialChar.Egova_2.toString()));
        strXml = P1.matcher(strXml).replaceAll(Matcher.quoteReplacement(SpecialChar.Egova_3.toString()));
        strXml = P1.matcher(strXml).replaceAll(Matcher.quoteReplacement(SpecialChar.Egova_4.toString()));
        strXml = P1.matcher(strXml).replaceAll(Matcher.quoteReplacement(SpecialChar.Egova_5.toString()));
        
        return strXml;
    }

	/**
	 * returns the string representation of a given org.w3c.dom.Node object
	 * (usually an instance of org.w3c.dom.Element or org.w3c.dom.Document)
	 * 
	 * @param node
	 * @return
	 */
	public static String xmlToString(Node node) {
		try {
			Source source = new DOMSource(node);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.transform(source, result);
			return stringWriter.getBuffer().toString();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 把row解析成List
	 * 
	 * @param xml
	 * @return
	 */
	public static List<List<String>> xmlToList(String xml) {
		BasicDataSAXHandler handler = new BasicDataSAXHandler(null);
		try {
			SAXParserFactory.newInstance().newSAXParser().parse(new ByteArrayInputStream(xml.getBytes()), handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return handler.getResult();
	}
	
	/**
	 * 把row解析成List(Map)
	 * 
	 * @param xml
	 * @return
	 */
	public static List<Map<String, String>> xmlToMapList(String xml) {
		MapListSAXHandler handler = new MapListSAXHandler(null);
		try {
			SAXParserFactory.newInstance().newSAXParser().parse(new ByteArrayInputStream(xml.getBytes()), handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return handler.getResult();
	}
	
//	public static List<List<String>> xmlToList(NodeList nodelist) {
//		Element ele = (Element) nodelist.item(0);
//		nodelist = ele.getElementsByTagName("row");
//		int nRowLength = nodelist.getLength();
//		List<List<String>> data = new ArrayList<List<String>>(
//				nRowLength);
//		List<String> item;
//		NodeList cnlist;
//		for (int i = 0; i < nRowLength; i++) {
//			ele = (Element) nodelist.item(i);
//			cnlist = ele.getChildNodes();
//			int nHeadLength = cnlist.getLength();
//			item = new ArrayList<String>(nHeadLength);
//			for (int j = 0; j < nHeadLength; j++) {
//				if (!cnlist.item(j).getNodeName().startsWith("#")) {
//					item.add(XmlHelper.getNodeValue(ele, cnlist.item(j)
//							.getNodeName()));
//				}
//			}
//			data.add(item);
//		}
//		return data;
//	}
	
	/**
	 * 把结果解析成List
	 * 
	 * @author LiJiansheng
	 *
	 */
	public static class BasicDataSAXHandler extends DefaultHandler {
		private List<List<String>> list;
		private StringBuilder val;
		private List<String> row;
		private int size = 3;

		public BasicDataSAXHandler(List<List<String>> list) {
			if (list == null) {
				this.list = new ArrayList<List<String>>();
			} else {
				this.list = list;
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (val == null){
				return;
			}
			val.append(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (qName.equalsIgnoreCase("row")) {
				list.add(row);
				size  = row.size();
			} else {
				row.add(ParseEnityToChar(val.toString()));
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (qName.equalsIgnoreCase("row")) {
				row = new ArrayList<String>(size);
			}
			val = new StringBuilder();
		}

		public List<List<String>> getResult() {
			return list;
		}

	}
	
	/**
	 * 把结果解析成List(Map)
	 * 
	 * @author LiJiansheng
	 *
	 */
	public static class MapListSAXHandler extends DefaultHandler {
		private List<Map<String, String>> list;
		private StringBuilder val;
		private Map<String, String> row;
		private int size = 3;

		public MapListSAXHandler(List<Map<String, String>> list) {
			if (list == null) {
				this.list = new ArrayList<Map<String, String>>();
			} else {
				this.list = list;
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (val == null){
				return;
			}
			val.append(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (qName.equalsIgnoreCase("row")) {
				list.add(row);
				size  = row.size();
			} else {
				row.put(qName, ParseEnityToChar(val.toString()).replaceAll("<br>", "\n"));
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (qName.equalsIgnoreCase("row")) {
				row = new HashMap<String, String>(size);
			}
			val = new StringBuilder();
		}

		public List<Map<String, String>> getResult() {
			return list;
		}

	}
}
