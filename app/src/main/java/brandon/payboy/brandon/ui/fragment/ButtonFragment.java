package brandon.payboy.brandon.ui.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payboy.brandon.R;
import com.payboy.brandon.databinding.FragmentMainButtonsBinding;

import brandon.payboy.brandon.viewmodels.ButtonViewModel;

public class ButtonFragment extends Fragment implements ButtonViewModel.ButtonViewModelListener{

    private ButtonFragmentClickListener activityCallback;
    private FragmentMainButtonsBinding binding;
    private ButtonViewModel buttonViewModel;

    public ButtonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activityCallback = (ButtonFragmentClickListener) getActivity();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_buttons, container, false);
        View view = binding.getRoot();
        buttonViewModel = new ButtonViewModel(this);
        binding.setViewModel(buttonViewModel);
        return view;
    }

    @Override
    public void playPressed() {
        if (activityCallback != null) {
            activityCallback.onPlayButtonClick();
        }
    }

    @Override
    public void pausePressed() {
        if (activityCallback != null) {
            activityCallback.onPauseButtonClick();
        }
    }

    @Override
    public void resetPressed() {
        if (activityCallback != null) {
            activityCallback.onClearButtonClick();
        }
    }

    public interface ButtonFragmentClickListener {
        void onPlayButtonClick();
        void onPauseButtonClick();
        void onClearButtonClick();
    }
}