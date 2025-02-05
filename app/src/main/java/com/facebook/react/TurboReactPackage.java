package com.facebook.react;

import com.facebook.react.bridge.ModuleHolder;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.config.ReactFeatureFlags;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.uimanager.ViewManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.inject.Provider;

/* loaded from: classes.dex */
public abstract class TurboReactPackage implements ReactPackage {
    public abstract NativeModule getModule(String str, ReactApplicationContext reactApplicationContext);

    public abstract ReactModuleInfoProvider getReactModuleInfoProvider();

    @Override // com.facebook.react.ReactPackage
    public List<NativeModule> createNativeModules(ReactApplicationContext reactApplicationContext) {
        throw new UnsupportedOperationException("In case of TurboModules, createNativeModules is not supported. NativeModuleRegistry should instead use getModuleList or getModule method");
    }

    public Iterable<ModuleHolder> getNativeModuleIterator(final ReactApplicationContext reactApplicationContext) {
        final Iterator<Map.Entry<String, ReactModuleInfo>> it = getReactModuleInfoProvider().getReactModuleInfos().entrySet().iterator();
        return new Iterable<ModuleHolder>() { // from class: com.facebook.react.TurboReactPackage.1
            @Override // java.lang.Iterable
            public Iterator<ModuleHolder> iterator() {
                return new Iterator<ModuleHolder>() { // from class: com.facebook.react.TurboReactPackage.1.1
                    Map.Entry<String, ReactModuleInfo> nextEntry = null;

                    private void findNext() {
                        while (it.hasNext()) {
                            Map.Entry<String, ReactModuleInfo> entry = (Map.Entry) it.next();
                            ReactModuleInfo value = entry.getValue();
                            if (!ReactFeatureFlags.useTurboModules || !value.isTurboModule()) {
                                this.nextEntry = entry;
                                return;
                            }
                        }
                        this.nextEntry = null;
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        if (this.nextEntry == null) {
                            findNext();
                        }
                        return this.nextEntry != null;
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // java.util.Iterator
                    public ModuleHolder next() {
                        if (this.nextEntry == null) {
                            findNext();
                        }
                        Map.Entry<String, ReactModuleInfo> entry = this.nextEntry;
                        if (entry == null) {
                            throw new NoSuchElementException("ModuleHolder not found");
                        }
                        findNext();
                        return new ModuleHolder(entry.getValue(), TurboReactPackage.this.new ModuleHolderProvider(entry.getKey(), reactApplicationContext));
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        throw new UnsupportedOperationException("Cannot remove native modules from the list");
                    }
                };
            }
        };
    }

    protected List<ModuleSpec> getViewManagers(ReactApplicationContext reactApplicationContext) {
        return Collections.emptyList();
    }

    @Override // com.facebook.react.ReactPackage
    public List<ViewManager> createViewManagers(ReactApplicationContext reactApplicationContext) {
        List<ModuleSpec> viewManagers = getViewManagers(reactApplicationContext);
        if (viewManagers == null || viewManagers.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<ModuleSpec> it = viewManagers.iterator();
        while (it.hasNext()) {
            arrayList.add((ViewManager) it.next().getProvider().get());
        }
        return arrayList;
    }

    private class ModuleHolderProvider implements Provider<NativeModule> {
        private final String mName;
        private final ReactApplicationContext mReactContext;

        public ModuleHolderProvider(String str, ReactApplicationContext reactApplicationContext) {
            this.mName = str;
            this.mReactContext = reactApplicationContext;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // javax.inject.Provider
        public NativeModule get() {
            return TurboReactPackage.this.getModule(this.mName, this.mReactContext);
        }
    }
}
