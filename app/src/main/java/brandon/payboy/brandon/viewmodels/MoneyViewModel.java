package brandon.payboy.brandon.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Locale;

import brandon.payboy.brandon.util.AppPreferences;
import brandon.payboy.brandon.util.Notification;

public class MoneyViewModel extends BaseObservable {

    private MoneyViewModelListener listener;
    private AppPreferences appPreferences;
    private Notification notification;

    // AppPreferences convenience
    private double rate;
    private boolean isNotificationEnabled;

    public ObservableField<String> displayMoney = new ObservableField<>();

    public MoneyViewModel(MoneyViewModelListener listener, AppPreferences appPreferences, Notification notification) {
        this.listener = listener;
        this.appPreferences = appPreferences;
        this.notification = notification;
    }

    public void setup() {
        displayMoney.set("0.00");
        rate = appPreferences.getWageValue();
        isNotificationEnabled = appPreferences.isNotificationEnabled();
    }

    public void clearValues() {
        displayMoney.set("0.00");
        notification.cancel();
    }

    public void setMoneyValue(long elapsedSeconds) {
        double ratePerHour = rate / 3600;
        double moneyValue = ratePerHour * elapsedSeconds;

        String displayValue = String.format(Locale.US, "%.2f", moneyValue);
        displayMoney.set(displayValue);

        if (isNotificationEnabled) {
            notification.update(displayValue);
        }
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
