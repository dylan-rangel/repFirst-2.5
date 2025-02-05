package com.facebook.systrace;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class SystraceMessage {
    public static Boolean INCLUDE_ARGS = false;

    public static abstract class Builder {
        public abstract Builder arg(String str, double d);

        public abstract Builder arg(String str, int i);

        public abstract Builder arg(String str, long j);

        public abstract Builder arg(String str, Object obj);

        public abstract void flush();
    }

    public static Builder beginSection(long j, String str) {
        return new StartSectionBuilder(j, str);
    }

    public static Builder endSection(long j) {
        return new EndSectionBuilder(j);
    }

    private static class StartSectionBuilder extends Builder {
        private List<String> mArgs = new ArrayList();
        private String mSectionName;
        private long mTag;

        public StartSectionBuilder(long j, String str) {
            this.mTag = j;
            this.mSectionName = str;
        }

        @Override // com.facebook.systrace.SystraceMessage.Builder
        public void flush() {
            String str;
            long j = this.mTag;
            StringBuilder sb = new StringBuilder();
            sb.append(this.mSectionName);
            if (!SystraceMessage.INCLUDE_ARGS.booleanValue() || this.mArgs.size() <= 0) {
                str = "";
            } else {
                str = " (" + SystraceMessage$StartSectionBuilder$$ExternalSyntheticBackport0.m(", ", this.mArgs) + ")";
            }
            sb.append(str);
            Systrace.beginSection(j, sb.toString());
        }

        @Override // com.facebook.systrace.SystraceMessage.Builder
        public Builder arg(String str, Object obj) {
            addArg(str, String.valueOf(obj));
            return this;
        }

        @Override // com.facebook.systrace.SystraceMessage.Builder
        public Builder arg(String str, int i) {
            addArg(str, String.valueOf(i));
            return this;
        }

        @Override // com.facebook.systrace.SystraceMessage.Builder
        public Builder arg(String str, long j) {
            addArg(str, String.valueOf(j));
            return this;
        }

        @Override // com.facebook.systrace.SystraceMessage.Builder
        public Builder arg(String str, double d) {
            addArg(str, String.valueOf(d));
            return this;
        }

        private void addArg(String str, String str2) {
            this.mArgs.add(str + ": " + str2);
        }
    }

    private static class EndSectionBuilder extends Builder {
        private long mTag;

        @Override // com.facebook.systrace.SystraceMessage.Builder
        public Builder arg(String str, double d) {
            return this;
        }

        @Override // com.facebook.systrace.SystraceMessage.Builder
        public Builder arg(String str, int i) {
            return this;
        }

        @Override // com.facebook.systrace.SystraceMessage.Builder
        public Builder arg(String str, long j) {
            return this;
        }

        @Override // com.facebook.systrace.SystraceMessage.Builder
        public Builder arg(String str, Object obj) {
            return this;
        }

        public EndSectionBuilder(long j) {
            this.mTag = j;
        }

        @Override // com.facebook.systrace.SystraceMessage.Builder
        public void flush() {
            Systrace.endSection(this.mTag);
        }
    }
}
