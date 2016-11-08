package egova.com.cn.environment.di.components;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import egova.com.cn.environment.core.api.EgovaApi;
import egova.com.cn.environment.di.modules.ApplicationModule;
import egova.com.cn.environment.util.XmlResultProcessorNew;
import retrofit2.Retrofit;

@Singleton
@Component(modules={ApplicationModule.class})
public interface ApplicationComponent {
    Retrofit retrofit();
    EgovaApi egovaApi();
    Application application();
    XmlResultProcessorNew processorXmlProcessor();
}
