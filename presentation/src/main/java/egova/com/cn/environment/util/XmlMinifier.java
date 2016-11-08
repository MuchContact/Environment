package egova.com.cn.environment.util;

public class XmlMinifier {
    public static String minify(String origin) {
        return origin.replaceAll("\n", "")
                .replaceAll("\t", "")
                .replaceAll("\\s{2,}", "");
    }
}
