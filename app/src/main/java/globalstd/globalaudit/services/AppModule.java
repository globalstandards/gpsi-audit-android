package globalstd.globalaudit.services;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import globalstd.globalaudit.activitys.LoginActivity;

/**
 * Created by software on 21/03/17.
 */

@Module(injects = LoginActivity.class)
public class AppModule {
    @Provides @Singleton
    EventBus provideEventBus() {
        return EventBus.getDefault();
    }

    @Provides @Singleton
    AuthService provideAuthService() {
        return new AuthService();
    }
}
