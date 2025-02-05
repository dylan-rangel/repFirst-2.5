package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzq implements Parcelable.Creator<zzo> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzo createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String str = "";
        String str2 = str;
        String str3 = str2;
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        long j5 = 0;
        long j6 = 0;
        long j7 = 0;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        String str9 = null;
        String str10 = null;
        Boolean bool = null;
        ArrayList<String> arrayList = null;
        String str11 = null;
        String str12 = null;
        long j8 = -2147483648L;
        boolean z = true;
        boolean z2 = false;
        int i = 0;
        boolean z3 = true;
        boolean z4 = false;
        boolean z5 = false;
        int i2 = 100;
        int i3 = 0;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 2:
                    str4 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 3:
                    str5 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 4:
                    str6 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 5:
                    str7 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 6:
                    j = SafeParcelReader.readLong(parcel, readHeader);
                    break;
                case 7:
                    j2 = SafeParcelReader.readLong(parcel, readHeader);
                    break;
                case 8:
                    str8 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 9:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 10:
                    z2 = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 11:
                    j8 = SafeParcelReader.readLong(parcel, readHeader);
                    break;
                case 12:
                    str9 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 13:
                    j3 = SafeParcelReader.readLong(parcel, readHeader);
                    break;
                case 14:
                    j4 = SafeParcelReader.readLong(parcel, readHeader);
                    break;
                case 15:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 16:
                    z3 = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 17:
                case 20:
                case 33:
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
                case 18:
                    z4 = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 19:
                    str10 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 21:
                    bool = SafeParcelReader.readBooleanObject(parcel, readHeader);
                    break;
                case 22:
                    j5 = SafeParcelReader.readLong(parcel, readHeader);
                    break;
                case 23:
                    arrayList = SafeParcelReader.createStringList(parcel, readHeader);
                    break;
                case 24:
                    str11 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 25:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 26:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 27:
                    str12 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 28:
                    z5 = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 29:
                    j6 = SafeParcelReader.readLong(parcel, readHeader);
                    break;
                case 30:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 31:
                    str3 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 32:
                    i3 = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 34:
                    j7 = SafeParcelReader.readLong(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new zzo(str4, str5, str6, str7, j, j2, str8, z, z2, j8, str9, j3, j4, i, z3, z4, str10, bool, j5, arrayList, str11, str, str2, str12, z5, j6, i2, str3, i3, j7);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzo[] newArray(int i) {
        return new zzo[i];
    }
}
