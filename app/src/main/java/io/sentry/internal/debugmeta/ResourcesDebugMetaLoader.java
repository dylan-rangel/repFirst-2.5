package io.sentry.internal.debugmeta;

import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.util.ClassLoaderUtils;
import io.sentry.util.DebugMetaPropertiesApplier;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/* loaded from: classes3.dex */
public final class ResourcesDebugMetaLoader implements IDebugMetaLoader {
    private final ClassLoader classLoader;
    private final ILogger logger;

    public ResourcesDebugMetaLoader(ILogger iLogger) {
        this(iLogger, ResourcesDebugMetaLoader.class.getClassLoader());
    }

    ResourcesDebugMetaLoader(ILogger iLogger, ClassLoader classLoader) {
        this.logger = iLogger;
        this.classLoader = ClassLoaderUtils.classLoaderOrDefault(classLoader);
    }

    @Override // io.sentry.internal.debugmeta.IDebugMetaLoader
    public Properties loadDebugMeta() {
        try {
            InputStream resourceAsStream = this.classLoader.getResourceAsStream(DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME);
            try {
                if (resourceAsStream == null) {
                    this.logger.log(SentryLevel.INFO, "%s file was not found.", DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME);
                } else {
                    try {
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(resourceAsStream);
                        try {
                            Properties properties = new Properties();
                            properties.load(bufferedInputStream);
                            bufferedInputStream.close();
                            if (resourceAsStream != null) {
                                resourceAsStream.close();
                            }
                            return properties;
                        } catch (Throwable th) {
                            try {
                                bufferedInputStream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (IOException e) {
                        this.logger.log(SentryLevel.ERROR, e, "Failed to load %s", DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME);
                    } catch (RuntimeException e2) {
                        this.logger.log(SentryLevel.ERROR, e2, "%s file is malformed.", DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME);
                    }
                }
                if (resourceAsStream == null) {
                    return null;
                }
                resourceAsStream.close();
                return null;
            } finally {
            }
        } catch (IOException e3) {
            this.logger.log(SentryLevel.ERROR, e3, "Failed to load %s", DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME);
            return null;
        }
    }
}
