package brandon.payboy.brandon.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.payboy.brandon.BuildConfig;
import com.payboy.brandon.R;

import brandon.payboy.brandon.WageCalculatorActivity;
import brandon.payboy.brandon.settings.SettingsActivity;
import brandon.payboy.brandon.util.AppPreferences;
import io.fabric.sdk.android.Fabric;

public class LauncherActivity extends Activity {

    private AppPreferences appPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.BUILD_TYPE.equals("release")) {
            Fabric.with(this, new Crashlytics());
        }

        setContentView(R.layout.launcher_activity);

        appPrefs = new AppPreferences(getApplicationContext());

        final Class<? extends Activity> activityClass;

        if (appPrefs.getWageValue() == 0.0) {
            activityClass = SettingsActivity.class;
        } else {
            activityClass = WageCalculatorActivity.class;
        }

        Intent newActivity = new Intent(this, activityClass);
        this.startActivity(newActivity);
        this.finish();
    }

}
