package com.rnappauth.utils;

import android.net.Uri;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.openid.appauth.connectivity.ConnectionBuilder;

/* loaded from: classes.dex */
public final class CustomConnectionBuilder implements ConnectionBuilder {
    private ConnectionBuilder connectionBuilder;
    private Map<String, String> headers = null;
    private int connectionTimeoutMs = (int) TimeUnit.SECONDS.toMillis(15);
    private int readTimeoutMs = (int) TimeUnit.SECONDS.toMillis(10);

    public CustomConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    public void setHeaders(Map<String, String> map) {
        this.headers = map;
    }

    public void setConnectionTimeout(int i) {
        this.connectionTimeoutMs = i;
        this.readTimeoutMs = i;
    }

    @Override // net.openid.appauth.connectivity.ConnectionBuilder
    public HttpURLConnection openConnection(Uri uri) throws IOException {
        HttpURLConnection openConnection = this.connectionBuilder.openConnection(uri);
        Map<String, String> map = this.headers;
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                openConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        openConnection.setConnectTimeout(this.connectionTimeoutMs);
        openConnection.setReadTimeout(this.readTimeoutMs);
        return openConnection;
    }
}
