package globalstd.globalaudit.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
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

import android.support.v4.app.Fragment;

import globalstd.globalaudit.R;
import globalstd.globalaudit.adapters.AuditCertificationAdapter;
import globalstd.globalaudit.adapters.AuditSupplierAdpter;
import globalstd.globalaudit.objects.AuditStatus;
import globalstd.globalaudit.objects.Auditor;
import globalstd.globalaudit.objects.Data;
import globalstd.globalaudit.objects.AuditSupplier;

/**
 * Created by Gabriel Vazquez on 01/03/2017.
 */

public class ListAuditSuppliersFragment  extends Fragment {
    View rootView;
    private RelativeLayout voidList;
    private List<AuditSupplier> listAuditSuppliers;
    RecyclerView list;
    FloatingActionButton btnAdd;

    private AuditSupplierAdpter adapter;

    public static Context context;

    public ListAuditSuppliersFragment(){}
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recycler, container, false);

        list = (RecyclerView) rootView.findViewById(R.id.list);
        btnAdd = (FloatingActionButton) rootView.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the fragment and show it as a dialog.
                //AddAuditSupplierDialog eDialog = new AddAuditSupplierDialog();
                //eDialog.show( getFragmentManager(), "" );
                Fragment fragment= new AddAuditSupplierFragment();
                String TAG_HOME = "";

                if (fragment != null) {

                    FragmentTransaction fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();

                    //fragmentManager.setCustomAnimations(R.anim.fab_slide_in_from_right, R.anim.slide_out_right);
                    fragmentManager.replace(R.id.main_content, fragment, TAG_HOME);
                    fragmentManager.addToBackStack(null);
                    fragmentManager.commit();

                }

            }
        });

        initializeData();
        initializeAdapter();

        return rootView;
    }

    private void initializeData(){
        listAuditSuppliers = new ArrayList<>();
        Data data = new Data("05/06/2017", "14:00 pm");
        Auditor leaderAuditor = new Auditor(1, "Alba", "Castro", "Lider");

        Data data1 = new Data("06/06/20017", "09:00 am");
        Auditor leaderAuditor1 = new Auditor(1, "Arturo", "Gonzalez", "Observador");

        AuditStatus status = new AuditStatus(1, "Completo");
        listAuditSuppliers.add(new AuditSupplier(1,"ISO 9001", data, status, leaderAuditor, "Cosco"));
        listAuditSuppliers.add(new AuditSupplier(2,"ISO 1400", data1, status, leaderAuditor1, "Sams Club"));
        listAuditSuppliers.add(new AuditSupplier(3,"ISO 2200", data1, status, leaderAuditor1, "Dulces Candy"));

    }
    private void initializeAdapter(){
        if(listAuditSuppliers.size()==0){
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

            adapter = new AuditSupplierAdpter(getContext(), listAuditSuppliers);
            list.setAdapter(adapter);
            adapter.setOnItemClickListener(onItemClickListener);
        }
    }

    AuditSupplierAdpter.OnItemClickListener onItemClickListener = new AuditSupplierAdpter.OnItemClickListener() {
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

