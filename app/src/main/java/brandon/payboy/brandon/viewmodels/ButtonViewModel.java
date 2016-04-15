package brandon.payboy.brandon.viewmodels;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.view.View;

public class ButtonViewModel extends BaseObservable {

    private ButtonViewModelListener listener;

    public ButtonViewModel(ButtonViewModelListener listener) {
        this.listener = listener;
    }

    @SuppressWarnings("unused")
    public void playPressed(@NonNull final View view) {
        if (listener != null) {
            listener.playPressed();
        }
    }

    @SuppressWarnings("unused")
    public void pausePressed(@NonNull final View view) {
        if (listener != null) {
            listener.pausePressed();
        }
    }

    @SuppressWarnings("unused")
    public void resetPressed(@NonNull final View view) {
        if (listener != null) {
            listener.resetPressed();
        }
    }

    public interface ButtonViewModelListener {
        void playPressed();
        void pausePressed();
        void resetPressed();
    }
}
