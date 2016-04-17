package brandon.payboy.brandon.viewmodels;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.widget.Chronometer;

import brandon.payboy.brandon.util.TimerHandler;

public class TimeViewModel extends BaseObservable {

    public ObservableField<String> displayTime = new ObservableField<>();
    private TimeViewModelListener listener;
    private TimerHandler timerHandler;

    public TimeViewModel(TimeViewModelListener listener) {
        this.listener = listener;
    }

    public void setup() {
        timerHandler = new TimerHandler(this::update);
        displayTime.set("00:00:00");
    }

    public void update() {
        if (this.listener != null && this.listener.getRunningActivity() != null) {
            this.listener.getRunningActivity().runOnUiThread(this::showElapsedTime);
        }
    }

    private void showElapsedTime() {
        if (this.listener == null) {
            return;
        }

        long elapsedSeconds = (this.listener.getElapsedRealTime() - this.listener.getChronometer()
                .getBase()) / 1000;
        this.listener.onTimeChanged(elapsedSeconds);
    }

    public void clearValues() {
        timerHandler.stopUpdating();
        this.listener.getChronometer().stop();
        displayTime.set("00:00:00");
    }

    public void startCalculating() {
        long stoppedMilliseconds = 0;

        // Creates Timer view and formats it
        String chronoText = this.listener.getChronometer().getText().toString();
        String array[] = chronoText.split(":");

        if (array.length == 3) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                    + Integer.parseInt(array[1]) * 60 * 1000
                    + Integer.parseInt(array[2]) * 1000;
        }

        this.listener.getChronometer().setBase(this.listener.getElapsedRealTime() - stoppedMilliseconds);
        this.listener.getChronometer().setOnChronometerTickListener(chronometer -> onChronometerTick());
        this.listener.getChronometer().start();

        timerHandler.startUpdating();
    }

    private void onChronometerTick() {
        if (listener == null) {
            return;
        }

        long time = this.listener.getElapsedRealTime() - this.listener.getChronometer().getBase();
        updateDisplayTime(time);
    }

    public void updateDisplayTime(long time) {
        int h = (int) (time / 3600000);
        int m = (int) (time - h * 3600000) / 60000;
        int s = (int) (time - h * 3600000 - m * 60000) / 1000;
        String hh = h < 10 ? "0" + h : h + "";
        String mm = m < 10 ? "0" + m : m + "";
        String ss = s < 10 ? "0" + s : s + "";
        this.displayTime.set(hh + ":" + mm + ":" + ss);
    }

    public void stopCalculating() {
        timerHandler.stopUpdating();
        this.listener.getChronometer().stop();
    }

    public interface TimeViewModelListener {
        void onTimeChanged(long elapsedSeconds);

        Chronometer getChronometer();

        Activity getRunningActivity();

        long getElapsedRealTime();
    }
}