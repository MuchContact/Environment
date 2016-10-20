package egova.com.cn.environment;

import android.app.Application;

import com.facebook.stetho.Stetho;

import egova.com.cn.environment.di.components.ApplicationComponent;
import egova.com.cn.environment.di.components.DaggerApplicationComponent;
import egova.com.cn.environment.di.modules.ApplicationModule;

public class EgovaApplication extends Application{
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

    public ApplicationComponent getAppComponent() {
        if(applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        }
        return applicationComponent;
    }

}
