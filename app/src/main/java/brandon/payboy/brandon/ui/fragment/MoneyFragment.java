package brandon.payboy.brandon.ui.fragment;


import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payboy.brandon.R;
import com.payboy.brandon.databinding.FragmentMainMoneyBinding;

import java.util.Locale;

import brandon.payboy.brandon.ui.activity.SettingsActivity;
import brandon.payboy.brandon.ui.activity.WageCalculatorActivity;
import brandon.payboy.brandon.util.AppPreferences;
import brandon.payboy.brandon.util.AutoResizeTextView;
import brandon.payboy.brandon.viewmodels.MoneyViewModel;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MoneyFragment extends Fragment implements MoneyViewModel.MoneyViewModelListener {

    @Bind(R.id.money_tv) AutoResizeTextView moneyTextView;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    AppPreferences appPrefs;

    FragmentMainMoneyBinding binding;
    MoneyViewModel moneyViewModel;

    public MoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appPrefs = new AppPreferences(getActivity());
        mNotificationManager = (NotificationManager) getActivity().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_money, container, false);
        View view = binding.getRoot();
        moneyViewModel = new MoneyViewModel(this, appPrefs.getWageValue());
        binding.setViewModel(moneyViewModel);
        moneyViewModel.setup();

        ButterKnife.bind(this, view);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mplus-1c-black.ttf");
        moneyTextView.setTypeface(font);

        return view;
    }

    public void clearValues() {
        moneyViewModel.clearValues();
        mNotificationManager.cancelAll();
    }

    public void setMoneyValue(long elapsedSeconds) {
        moneyViewModel.setMoneyValue(elapsedSeconds);

        if (appPrefs.isNotificationEnabled()) {
            if (mBuilder == null) {
                createNotification(0.00);
            }
            mBuilder.setContentText(String.format(Locale.US, "$%.2f", appPrefs.getWageValue()));
            int NOTIFICATION_ID = 1;
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
    }

    public void createNotification(Double moneyValue) {
        mBuilder = new NotificationCompat.Builder(
                getActivity()).setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Wage Calculator")
                .setContentText(String.format(Locale.US, "$%.2f", moneyValue));

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

    @Override
    public void settingsPressed() {
        startActivity(new Intent(getActivity(), SettingsActivity.class));
    }
}
