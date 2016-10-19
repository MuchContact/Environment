package egova.com.cn.environment.login;

public interface LoginView {
    String getUsername();

    String getPassword();

    void hideErrors();

    void showLoginProgress();

    void showErrors(String error);

    void dismissLoginProgress();

    void navigateToMainView();

}
