package com.RNFetchBlob;

/* loaded from: classes.dex */
public class RNFetchBlobProgressConfig {
    private int count;
    private boolean enable;
    private int interval;
    private long lastTick = 0;
    private int tick = 0;
    private ReportType type;

    enum ReportType {
        Upload,
        Download
    }

    RNFetchBlobProgressConfig(boolean z, int i, int i2, ReportType reportType) {
        this.count = -1;
        this.interval = -1;
        this.enable = false;
        ReportType reportType2 = ReportType.Download;
        this.enable = z;
        this.interval = i;
        this.type = reportType;
        this.count = i2;
    }

    public boolean shouldReport(float f) {
        int i = this.count;
        boolean z = false;
        boolean z2 = i <= 0 || f <= 0.0f || Math.floor((double) (f * ((float) i))) > ((double) this.tick);
        if (System.currentTimeMillis() - this.lastTick > this.interval && this.enable && z2) {
            z = true;
        }
        if (z) {
            this.tick++;
            this.lastTick = System.currentTimeMillis();
        }
        return z;
    }
}
