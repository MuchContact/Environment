package egova.com.cn.environment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LoginActivityTest {
    @Test
    public void should_login_with_right_auth_info() throws Exception {
//
//        username.setText("muco");
//        password.setText("right_password");
//        login.performClick();
//
//        ShadowApplication instance = ShadowApplication.getInstance();
//        Intent nextActivity = instance.getNextStartedActivity();
//        String className = nextActivity.getComponent().getClassName();
//        assertThat(className, is(MainActivity.class.getName()));
    }
}
