package brandon.payboy.brandon.util;

import android.os.SystemClock;

public class SystemClockWrapper {
    public static long getElapsedRealTime() {
        return SystemClock.elapsedRealtime();
    }
}
