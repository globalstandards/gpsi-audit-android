package globalstd.globalaudit.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import globalstd.globalaudit.R;
import globalstd.globalaudit.activitys.MainActivity;
import globalstd.globalaudit.adapters.CustomSpinnerAdapter;

/**
 * Created by Gabriel Vazquez on 09/03/2017.
 */

public class AddAuditSupplierFragment   extends Fragment   implements View.OnClickListener{
    View rootView;
    public static Context context;

    Spinner spinnerSupplier, spinnerChecklist;
    ImageButton btnData;
    EditText txtDate;
    int year, month, day;


    private static final String TAG_LIST_MATCH= "tag_list_match";

    public AddAuditSupplierFragment(){}
    @Override
    public void onAttach(Activity activity) {
        context=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_audit_supplier, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Nueva Auditoría");

        init();


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Make sure you have this line of code.
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
    }
    @Override
    public void onResume() {
        super.onResume();
        //((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.title_fragment_details));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void init(){
        txtDate=(EditText)rootView.findViewById(R.id.txtDate);

        btnData=(ImageButton)rootView.findViewById(R.id.btnData);

        btnData.setOnClickListener(this);

        initCustomSpinner();
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
}


