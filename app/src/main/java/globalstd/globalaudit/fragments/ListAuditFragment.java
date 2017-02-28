package globalstd.globalaudit.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import globalstd.globalaudit.R;

/**
 * Created by Gabriel Vazquez on 28/02/2017.
 */

public class ListAuditFragment  extends Fragment {
    View rootView;
    private RelativeLayout voidList;

    public static Context context;

    public ListAuditFragment(){}
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
        }


        */
        RecyclerView list = (RecyclerView) rootView.findViewById(R.id.list);
        list.setVisibility(View.GONE);

        voidList = (RelativeLayout) rootView.findViewById(R.id.voidList);
        voidList.setVisibility(View.VISIBLE);

        ImageView imgIconMenu = (ImageView) rootView.findViewById(R.id.imgIconMenu);
        TextView lblMenuTitle = (TextView) rootView.findViewById(R.id.lblMenuTitle);
        TextView lblVoidDescription = (TextView) rootView.findViewById(R.id.lblVoidDescription);

        imgIconMenu.setImageResource(R.drawable.ic_checklist);
        lblMenuTitle.setText(R.string.menu_home);
        lblVoidDescription.setText(R.string.void_audit);

        return rootView;
    }


    /*
    private void initializeAdapter(List<Continent> groupContinentItem){
        adapter = new ContinentCountriesAdapter(getActivity(), groupContinentItem, sport);
        //adapter.setData(groupLeagueItem);


        listView.setAdapter(adapter);

        // In order to show animations, we need to use a custom click handler
        // for our ExpandableListView.
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (listView.isGroupExpanded(groupPosition)) {
                    listView.collapseGroupWithAnimation(groupPosition);
                } else {
                    listView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });
    }

*/
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

