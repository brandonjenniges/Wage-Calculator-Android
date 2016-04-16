package brandon.payboy.brandon.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.payboy.brandon.R;

import brandon.payboy.brandon.ui.activity.WageCalculatorActivity;

public class Notification {

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private Activity activity;

    public Notification(Activity activity) {
        this.activity = activity;
        this.notificationManager = ((NotificationManager) activity.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE));
    }

    private void create() {
        builder = new NotificationCompat.Builder(
                activity).setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Wage Calculator")
                .setContentText("$0.00");

        builder.setOngoing(true);
        builder.setOnlyAlertOnce(true);
        Intent toLaunch = new Intent(activity, WageCalculatorActivity.class);

        toLaunch.setAction("android.intent.action.MAIN");
        toLaunch.addCategory("android.intent.category.LAUNCHER");
        toLaunch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intentBack = PendingIntent.getActivity(
                activity, 0, toLaunch,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intentBack);
    }

    public void update(String value) {
        if (builder == null) {
            create();
        }
        builder.setContentText(value);
        int NOTIFICATION_ID = 1;
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void cancel() {
        notificationManager.cancelAll();
    }

}