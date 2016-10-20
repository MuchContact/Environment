package egova.com.cn.environment.di.modules;

import android.app.Application;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;

import egova.com.cn.environment.MockInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;

public class TestApplicationModule extends ApplicationModule {
    public TestApplicationModule(Application testApplication) {
        super(testApplication);
    }

    @Override
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new MockInterceptor(new File(getClass().getResource("/xml/login_response.xml").getFile()), getResponseBuilder(200)))
                .build();
    }


    private Response.Builder getResponseBuilder(int responseCode) {
        return new Response.Builder()
                .code(responseCode)
                .protocol(Protocol.HTTP_1_0)
                .addHeader("content-type", "text/xml");
    }
}
