package brandon.payboy.brandon.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by brandonjenniges on 4/8/15.
 */
public class AppPreferences {

    public static final String KEY_WAGE_VALUE = "wage_value";
    public static final String KEY_NOTIFICATION = "notification_value";

    private static final String APP_SHARED_PREFS = AppPreferences.class.getSimpleName();
    private static SharedPreferences sharedPrefs;
    private static SharedPreferences.Editor prefsEditor;

    public AppPreferences(Context context) {
        sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        prefsEditor = sharedPrefs.edit();
    }

    public boolean isNotificationEnabled() {
        return sharedPrefs.getBoolean(KEY_NOTIFICATION, true);
    }

    public void setNotificationEnabled(boolean enabled) {
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

    public void setWageValue(double wageValue) {
        String wageString = String.valueOf(wageValue);
        prefsEditor.putString(KEY_WAGE_VALUE, wageString);
        prefsEditor.commit();
    }
}
