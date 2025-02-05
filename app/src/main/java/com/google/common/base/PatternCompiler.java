package com.google.common.base;

@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
interface PatternCompiler {
    CommonPattern compile(String str);

    boolean isPcreLike();
}
