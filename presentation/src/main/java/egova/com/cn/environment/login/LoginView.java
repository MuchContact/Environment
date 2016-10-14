package egova.com.cn.environment.login;

public interface LoginView {
    String getUsername();

    String getPassword();

    void hideErrors();

    void showLoginProgress();

    void showErrors();

    void dismissLoginProgress();

    void navigateToMainView();

    void clickToLogin();
}
