package globalstd.globalaudit.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import globalstd.globalaudit.R;

/**
 * Created by Gabriel Vazquez on 28/02/2017.
 */

public class RegisterActivity extends AppCompatActivity {
    private CoordinatorLayout coordinatorLayout;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    TextInputEditText txtName;
    TextInputEditText txtLastname;
    TextInputEditText txtEmail;
    TextInputEditText txtPsw;
    RelativeLayout btnCreateAcount;

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
        txtLastname = (TextInputEditText) findViewById(R.id.txtLastname);

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

                else if (!validatePassword(txtLastname.getText().toString())) {
                    txtLastname.setError(getString(R.string.enter_lastname));
                }

                else if (!validatePassword(txtPsw.getText().toString())) {
                    txtPsw.setError(getString(R.string.enter_psw));
                } else {
                    bloqueo();
                    //eventBus.post(new LoginActivity.SignInEvent(txtEmail.getText().toString(), txtPsw.getText().toString()));
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
        txtLastname.setEnabled(false);
        txtEmail.setEnabled(false);
        txtPsw.setEnabled(false);
        btnCreateAcount.setEnabled(false);
    }

    public void desbloqueo() {
        txtName.setEnabled(true);
        txtLastname.setEnabled(true);
        txtEmail.setEnabled(true);
        txtPsw.setEnabled(true);
        btnCreateAcount.setEnabled(true);
    }

}
