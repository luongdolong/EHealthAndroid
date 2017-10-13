package vn.gov.ehealth.dkonline.ehealth.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by LuongDoLong on 8/9/2017.
 */

public class CommonUtils {
    // Sharedpref file name
    private static final String PREF_NAME = "AutomateTrackingPref";

    /**
     * put data in SharedPreferences
     *
     * @param context app context
     * @param key     key get value
     * @param value   value
     * @author luongdolong
     */
    public static void putPref(Context context, String key, Object value) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        editor.commit();
    }

    /**
     * get String from SharedPreferences
     *
     * @param context app context
     * @param key     key
     * @return value
     * @author luongdolong
     */
    public static String getPrefString(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(key, Constant.EMPTY);
    }

    /**
     * get Boolean from SharedPreferences
     *
     * @param context app context
     * @param key     key
     * @return value
     * @author luongdolong
     */
    public static boolean getPrefBoolean(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    /**
     * get Integer from SharedPreferences
     *
     * @param context app context
     * @param key     key
     * @return value
     * @author luongdolong
     */
    public static int getPrefInteger(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getInt(key, 0);
    }

    /**
     * get Long from SharedPreferences
     *
     * @param context app context
     * @param key     key
     * @return value
     * @author luongdolong
     */
    public static long getPrefLong(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getLong(key, 0);
    }

    /**
     * get Float from SharedPreferences
     *
     * @param context app context
     * @param key     key
     * @return value
     * @author luongdolong
     */
    public static float getPrefFloat(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getFloat(key, 0);
    }

    /**
     * remove value from SharedPreferences with key
     *
     * @param context context
     * @param key     key
     * @author luongdolong
     */
    public static void removePref(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * check input is empty
     *
     * @param string input
     * @return true if empty, false if not
     * @author luongdolong
     */
    public static boolean isEmpty(String string) {
        if (string == null) {
            return true;
        }
        if (string.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Check connection state internet
     *
     * @param context
     * @return
     * @uthor longld
     */
    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }

    /**
     * check internet
     *
     * @param context
     * @return true if have internet, false if not
     * @author luongdolong
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    /**
     * convert date to string with format
     *
     * @param date   date
     * @param format format
     * @return string date with format pass
     * @author longld
     */
    public static String date2str(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat mySimpleDateFormat = new SimpleDateFormat(format);
        return mySimpleDateFormat.format(date);
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public static float meterDistanceBetweenPoints(double startLat, double startLng, double endLat, double endLng) {
        Location locationA = new Location("point A");

        locationA.setLatitude(startLat);
        locationA.setLongitude(startLng);

        Location locationB = new Location("point B");

        locationB.setLatitude(endLat);
        locationB.setLongitude(endLng);

        float distance = locationA.distanceTo(locationB);
        return distance;
    }
}
