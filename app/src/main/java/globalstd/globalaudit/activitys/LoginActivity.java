package globalstd.globalaudit.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import globalstd.globalaudit.GlobalAuditException;
import globalstd.globalaudit.R;
import globalstd.globalaudit.services.AuthService;

/**
 * Created by Gabriel Vazquez on 27/02/2017.
 */

public class LoginActivity extends BaseActivity {
    @Inject AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        final EditText txtPsw = (EditText) findViewById(R.id.txtPsw);
        txtPsw.setTransformationMethod(new PasswordTransformationMethod());

        RelativeLayout btnLogin = (RelativeLayout) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventBus.post(new SignInEvent(txtPsw.getText().toString(), txtEmail.getText().toString()));
                // showMainActivity();
            }
        });

        RelativeLayout btnNewAcount = (RelativeLayout) findViewById(R.id.btnNewAcount);
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
        try {
            authService.signIn(event.email, event.pass);
        } catch(GlobalAuditException e) {
            switch (e.getCode()) {
                case GlobalAuditException.INVALID_CREDENTIALS:
                    break;
            }
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
}
