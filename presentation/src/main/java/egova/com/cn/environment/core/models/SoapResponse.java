package egova.com.cn.environment.core.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "Envelope", strict = false)
public class SoapResponse {
    @Element(name = "return", required = true)
    @Path("Body/processResponse")
    private String body;

    public String getBody() {
        return body;
    }
}
