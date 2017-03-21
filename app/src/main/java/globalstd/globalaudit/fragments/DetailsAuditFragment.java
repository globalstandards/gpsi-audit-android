package globalstd.globalaudit.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import globalstd.globalaudit.R;
import globalstd.globalaudit.activitys.MainActivity;
import globalstd.globalaudit.adapters.TabAdapter;

import android.support.v4.app.FragmentTransaction;
/**
 * Created by Gabriel Vazquez on 15/03/2017.
 */

public class DetailsAuditFragment extends Fragment {
    View rootView;
    public static Context context;

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int typeUser = 0 ;

    private static final String TAG_LIST_MATCH= "tag_list_match";

    public DetailsAuditFragment(){}
    @Override
    public void onAttach(Activity activity) {
        context=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details_audit, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Detalles");

        init();
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        setupTabIcons(viewPager);

        return rootView;
    }

    private void setupTabIcons(ViewPager viewPager) {

        if(typeUser==0){
            TabAdapter adapter = new TabAdapter(getChildFragmentManager());
            adapter.addFrag(new UpdateAuditFragment(), 1, "Auditoria");//Auditoria de certificacion
            adapter.addFrag(new NotesFragment(), 2,  "Notas");//
            adapter.addFrag(new ListAuditorsFragment(), 3,  "Auditores");//
            adapter.addFrag(new AuditPlan2Fragment(), 3,  "Plan");//

            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);

            TextView tabAudit = (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            tabAudit.setText("Auditoria");
            tabAudit.setGravity(Gravity.CENTER);
            tabLayout.getTabAt(0).setCustomView(tabAudit);


            TextView tabNotes = (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            tabNotes.setText("Notas");
            tabNotes.setGravity(Gravity.CENTER);
            tabLayout.getTabAt(1).setCustomView(tabNotes);

            TextView tabAuditor = (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            tabAuditor.setText("Auditores");
            tabAuditor.setGravity(Gravity.CENTER);
            tabLayout.getTabAt(2).setCustomView(tabAuditor);

            TextView tabPlan = (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            tabPlan.setText("Plan");
            tabPlan.setGravity(Gravity.CENTER);
            tabLayout.getTabAt(3).setCustomView(tabPlan);
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment;

        switch (item.getItemId()) {

            case R.id.action_cars:
                showCars();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void showCars(){
        Fragment fragment = new ListCorrectiveActionsFragment();
            String TAG = "TEST_A";
            FragmentTransaction fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();
            //fragmentManager.setCustomAnimations(R.anim.slide_zoom_back_in, R.anim.slide_zoom_back_out);

            fragmentManager.replace(R.id.main_content, fragment, TAG);
            fragmentManager.addToBackStack(TAG);
            fragmentManager.commit();
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


    private void init(){    }
}

