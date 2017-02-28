package globalstd.globalaudit.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import globalstd.globalaudit.R;

/**
 * Created by Gabriel Vazquez on 28/02/2017.
 */

public class RegisterActivity extends AppCompatActivity {

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

        RelativeLayout btnCreateAcount = (RelativeLayout) findViewById(R.id.btnCreateAcount);
        btnCreateAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent( getApplicationContext(), NewAcountActivity.class);
                //Intent i = new Intent( getApplicationContext(), MainEmuledActivity.class);
                //Intent i = new Intent( getApplicationContext(), DirectoryFragment.class);
                //startActivity( i );
                //finish();

            }


        });


    }

}
