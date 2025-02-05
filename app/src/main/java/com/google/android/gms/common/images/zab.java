package com.google.android.gms.common.images;

import android.content.Context;
import android.net.Uri;
import android.os.SystemClock;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.images.ImageManager.ImageReceiver;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.internal.base.zam;
import java.util.HashSet;
import java.util.Map;
import org.apache.commons.lang3.time.DateUtils;

/* compiled from: com.google.android.gms:play-services-base@@18.1.0 */
/* loaded from: classes2.dex */
final class zab implements Runnable {
    final /* synthetic */ ImageManager zaa;
    private final zag zab;

    public zab(ImageManager imageManager, zag zagVar) {
        this.zaa = imageManager;
        this.zab = zagVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Map map;
        zam zamVar;
        Map map2;
        Map map3;
        Object obj;
        HashSet hashSet;
        HashSet hashSet2;
        Map map4;
        Map map5;
        Map map6;
        zam zamVar2;
        Map map7;
        Asserts.checkMainThread("LoadImageRunnable must be executed on the main thread");
        map = this.zaa.zah;
        ImageManager.ImageReceiver imageReceiver = (ImageManager.ImageReceiver) map.get(this.zab);
        if (imageReceiver != null) {
            map7 = this.zaa.zah;
            map7.remove(this.zab);
            imageReceiver.zac(this.zab);
        }
        zag zagVar = this.zab;
        zad zadVar = zagVar.zaa;
        Uri uri = zadVar.zaa;
        if (uri == null) {
            ImageManager imageManager = this.zaa;
            Context context = imageManager.zad;
            zamVar = imageManager.zag;
            zagVar.zab(context, zamVar, true);
            return;
        }
        map2 = this.zaa.zaj;
        Long l = (Long) map2.get(uri);
        if (l != null) {
            if (SystemClock.elapsedRealtime() - l.longValue() < DateUtils.MILLIS_PER_HOUR) {
                zag zagVar2 = this.zab;
                ImageManager imageManager2 = this.zaa;
                Context context2 = imageManager2.zad;
                zamVar2 = imageManager2.zag;
                zagVar2.zab(context2, zamVar2, true);
                return;
            }
            map6 = this.zaa.zaj;
            map6.remove(zadVar.zaa);
        }
        this.zab.zaa(null, false, true, false);
        map3 = this.zaa.zai;
        ImageManager.ImageReceiver imageReceiver2 = (ImageManager.ImageReceiver) map3.get(zadVar.zaa);
        if (imageReceiver2 == null) {
            imageReceiver2 = this.zaa.new ImageReceiver(zadVar.zaa);
            map5 = this.zaa.zai;
            map5.put(zadVar.zaa, imageReceiver2);
        }
        imageReceiver2.zab(this.zab);
        zag zagVar3 = this.zab;
        if (!(zagVar3 instanceof zaf)) {
            map4 = this.zaa.zah;
            map4.put(zagVar3, imageReceiver2);
        }
        obj = ImageManager.zaa;
        synchronized (obj) {
            hashSet = ImageManager.zab;
            if (!hashSet.contains(zadVar.zaa)) {
                hashSet2 = ImageManager.zab;
                hashSet2.add(zadVar.zaa);
                imageReceiver2.zad();
            }
        }
    }
}
