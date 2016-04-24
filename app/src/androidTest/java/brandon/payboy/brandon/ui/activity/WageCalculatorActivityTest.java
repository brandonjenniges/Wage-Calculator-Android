package brandon.payboy.brandon.ui.activity;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import com.payboy.brandon.R;

import brandon.payboy.brandon.util.AppPreferences;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
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
        appPrefs.setWageValue(36);

        activity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void test_PlayButtonStartsCalculations() throws  Exception {
        onView(withText("0.00")).check(matches(isDisplayed()));
        onView(withText("00:00:00")).check(matches(isDisplayed()));
        onView(withId(R.id.play_button)).perform(click());

        Thread.sleep(2000L);
        onView(withText("0.02")).check(matches(isDisplayed()));
        onView(withText("00:00:02")).check(matches(isDisplayed()));
    }

    public void test_PlayPressWhilePlayingDoesNothing() throws  Exception {
        onView(withText("0.00")).check(matches(isDisplayed()));
        onView(withText("00:00:00")).check(matches(isDisplayed()));
        onView(withId(R.id.play_button)).perform(click());

        Thread.sleep(2000L);
        onView(withId(R.id.play_button)).perform(click());
        Thread.sleep(2000L);

        onView(withText("0.04")).check(matches(isDisplayed()));
        onView(withText("00:00:04")).check(matches(isDisplayed()));
    }

    public void test_PauseButtonStopsCalculations() throws  Exception {
        onView(withText("0.00")).check(matches(isDisplayed()));
        onView(withText("00:00:00")).check(matches(isDisplayed()));
        onView(withId(R.id.play_button)).perform(click());

        Thread.sleep(2000L);
        onView(withId(R.id.pause_button)).perform(click());

        Thread.sleep(2000L);
        onView(withText("0.02")).check(matches(isDisplayed()));
        onView(withText("00:00:02")).check(matches(isDisplayed()));
    }

    public void test_PlayAfterPauseContinuesCalculations() throws  Exception {
        onView(withText("0.00")).check(matches(isDisplayed()));
        onView(withText("00:00:00")).check(matches(isDisplayed()));
        onView(withId(R.id.play_button)).perform(click());

        Thread.sleep(2000L);
        onView(withId(R.id.pause_button)).perform(click());

        Thread.sleep(2000L);
        onView(withId(R.id.play_button)).perform(click());
        Thread.sleep(2000L);

        onView(withText("0.04")).check(matches(isDisplayed()));
        onView(withText("00:00:04")).check(matches(isDisplayed()));
    }

    public void test_ClearButtonStopsAndResetsCalculations() throws  Exception {
        onView(withText("0.00")).check(matches(isDisplayed()));
        onView(withText("00:00:00")).check(matches(isDisplayed()));
        onView(withId(R.id.play_button)).perform(click());

        Thread.sleep(2000L);
        onView(withId(R.id.clear_button)).perform(click());

        Thread.sleep(2000L);
        onView(withText("0.00")).check(matches(isDisplayed()));
        onView(withText("00:00:00")).check(matches(isDisplayed()));
    }

}