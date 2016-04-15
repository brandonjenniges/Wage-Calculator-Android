package brandon.payboy.brandon.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Locale;

public class MoneyViewModel extends BaseObservable {

    private MoneyViewModelListener listener;
    private double wageValue;

    public ObservableField<String> displayMoney = new ObservableField<>();

    public MoneyViewModel(MoneyViewModelListener listener, double wageValue) {
        this.listener = listener;
        this.wageValue = wageValue;
    }

    public void setup() {
        displayMoney.set("0.00");
    }

    public void clearValues() {
        displayMoney.set("0.00");
    }

    public void setMoneyValue(long elapsedSeconds) {
        double ratePerHour = wageValue / 3600;
        double moneyValue = ratePerHour * elapsedSeconds;
        displayMoney.set(String.format(Locale.US, "%.2f", moneyValue));
    }

    @SuppressWarnings("unused")
    public void settingsPressed(@NonNull final View view) {
        if (listener != null) {
            listener.settingsPressed();
        }
    }

    public interface MoneyViewModelListener {
        void settingsPressed();
    }
}
