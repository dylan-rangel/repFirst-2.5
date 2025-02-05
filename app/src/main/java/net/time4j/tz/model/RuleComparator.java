package net.time4j.tz.model;

import com.google.android.gms.auth.api.credentials.CredentialsApi;
import java.util.Comparator;

/* loaded from: classes3.dex */
enum RuleComparator implements Comparator<DaylightSavingRule> {
    INSTANCE;

    @Override // java.util.Comparator
    public int compare(DaylightSavingRule daylightSavingRule, DaylightSavingRule daylightSavingRule2) {
        int compareTo = daylightSavingRule.getDate(CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE).compareTo(daylightSavingRule2.getDate(CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE));
        return compareTo == 0 ? daylightSavingRule.getTimeOfDay().compareTo(daylightSavingRule2.getTimeOfDay()) : compareTo;
    }
}
