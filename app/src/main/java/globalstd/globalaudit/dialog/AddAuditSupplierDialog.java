package globalstd.globalaudit.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;

import globalstd.globalaudit.R;
import globalstd.globalaudit.adapters.CustomSpinnerAdapter;

import static android.R.attr.startYear;

/**
 * Created by Gabriel Vazquez on 02/03/2017.
 */

public class AddAuditSupplierDialog extends DialogFragment  implements View.OnClickListener  {
    public static Context context;
    public View rootView;
    Spinner spinnerSupplier, spinnerChecklist;
    ImageButton btnData,btnTime;
    EditText txtDate,txtHour;
    int year, month, day, hour, minute ;

    public AddAuditSupplierDialog() {}
    /*
    @SuppressLint("ValidFragment")
    public AddAuditSupplierDialog(QuestionFragment questionFragment) {
        this.questionFragment=questionFragment;
    }*/


    @Override
    public void onAttach(Activity activity) {
        context=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialog();
    }

    /**
     * Crea un PopUp con personalizado para comportarse
     * como formulario de PopUp
     *
     * @return PopUp
     */
    public AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_add_audit_supplier, null);
        builder.setView(rootView);        // Obtiene el btn de cerrar de menu bar

        txtDate=(EditText)rootView.findViewById(R.id.txtDate);
        txtHour=(EditText)rootView.findViewById(R.id.txtHour);

        btnData=(ImageButton)rootView.findViewById(R.id.btnData);
        btnTime=(ImageButton)rootView.findViewById(R.id.btnTime);

        btnData.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        init();


        return builder.create();
    }

    @Override
    public void onClick(View v) {
        if(v==btnData){
            final Calendar now= Calendar.getInstance();
            day=now.get(Calendar.DAY_OF_MONTH);
            month=now.get(Calendar.MONTH);
            year=now.get(Calendar.YEAR);


            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    txtDate.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                }
            },year, month, day);
            datePickerDialog.show();
        }
        if (v==btnTime){
            final Calendar c= Calendar.getInstance();
            hour=c.get(Calendar.HOUR_OF_DAY);
            minute=c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    txtHour.setText(hourOfDay+":"+minute+view.getCurrentHour()+"");
                }
            },hour,minute,false);
            timePickerDialog.show();
        }
    }

    public void init(){

        initCustomSpinner();

    }
    private void initCustomSpinner() {
        spinnerSupplier= (Spinner) rootView.findViewById(R.id.spinnerSupplier);
        spinnerChecklist= (Spinner) rootView.findViewById(R.id.spinnerChecklist);

        // Spinner Drop down elements
        ArrayList<String> supplierList = new ArrayList<String>();
        supplierList.add("Coca-Cola");
        supplierList.add("Alsea");
        supplierList.add("Ocitrimex");
        supplierList.add("Melchor");
        supplierList.add("Upg");

        ArrayList<String> checkListsList = new ArrayList<String>();
        checkListsList.add("ISO 9001");
        checkListsList.add("ISO 1400");
        checkListsList.add("ISO 2200");

        CustomSpinnerAdapter supplierSpinnerAdapter=new CustomSpinnerAdapter(context,supplierList );
        CustomSpinnerAdapter checkListSpinnerAdapter=new CustomSpinnerAdapter(context,checkListsList );

        spinnerSupplier.setAdapter(supplierSpinnerAdapter);
        spinnerSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                //Toast.makeText(parent.getContext(), "Android Custom Spinner Example Output..." + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerChecklist.setAdapter(checkListSpinnerAdapter);
        spinnerChecklist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                //Toast.makeText(parent.getContext(), "Android Custom Spinner Example Output..." + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}

