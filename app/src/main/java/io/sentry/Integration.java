package io.sentry;

/* loaded from: classes3.dex */
public interface Integration extends IntegrationName {
    void register(IHub iHub, SentryOptions sentryOptions);
}
