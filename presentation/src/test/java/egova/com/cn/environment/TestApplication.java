package egova.com.cn.environment;

import egova.com.cn.environment.di.components.ApplicationComponent;
import egova.com.cn.environment.di.components.DaggerApplicationComponent;
import egova.com.cn.environment.di.modules.TestApplicationModule;

public class TestApplication extends EgovaApplication{
    @Override
    public ApplicationComponent getAppComponent() {
        return DaggerApplicationComponent.builder().applicationModule(new TestApplicationModule(this)).build();
    }
}
