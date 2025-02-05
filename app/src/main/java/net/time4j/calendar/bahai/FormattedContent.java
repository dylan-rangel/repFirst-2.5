package net.time4j.calendar.bahai;

/* loaded from: classes3.dex */
public enum FormattedContent {
    TRANSCRIPTION { // from class: net.time4j.calendar.bahai.FormattedContent.1
        @Override // net.time4j.calendar.bahai.FormattedContent
        String variant() {
            return "t";
        }
    },
    MEANING { // from class: net.time4j.calendar.bahai.FormattedContent.2
        @Override // net.time4j.calendar.bahai.FormattedContent
        String variant() {
            return "m";
        }
    },
    HTML { // from class: net.time4j.calendar.bahai.FormattedContent.3
        @Override // net.time4j.calendar.bahai.FormattedContent
        String variant() {
            return "h";
        }
    };

    abstract String variant();
}
