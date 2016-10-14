package egova.com.cn.environment.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

@Root(name = "soapenv:Envelope")
@NamespaceList({
        @Namespace( prefix = "soapenv", reference = "http://schemas.xmlsoap.org/soap/envelope/"),
        @Namespace( prefix = "hs", reference = "http://inspect.mi.egova.com.cn/")
})
public class SoapEnvelop {
    @Element(name = "soapenv:Body", required = false)
    private SoapRequestBody body;

    public SoapRequestBody getBody() {
        return body;
    }

    public void setBody(SoapRequestBody body) {
        this.body = body;
    }
}
