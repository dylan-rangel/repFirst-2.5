package com.rnappauth.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.openid.appauth.browser.BrowserDescriptor;
import net.openid.appauth.browser.BrowserMatcher;

/* loaded from: classes.dex */
public class MutableBrowserAllowList implements BrowserMatcher {
    private final List<BrowserMatcher> mBrowserMatchers = new ArrayList();

    public void add(BrowserMatcher browserMatcher) {
        this.mBrowserMatchers.add(browserMatcher);
    }

    public void remove(BrowserMatcher browserMatcher) {
        this.mBrowserMatchers.remove(browserMatcher);
    }

    @Override // net.openid.appauth.browser.BrowserMatcher
    public boolean matches(BrowserDescriptor browserDescriptor) {
        Iterator<BrowserMatcher> it = this.mBrowserMatchers.iterator();
        while (it.hasNext()) {
            if (it.next().matches(browserDescriptor)) {
                return true;
            }
        }
        return false;
    }
}
