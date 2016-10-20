package egova.com.cn.environment.di.modules;

import dagger.Module;
import dagger.Provides;
import egova.com.cn.environment.di.PerActivity;
import egova.com.cn.environment.login.LoginPresenter;

@Module
public class LoginModule {
    @Provides
    @PerActivity
    public LoginPresenter providesLoginPresenter(LoginPresenter presenter) {
        return presenter;
    }


}
