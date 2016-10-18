package egova.com.cn.environment.login;

import org.junit.Test;

import egova.com.cn.environment.EgovaApi;
import egova.com.cn.environment.models.SoapEnvelop;
import okhttp3.ResponseBody;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginPresenterTest {

    @Test
    public void should_initial_and_then_login() throws Exception {
        LoginPresenter loginPresenter = new LoginPresenter();
        LoginView mockLoginView = mock(LoginView.class);
        loginPresenter.setView(mockLoginView);
        EgovaApi mockLoginService = mock(EgovaApi.class);
        loginPresenter.setLoginService(mockLoginService);
        ResponseBody mockResponse = mock(ResponseBody.class);
        when(mockLoginService.request(any(SoapEnvelop.class))).thenReturn(Observable.just(mockResponse));
        loginPresenter.initialize();
        verify(mockLoginView).hideErrors();

        when(mockLoginView.getUsername()).thenReturn("ValidUsername");
        when(mockLoginView.getPassword()).thenReturn("ValidPassword");
        loginPresenter.doLogin();
        verify(mockLoginView, times(2)).hideErrors();
        verify(mockLoginView).showLoginProgress();
        verify(mockLoginView).dismissLoginProgress();
        verify(mockLoginView).navigateToMainView();

    }
}
