package egova.com.cn.environment;

import egova.com.cn.environment.models.SoapEnvelop;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface EgovaApi {
    String HOST = "http://192.168.32.179:8080";

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("/egovaProj/services/Inspector")
    Observable<ResponseBody> request(@Body SoapEnvelop body);
}
