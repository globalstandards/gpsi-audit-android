package globalstd.globalaudit.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import globalstd.globalaudit.R;

/**
 * Created by Gabriel Vazquez on 27/02/2017.
 */

public class LoginActivity   extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        EditText txtPsw = (EditText) findViewById(R.id.txtPsw);
        txtPsw.setTransformationMethod(new PasswordTransformationMethod());

        RelativeLayout btnLogin = (RelativeLayout) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext(), MainActivity.class);
                //Intent i = new Intent( getApplicationContext(), MainEmuledActivity.class);
                //Intent i = new Intent( getApplicationContext(), DirectoryFragment.class);
                startActivity( i );
                finish();

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

}
