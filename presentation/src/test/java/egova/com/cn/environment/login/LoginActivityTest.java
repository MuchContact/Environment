package egova.com.cn.environment.login;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, application = TestApplication.class)
public class LoginActivityTest {
    private final LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
    private EditText username;
    private EditText password;
    private Button login;
    @Before
    public void setUp() throws Exception {
        username = loginActivity.username;
        password = loginActivity.password;
        login = (Button) loginActivity.findViewById(R.id.login);
    }

    @Test
    public void should_login_with_right_auth_info() throws Exception {

        username.setText("muco");
        password.setText("right_password");
        login.performClick();

        ShadowApplication instance = ShadowApplication.getInstance();
        Intent nextActivity = instance.getNextStartedActivity();
        String className = nextActivity.getComponent().getClassName();
        assertThat(className, is(MainActivity.class.getName()));
    }

    @Test
    public void should_login_failed_with_wrong_username() throws Exception {

        username.setText("un_existing_username");
        password.setText("fakepassword");
        login.performClick();

    }
}
