package globalstd.globalaudit.fragments;

import android.app.Activity;
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
import android.widget.Spinner;

import java.util.ArrayList;

import globalstd.globalaudit.R;
import globalstd.globalaudit.activitys.MainActivity;
import globalstd.globalaudit.adapters.CustomSpinnerAdapter;

/**
 * Created by Gabriel Vazquez on 14/03/2017.
 */

public class AddUserFragment   extends Fragment {
    View rootView;
    public static Context context;

    Spinner spinnerCountry, spinnerState, spinnerLenguage, spinnerRole;


    private static final String TAG_LIST_MATCH= "tag_list_match";

    public AddUserFragment(){}
    @Override
    public void onAttach(Activity activity) {
        context=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_user, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Nuevo Usuario");

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
        initCustomSpinner();
    }


    private void initCustomSpinner() {
        spinnerCountry= (Spinner) rootView.findViewById(R.id.spinnerCountry);
        spinnerState= (Spinner) rootView.findViewById(R.id.spinnerState);
        spinnerLenguage= (Spinner) rootView.findViewById(R.id.spinnerLenguage);
        spinnerRole= (Spinner) rootView.findViewById(R.id.spinnerRole);

        // Spinner Drop down elements
        ArrayList<String> CountryList = new ArrayList<String>();
        CountryList.add("México");
        CountryList.add("E.U.");
        CountryList.add("Colombia");

        ArrayList<String> stateList = new ArrayList<String>();
        stateList.add("Jalisco");
        stateList.add("Cd. Mexico");
        stateList.add("Sonora");

        ArrayList<String> lenguageList = new ArrayList<String>();
        lenguageList.add("Español");
        lenguageList.add("Ingles");
        lenguageList.add("Frances");

        ArrayList<String> roleList = new ArrayList<String>();
        roleList.add("Admnistrativo");
        roleList.add("TI");
        roleList.add("Finanzas");


        CustomSpinnerAdapter countrySpinnerAdapter=new CustomSpinnerAdapter(context,CountryList );
        CustomSpinnerAdapter stateListSpinnerAdapter=new CustomSpinnerAdapter(context,stateList );
        CustomSpinnerAdapter roleSpinnerAdapter=new CustomSpinnerAdapter(context, roleList );
        CustomSpinnerAdapter lenguageListSpinnerAdapter=new CustomSpinnerAdapter(context,lenguageList );

        spinnerCountry.setAdapter(countrySpinnerAdapter);
        //spinnerCountry.setSelection(countrySpinnerAdapter.getCount()); //display hint
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                //Toast.makeText(parent.getContext(), "Android Custom Spinner Example Output..." + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerState.setAdapter(stateListSpinnerAdapter);
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                //Toast.makeText(parent.getContext(), "Android Custom Spinner Example Output..." + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerRole.setAdapter(roleSpinnerAdapter);
        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                //Toast.makeText(parent.getContext(), "Android Custom Spinner Example Output..." + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerLenguage.setAdapter(lenguageListSpinnerAdapter);
        spinnerLenguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

