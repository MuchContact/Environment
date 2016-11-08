package egova.com.cn.environment.login;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import egova.com.cn.environment.BuildConfig;
import egova.com.cn.environment.MainActivity;
import egova.com.cn.environment.R;
import egova.com.cn.environment.TestApplication;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, application = TestApplication.class)
public class LoginActivityTest {
    private final LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
    private EditText username;
    private EditText password;
    private Button login;

    @BeforeClass
    public static void setUpClass() throws Exception {
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        });
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Before
    public void setUp() throws Exception {
        username = loginActivity.username;
        password = loginActivity.password;
        login = (Button) loginActivity.findViewById(R.id.login);
    }

    @Test
    public void should_login_with_valid_auth_info() throws Exception {

        username.setText("valid_user");
        password.setText("valid_password");
        login.performClick();

        ShadowApplication instance = ShadowApplication.getInstance();
        Intent nextActivity = instance.getNextStartedActivity();
        String className = nextActivity.getComponent().getClassName();
        assertThat(className, is(MainActivity.class.getName()));
    }

}
