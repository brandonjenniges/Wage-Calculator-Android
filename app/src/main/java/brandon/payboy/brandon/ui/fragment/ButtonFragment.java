package brandon.payboy.brandon.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.payboy.brandon.R;

public class ButtonFragment extends Fragment {

    ButtonFragmentClickListener activityCallback;
    ImageButton mPlayButton;
    ImageButton mPauseButton;
    ImageButton mClearButton;

    public ButtonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCallback = (ButtonFragmentClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ToolbarListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_buttons, container, false);

        mPlayButton = (ImageButton) view.findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(v -> activityCallback.onPlayButtonClick());

        mPauseButton = (ImageButton) view.findViewById(R.id.pause_button);
        mPauseButton.setOnClickListener(v -> activityCallback.onPauseButtonClick());

        mClearButton = (ImageButton) view.findViewById(R.id.clear_button);
        mClearButton.setOnClickListener(v -> activityCallback.onClearButtonClick());

        return view;
    }

    public interface ButtonFragmentClickListener {
        void onPlayButtonClick();

        void onPauseButtonClick();

        void onClearButtonClick();
    }


}
