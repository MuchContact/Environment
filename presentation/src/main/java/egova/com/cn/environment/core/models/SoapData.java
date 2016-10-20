package egova.com.cn.environment.core.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "hs:process", strict = false)
public class SoapData {
    @Element(name = "request", required = false)
    private String body;

    public void setBody(String body) {
        this.body = body;
    }
}
