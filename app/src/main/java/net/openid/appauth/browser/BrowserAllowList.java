package net.openid.appauth.browser;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class BrowserAllowList implements BrowserMatcher {
    private List<BrowserMatcher> mBrowserMatchers;

    public BrowserAllowList(BrowserMatcher... matchers) {
        this.mBrowserMatchers = Arrays.asList(matchers);
    }

    @Override // net.openid.appauth.browser.BrowserMatcher
    public boolean matches(BrowserDescriptor descriptor) {
        Iterator<BrowserMatcher> it = this.mBrowserMatchers.iterator();
        while (it.hasNext()) {
            if (it.next().matches(descriptor)) {
                return true;
            }
        }
        return false;
    }
}
