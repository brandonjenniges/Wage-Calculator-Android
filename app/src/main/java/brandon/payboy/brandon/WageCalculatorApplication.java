package brandon.payboy.brandon;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.payboy.brandon.BuildConfig;

import brandon.payboy.brandon.ui.activity.WageCalculatorActivity;
import io.fabric.sdk.android.Fabric;

public class WageCalculatorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.USE_CRASHLYTICS) {
            Fabric.with(this, new Crashlytics());
        }

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (activity instanceof WageCalculatorActivity) {
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.cancelAll();
                }
            }
        });
    }

}
