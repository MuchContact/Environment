package egova.com.cn.environment.login;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import egova.com.cn.environment.EgovaApi;
import egova.com.cn.environment.models.SoapEnvelop;
import egova.com.cn.environment.util.CommonResult;
import egova.com.cn.environment.util.XmlResultProcessorNew;
import okhttp3.ResponseBody;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ResponseBody.class)
public class LoginPresenterTest {

    @Mock
    private XmlResultProcessorNew mockXmlProcessor;
    @Mock
    private EgovaApi mockLoginService;
    @Mock
    private LoginView mockLoginView;
    @Mock
    private CommonResult mockResult;

    public LoginPresenterTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_initial_and_then_login() throws Exception {
        ResponseBody mockResponse = PowerMockito.mock(ResponseBody.class);
        when(mockResponse.string()).thenReturn("<xml></xml>");
        when(mockLoginService.request(any(SoapEnvelop.class))).thenReturn(Observable.just(mockResponse));
        when(mockResult.getErrorCode()).thenReturn(CommonResult.CODE_SUCCESS);
        when(mockXmlProcessor.convert(anyString())).thenReturn(mockResult);

        LoginPresenter loginPresenter = new LoginPresenter(mockLoginService, mockXmlProcessor);
        loginPresenter.setView(mockLoginView);
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
