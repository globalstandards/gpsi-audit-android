package globalstd.globalaudit;

import android.app.Application;

import java.util.Arrays;

import dagger.ObjectGraph;
import globalstd.globalaudit.services.AppModule;

/**
 * Created by software on 21/03/17.
 */

public class GlobalAuditApp extends Application {
    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(Arrays.asList(new AppModule()).toArray());
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }
}
