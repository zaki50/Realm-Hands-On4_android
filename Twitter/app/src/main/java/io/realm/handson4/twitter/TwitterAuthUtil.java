package io.realm.handson4.twitter;

import android.content.Context;
import android.content.SharedPreferences;

import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public final class TwitterAuthUtil {
    private static String packageName;

    private static final String PREF_NAME = "auth";
    private static final String KEY_REQUEST_TOKEN = "requestToken";
    private static final String KEY_REQUEST_TOKEN_SECRET = "requestTokenSecret";
    private static final String KEY_ACCESS_TOKEN = "accessToken";
    private static final String KEY_ACCESS_TOKEN_SECRET = "accessTokenSecret";
    private static final String KEY_ACCESS_USER = "accessUser";

    public static void init(Context context) {
        TwitterAuthUtil.packageName = context.getPackageName();
    }

    public static String getCallbackUrlString() {
        if (packageName == null) {
            throw new RuntimeException("please call TwitterAuthUtil.init() first.");
        }
        return packageName + ".callback://CallBackActivity";
    }

    public synchronized static void saveRequestToken(Context context, RequestToken requestToken) {
        getPreference(context).edit()
                .putString(KEY_REQUEST_TOKEN, requestToken.getToken())
                .putString(KEY_REQUEST_TOKEN_SECRET, requestToken.getTokenSecret())
                .apply();
    }

    public synchronized static RequestToken loadRequestToken(Context context) {
        final SharedPreferences pref = getPreference(context);

        final String token = pref.getString(KEY_REQUEST_TOKEN, null);
        final String secret = pref.getString(KEY_REQUEST_TOKEN_SECRET, null);
        if (token == null || secret == null) {
            return null;
        }
        return new RequestToken(token, secret);
    }

    public synchronized static void saveAccessToken(Context context, AccessToken accessToken) {
        getPreference(context).edit()
                .putString(KEY_ACCESS_TOKEN, accessToken.getToken())
                .putString(KEY_ACCESS_TOKEN_SECRET, accessToken.getTokenSecret())
                .putLong(KEY_ACCESS_USER, accessToken.getUserId())
                .apply();
    }

    public synchronized static AccessToken loadAccessToken(Context context) {
        final SharedPreferences pref = getPreference(context);

        final String token = pref.getString(KEY_ACCESS_TOKEN, null);
        final String secret = pref.getString(KEY_ACCESS_TOKEN_SECRET, null);
        if (token == null || secret == null) {
            return null;
        }
        return new AccessToken(token, secret, pref.getLong(KEY_ACCESS_USER, -1L));
    }

    public synchronized static void clearTokens(Context context) {
        getPreference(context).edit()
                .clear()
                .apply();
    }

    private static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
