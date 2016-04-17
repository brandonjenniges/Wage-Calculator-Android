package brandon.payboy.brandon.ui.activity;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.Intents;
import android.test.ActivityInstrumentationTestCase2;

import com.payboy.brandon.R;

import brandon.payboy.brandon.testUtil.AppPreferencesTestWrapper;
import brandon.payboy.brandon.util.AppPreferences;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SettingsActivityTest
        extends ActivityInstrumentationTestCase2<SettingsActivity> {
    SettingsActivity activity;

    public  SettingsActivityTest() {
        super(SettingsActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        Intents.init();
        this.injectInstrumentation(InstrumentationRegistry.getInstrumentation());

        AppPreferencesTestWrapper appPreferences = new AppPreferencesTestWrapper(this.getInstrumentation().getTargetContext());
        appPreferences.clear();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        Intents.release();
    }

    public void test_ShouldEnterWithPreviousWage() throws Exception {
        AppPreferences appPreferences = new AppPreferences(this.getInstrumentation().getTargetContext());
        appPreferences.setWageValue(60.0);

        this.activity = this.getActivity();
        Thread.sleep(2000);

        Espresso.onView(withText("60.00")).check(ViewAssertions.matches(isDisplayed()));
    }

    public void test_ShouldEnterWithNoPreviousWage() throws Exception {
        this.activity = this.getActivity();
        Thread.sleep(2000);

        Espresso.onView(withId(R.id.wage_enter_et)).check(ViewAssertions.matches(withText("")));
    }
}