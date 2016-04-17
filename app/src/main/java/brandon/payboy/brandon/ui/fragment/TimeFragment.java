package brandon.payboy.brandon.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.payboy.brandon.R;
import com.payboy.brandon.databinding.FragmentMainTimeBinding;

import brandon.payboy.brandon.util.SystemClockWrapper;
import brandon.payboy.brandon.viewmodels.TimeViewModel;

public class TimeFragment extends Fragment implements TimeViewModel.TimeViewModelListener {

    TimeDisplayListener activityCallback;
    private Chronometer mTimeDisplayChronometer;

    FragmentMainTimeBinding binding;
    TimeViewModel timeViewModel;

    public TimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activityCallback = (TimeDisplayListener) getActivity();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_time, container, false);
        View view = binding.getRoot();
        timeViewModel = new TimeViewModel(this);
        binding.setViewModel(timeViewModel);
        timeViewModel.setup();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mplus-1c-black.ttf");
        mTimeDisplayChronometer = (Chronometer) view.findViewById(R.id.time_display_tv);
        mTimeDisplayChronometer.setTypeface(font);

        return view;
    }

    public void clearValues() {
        timeViewModel.clearValues();
    }

    public void startCalculating() {
        timeViewModel.startCalculating();
    }

    public void stopCalculating() {
        timeViewModel.stopCalculating();
    }

    @Override
    public void onTimeChanged(long elapsedSeconds) {
        activityCallback.onTimeChanged(elapsedSeconds);
    }

    @Override
    public Chronometer getChronometer() {
        return mTimeDisplayChronometer;
    }

    @Override
    public Activity getRunningActivity() {
        return getActivity();
    }

    @Override
    public long getElapsedRealTime() {
        return SystemClockWrapper.getElapsedRealTime();
    }

    public interface TimeDisplayListener {
        void onTimeChanged(long elapsedSeconds);
    }
}