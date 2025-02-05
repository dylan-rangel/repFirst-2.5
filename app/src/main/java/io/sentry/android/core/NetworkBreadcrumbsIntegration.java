package io.sentry.android.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import io.sentry.Breadcrumb;
import io.sentry.Hint;
import io.sentry.IHub;
import io.sentry.ILogger;
import io.sentry.Integration;
import io.sentry.SentryIntegrationPackageStorage;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.TypeCheckHint;
import io.sentry.android.core.internal.util.ConnectivityChecker;
import io.sentry.util.Objects;
import java.io.Closeable;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class NetworkBreadcrumbsIntegration implements Integration, Closeable {
    private final BuildInfoProvider buildInfoProvider;
    private final Context context;
    private final ILogger logger;
    NetworkBreadcrumbsNetworkCallback networkCallback;

    @Override // io.sentry.IntegrationName
    public /* synthetic */ void addIntegrationToSdkVersion() {
        SentryIntegrationPackageStorage.getInstance().addIntegration(getIntegrationName());
    }

    @Override // io.sentry.IntegrationName
    public /* synthetic */ String getIntegrationName() {
        String replace;
        replace = getClass().getSimpleName().replace("Sentry", "").replace("Integration", "").replace("Interceptor", "").replace("EventProcessor", "");
        return replace;
    }

    public NetworkBreadcrumbsIntegration(Context context, BuildInfoProvider buildInfoProvider, ILogger iLogger) {
        this.context = (Context) Objects.requireNonNull(context, "Context is required");
        this.buildInfoProvider = (BuildInfoProvider) Objects.requireNonNull(buildInfoProvider, "BuildInfoProvider is required");
        this.logger = (ILogger) Objects.requireNonNull(iLogger, "ILogger is required");
    }

    @Override // io.sentry.Integration
    public void register(IHub iHub, SentryOptions sentryOptions) {
        Objects.requireNonNull(iHub, "Hub is required");
        SentryAndroidOptions sentryAndroidOptions = (SentryAndroidOptions) Objects.requireNonNull(sentryOptions instanceof SentryAndroidOptions ? (SentryAndroidOptions) sentryOptions : null, "SentryAndroidOptions is required");
        this.logger.log(SentryLevel.DEBUG, "NetworkBreadcrumbsIntegration enabled: %s", Boolean.valueOf(sentryAndroidOptions.isEnableNetworkEventBreadcrumbs()));
        if (sentryAndroidOptions.isEnableNetworkEventBreadcrumbs()) {
            if (this.buildInfoProvider.getSdkInfoVersion() < 21) {
                this.networkCallback = null;
                this.logger.log(SentryLevel.DEBUG, "NetworkBreadcrumbsIntegration requires Android 5+", new Object[0]);
                return;
            }
            NetworkBreadcrumbsNetworkCallback networkBreadcrumbsNetworkCallback = new NetworkBreadcrumbsNetworkCallback(iHub, this.buildInfoProvider);
            this.networkCallback = networkBreadcrumbsNetworkCallback;
            if (!ConnectivityChecker.registerNetworkCallback(this.context, this.logger, this.buildInfoProvider, networkBreadcrumbsNetworkCallback)) {
                this.networkCallback = null;
                this.logger.log(SentryLevel.DEBUG, "NetworkBreadcrumbsIntegration not installed.", new Object[0]);
            } else {
                this.logger.log(SentryLevel.DEBUG, "NetworkBreadcrumbsIntegration installed.", new Object[0]);
                addIntegrationToSdkVersion();
            }
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        NetworkBreadcrumbsNetworkCallback networkBreadcrumbsNetworkCallback = this.networkCallback;
        if (networkBreadcrumbsNetworkCallback != null) {
            ConnectivityChecker.unregisterNetworkCallback(this.context, this.logger, this.buildInfoProvider, networkBreadcrumbsNetworkCallback);
            this.logger.log(SentryLevel.DEBUG, "NetworkBreadcrumbsIntegration remove.", new Object[0]);
        }
        this.networkCallback = null;
    }

    static final class NetworkBreadcrumbsNetworkCallback extends ConnectivityManager.NetworkCallback {
        final BuildInfoProvider buildInfoProvider;
        final IHub hub;
        Network currentNetwork = null;
        NetworkCapabilities lastCapabilities = null;

        NetworkBreadcrumbsNetworkCallback(IHub iHub, BuildInfoProvider buildInfoProvider) {
            this.hub = (IHub) Objects.requireNonNull(iHub, "Hub is required");
            this.buildInfoProvider = (BuildInfoProvider) Objects.requireNonNull(buildInfoProvider, "BuildInfoProvider is required");
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            if (network.equals(this.currentNetwork)) {
                return;
            }
            this.hub.addBreadcrumb(createBreadcrumb("NETWORK_AVAILABLE"));
            this.currentNetwork = network;
            this.lastCapabilities = null;
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            NetworkBreadcrumbConnectionDetail newConnectionDetails;
            if (network.equals(this.currentNetwork) && (newConnectionDetails = getNewConnectionDetails(this.lastCapabilities, networkCapabilities)) != null) {
                this.lastCapabilities = networkCapabilities;
                Breadcrumb createBreadcrumb = createBreadcrumb("NETWORK_CAPABILITIES_CHANGED");
                createBreadcrumb.setData("download_bandwidth", Integer.valueOf(newConnectionDetails.downBandwidth));
                createBreadcrumb.setData("upload_bandwidth", Integer.valueOf(newConnectionDetails.upBandwidth));
                createBreadcrumb.setData("vpn_active", Boolean.valueOf(newConnectionDetails.isVpn));
                createBreadcrumb.setData("network_type", newConnectionDetails.type);
                if (newConnectionDetails.signalStrength != 0) {
                    createBreadcrumb.setData("signal_strength", Integer.valueOf(newConnectionDetails.signalStrength));
                }
                Hint hint = new Hint();
                hint.set(TypeCheckHint.ANDROID_NETWORK_CAPABILITIES, newConnectionDetails);
                this.hub.addBreadcrumb(createBreadcrumb, hint);
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            if (network.equals(this.currentNetwork)) {
                this.hub.addBreadcrumb(createBreadcrumb("NETWORK_LOST"));
                this.currentNetwork = null;
                this.lastCapabilities = null;
            }
        }

        private Breadcrumb createBreadcrumb(String str) {
            Breadcrumb breadcrumb = new Breadcrumb();
            breadcrumb.setType("system");
            breadcrumb.setCategory("network.event");
            breadcrumb.setData("action", str);
            breadcrumb.setLevel(SentryLevel.INFO);
            return breadcrumb;
        }

        private NetworkBreadcrumbConnectionDetail getNewConnectionDetails(NetworkCapabilities networkCapabilities, NetworkCapabilities networkCapabilities2) {
            if (networkCapabilities == null) {
                return new NetworkBreadcrumbConnectionDetail(networkCapabilities2, this.buildInfoProvider);
            }
            NetworkBreadcrumbConnectionDetail networkBreadcrumbConnectionDetail = new NetworkBreadcrumbConnectionDetail(networkCapabilities, this.buildInfoProvider);
            NetworkBreadcrumbConnectionDetail networkBreadcrumbConnectionDetail2 = new NetworkBreadcrumbConnectionDetail(networkCapabilities2, this.buildInfoProvider);
            if (networkBreadcrumbConnectionDetail2.isSimilar(networkBreadcrumbConnectionDetail)) {
                return null;
            }
            return networkBreadcrumbConnectionDetail2;
        }
    }

    static class NetworkBreadcrumbConnectionDetail {
        final int downBandwidth;
        final boolean isVpn;
        final int signalStrength;
        final String type;
        final int upBandwidth;

        NetworkBreadcrumbConnectionDetail(NetworkCapabilities networkCapabilities, BuildInfoProvider buildInfoProvider) {
            Objects.requireNonNull(networkCapabilities, "NetworkCapabilities is required");
            Objects.requireNonNull(buildInfoProvider, "BuildInfoProvider is required");
            this.downBandwidth = networkCapabilities.getLinkDownstreamBandwidthKbps();
            this.upBandwidth = networkCapabilities.getLinkUpstreamBandwidthKbps();
            int signalStrength = buildInfoProvider.getSdkInfoVersion() >= 29 ? networkCapabilities.getSignalStrength() : 0;
            this.signalStrength = signalStrength > -100 ? signalStrength : 0;
            this.isVpn = networkCapabilities.hasTransport(4);
            String connectionType = ConnectivityChecker.getConnectionType(networkCapabilities, buildInfoProvider);
            this.type = connectionType == null ? "" : connectionType;
        }

        boolean isSimilar(NetworkBreadcrumbConnectionDetail networkBreadcrumbConnectionDetail) {
            if (this.isVpn == networkBreadcrumbConnectionDetail.isVpn && this.type.equals(networkBreadcrumbConnectionDetail.type)) {
                int i = this.signalStrength;
                int i2 = networkBreadcrumbConnectionDetail.signalStrength;
                if (-5 <= i - i2 && i - i2 <= 5) {
                    int i3 = this.downBandwidth;
                    int i4 = networkBreadcrumbConnectionDetail.downBandwidth;
                    if (-1000 <= i3 - i4 && i3 - i4 <= 1000) {
                        int i5 = this.upBandwidth;
                        int i6 = networkBreadcrumbConnectionDetail.upBandwidth;
                        if (-1000 <= i5 - i6 && i5 - i6 <= 1000) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }
}
