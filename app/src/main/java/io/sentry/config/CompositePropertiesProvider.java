package io.sentry.config;

import io.sentry.config.PropertiesProvider;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
final class CompositePropertiesProvider implements PropertiesProvider {
    private final List<PropertiesProvider> providers;

    @Override // io.sentry.config.PropertiesProvider
    public /* synthetic */ Boolean getBooleanProperty(String str) {
        return PropertiesProvider.CC.$default$getBooleanProperty(this, str);
    }

    @Override // io.sentry.config.PropertiesProvider
    public /* synthetic */ Double getDoubleProperty(String str) {
        return PropertiesProvider.CC.$default$getDoubleProperty(this, str);
    }

    @Override // io.sentry.config.PropertiesProvider
    public /* synthetic */ List getList(String str) {
        return PropertiesProvider.CC.$default$getList(this, str);
    }

    @Override // io.sentry.config.PropertiesProvider
    public /* synthetic */ Long getLongProperty(String str) {
        return PropertiesProvider.CC.$default$getLongProperty(this, str);
    }

    @Override // io.sentry.config.PropertiesProvider
    public /* synthetic */ String getProperty(String str, String str2) {
        return PropertiesProvider.CC.$default$getProperty(this, str, str2);
    }

    public CompositePropertiesProvider(List<PropertiesProvider> list) {
        this.providers = list;
    }

    @Override // io.sentry.config.PropertiesProvider
    public String getProperty(String str) {
        Iterator<PropertiesProvider> it = this.providers.iterator();
        while (it.hasNext()) {
            String property = it.next().getProperty(str);
            if (property != null) {
                return property;
            }
        }
        return null;
    }

    @Override // io.sentry.config.PropertiesProvider
    public Map<String, String> getMap(String str) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        Iterator<PropertiesProvider> it = this.providers.iterator();
        while (it.hasNext()) {
            concurrentHashMap.putAll(it.next().getMap(str));
        }
        return concurrentHashMap;
    }
}
