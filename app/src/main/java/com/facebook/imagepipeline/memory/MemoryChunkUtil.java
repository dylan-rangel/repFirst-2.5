package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;

/* loaded from: classes.dex */
public class MemoryChunkUtil {
    static int adjustByteCount(final int offset, final int count, final int memorySize) {
        return Math.min(Math.max(0, memorySize - offset), count);
    }

    static void checkBounds(final int offset, final int otherLength, final int otherOffset, final int count, final int memorySize) {
        Preconditions.checkArgument(Boolean.valueOf(count >= 0));
        Preconditions.checkArgument(Boolean.valueOf(offset >= 0));
        Preconditions.checkArgument(Boolean.valueOf(otherOffset >= 0));
        Preconditions.checkArgument(Boolean.valueOf(offset + count <= memorySize));
        Preconditions.checkArgument(Boolean.valueOf(otherOffset + count <= otherLength));
    }
}
