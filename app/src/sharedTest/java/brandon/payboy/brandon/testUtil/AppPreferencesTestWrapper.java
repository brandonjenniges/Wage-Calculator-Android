package brandon.payboy.brandon.testUtil;

import android.annotation.SuppressLint;
import android.content.Context;

import brandon.payboy.brandon.util.AppPreferences;

public class AppPreferencesTestWrapper extends AppPreferences{
    public AppPreferencesTestWrapper(Context context) {
        super(context);
    }

    @SuppressLint("CommitPrefEdits")
    public void clear() {
        prefsEditor = sharedPrefs.edit();
        prefsEditor.clear();
        prefsEditor.commit();
    }
}
