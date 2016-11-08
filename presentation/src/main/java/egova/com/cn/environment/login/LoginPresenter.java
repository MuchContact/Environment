package egova.com.cn.environment.login;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import egova.com.cn.environment.R;
import egova.com.cn.environment.core.api.EgovaApi;
import egova.com.cn.environment.core.models.SoapEnvelop;
import egova.com.cn.environment.core.models.SoapResponse;
import egova.com.cn.environment.di.PerActivity;
import egova.com.cn.environment.util.CommonResult;
import egova.com.cn.environment.util.MD5;
import egova.com.cn.environment.util.RequestParam;
import egova.com.cn.environment.util.RequestParam2Xml;
import egova.com.cn.environment.util.XmlResultProcessorNew;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static java.lang.Integer.valueOf;

@PerActivity
public class LoginPresenter {

    private final EgovaApi loginService;

    private final XmlResultProcessorNew xmlProcessor;

    private final Context context;

    @Inject
    public LoginPresenter(EgovaApi loginService, XmlResultProcessorNew xmlProcessor, Application application) {
        this.loginService = loginService;
        this.xmlProcessor = xmlProcessor;
        this.context = application.getApplicationContext();
    }

    private final Subscriber<SoapResponse> subscriber = new Subscriber<SoapResponse>() {
        @Override
        public void onCompleted() {
            view.dismissLoginProgress();
        }

        @Override
        public void onError(Throwable e) {
            view.showErrors(e.getLocalizedMessage());
            view.dismissLoginProgress();
        }

        @Override
        public void onNext(SoapResponse responseBody) {
            String response = responseBody.getBody();
            CommonResult result = xmlProcessor.convert(response);
            if (result == null) {
                view.showErrors("handle failed for response:" + response);
                return;
            }
            if (result.getErrorCode() == CommonResult.CODE_SUCCESS) {
                view.navigateToMainView();
            } else {
                view.showErrors(result.getErrorDesc());
            }
        }
    };

    private LoginView view;

    public void setView(LoginView view) {
        this.view = view;
    }

    public void initialize() {
        view.hideErrors();
    }

    final int[] userID = {-1};

    public void doLogin() {
        final RequestParam rp = new RequestParam("mobile/sys/login");

        view.hideErrors();
        view.showLoginProgress();
        Observable.just(null)
                .flatMap(new Func1<Object, Observable<SoapResponse>>() {
                    @Override
                    public Observable<SoapResponse> call(Object o) {
                        String username = view.getUsername();
                        String password = view.getPassword();
                        String strPassword = MD5.encrypt("" + userID[0], password);
                        check(username, password);
                        rp.functionName = "humanLogin_ZX";
                        rp.paramMap.put("userID", "" + userID[0]);
                        rp.paramMap.put("userName", username);
                        rp.paramMap.put("password", strPassword);
                        return userID[0] == -1 ?
                                Observable.<SoapResponse>error(new NullPointerException(context.getString(R.string.user_id_not_defined))) :
                                loginService.request(getSoapEnvelop(rp));
                    }
                })
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> call(Throwable throwable) {
                                if (throwable instanceof NullPointerException) {
                                    return loginService.request(getSoapEnvelop(rp))
                                            .doOnNext(new Action1<SoapResponse>() {
                                                @Override
                                                public void call(SoapResponse responseBody) {
                                                    String response = null;
                                                    try {
                                                        response = responseBody.getBody();
                                                        CommonResult result = xmlProcessor.convert(response);
                                                        if (result == null) {
                                                            throw new RuntimeException(context.getString(R.string.error_msg_header) + response);
                                                        }
                                                        if (result.getErrorCode() == LoginResponseCode.ERR_CODE_USER_ID) {
                                                            userID[0] = valueOf(result.getErrorDesc().trim());
                                                        } else {
                                                            throw new RuntimeException(result.getErrorDesc());
                                                        }
                                                    } catch (NumberFormatException e) {
                                                        throw new RuntimeException(context.getString(R.string.number_format_error));
                                                    }
                                                }
                                            });
                                }
                                return Observable.error(throwable);
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    @NonNull
    private SoapEnvelop getSoapEnvelop(RequestParam rp) {
        String body = new RequestParam2Xml().getRequestXml(rp);
        SoapEnvelop soapEnvelop = new SoapEnvelop(body);
        return soapEnvelop;
    }

    private void check(String username, String password) {

    }
}
