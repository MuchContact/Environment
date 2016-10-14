package egova.com.cn.environment.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "soapenv:Body", strict = false)
public class SoapRequestBody {
    @Element(name = "hs:process",required = false)
    private SoapData soapData;

    public SoapData getSoapData() {
        return soapData;
    }

    public void setSoapData(SoapData soapData) {
        this.soapData = soapData;
    }
}
