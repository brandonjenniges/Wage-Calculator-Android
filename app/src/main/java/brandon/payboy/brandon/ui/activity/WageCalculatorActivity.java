package brandon.payboy.brandon.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.payboy.brandon.R;

import brandon.payboy.brandon.ui.fragment.ButtonFragment;
import brandon.payboy.brandon.ui.fragment.MoneyFragment;
import brandon.payboy.brandon.ui.fragment.TimeFragment;


public class WageCalculatorActivity extends FragmentActivity implements ButtonFragment.ButtonFragmentClickListener, TimeFragment.TimeDisplayListener {

    private boolean isRunning;
    private TimeFragment timeFragment;
    private MoneyFragment moneyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeFragment = (TimeFragment) getFragmentManager().findFragmentById(R.id.time_fragment);
        moneyFragment = (MoneyFragment) getFragmentManager().findFragmentById(R.id.money_fragment);
    }

    @Override
    public void onPlayButtonClick() {
        if (isRunning) return;
        isRunning = true;
        timeFragment.startCalculating();
    }

    @Override
    public void onPauseButtonClick() {
        isRunning = false;
        timeFragment.stopCalculating();
    }

    @Override
    public void onClearButtonClick() {
        isRunning = false;
        moneyFragment.clearValues();
        timeFragment.clearValues();
    }

    @Override
    public void onTimeChanged(long elapsedSeconds) {
        moneyFragment.setMoneyValue(elapsedSeconds);
    }
}
