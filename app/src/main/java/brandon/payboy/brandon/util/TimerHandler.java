package brandon.payboy.brandon.util;

import android.os.Handler;
import android.os.Looper;

public class TimerHandler {
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private Runnable mStatusChecker;
    private int INTERVAL = 1000;

    public TimerHandler(final Runnable uiUpdater) {
        mStatusChecker = new Runnable() {
            @Override
            public void run() {
                uiUpdater.run();
                mHandler.postDelayed(this, INTERVAL);
            }
        };
    }

    public TimerHandler(Runnable uiUpdater, int interval){
        this(uiUpdater);
        INTERVAL = interval;
    }

    public synchronized void startUpdating(){
        mStatusChecker.run();
    }

    public synchronized void stopUpdating(){
        mHandler.removeCallbacks(mStatusChecker);
    }
}