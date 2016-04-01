package brandon.payboy.brandon;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.payboy.brandon.BuildConfig;

import io.fabric.sdk.android.Fabric;

public class WageCalculatorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.USE_CRASHLYTICS) {
            Fabric.with(this, new Crashlytics());
        }
    }
}
