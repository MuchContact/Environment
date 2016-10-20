package egova.com.cn.environment.di.components;

import dagger.Component;
import egova.com.cn.environment.di.PerActivity;
import egova.com.cn.environment.di.modules.LoginModule;
import egova.com.cn.environment.login.LoginActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules={LoginModule.class})
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
