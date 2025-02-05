package com.horcrux.svg;

import android.view.View;
import com.horcrux.svg.TextProperties;
import java.util.ArrayList;

/* loaded from: classes2.dex */
class TextLayoutAlgorithm {
    TextLayoutAlgorithm() {
    }

    class CharacterInformation {
        double advance;
        char character;
        TextView element;
        int index;
        double x = 0.0d;
        double y = 0.0d;
        double rotate = 0.0d;
        boolean hidden = false;
        boolean middle = false;
        boolean resolved = false;
        boolean xSpecified = false;
        boolean ySpecified = false;
        boolean addressable = true;
        boolean anchoredChunk = false;
        boolean rotateSpecified = false;
        boolean firstCharacterInResolvedDescendant = false;

        CharacterInformation(int i, char c) {
            this.index = i;
            this.character = c;
        }
    }

    class LayoutInput {
        boolean horizontal;
        TextView text;

        LayoutInput() {
        }
    }

    private void getSubTreeTypographicCharacterPositions(ArrayList<TextPathView> arrayList, ArrayList<TextView> arrayList2, StringBuilder sb, View view, TextPathView textPathView) {
        int i = 0;
        if (view instanceof TSpanView) {
            TSpanView tSpanView = (TSpanView) view;
            String str = tSpanView.mContent;
            if (str == null) {
                while (i < tSpanView.getChildCount()) {
                    getSubTreeTypographicCharacterPositions(arrayList, arrayList2, sb, tSpanView.getChildAt(i), textPathView);
                    i++;
                }
                return;
            } else {
                while (i < str.length()) {
                    arrayList2.add(tSpanView);
                    arrayList.add(textPathView);
                    i++;
                }
                sb.append(str);
                return;
            }
        }
        if (view instanceof TextPathView) {
            textPathView = (TextPathView) view;
        }
        while (i < textPathView.getChildCount()) {
            getSubTreeTypographicCharacterPositions(arrayList, arrayList2, sb, textPathView.getChildAt(i), textPathView);
            i++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x01db, code lost:
    
        r12 = r4 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x01c6, code lost:
    
        r12 = com.horcrux.svg.TextProperties.Direction.ltr;
        r2 = r2 - ((r18 + r16) / 2.0d);
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x01d0, code lost:
    
        if (r13 != com.horcrux.svg.TextProperties.Direction.ltr) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x019d, code lost:
    
        if (r4 == (r10 - 1)) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0196, code lost:
    
        if (r18 == Double.POSITIVE_INFINITY) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x019f, code lost:
    
        r12 = com.horcrux.svg.TextProperties.TextAnchor.start;
        r13 = com.horcrux.svg.TextProperties.Direction.ltr;
        r14 = r10 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01a5, code lost:
    
        if (r4 != r14) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01a7, code lost:
    
        r16 = r0;
        r18 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01ab, code lost:
    
        r2 = r11[r8].x;
        r12 = com.horcrux.svg.TextLayoutAlgorithm.AnonymousClass1.$SwitchMap$com$horcrux$svg$TextProperties$TextAnchor[r12.ordinal()];
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01b8, code lost:
    
        if (r12 == 1) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01bb, code lost:
    
        if (r12 == 2) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01be, code lost:
    
        if (r12 == 3) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x01c3, code lost:
    
        if (r13 != com.horcrux.svg.TextProperties.Direction.ltr) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x01d2, code lost:
    
        r2 = r2 - r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01d5, code lost:
    
        r2 = r2 - r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01d7, code lost:
    
        if (r4 != r14) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01d9, code lost:
    
        r12 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x01dd, code lost:
    
        if (r8 > r12) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01df, code lost:
    
        r11[r8].x += r2;
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x01e9, code lost:
    
        r8 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x01ea, code lost:
    
        r20 = r16;
        r16 = r0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:143:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0314  */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.horcrux.svg.TextLayoutAlgorithm$1TextLengthResolver] */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [android.graphics.Canvas, android.graphics.Paint] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    com.horcrux.svg.TextLayoutAlgorithm.CharacterInformation[] layoutText(com.horcrux.svg.TextLayoutAlgorithm.LayoutInput r27) {
        /*
            Method dump skipped, instructions count: 927
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.TextLayoutAlgorithm.layoutText(com.horcrux.svg.TextLayoutAlgorithm$LayoutInput):com.horcrux.svg.TextLayoutAlgorithm$CharacterInformation[]");
    }

    /* renamed from: com.horcrux.svg.TextLayoutAlgorithm$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor;

        static {
            int[] iArr = new int[TextProperties.TextAnchor.values().length];
            $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor = iArr;
            try {
                iArr[TextProperties.TextAnchor.start.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor[TextProperties.TextAnchor.middle.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor[TextProperties.TextAnchor.end.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }
}
