package globalstd.globalaudit.activitys;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import dagger.ObjectGraph;
import globalstd.globalaudit.GlobalAuditApp;
import globalstd.globalaudit.services.AppModule;

/**
 * Created by software on 21/03/17.
 */

public class BaseActivity extends AppCompatActivity {
    @Inject
    protected EventBus eventBus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((GlobalAuditApp) getApplication()).inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventBus.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDummyEvent(DummyEvent event) {
    }

    private static class DummyEvent {}
}
