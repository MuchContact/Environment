package egova.com.cn.environment.core.api;

import egova.com.cn.environment.core.models.SoapEnvelop;
import egova.com.cn.environment.core.models.SoapResponse;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface EgovaApi {

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("/egovaProj/services/Inspector")
    Observable<SoapResponse> request(@Body SoapEnvelop body);
}
