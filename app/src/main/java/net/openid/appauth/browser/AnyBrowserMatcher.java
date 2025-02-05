package net.openid.appauth.browser;

/* loaded from: classes3.dex */
public final class AnyBrowserMatcher implements BrowserMatcher {
    public static final AnyBrowserMatcher INSTANCE = new AnyBrowserMatcher();

    @Override // net.openid.appauth.browser.BrowserMatcher
    public boolean matches(BrowserDescriptor descriptor) {
        return true;
    }

    private AnyBrowserMatcher() {
    }
}
