package brandon.payboy.brandon.ui.activity;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import com.payboy.brandon.R;

import brandon.payboy.brandon.util.AppPreferences;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class WageCalculatorActivityTest extends ActivityInstrumentationTestCase2<WageCalculatorActivity> {
    WageCalculatorActivity activity;

    public  WageCalculatorActivityTest() {
        super(WageCalculatorActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());

        AppPreferences appPrefs = new AppPreferences(this.getInstrumentation().getTargetContext());
        appPrefs.setWageValue(100);

        activity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void test_PlayButtonStartsContent() throws  Exception {
        onView(withText("0.00")).check(matches(isDisplayed()));
        onView(withId(R.id.play_button)).perform(click());
        Thread.sleep(2000L);
        onView(withText("0.00")).check(doesNotExist());
    }


}