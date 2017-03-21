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
 * Created by Gabriel Vazquez on 15/03/2017.
 */

public class UpdateAuditFragment  extends Fragment  implements View.OnClickListener {
    View rootView;
    public static Context context;

    Spinner spinnerSupplier, spinnerChecklist;
    ImageButton btnData,btnTime;
    EditText txtDate;//,txtHour;
    int year, month, day;//, hour, minute ;


    private static final String TAG_LIST_MATCH = "tag_list_match";

    public UpdateAuditFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        context = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_audit, container, false);
        //((MainActivity) getActivity()).setActionBarTitle("Auditoria");

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
        inflater.inflate(R.menu.menu_detail_audit, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        //((MainActivity) getActivity()).setActionBarTitle("Auditoria");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void init() {
        txtDate=(EditText)rootView.findViewById(R.id.txtDate);
        //txtHour=(EditText)rootView.findViewById(R.id.txtHour);

        btnData=(ImageButton)rootView.findViewById(R.id.btnData);
      //  btnTime=(ImageButton)rootView.findViewById(R.id.btnTime);

        btnData.setOnClickListener(this);
      //  btnTime.setOnClickListener(this);

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
            /*hour=c.get(Calendar.HOUR_OF_DAY);
            minute=c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    txtHour.setText(hourOfDay+":"+minute+view.getCurrentHour()+"");
                }
            },hour,minute,false);
            timePickerDialog.show();
            */
        }
    }

}

