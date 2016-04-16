package brandon.payboy.brandon.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.payboy.brandon.R;
import com.payboy.brandon.databinding.FragmentMainTimeBinding;

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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_time, container, false);
        View view = binding.getRoot();
        timeViewModel = new TimeViewModel(this);
        binding.setViewModel(timeViewModel);
        timeViewModel.setup();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mplus-1c-black.ttf");
        mTimeDisplayChronometer = (Chronometer) view.findViewById(R.id.time_display_tv);
        mTimeDisplayChronometer.setText("00:00:00");
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

    public interface TimeDisplayListener {
        void onTimeChanged(long elapsedSeconds);
    }
}