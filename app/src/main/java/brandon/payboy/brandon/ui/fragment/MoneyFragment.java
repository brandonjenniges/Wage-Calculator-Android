package brandon.payboy.brandon.ui.fragment;


import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payboy.brandon.R;

import brandon.payboy.brandon.ui.activity.WageCalculatorActivity;
import brandon.payboy.brandon.ui.activity.SettingsActivity;
import brandon.payboy.brandon.util.AppPreferences;
import brandon.payboy.brandon.util.AutoResizeTextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoneyFragment extends Fragment {

    @Bind(R.id.money_tv)
    AutoResizeTextView mMoneyTextView;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    private boolean displayNotification;
    private double wageValue;

    public MoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        AppPreferences appPrefs = new AppPreferences(context);
        wageValue = appPrefs.getWageValue();
        displayNotification = appPrefs.isNotificationEnabled();

        mNotificationManager = (NotificationManager) getActivity().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_money,
                container, false);
        ButterKnife.bind(this, view);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mplus-1c-black.ttf");
        mMoneyTextView.setTypeface(font);
        return view;
    }

    public void clearValues() {
        mMoneyTextView.setText("0.00");
        mNotificationManager.cancelAll();
    }

    public void setMoneyValue(long elapsedSeconds) {
        double ratePerHour = wageValue / 3600;
        double moneyValue = ratePerHour * elapsedSeconds;
        mMoneyTextView.setText(String.format("%.2f", moneyValue));

        Log.d("MONEY", String.format("%.2f", moneyValue));
        //Only show notification if setting is enabled
        if (displayNotification) {
            if (mBuilder == null) {
                createNotification(0.00);
            }
            mBuilder.setContentText(String.format("$%.2f", moneyValue));
            int NOTIFICATION_ID = 1;
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
    }

    public void createNotification(Double moneyValue) {
        mBuilder = new NotificationCompat.Builder(
                getActivity()).setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Wage Calculator")
                .setContentText(String.format("$%.2f", moneyValue));

        mBuilder.setOngoing(true);
        mBuilder.setOnlyAlertOnce(true);
        Intent toLaunch = new Intent(getActivity(),
                WageCalculatorActivity.class);

        toLaunch.setAction("android.intent.action.MAIN");

        toLaunch.addCategory("android.intent.category.LAUNCHER");
        toLaunch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intentBack = PendingIntent.getActivity(
                getActivity(), 0, toLaunch,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(intentBack);
    }

    @OnClick(R.id.settings_btn)
    public void onNotificationToggle(View view) {
        startActivity(new Intent(getActivity(), SettingsActivity.class));
    }
}
