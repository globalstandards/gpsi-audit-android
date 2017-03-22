package globalstd.globalaudit.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import globalstd.globalaudit.GlobalAuditException;
import globalstd.globalaudit.R;
import globalstd.globalaudit.services.AuthService;

/**
 * Created by Gabriel Vazquez on 27/02/2017.
 */

public class LoginActivity extends BaseActivity {
    @Inject
    AuthService authService;

    private CoordinatorLayout coordinatorLayout;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    TextInputEditText txtEmail;
    TextInputEditText txtPsw;
    RelativeLayout btnLogin;
    RelativeLayout btnNewAcount;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        txtEmail = (TextInputEditText) findViewById(R.id.txtEmail);
        txtPsw = (TextInputEditText) findViewById(R.id.txtPsw);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        txtPsw.setTransformationMethod(new PasswordTransformationMethod());

        btnLogin = (RelativeLayout) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();

                /*if (!validateEmail(txtEmail.getText().toString())) {
                    txtEmail.setError(getString(R.string.invalidad_email));
                } else*/
                if (!validatePassword(txtPsw.getText().toString())) {
                    txtPsw.setError(getString(R.string.enter_psw));
                } else {
                    txtEmail.setEnabled(false);
                    txtPsw.setEnabled(false);

                    btnLogin.setEnabled(false);
                    btnNewAcount.setEnabled(false);
                    eventBus.post(new SignInEvent(txtEmail.getText().toString(), txtPsw.getText().toString()));
                }



            }
        });

        btnNewAcount = (RelativeLayout) findViewById(R.id.btnNewAcount);
        btnNewAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext(), RegisterActivity.class);
                //Intent i = new Intent( getApplicationContext(), MainEmuledActivity.class);
                //Intent i = new Intent( getApplicationContext(), DirectoryFragment.class);
                startActivity( i );
            }
        });
    }

    private void showMainActivity() {
        Intent i = new Intent( getApplicationContext(), MainActivity.class);
        //Intent i = new Intent( getApplicationContext(), MainEmuledActivity.class);
        //Intent i = new Intent( getApplicationContext(), DirectoryFragment.class);
        startActivity( i );
        finish();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSignInEvent(SignInEvent event) {
        GlobalAuditException error = null;
        try {
            authService.signIn(event.email, event.pass);
        } catch(GlobalAuditException e) {
            error = e;
        }
        eventBus.post(new SignInResponseEvent(error));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSignInResponseEvent(SignInResponseEvent event) {
        if (event.error != null) {
            switch (event.error.getCode()) {
                case GlobalAuditException.INVALID_CREDENTIALS:
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.invalid_credentials), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    desbloqueo();
                    break;

                case GlobalAuditException.INTERNET_ERROR:
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.internet_error), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    desbloqueo();
                break;

                case GlobalAuditException.SERVER_ERROR:
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.server_error), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    desbloqueo();
                break;
            }
        }
        //Sucess
        else{
            showMainActivity();
        }
    }

    private static class SignInEvent {
        public String email;
        public String pass;

        public SignInEvent(String email, String pass) {
            this.email = email;
            this.pass = pass;
        }
    }

    private static class SignInResponseEvent {
        public GlobalAuditException error;

        public SignInResponseEvent(GlobalAuditException error) {
            this.error = error;
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean validatePassword(String password) {
        return password.length() > 2;
    }

    public void desbloqueo() {
        txtEmail.setEnabled(true);
        txtPsw.setEnabled(true);
        btnLogin.setEnabled(true);
        btnNewAcount.setEnabled(true);
    }

}
