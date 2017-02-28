package globalstd.globalaudit.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import globalstd.globalaudit.R;
import globalstd.globalaudit.activitys.MainActivity;
import globalstd.globalaudit.adapters.TabAdapter;

/**
 * Created by Gabriel Vazquez on 28/02/2017.
 */

public class HomeFragment  extends Fragment {
    View rootView;
    public static Context context;

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int typeUser = 0 ;

    private static final String TAG_LIST_MATCH= "tag_list_match";

    public HomeFragment(){}
    @Override
    public void onAttach(Activity activity) {
        context=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.menu_home));

        init();
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        setupTabIcons(viewPager);

        return rootView;
    }

    private void setupTabIcons(ViewPager viewPager) {

        if(typeUser==0){
            TabAdapter adapter = new TabAdapter(getChildFragmentManager());
            adapter.addFrag(new ListAuditFragment(), 1, "Certificaciónes");//Auditoria de certificacion
            adapter.addFrag(new ListAuditFragment(), 2,  "Provedores");//

            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);

            TextView tabCertification = (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            tabCertification.setText("Certificaciónes");
            tabCertification.setGravity(Gravity.CENTER);

            //tabCertification.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_launcher, 0, 0, 0);
            //tabCertification.setCompoundDrawablePadding(15);
            tabLayout.getTabAt(0).setCustomView(tabCertification);


            TextView tabSuppliers = (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            tabSuppliers.setText("Provedores");
            tabSuppliers.setGravity(Gravity.CENTER_HORIZONTAL);
            //tabSuppliers.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_launcher, 0, 0, 0);
            //tabSuppliers.setCompoundDrawablePadding(15);
            tabLayout.getTabAt(1).setCustomView(tabSuppliers);
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


    private void init(){    }
}

