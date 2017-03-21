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
import android.widget.EditText;
import android.widget.RelativeLayout;

import globalstd.globalaudit.R;
import globalstd.globalaudit.activitys.MainActivity;

/**
 * Created by Gabriel Vazquez on 15/03/2017.
 */

public class NotesFragment extends Fragment {
    View rootView;
    public static Context context;

    private static final String TAG_LIST_MATCH = "tag_list_match";

    public NotesFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        context = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notes, container, false);
        //((MainActivity) getActivity()).setActionBarTitle("Notas");

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
        //((MainActivity) getActivity()).setActionBarTitle("Notas");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void init() {
        RelativeLayout btnAccept = (RelativeLayout) rootView.findViewById(R.id.btnAccept);
        btnAccept.setVisibility(View.GONE);

        EditText txtNotes = (EditText) rootView.findViewById(R.id.txtNotes);
        txtNotes.setEnabled(false);

    }

}

