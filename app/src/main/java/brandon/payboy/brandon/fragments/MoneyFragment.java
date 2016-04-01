package brandon.payboy.brandon.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.facebook.drawee.view.SimpleDraweeView;
import com.payboy.brandon.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;

import brandon.payboy.brandon.WageCalculatorActivity;
import brandon.payboy.brandon.settings.SettingsActivity;
import brandon.payboy.brandon.util.AppPreferences;
import brandon.payboy.brandon.util.AutoResizeTextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoneyFragment extends Fragment {

    private AppPreferences appPrefs;

    private boolean displayNotification;
    private double wageValue;

    private static int NOTIFICATION_ID = 1;
    private NumberFormat mMoneyFormatter;

    @Bind(R.id.money_tv) AutoResizeTextView mMoneyTextView;

    @Bind(R.id.logo_img) SimpleDraweeView mLogoGif;

    @Bind(R.id.settings_btn) ImageButton mSettingsButton;

    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;

    public MoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMoneyFormatter = NumberFormat.getCurrencyInstance();

        appPrefs = new AppPreferences(activity);
        wageValue = appPrefs.getWageValue();
        displayNotification = appPrefs.isNotificationEnabled();

        mNotificationManager = (NotificationManager) getActivity().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.money_fragment,
                container, false);
        ButterKnife.bind(this, view);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mplus-1c-black.ttf");
        mMoneyTextView.setTypeface(font);

        /*
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(getUriForAsset("megan.gif", "gif"))
                .setAutoPlayAnimations(true)
                .build();
        mLogoGif.setController(controller);
*/
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

    private Uri getUriForAsset(String file, String assetFolder) {
        AssetManager assetManager = getActivity().getAssets();

        InputStream in = null;
        OutputStream out = null;
        File f = new File(getActivity().getFilesDir(), file);
        if (!f.exists()) {
            Log.d("FILE", "FILE DOESNT EXIST");
            try {
                if (assetFolder != null) {
                    in = assetManager.open(assetFolder + "/" + file);
                } else {
                    in = assetManager.open(file);
                }
                out = getActivity().openFileOutput(f.getName(), Context.MODE_WORLD_READABLE);

                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch (Exception e) {
                Log.e("tag", e.getMessage());
            }
        }
        return Uri.parse("file://" + getActivity().getFilesDir() + "/" + file);
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    @OnClick(R.id.settings_btn)
    public void onNotificationToggle(View view) {
        startActivity(new Intent(getActivity(), SettingsActivity.class));
    }
}
