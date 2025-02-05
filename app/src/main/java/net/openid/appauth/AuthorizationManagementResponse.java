package net.openid.appauth;

import android.content.Intent;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public abstract class AuthorizationManagementResponse {
    public abstract String getState();

    public abstract JSONObject jsonSerialize();

    public abstract Intent toIntent();

    public String jsonSerializeString() {
        return jsonSerialize().toString();
    }
}
