package brandon.payboy.brandon;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.payboy.brandon.BuildConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by brandonjenniges on 4/8/15.
 */
public class WageCalculatorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.USE_CRASHLYTICS) {
            Fabric.with(this, new Crashlytics());
        }

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        if (BuildConfig.USE_ANALYTICS) {
            analytics.newTracker(BuildConfig.GOOGLE_ANALYTICS_KEY);
            analytics.setAppOptOut(true);
            analytics.setLocalDispatchPeriod(20);
            analytics.enableAutoActivityReports(this);
        }
    }
}
