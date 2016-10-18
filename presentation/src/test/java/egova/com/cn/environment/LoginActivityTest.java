package egova.com.cn.environment;

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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
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
}
