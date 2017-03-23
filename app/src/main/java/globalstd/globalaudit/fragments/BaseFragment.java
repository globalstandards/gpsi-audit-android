package globalstd.globalaudit.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import globalstd.globalaudit.GlobalAuditApp;

/**
 * Created by software on 22/03/17.
 */

public class BaseFragment extends Fragment {
    @Inject
    protected EventBus eventBus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((GlobalAuditApp) getActivity().getApplication()).inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        eventBus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        eventBus.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDummyEvent(DummyEvent event) {
    }

    private static class DummyEvent {}
}
