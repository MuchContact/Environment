package egova.com.cn.environment.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import egova.com.cn.environment.EgovaApplication;
import egova.com.cn.environment.MainActivity;
import egova.com.cn.environment.R;
import egova.com.cn.environment.di.components.DaggerLoginComponent;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @Inject
    LoginPresenter presenter;

    @Bind(R.id.username)
    EditText username;

    @Bind(R.id.password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DaggerLoginComponent.builder()
                .applicationComponent(((EgovaApplication) getApplication()).getAppComponent())
                .build()
                .inject(this);
        ButterKnife.bind(this);
        presenter.setView(this);
        presenter.initialize();
    }

    @OnClick(R.id.login)
    public void login() {
        presenter.doLogin();
    }

    @Override
    public String getUsername() {
        return username.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public void hideErrors() {

    }

    @Override
    public void showLoginProgress() {

    }

    @Override
    public void showErrors(String error) {
        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void dismissLoginProgress() {

    }

    @Override
    public void navigateToMainView() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
