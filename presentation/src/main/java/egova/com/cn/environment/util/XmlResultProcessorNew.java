package egova.com.cn.environment.util;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlResultProcessorNew extends DefaultHandler implements IResultProcessor {
    static final String TAG = "[XmlResultProcessor]";


    @Override
    public CommonResult convert(CharSequence s) {
        if (null == s || s.length() <= 0) {
            CommonResult result = new CommonResult();
            result.setErrorCode(CommonResult.CONNECTION_ERROR);
            result.setErrorDesc(CommonResult.CONNECTION_ERROR_MSG);
            result.setDataList(new ArrayList<Map<String, String>>(0));
            return result;
        }
        return new XmlResultHandler().getResult(s.toString());
    }


    static class XmlResultHandler extends DefaultHandler {

        private int rowCount = 0; // 作用是记录以row开头的行
        private Map<String, String> map = null;
        private String preTag = null;// 作用是记录解析时的上一个节点名称

        /**
         * 目前节点所对应的值.
         */
        private StringBuilder strValue = null;
        private int errorCode;
        private String errorDesc;


        private Stack<String> resStrTagStack = new Stack<String>();

        private Map<String, List<Map<String, String>>> dataListMaps = null;

        private List<Map<String, String>> list = null;
        private StringBuilder resultStr = null;    //存储xml中的resultStr内容
        private boolean isResultStr = false;


        public CommonResult getResult(String xml) {
            CommonResult result = new CommonResult();
            SAXParser parser;
            try {
                parser = SAXParserFactory.newInstance().newSAXParser();
                parser.parse(new InputSource(new StringReader(xml)), this);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "--getResult1--字符串:" + xml + ";解析错误:" + e);
            }

            result.setErrorCode(errorCode);
            result.setErrorDesc(errorDesc);
            result.setDataList(list);
            result.setDataListMaps(dataListMaps);
            result.setResultStr(resultStr.toString());

            Log.i(TAG, "共获得数据" + (null == list ? "null" : ("" + list.size())) + "行");

            return result;
        }

        @Override
        public void startDocument() throws SAXException {
            rowCount = 0;

            dataListMaps = new HashMap<String, List<Map<String, String>>>();
            errorCode = -1;
            errorDesc = "解析错误";
            strValue = new StringBuilder();
            resultStr = new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (isResultStr) {
                resultStr.append("<").append(qName).append(">");
                //处于 result标签内 时，标签进栈。
                if (resStrTagStack.size() == 0) {//
                    List<Map<String, String>> map = new ArrayList<Map<String, String>>();
                    list = map;
                    dataListMaps.put(qName, map);
                }
                resStrTagStack.push(qName);

            }
            if ("row".equalsIgnoreCase(qName)) {

                map = new CaseInsensitiveMap<String>();
                rowCount++;
            } else {
                if ("resultStr".equalsIgnoreCase(qName)) {
                    isResultStr = true;
                }
                preTag = qName;// 将正在解析的节点名称赋给preTag
                strValue.setLength(0);
            }

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {


            if (isResultStr && !localName.equalsIgnoreCase("resultStr")) {
                resultStr.append(strValue).append("</").append(qName).append(">");
                //处于 result标签内 时，标签出栈。
                resStrTagStack.pop();
            }

            if ("errorCode".equalsIgnoreCase(qName)) {
                try {
                    errorCode = Integer.parseInt(strValue.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Log.e("XmlParseService", "[--endElement3--errorCode]" + strValue + ";" + e);
                }
            } else if ("errorDesc".equalsIgnoreCase(qName)) {
                errorDesc = strValue.toString();
            } else if ("row".equalsIgnoreCase(qName)) {

                dataListMaps.get(resStrTagStack.peek()).add(map);
                rowCount--;
            } else if ("resultStr".equalsIgnoreCase(qName)) {
                if (isResultStr) {
                    resultStr.append(strValue);
                }
                isResultStr = false;
            } else if (rowCount > 0) {
                map.put(qName, XmlHelper.ParseEnityToChar(strValue.toString()));
            } else if ("recCount".equalsIgnoreCase(qName)) { //兼容处置通
                if (errorCode == CommonResult.CODE_SUCCESS) {
                    errorDesc = strValue.toString();
                }
            }

            preTag = null;
            strValue.setLength(0);
            /**
             * 当解析结束时置为空。这里很重要，例如，当图中画3的位置结束后，会调用这个方法
             * ，如果这里不把preTag置为null，根据startElement(....)方法，preTag的值还是book，当文档顺序读到图
             * 中标记4的位置时，会执行characters(char[] ch, int start, int
             * length)这个方法，而characters(....)方
             * 法判断preTag!=null，会执行if判断的代码，这样就会把空值赋值给book，这不是我们想要的。
             */
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (preTag != null) {
                strValue.append(new String(ch, start, length));
            }
        }

    }
}
