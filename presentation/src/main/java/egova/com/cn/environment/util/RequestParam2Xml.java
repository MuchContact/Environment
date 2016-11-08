package egova.com.cn.environment.util;

import java.util.Map;

public class RequestParam2Xml {

    private boolean isNullString(String str) {
        return str == null || "".equals(str);
    }

    private static final int PRODUCT_ID = 68;

    public String getRequestXml(RequestParam rp) {

        StringBuilder buf = new StringBuilder();
        buf.append("<?xml version='1.0' encoding='gb2312'?><request>");
        buf.append("<product name='").append(PRODUCT_ID).append("'/>");
        if (isNullString(rp.procedureName)) {
            //通过functionName原始流程执行存储过程
            buf.append("<function name='").append(rp.functionName).append("'/>");
            buf.append("<params>");
            if (null != rp.paramMap) {
                for (Map.Entry<String, Object> entry : rp.paramMap.entrySet()) {
                    buf.append("<").append(entry.getKey()).append(">").append(entry.getValue()).append("</").append(entry.getKey()).append(">");
                }
            }

            buf.append("<startNum>").append(rp.startNum).append("</startNum>");
            buf.append("<endNum>").append(rp.endNum).append("</endNum>");
        } else {
            //通过procedureName简单流程执行存储过程，不需要服务器端数据库配置参数
            buf.append("<procedure name='").append(rp.procedureName).append("'/>");
            buf.append("<params>");
            if (null != rp.paramMap) {
                for (Map.Entry<String, Object> entry : rp.paramMap.entrySet()) {
                    buf.append("<").append(entry.getKey()).append(">").append(entry.getValue()).append("</").append(entry.getKey()).append(">");
                }
            }
        }

        buf.append("</params></request>");
        String requestXML = buf.toString();
        // 若未设定请求对应的产品，则设定默认值SysConfig.PRODUCT_ID
        if (requestXML != null && !requestXML.contains("<product name='") && requestXML.indexOf("<function") > 0) {
            requestXML = requestXML.replace("<function", "<product name='" + PRODUCT_ID + "'/><function");
        }
        return requestXML;
    }
}
