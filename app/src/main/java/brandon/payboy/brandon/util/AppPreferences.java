package brandon.payboy.brandon.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    public static final String KEY_WAGE_VALUE = "wage_value";
    public static final String KEY_NOTIFICATION = "notification_value";

    private static final String APP_SHARED_PREFS = AppPreferences.class.getSimpleName();
    protected static SharedPreferences sharedPrefs;
    protected static SharedPreferences.Editor prefsEditor;

    public AppPreferences(Context context) {
        sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
    }

    public boolean isNotificationEnabled() {
        return sharedPrefs.getBoolean(KEY_NOTIFICATION, true);
    }

    @SuppressLint("CommitPrefEdits")
    public void setNotificationEnabled(boolean enabled) {
        prefsEditor = sharedPrefs.edit();
        prefsEditor.putBoolean(KEY_NOTIFICATION, enabled);
        prefsEditor.commit();
    }

    public double getWageValue() {
        String wageString = sharedPrefs.getString(KEY_WAGE_VALUE, "");
        try {
            return Double.parseDouble(wageString);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @SuppressLint("CommitPrefEdits")
    public void setWageValue(double wageValue) {
        String wageString = String.valueOf(wageValue);
        prefsEditor = sharedPrefs.edit();
        prefsEditor.putString(KEY_WAGE_VALUE, wageString);
        prefsEditor.commit();
    }
}
