package globalstd.globalaudit.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v4.app.FragmentTransaction;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import globalstd.globalaudit.GlobalAuditException;
import globalstd.globalaudit.R;
import globalstd.globalaudit.adapters.UserAdapter;
import globalstd.globalaudit.objects.User;
import globalstd.globalaudit.services.AuthService;

/**
 * Created by Gabriel Vazquez on 14/03/2017.
 */

public class ListUsersFragment extends BaseFragment {
    @Inject
    AuthService authService;

    View rootView;
    private RelativeLayout voidList;
    private List<User> listUsers;
    RecyclerView list;
    FloatingActionButton btnAdd;

    private UserAdapter adapter;

    public static Context context;

    public ListUsersFragment(){}
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

                Fragment fragment= new AddUserFragment();
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
        listUsers = new ArrayList<>();

//        listUsers.add( new User(1, "Paola Padilla", "contacto@coca.com", null, "Administrativa", "Frances"));
//        listUsers.add( new User(1, "Gloria Ortis", "ventas@coca.com", null, "Ventas", "Ingles"));
//        listUsers.add( new User(1, "Jumena Guzman", "stock@c0ca.com", null, "Cobranz", "Español"));

    }
    private void initializeAdapter(){
        adapter = new UserAdapter(context, listUsers);
        list.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

        if(listUsers.size() == 0){
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
        }
    }

    UserAdapter.OnItemClickListener onItemClickListener = new UserAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

            //Fragment fragment = new DetailsSupplierFragment(listSuppliers.get(position).getId());
            Fragment fragment = new AddUserFragment();
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
    public void onStart() {
        super.onStart();
        this.eventBus.post(new GetUsersEvent());
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

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onGetUsersEvent(GetUsersEvent event) {
        List<User> users = null;
        GlobalAuditException error = null;
        try {
            users = this.authService.getUsers(0, 20);
        } catch (GlobalAuditException e) {
            error = e;
        }
        this.eventBus.post(new GetUsersResponseEvent(users, error));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetUsersResponseEvent(GetUsersResponseEvent event) {
        if (event.error == null) {
            this.listUsers.clear();
            this.listUsers.addAll(event.users);
            this.adapter.notifyDataSetChanged();

            this.list.setVisibility(View.VISIBLE);
        }
    }


    private static class GetUsersEvent {}

    private static class GetUsersResponseEvent {
        public List<User> users;
        public GlobalAuditException error;

        public GetUsersResponseEvent(List<User> users, GlobalAuditException error) {
            this.users = users;
            this.error = error;
        }
    }
}

