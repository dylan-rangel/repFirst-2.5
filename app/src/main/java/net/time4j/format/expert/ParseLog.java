package net.time4j.format.expert;

import java.text.ParsePosition;
import kotlin.text.Typography;
import net.time4j.engine.ChronoEntity;

/* loaded from: classes3.dex */
public class ParseLog {
    private String errorMessage;
    private ParsePosition pp;
    private ChronoEntity<?> rawValues;
    private boolean warning;

    public ParseLog() {
        this(0);
    }

    public ParseLog(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Undefined: " + i);
        }
        this.pp = new ParsePosition(i);
        this.errorMessage = "";
        this.rawValues = null;
        this.warning = false;
    }

    ParseLog(ParsePosition parsePosition) {
        if (parsePosition.getIndex() < 0) {
            throw new IllegalArgumentException("Undefined position: " + parsePosition.getIndex());
        }
        parsePosition.setErrorIndex(-1);
        this.pp = parsePosition;
        this.errorMessage = "";
        this.rawValues = null;
        this.warning = false;
    }

    public int getPosition() {
        return this.pp.getIndex();
    }

    public boolean isError() {
        return this.pp.getErrorIndex() != -1;
    }

    public int getErrorIndex() {
        return this.pp.getErrorIndex();
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public ChronoEntity<?> getRawValues() {
        if (this.rawValues == null) {
            this.rawValues = new ParsedValues(0, false);
        }
        return this.rawValues;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("[position=");
        sb.append(getPosition());
        sb.append(", error-index=");
        sb.append(getErrorIndex());
        sb.append(", error-message=\"");
        sb.append(this.errorMessage);
        sb.append(Typography.quote);
        if (this.warning) {
            sb.append(", warning-active");
        }
        if (this.rawValues != null) {
            sb.append(", raw-values=");
            sb.append(this.rawValues);
        }
        sb.append(']');
        return sb.toString();
    }

    public void setPosition(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Undefined position: " + i);
        }
        this.pp.setIndex(i);
    }

    public void setError(int i, String str) {
        if (i >= 0) {
            if (str == null || str.isEmpty()) {
                str = "Error occurred at position: " + i;
            }
            this.errorMessage = str;
            this.pp.setErrorIndex(i);
            return;
        }
        throw new IllegalArgumentException("Undefined error index: " + i);
    }

    public void setWarning() {
        if (!isError()) {
            this.errorMessage = "Warning state active.";
            this.pp.setErrorIndex(getPosition());
        }
        this.warning = true;
    }

    public void reset() {
        this.pp.setIndex(0);
        this.pp.setErrorIndex(-1);
        this.errorMessage = "";
        this.warning = false;
        this.rawValues = null;
    }

    ChronoEntity<?> getRawValues0() {
        return this.rawValues;
    }

    void clearError() {
        this.pp.setErrorIndex(-1);
        this.errorMessage = "";
    }

    void setRawValues(ChronoEntity<?> chronoEntity) {
        this.rawValues = chronoEntity;
    }

    boolean isWarning() {
        return this.warning;
    }

    void clearWarning() {
        this.warning = false;
    }

    ParsePosition getPP() {
        return this.pp;
    }
}
