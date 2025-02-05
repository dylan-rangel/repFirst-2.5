package net.openid.appauth.browser;

/* loaded from: classes3.dex */
public class ExactBrowserMatcher implements BrowserMatcher {
    private BrowserDescriptor mBrowser;

    public ExactBrowserMatcher(BrowserDescriptor browser) {
        this.mBrowser = browser;
    }

    @Override // net.openid.appauth.browser.BrowserMatcher
    public boolean matches(BrowserDescriptor descriptor) {
        return this.mBrowser.equals(descriptor);
    }
}
