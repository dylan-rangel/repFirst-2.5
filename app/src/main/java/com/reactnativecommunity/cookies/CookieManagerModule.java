package com.reactnativecommunity.cookies;

import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import com.RNFetchBlob.RNFetchBlobConst;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import java.io.IOException;
import java.net.HttpCookie;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang3.time.TimeZones;

/* loaded from: classes2.dex */
public class CookieManagerModule extends ReactContextBaseJavaModule {
    private static final String CLEAR_BY_NAME_NOT_SUPPORTED = "Cannot remove a single cookie by name on Android";
    private static final String GET_ALL_NOT_SUPPORTED = "Get all cookies not supported for Android (iOS only)";
    private static final boolean HTTP_ONLY_SUPPORTED;
    private static final String INVALID_COOKIE_VALUES = "Unable to add cookie - invalid values";
    private static final String INVALID_DOMAINS = "Cookie URL host %s and domain %s mismatched. The cookie won't set correctly.";
    private static final String INVALID_URL_MISSING_HTTP = "Invalid URL: It may be missing a protocol (ex. http:// or https://).";
    private static final boolean USES_LEGACY_STORE;
    private CookieSyncManager mCookieSyncManager;

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "RNCookieManagerAndroid";
    }

    static {
        USES_LEGACY_STORE = Build.VERSION.SDK_INT < 21;
        HTTP_ONLY_SUPPORTED = Build.VERSION.SDK_INT >= 24;
    }

    CookieManagerModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.mCookieSyncManager = CookieSyncManager.createInstance(reactApplicationContext);
    }

    private CookieManager getCookieManager() throws Exception {
        try {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            return cookieManager;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @ReactMethod
    public void set(String str, ReadableMap readableMap, Boolean bool, Promise promise) {
        try {
            String rFC6265string = toRFC6265string(makeHTTPCookieObject(str, readableMap));
            if (rFC6265string == null) {
                promise.reject(new Exception(INVALID_COOKIE_VALUES));
            } else {
                addCookies(str, rFC6265string, promise);
            }
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setFromResponse(String str, String str2, Promise promise) {
        if (str2 == null) {
            promise.reject(new Exception(INVALID_COOKIE_VALUES));
        } else {
            addCookies(str, str2, promise);
        }
    }

    @ReactMethod
    public void flush(Promise promise) {
        try {
            getCookieManager().flush();
            promise.resolve(true);
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void removeSessionCookies(final Promise promise) {
        try {
            getCookieManager().removeSessionCookies(new ValueCallback<Boolean>() { // from class: com.reactnativecommunity.cookies.CookieManagerModule.1
                @Override // android.webkit.ValueCallback
                public void onReceiveValue(Boolean bool) {
                    promise.resolve(bool);
                }
            });
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getFromResponse(String str, Promise promise) throws URISyntaxException, IOException {
        promise.resolve(str);
    }

    @ReactMethod
    public void getAll(Boolean bool, Promise promise) {
        promise.reject(new Exception(GET_ALL_NOT_SUPPORTED));
    }

    @ReactMethod
    public void get(String str, Boolean bool, Promise promise) {
        if (isEmpty(str)) {
            promise.reject(new Exception(INVALID_URL_MISSING_HTTP));
            return;
        }
        try {
            promise.resolve(createCookieList(getCookieManager().getCookie(str)));
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void clearByName(String str, String str2, Boolean bool, Promise promise) {
        promise.reject(new Exception(CLEAR_BY_NAME_NOT_SUPPORTED));
    }

    @ReactMethod
    public void clearAll(Boolean bool, final Promise promise) {
        try {
            CookieManager cookieManager = getCookieManager();
            if (USES_LEGACY_STORE) {
                cookieManager.removeAllCookie();
                cookieManager.removeSessionCookie();
                this.mCookieSyncManager.sync();
                promise.resolve(true);
            } else {
                cookieManager.removeAllCookies(new ValueCallback<Boolean>() { // from class: com.reactnativecommunity.cookies.CookieManagerModule.2
                    @Override // android.webkit.ValueCallback
                    public void onReceiveValue(Boolean bool2) {
                        promise.resolve(bool2);
                    }
                });
                cookieManager.flush();
            }
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    private void addCookies(String str, String str2, final Promise promise) {
        try {
            CookieManager cookieManager = getCookieManager();
            if (USES_LEGACY_STORE) {
                cookieManager.setCookie(str, str2);
                this.mCookieSyncManager.sync();
                promise.resolve(true);
            } else {
                cookieManager.setCookie(str, str2, new ValueCallback<Boolean>() { // from class: com.reactnativecommunity.cookies.CookieManagerModule.3
                    @Override // android.webkit.ValueCallback
                    public void onReceiveValue(Boolean bool) {
                        promise.resolve(bool);
                    }
                });
                cookieManager.flush();
            }
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    private WritableMap createCookieList(String str) throws Exception {
        WritableMap createMap = Arguments.createMap();
        if (!isEmpty(str)) {
            for (String str2 : str.split(";")) {
                for (HttpCookie httpCookie : HttpCookie.parse(str2)) {
                    if (httpCookie != null) {
                        String name = httpCookie.getName();
                        String value = httpCookie.getValue();
                        if (!isEmpty(name) && !isEmpty(value)) {
                            createMap.putMap(name, createCookieData(httpCookie));
                        }
                    }
                }
            }
        }
        return createMap;
    }

    private HttpCookie makeHTTPCookieObject(String str, ReadableMap readableMap) throws Exception {
        Date parseDate;
        try {
            String host = new URL(str).getHost();
            if (isEmpty(host)) {
                throw new Exception(INVALID_URL_MISSING_HTTP);
            }
            HttpCookie httpCookie = new HttpCookie(readableMap.getString("name"), readableMap.getString("value"));
            if (readableMap.hasKey("domain") && !isEmpty(readableMap.getString("domain"))) {
                String string = readableMap.getString("domain");
                if (string.startsWith(".")) {
                    string = string.substring(1);
                }
                if (!host.contains(string) && !host.equals(string)) {
                    throw new Exception(String.format(INVALID_DOMAINS, host, string));
                }
                httpCookie.setDomain(string);
            } else {
                httpCookie.setDomain(host);
            }
            if (readableMap.hasKey(RNFetchBlobConst.RNFB_RESPONSE_PATH) && !isEmpty(readableMap.getString(RNFetchBlobConst.RNFB_RESPONSE_PATH))) {
                httpCookie.setPath(readableMap.getString(RNFetchBlobConst.RNFB_RESPONSE_PATH));
            }
            if (readableMap.hasKey("expires") && !isEmpty(readableMap.getString("expires")) && (parseDate = parseDate(readableMap.getString("expires"))) != null) {
                httpCookie.setMaxAge(parseDate.getTime());
            }
            if (readableMap.hasKey("secure") && readableMap.getBoolean("secure")) {
                httpCookie.setSecure(true);
            }
            if (HTTP_ONLY_SUPPORTED && readableMap.hasKey("httpOnly") && readableMap.getBoolean("httpOnly")) {
                httpCookie.setHttpOnly(true);
            }
            return httpCookie;
        } catch (Exception unused) {
            throw new Exception(INVALID_URL_MISSING_HTTP);
        }
    }

    private WritableMap createCookieData(HttpCookie httpCookie) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("name", httpCookie.getName());
        createMap.putString("value", httpCookie.getValue());
        createMap.putString("domain", httpCookie.getDomain());
        createMap.putString(RNFetchBlobConst.RNFB_RESPONSE_PATH, httpCookie.getPath());
        createMap.putBoolean("secure", httpCookie.getSecure());
        if (HTTP_ONLY_SUPPORTED) {
            createMap.putBoolean("httpOnly", httpCookie.isHttpOnly());
        }
        long maxAge = httpCookie.getMaxAge();
        if (maxAge > 0) {
            String formatDate = formatDate(new Date(maxAge));
            if (!isEmpty(formatDate)) {
                createMap.putString("expires", formatDate);
            }
        }
        return createMap;
    }

    private String toRFC6265string(HttpCookie httpCookie) {
        StringBuilder sb = new StringBuilder();
        sb.append(httpCookie.getName());
        sb.append('=');
        sb.append(httpCookie.getValue());
        if (!httpCookie.hasExpired()) {
            long maxAge = httpCookie.getMaxAge();
            if (maxAge > 0) {
                String formatDate = formatDate(new Date(maxAge), true);
                if (!isEmpty(formatDate)) {
                    sb.append("; expires=");
                    sb.append(formatDate);
                }
            }
        }
        if (!isEmpty(httpCookie.getDomain())) {
            sb.append("; domain=");
            sb.append(httpCookie.getDomain());
        }
        if (!isEmpty(httpCookie.getPath())) {
            sb.append("; path=");
            sb.append(httpCookie.getPath());
        }
        if (httpCookie.getSecure()) {
            sb.append("; secure");
        }
        if (HTTP_ONLY_SUPPORTED && httpCookie.isHttpOnly()) {
            sb.append("; httponly");
        }
        return sb.toString();
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private DateFormat dateFormatter() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TimeZones.GMT_ID));
        return simpleDateFormat;
    }

    private DateFormat RFC1123dateFormatter() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TimeZones.GMT_ID));
        return simpleDateFormat;
    }

    private Date parseDate(String str) {
        return parseDate(str, false);
    }

    private Date parseDate(String str, boolean z) {
        try {
            return (z ? RFC1123dateFormatter() : dateFormatter()).parse(str);
        } catch (Exception e) {
            String message = e.getMessage();
            if (message == null) {
                message = "Unable to parse date";
            }
            Log.i("Cookies", message);
            return null;
        }
    }

    private String formatDate(Date date) {
        return formatDate(date, false);
    }

    private String formatDate(Date date, boolean z) {
        try {
            return (z ? RFC1123dateFormatter() : dateFormatter()).format(date);
        } catch (Exception e) {
            String message = e.getMessage();
            if (message == null) {
                message = "Unable to format date";
            }
            Log.i("Cookies", message);
            return null;
        }
    }
}
