package egova.com.cn.environment.core.api;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import egova.com.cn.environment.core.models.SoapEnvelop;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.functions.Action1;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class EgovaApiTest {
    public static OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();
    private String url = "http://192.168.32.179:8080";
    private static final String XML_ROOT_PATH = "/xml";

    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            System.out.println(bodyToString(request));
            Response response = chain.proceed(request);
            return response;
        }

        private String bodyToString(final Request request){
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }
        }
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void should_request_login() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        EgovaApi egovaApi = retrofit.create(EgovaApi.class);
        egovaApi.request(getSoapEnvelop("login_request.xml")).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody responseBody) {
                try {
                    assertThat(responseBody.string(), not(nullValue()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private SoapEnvelop getSoapEnvelop(String fileName) throws URISyntaxException, IOException {
        String path = String.format("%s/%s", XML_ROOT_PATH, fileName);
        String body = FileUtils.readFileToString(new File(getClass().getResource(path).getFile()), "gb2312");
        return new SoapEnvelop(body);
    }
}
