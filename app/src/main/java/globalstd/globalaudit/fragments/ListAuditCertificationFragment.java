package globalstd.globalaudit.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import globalstd.globalaudit.R;
import globalstd.globalaudit.adapters.AuditCertificationAdapter;
import globalstd.globalaudit.objects.Audit;
import globalstd.globalaudit.objects.AuditStatus;
import globalstd.globalaudit.objects.Auditor;
import globalstd.globalaudit.objects.Data;
import globalstd.globalaudit.objects.AuditSupplier;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;

/**
 * Created by Gabriel Vazquez on 28/02/2017.
 */

public class ListAuditCertificationFragment extends Fragment {
    View rootView;
    private RelativeLayout voidList;
    private List<Audit> listAudits;
    RecyclerView list;
    private AuditCertificationAdapter adapter;

    public static Context context;

    public ListAuditCertificationFragment(){}
    @Override
    public void onAttach(Activity activity) {
        this.context=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recycler, container, false);

        /*sport=new Sport();
        Bundle args = getArguments();
        if (args  != null) {
            sport.setIdSport(args.getInt("idSport"));
            sport.setSportName(args.getString("nameSport"));
        }

        // Populate our list with groups and it's children ( Continentes )
        List<Continent> groupContinentItem =  getAllGroupContinentItems();
        for(int i = 0; i < groupContinentItem.size(); i++) {
            List<Countries> CountrieItem =  getAllCountriesItems(groupContinentItem.get(i).getId());
            if(CountrieItem.size()>0) {
                groupContinentItem.get(i).setCountrieItem(CountrieItem);
            }

        */
        list = (RecyclerView) rootView.findViewById(R.id.list);
        initializeData();
        initializeAdapter();




        return rootView;
    }

    private void initializeData(){
        listAudits = new ArrayList<>();
        Data data = new Data("05/06/2017", "14:00 pm");
        Auditor leaderAuditor = new Auditor(1, "Alba", "Castro", "Lider");

        Data data1 = new Data("06/06/20017", "09:00 am");
        Auditor leaderAuditor1 = new Auditor(1, "Arturo", "Gonzalez", "Observador");

        AuditStatus status = new AuditStatus(1, "Completo");
        listAudits.add(new Audit(1,"ISO 9001", data, status, leaderAuditor));
        listAudits.add(new Audit(2,"ISO 1400", data1, status, leaderAuditor1));
        listAudits.add(new AuditSupplier(3,"ISO 2200", data1, status, leaderAuditor1, "Dulces Candy"));

    }
    private void initializeAdapter(){
        if(listAudits.size()==0){
            list.setVisibility(View.GONE);

            voidList = (RelativeLayout) rootView.findViewById(R.id.voidList);
            voidList.setVisibility(View.VISIBLE);

            ImageView imgIconMenu = (ImageView) rootView.findViewById(R.id.imgIconMenu);
            TextView lblMenuTitle = (TextView) rootView.findViewById(R.id.lblMenuTitle);
            TextView lblVoidDescription = (TextView) rootView.findViewById(R.id.lblVoidDescription);

            imgIconMenu.setImageResource(R.drawable.ic_checklist);
            lblMenuTitle.setText(R.string.menu_home);
            lblVoidDescription.setText(R.string.void_audit);

        }else {
            list.setVisibility(View.VISIBLE);

            adapter = new AuditCertificationAdapter(getContext(), listAudits);
            list.setAdapter(adapter);
            adapter.setOnItemClickListener(onItemClickListener);
        }
    }

    AuditCertificationAdapter.OnItemClickListener onItemClickListener = new AuditCertificationAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            //Fragment fragment = new DetailsSupplierFragment(listSuppliers.get(position).getId());
            Fragment fragment = new DetailsAuditFragment();
            String TAG = "TEST_A";
            FragmentTransaction fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();
            //fragmentManager.setCustomAnimations(R.anim.slide_zoom_back_in, R.anim.slide_zoom_back_out);

            fragmentManager.replace(R.id.main_content, fragment, TAG);
            fragmentManager.addToBackStack(TAG);
            fragmentManager.commit();

        }
    };


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
        //  ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.title_fragment_contacts));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}

