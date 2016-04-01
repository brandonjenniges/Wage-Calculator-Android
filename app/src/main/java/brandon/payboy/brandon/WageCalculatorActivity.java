package brandon.payboy.brandon;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.payboy.brandon.R;

import brandon.payboy.brandon.fragments.ButtonFragment;
import brandon.payboy.brandon.fragments.MoneyFragment;
import brandon.payboy.brandon.fragments.TimeFragment;


public class WageCalculatorActivity extends FragmentActivity implements ButtonFragment.ButtonFragmentClickListener, TimeFragment.TimeDisplayListener {

    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wage_activity);
    }

    @Override
    public void onPlayButtonClick() {
        if (isRunning) return;

        isRunning = true;
        TimeFragment timeFragment =
                (TimeFragment)
                        getFragmentManager().findFragmentById(R.id.time_fragment);
        timeFragment.startCalculating();
    }

    @Override
    public void onPauseButtonClick() {
        isRunning = false;

        TimeFragment timeFragment =
                (TimeFragment)
                        getFragmentManager().findFragmentById(R.id.time_fragment);
        timeFragment.stopCalculating();
    }

    @Override
    public void onClearButtonClick() {
        isRunning = false;
        // Clear Money Fragment
        MoneyFragment moneyFragment =
                (MoneyFragment)
                        getFragmentManager().findFragmentById(R.id.money_fragment);
        moneyFragment.clearValues();

        // Clear Time Fragment
        TimeFragment timeFragment =
                (TimeFragment)
                        getFragmentManager().findFragmentById(R.id.time_fragment);
        timeFragment.clearValues();
    }


    @Override
    public void onTimeChanged(long elapsedSeconds) {
        MoneyFragment moneyFragment =
                (MoneyFragment)
                        getFragmentManager().findFragmentById(R.id.money_fragment);
        moneyFragment.setMoneyValue(elapsedSeconds);
    }
}
