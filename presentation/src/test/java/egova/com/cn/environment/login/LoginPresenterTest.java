package egova.com.cn.environment.login;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import egova.com.cn.environment.core.api.EgovaApi;
import egova.com.cn.environment.core.models.SoapEnvelop;
import egova.com.cn.environment.core.models.SoapResponse;
import egova.com.cn.environment.util.CommonResult;
import egova.com.cn.environment.util.XmlResultProcessorNew;
import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("rx.android.schedulers.AndroidSchedulers")
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

    @Test
    public void should_initial_and_then_login() throws Exception {
        SoapResponse mockResponse = PowerMockito.mock(SoapResponse.class);
        when(mockResponse.getBody()).thenReturn("");
        when(mockLoginService.request(any(SoapEnvelop.class))).thenReturn(
                Observable.just(mockResponse));
        when(mockResult.getErrorCode()).thenReturn(LoginResponseCode.ERR_CODE_USER_ID, CommonResult.CODE_SUCCESS);
        when(mockResult.getErrorDesc()).thenReturn("667", "");
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
