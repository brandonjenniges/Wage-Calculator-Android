package brandon.payboy.brandon.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.payboy.brandon.R;

import brandon.payboy.brandon.util.TimerHandler;

public class TimeFragment extends Fragment {

    TimeDisplayListener activityCallback;
    private Chronometer mTimeDisplayChronometer;
    private TimerHandler mTimerHandler;

    public TimeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            activityCallback = (TimeDisplayListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TimeDisplayListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_time,
                container, false);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mplus-1c-black.ttf");
        mTimeDisplayChronometer = (Chronometer) view.findViewById(R.id.time_display_tv);
        mTimeDisplayChronometer.setText("00:00:00");
        mTimeDisplayChronometer.setTypeface(font);

        mTimerHandler = new TimerHandler(() -> update());
        return view;
    }

    public void clearValues() {
        mTimerHandler.stopUpdating();
        mTimeDisplayChronometer.stop();
        mTimeDisplayChronometer.setText("00:00:00");
    }

    public void startCalculating() {
        long stoppedMilliseconds = 0;

        // Creates Timer view and formats it
        String chronoText = mTimeDisplayChronometer.getText().toString();
        String array[] = chronoText.split(":");

        if (array.length == 2) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                    + Integer.parseInt(array[1]) * 1000;
        } else if (array.length == 3) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                    + Integer.parseInt(array[1]) * 60 * 1000
                    + Integer.parseInt(array[2]) * 1000;
        }


        mTimeDisplayChronometer.setBase(SystemClock.elapsedRealtime()
                - stoppedMilliseconds);

        mTimeDisplayChronometer.setOnChronometerTickListener(chronometer -> {
            long time = SystemClock.elapsedRealtime() - mTimeDisplayChronometer.getBase();
            int h = (int) (time / 3600000);
            int m = (int) (time - h * 3600000) / 60000;
            int s = (int) (time - h * 3600000 - m * 60000) / 1000;
            String hh = h < 10 ? "0" + h : h + "";
            String mm = m < 10 ? "0" + m : m + "";
            String ss = s < 10 ? "0" + s : s + "";
            mTimeDisplayChronometer.setText(hh + ":" + mm + ":" + ss);
        });
        mTimeDisplayChronometer.start();
        mTimerHandler.startUpdating();
    }

    public void stopCalculating() {
        mTimerHandler.stopUpdating();
        mTimeDisplayChronometer.stop();
    }

    public void update() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(this::showElapsedTime);
        }
    }

    private void showElapsedTime() {
        long elapsedSeconds = (SystemClock.elapsedRealtime() - mTimeDisplayChronometer
                .getBase()) / 1000;
        activityCallback.onTimeChanged(elapsedSeconds);
    }

    public interface TimeDisplayListener {
        void onTimeChanged(long elapsedSeconds);
    }
}