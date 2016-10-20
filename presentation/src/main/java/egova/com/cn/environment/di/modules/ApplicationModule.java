package egova.com.cn.environment.di.modules;

import android.app.Application;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import egova.com.cn.environment.core.api.EgovaApi;
import egova.com.cn.environment.util.XmlResultProcessorNew;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application egovaApplication) {
        this.application = egovaApplication;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(@Named("EgovaServer") String url, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Named("EgovaServer")
    @Provides
    @Singleton
    public String provideEgovaServer() {
        return "http://192.168.32.179:8080";
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }


    @Provides
    @Singleton
    public EgovaApi providesEgovaApi(Retrofit retrofit) {
        return retrofit.create(EgovaApi.class);
    }

    @Provides
    public XmlResultProcessorNew processorXmlProcessor() {
        return new XmlResultProcessorNew();
    }
}
