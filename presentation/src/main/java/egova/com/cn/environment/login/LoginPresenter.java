package egova.com.cn.environment.login;

import egova.com.cn.environment.EgovaApi;
import egova.com.cn.environment.models.SoapEnvelop;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class LoginPresenter {
    private final Subscriber<ResponseBody> subscriber = new Subscriber<ResponseBody>() {
        @Override
        public void onCompleted() {
            view.dismissLoginProgress();
        }

        @Override
        public void onError(Throwable e) {
            view.showErrors();
            view.dismissLoginProgress();
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            view.navigateToMainView();
        }
    };

    private LoginView view;

    EgovaApi loginService;

    public void setLoginService(EgovaApi loginService) {
        this.loginService = loginService;
    }

    public void setView(LoginView view) {
        this.view = view;
    }

    public void initialize() {
        view.hideErrors();
    }

    public void doLogin() {
        String username = view.getUsername();
        String password = view.getPassword();
        check(username, password);
        view.hideErrors();
        view.showLoginProgress();
        loginService.request(new SoapEnvelop()).subscribeOn(Schedulers.io()).subscribe(subscriber);
    }

    private void check(String username, String password) {

    }
}
