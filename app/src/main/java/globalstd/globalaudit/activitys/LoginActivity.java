package globalstd.globalaudit.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
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

    public static boolean isOnline=true;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        validateInternet();


        txtEmail = (TextInputEditText) findViewById(R.id.txtEmail);
        txtPsw = (TextInputEditText) findViewById(R.id.txtPsw);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        txtPsw.setTransformationMethod(new PasswordTransformationMethod());

        btnLogin = (RelativeLayout) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline) {
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
                    break;

                case GlobalAuditException.SERVER_ERROR:
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


    public void validateInternet(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //Toast.makeText(getApplication(), "Testing timer", Toast.LENGTH_SHORT).show();
                if(isConnected()){
                    if(isOnline==false) {
                        isConnectionReset();
                    }
                }
                handler.postDelayed(this, 15000); //now is every 2 minutes
            }
        }, 15000); //Every 120000 ms (2 minutes)
        // comprobar si esta conectado a internet
    }
    public Boolean isConnected(){
        if(isOnlineWifiNetwork()){
            //showAlertDialog(this, "Conexion a Internet", "Tu Dispositivo tiene Conexion a Wifi.", true);
            return true;
        }else{
            if(isOnlineMobileNetwork()){
                //showAlertDialog(this, "Conexion a Internet", "Tu Dispositivo tiene Conexion Movil.", true);
                return true;
            }else{
                isOnline=false;
                showAlertDialog(this, "Conexion a Internet", "Tu Dispositivo no tiene Conexion a Internet.", false);
                return false;
            }
        }
    }
    public Boolean isConnectionReset(){
        if(isOnlineWifiNetwork()){
            isOnline=true;
            showAlertDialogTemp(this, "Conexion a Internet",
                    "Tu Dispositivo tiene Conexion a Wifi.", true);
            return true;
        }else{
            if(isOnlineMobileNetwork()){
                isOnline=true;
                showAlertDialogTemp(this, "Conexion a Internet",
                        "Tu Dispositivo tiene Conexion Movil.", true);
                return true;
            }else{
                isOnline=false;
                showAlertDialog(this, "Conexion a Internet",
                        "Tu Dispositivo no tiene Conexion a Internet.", false);
                return false;
            }
        }
    }

    protected Boolean isOnlineWifiNetwork(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
    protected Boolean isOnlineMobileNetwork(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {

        snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE);
        //snackbar.setActionTextColor(this.getResources().getColor(R.color.colorPrimary));

        View view = snackbar.getView();
        //view.setBackgroundColor(this.getResources().getColor(R.color.white));

        //Get the textview of the snackbar text
        //TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);

        //set text color
        //textView.setTextColor(this.getResources().getColor(R.color.colorAccent));

        /// /increase max lines of text in snackbar. default is 2.
        //textView.setMaxLines(10);

        // Position
        /*
        CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        */
        snackbar.show();



    }
    public void showAlertDialogTemp(Context context, String title, String message, Boolean status) {

        snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT);
        //snackbar.setActionTextColor(this.getResources().getColor(R.color.colorPrimary));

        View view = snackbar.getView();
        view.setBackgroundColor(this.getResources().getColor(R.color.strip_1));

        //Get the textview of the snackbar text
        //TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);

        //set text color
        //textView.setTextColor(this.getResources().getColor(R.color.colorAccent));

        /// /increase max lines of text in snackbar. default is 2.
        //textView.setMaxLines(10);

        // Position
        /*
        CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        */
        snackbar.show();



    }

}
