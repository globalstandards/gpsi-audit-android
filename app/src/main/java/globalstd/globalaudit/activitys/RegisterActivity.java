package globalstd.globalaudit.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import globalstd.globalaudit.GlobalAuditException;
import globalstd.globalaudit.R;
import globalstd.globalaudit.services.AuthService;

/**
 * Created by Gabriel Vazquez on 28/02/2017.
 */

public class RegisterActivity  extends BaseActivity  {
    @Inject
    AuthService authService;

    private CoordinatorLayout coordinatorLayout;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    TextInputEditText txtName;
    TextInputEditText txtCompany;
    TextInputEditText txtEmail;
    TextInputEditText txtPsw;
    RelativeLayout btnCreateAcount;

    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        TextView lblTermsConditions = (TextView) findViewById(R.id.lblTermsConditions);

        SpannableString spString = new SpannableString(lblTermsConditions.getText());
        //spString.setSpan(new RelativeSizeSpan(0.5f), 18, spString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //Tamaño
        //spString.setSpan(new BackgroundColorSpan(Color.GREEN), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //Fondo
        //spString.setSpan(new UnderlineSpan(), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //linea por debajo
        //spString.setSpan(new StrikethroughSpan(), 10, spString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); linea en medio
        //spString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 10, spString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  tipografia
        spString.setSpan(new ForegroundColorSpan(this.getResources().getColor(R.color.md_red_global_logo)), 37, 59, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        lblTermsConditions.setText(spString);

        btnCreateAcount = (RelativeLayout) findViewById(R.id.btnCreateAcount);
        txtName = (TextInputEditText) findViewById(R.id.txtName);
        txtCompany = (TextInputEditText) findViewById(R.id.txtCompany);

        txtEmail = (TextInputEditText) findViewById(R.id.txtEmail);
        txtPsw = (TextInputEditText) findViewById(R.id.txtPsw);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);


        btnCreateAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();

                if (!validateEmail(txtEmail.getText().toString())) {
                    txtEmail.setError(getString(R.string.invalidad_email));
                }
                else if (!validatePassword(txtName.getText().toString())) {
                    txtName.setError(getString(R.string.enter_name));
                }

                else if (!validatePassword(txtCompany.getText().toString())) {
                    txtCompany.setError(getString(R.string.enter_lastname));
                }

                else if (!validatePassword(txtPsw.getText().toString())) {
                    txtPsw.setError(getString(R.string.enter_psw));
                } else {
                    bloqueo();
                    eventBus.post(new SignUpEvent(txtCompany.getText().toString(),txtName.getText().toString(),
                            txtEmail.getText().toString(),  txtPsw.getText().toString()));
                }
                //Intent i = new Intent( getApplicationContext(), NewAcountActivity.class);
                //Intent i = new Intent( getApplicationContext(), MainEmuledActivity.class);
                //Intent i = new Intent( getApplicationContext(), DirectoryFragment.class);
                //startActivity( i );
                //finish();

            }


        });
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

    public void bloqueo() {
        txtName.setEnabled(false);
        txtCompany.setEnabled(false);
        txtEmail.setEnabled(false);
        txtPsw.setEnabled(false);
        btnCreateAcount.setEnabled(false);
    }

    public void desbloqueo() {
        txtName.setEnabled(true);
        txtCompany.setEnabled(true);
        txtEmail.setEnabled(true);
        txtPsw.setEnabled(true);
        btnCreateAcount.setEnabled(true);
    }

    private void showMainActivity() {
        Intent i = new Intent( getApplicationContext(), MainActivity.class);
        //Intent i = new Intent( getApplicationContext(), MainEmuledActivity.class);
        //Intent i = new Intent( getApplicationContext(), DirectoryFragment.class);
        startActivity( i );
        finish();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSignUpEvent(SignUpEvent event) {
        GlobalAuditException error = null;
        try {
            authService.signUp(event.company, event.username, event.email, event.password);
        } catch(GlobalAuditException e) {
            error = e;
        }
        eventBus.post(new SignUpResponseEvent(error));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SignUpResponseEvent(SignUpResponseEvent event) {
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

                case GlobalAuditException.EMAIL_ALREADY_EXISTS:
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.email_alreay_exists), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    desbloqueo();
                    break;

                case GlobalAuditException.COMPANY_NAME_ALREADY_EXISTS:
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.company_name_already_exists), Snackbar.LENGTH_LONG);
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

    private static class SignUpEvent {
        public String company;
        public String username;

        public String email;
        public String password;

        public SignUpEvent(String company, String username, String email, String password) {
            this.company = company;
            this.username = username;

            this.email = email;
            this.password = password;
        }

    }
    private static class SignUpResponseEvent {
        public GlobalAuditException error;
        public SignUpResponseEvent(GlobalAuditException error) {
            this.error = error;
        }
    }

}
