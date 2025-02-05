package com.reactnativecommunity.cameraroll;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.RNFetchBlob.RNFetchBlobConst;
import com.facebook.common.logging.FLog;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.module.annotations.ReactModule;
import com.oblador.keychain.KeychainModule;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@ReactModule(name = CameraRollModule.NAME)
/* loaded from: classes2.dex */
public class CameraRollModule extends NativeCameraRollModuleSpec {
    private static final String ASSET_TYPE_ALL = "All";
    private static final String ASSET_TYPE_PHOTOS = "Photos";
    private static final String ASSET_TYPE_VIDEOS = "Videos";
    private static final String ERROR_UNABLE_TO_DELETE = "E_UNABLE_TO_DELETE";
    private static final String ERROR_UNABLE_TO_FILTER = "E_UNABLE_TO_FILTER";
    private static final String ERROR_UNABLE_TO_LOAD = "E_UNABLE_TO_LOAD";
    private static final String ERROR_UNABLE_TO_LOAD_PERMISSION = "E_UNABLE_TO_LOAD_PERMISSION";
    private static final String ERROR_UNABLE_TO_SAVE = "E_UNABLE_TO_SAVE";
    private static final String INCLUDE_ALBUMS = "albums";
    private static final String INCLUDE_FILENAME = "filename";
    private static final String INCLUDE_FILE_EXTENSION = "fileExtension";
    private static final String INCLUDE_FILE_SIZE = "fileSize";
    private static final String INCLUDE_IMAGE_SIZE = "imageSize";
    private static final String INCLUDE_LOCATION = "location";
    private static final String INCLUDE_ORIENTATION = "orientation";
    private static final String INCLUDE_PLAYABLE_DURATION = "playableDuration";
    public static final String NAME = "RNCCameraRoll";
    private static final String[] PROJECTION = {"_id", "mime_type", "bucket_display_name", "datetaken", "date_added", "date_modified", "width", "height", "_size", "_data", "orientation"};
    private static final String SELECTION_BUCKET = "bucket_display_name = ?";

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    public CameraRollModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @Override // com.reactnativecommunity.cameraroll.NativeCameraRollModuleSpec
    @ReactMethod
    public void saveToCameraRoll(String str, ReadableMap readableMap, Promise promise) {
        new SaveToCameraRoll(getReactApplicationContext(), Uri.parse(str), readableMap, promise).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SaveToCameraRoll extends GuardedAsyncTask<Void, Void> {
        private final Context mContext;
        private final ReadableMap mOptions;
        private final Promise mPromise;
        private final Uri mUri;

        public SaveToCameraRoll(ReactContext reactContext, Uri uri, ReadableMap readableMap, Promise promise) {
            super(reactContext);
            this.mContext = reactContext;
            this.mUri = uri;
            this.mPromise = promise;
            this.mOptions = readableMap;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:46:0x01ea A[Catch: IOException -> 0x01ee, TRY_ENTER, TRY_LEAVE, TryCatch #6 {IOException -> 0x01ee, blocks: (B:31:0x01c1, B:46:0x01ea), top: B:8:0x0032 }] */
        /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:48:0x01df A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:59:0x0203 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:66:? A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:67:0x01f8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r10v10 */
        /* JADX WARN: Type inference failed for: r10v12 */
        /* JADX WARN: Type inference failed for: r10v17 */
        /* JADX WARN: Type inference failed for: r10v2, types: [java.lang.Boolean] */
        /* JADX WARN: Type inference failed for: r10v21, types: [java.io.OutputStream] */
        /* JADX WARN: Type inference failed for: r10v22, types: [java.io.OutputStream] */
        /* JADX WARN: Type inference failed for: r10v26 */
        /* JADX WARN: Type inference failed for: r10v27 */
        /* JADX WARN: Type inference failed for: r10v3 */
        /* JADX WARN: Type inference failed for: r10v6 */
        /* JADX WARN: Type inference failed for: r10v7, types: [java.io.OutputStream] */
        /* JADX WARN: Type inference failed for: r10v9 */
        /* JADX WARN: Type inference failed for: r11v12, types: [java.io.FileInputStream] */
        /* JADX WARN: Type inference failed for: r11v17, types: [java.io.FileInputStream] */
        /* JADX WARN: Type inference failed for: r11v19 */
        /* JADX WARN: Type inference failed for: r11v20 */
        /* JADX WARN: Type inference failed for: r11v3 */
        /* JADX WARN: Type inference failed for: r11v4, types: [java.io.FileInputStream] */
        /* JADX WARN: Type inference failed for: r11v5, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r11v6 */
        /* JADX WARN: Type inference failed for: r11v8 */
        @Override // com.facebook.react.bridge.GuardedAsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void doInBackgroundGuarded(java.lang.Void... r19) {
            /*
                Method dump skipped, instructions count: 525
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.reactnativecommunity.cameraroll.CameraRollModule.SaveToCameraRoll.doInBackgroundGuarded(java.lang.Void[]):void");
        }

        /* renamed from: lambda$doInBackgroundGuarded$0$com-reactnativecommunity-cameraroll-CameraRollModule$SaveToCameraRoll, reason: not valid java name */
        /* synthetic */ void m169x20a5b564(String str, Uri uri) {
            if (uri != null) {
                this.mPromise.resolve(uri.toString());
            } else {
                this.mPromise.reject(CameraRollModule.ERROR_UNABLE_TO_SAVE, "Could not add image to gallery");
            }
        }
    }

    @Override // com.reactnativecommunity.cameraroll.NativeCameraRollModuleSpec
    @ReactMethod
    public void getPhotos(ReadableMap readableMap, Promise promise) {
        int i = readableMap.getInt("first");
        String string = readableMap.hasKey("after") ? readableMap.getString("after") : null;
        String string2 = readableMap.hasKey("groupName") ? readableMap.getString("groupName") : null;
        String string3 = readableMap.hasKey("assetType") ? readableMap.getString("assetType") : ASSET_TYPE_PHOTOS;
        long j = readableMap.hasKey("fromTime") ? (long) readableMap.getDouble("fromTime") : 0L;
        long j2 = readableMap.hasKey("toTime") ? (long) readableMap.getDouble("toTime") : 0L;
        new GetMediaTask(getReactApplicationContext(), i, string, string2, readableMap.hasKey("mimeTypes") ? readableMap.getArray("mimeTypes") : null, string3, j, j2, readableMap.hasKey("include") ? readableMap.getArray("include") : null, promise).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    private static class GetMediaTask extends GuardedAsyncTask<Void, Void> {

        @Nullable
        private final String mAfter;
        private final String mAssetType;
        private final Context mContext;
        private final int mFirst;
        private final long mFromTime;

        @Nullable
        private final String mGroupName;
        private final Set<String> mInclude;

        @Nullable
        private final ReadableArray mMimeTypes;
        private final Promise mPromise;
        private final long mToTime;

        private GetMediaTask(ReactContext reactContext, int i, @Nullable String str, @Nullable String str2, @Nullable ReadableArray readableArray, String str3, long j, long j2, @Nullable ReadableArray readableArray2, Promise promise) {
            super(reactContext);
            this.mContext = reactContext;
            this.mFirst = i;
            this.mAfter = str;
            this.mGroupName = str2;
            this.mMimeTypes = readableArray;
            this.mPromise = promise;
            this.mAssetType = str3;
            this.mFromTime = j;
            this.mToTime = j2;
            this.mInclude = createSetFromIncludeArray(readableArray2);
        }

        private static Set<String> createSetFromIncludeArray(@Nullable ReadableArray readableArray) {
            HashSet hashSet = new HashSet();
            if (readableArray == null) {
                return hashSet;
            }
            for (int i = 0; i < readableArray.size(); i++) {
                String string = readableArray.getString(i);
                if (string != null) {
                    hashSet.add(string);
                }
            }
            return hashSet;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.facebook.react.bridge.GuardedAsyncTask
        public void doInBackgroundGuarded(Void... voidArr) {
            Cursor query;
            StringBuilder sb = new StringBuilder("1");
            ArrayList arrayList = new ArrayList();
            if (!TextUtils.isEmpty(this.mGroupName)) {
                sb.append(" AND bucket_display_name = ?");
                arrayList.add(this.mGroupName);
            }
            if (this.mAssetType.equals(CameraRollModule.ASSET_TYPE_PHOTOS)) {
                sb.append(" AND media_type = 1");
            } else if (this.mAssetType.equals(CameraRollModule.ASSET_TYPE_VIDEOS)) {
                sb.append(" AND media_type = 3");
            } else if (this.mAssetType.equals(CameraRollModule.ASSET_TYPE_ALL)) {
                sb.append(" AND media_type IN (3,1)");
            } else {
                this.mPromise.reject(CameraRollModule.ERROR_UNABLE_TO_FILTER, "Invalid filter option: '" + this.mAssetType + "'. Expected one of '" + CameraRollModule.ASSET_TYPE_PHOTOS + "', '" + CameraRollModule.ASSET_TYPE_VIDEOS + "' or '" + CameraRollModule.ASSET_TYPE_ALL + "'.");
                return;
            }
            ReadableArray readableArray = this.mMimeTypes;
            if (readableArray != null && readableArray.size() > 0) {
                sb.append(" AND mime_type IN (");
                for (int i = 0; i < this.mMimeTypes.size(); i++) {
                    sb.append("?,");
                    arrayList.add(this.mMimeTypes.getString(i));
                }
                sb.replace(sb.length() - 1, sb.length(), ")");
            }
            long j = this.mFromTime;
            if (j > 0) {
                sb.append(" AND (datetaken > ? OR ( datetaken IS NULL AND date_added> ? ))");
                arrayList.add(this.mFromTime + "");
                arrayList.add((j / 1000) + "");
            }
            long j2 = this.mToTime;
            if (j2 > 0) {
                sb.append(" AND (datetaken <= ? OR ( datetaken IS NULL AND date_added <= ? ))");
                arrayList.add(this.mToTime + "");
                arrayList.add((j2 / 1000) + "");
            }
            WritableNativeMap writableNativeMap = new WritableNativeMap();
            ContentResolver contentResolver = this.mContext.getContentResolver();
            try {
                if (Build.VERSION.SDK_INT >= 30) {
                    Bundle bundle = new Bundle();
                    bundle.putString("android:query-arg-sql-selection", sb.toString());
                    bundle.putStringArray("android:query-arg-sql-selection-args", (String[]) arrayList.toArray(new String[arrayList.size()]));
                    bundle.putString("android:query-arg-sql-sort-order", "date_added DESC, date_modified DESC");
                    bundle.putInt("android:query-arg-limit", this.mFirst + 1);
                    if (!TextUtils.isEmpty(this.mAfter)) {
                        bundle.putInt("android:query-arg-offset", Integer.parseInt(this.mAfter));
                    }
                    query = contentResolver.query(MediaStore.Files.getContentUri("external"), CameraRollModule.PROJECTION, bundle, null);
                } else {
                    String str = "limit=" + (this.mFirst + 1);
                    if (!TextUtils.isEmpty(this.mAfter)) {
                        str = "limit=" + this.mAfter + "," + (this.mFirst + 1);
                    }
                    query = contentResolver.query(MediaStore.Files.getContentUri("external").buildUpon().encodedQuery(str).build(), CameraRollModule.PROJECTION, sb.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]), "date_added DESC, date_modified DESC");
                }
                if (query != null) {
                    try {
                        CameraRollModule.putEdges(contentResolver, query, writableNativeMap, this.mFirst, this.mInclude);
                        CameraRollModule.putPageInfo(query, writableNativeMap, this.mFirst, TextUtils.isEmpty(this.mAfter) ? 0 : Integer.parseInt(this.mAfter));
                        query.close();
                        this.mPromise.resolve(writableNativeMap);
                        return;
                    } catch (Throwable th) {
                        query.close();
                        this.mPromise.resolve(writableNativeMap);
                        throw th;
                    }
                }
                this.mPromise.reject(CameraRollModule.ERROR_UNABLE_TO_LOAD, "Could not get media");
            } catch (SecurityException e) {
                this.mPromise.reject(CameraRollModule.ERROR_UNABLE_TO_LOAD_PERMISSION, "Could not get media: need READ_EXTERNAL_STORAGE permission", e);
            }
        }
    }

    @Override // com.reactnativecommunity.cameraroll.NativeCameraRollModuleSpec
    @ReactMethod
    public void getAlbums(ReadableMap readableMap, Promise promise) {
        String string = readableMap.hasKey("assetType") ? readableMap.getString("assetType") : ASSET_TYPE_ALL;
        StringBuilder sb = new StringBuilder("1");
        ArrayList arrayList = new ArrayList();
        if (string.equals(ASSET_TYPE_PHOTOS)) {
            sb.append(" AND media_type = 1");
        } else if (string.equals(ASSET_TYPE_VIDEOS)) {
            sb.append(" AND media_type = 3");
        } else {
            if (!string.equals(ASSET_TYPE_ALL)) {
                promise.reject(ERROR_UNABLE_TO_FILTER, "Invalid filter option: '" + string + "'. Expected one of '" + ASSET_TYPE_PHOTOS + "', '" + ASSET_TYPE_VIDEOS + "' or '" + ASSET_TYPE_ALL + "'.");
                return;
            }
            sb.append(" AND media_type IN (3,1)");
        }
        try {
            Cursor query = getReactApplicationContext().getContentResolver().query(MediaStore.Files.getContentUri("external"), new String[]{"bucket_display_name"}, sb.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]), null);
            if (query == null) {
                promise.reject(ERROR_UNABLE_TO_LOAD, "Could not get media");
                return;
            }
            WritableNativeArray writableNativeArray = new WritableNativeArray();
            try {
                if (query.moveToFirst()) {
                    HashMap hashMap = new HashMap();
                    do {
                        int columnIndex = query.getColumnIndex("bucket_display_name");
                        if (columnIndex < 0) {
                            throw new IndexOutOfBoundsException();
                        }
                        String string2 = query.getString(columnIndex);
                        if (string2 != null) {
                            Integer num = (Integer) hashMap.get(string2);
                            if (num == null) {
                                hashMap.put(string2, 1);
                            } else {
                                hashMap.put(string2, Integer.valueOf(num.intValue() + 1));
                            }
                        }
                    } while (query.moveToNext());
                    for (Map.Entry entry : hashMap.entrySet()) {
                        WritableNativeMap writableNativeMap = new WritableNativeMap();
                        writableNativeMap.putString(KeychainModule.AuthPromptOptions.TITLE, (String) entry.getKey());
                        writableNativeMap.putInt("count", ((Integer) entry.getValue()).intValue());
                        writableNativeArray.pushMap(writableNativeMap);
                    }
                }
                query.close();
                promise.resolve(writableNativeArray);
            } catch (Throwable th) {
                query.close();
                promise.resolve(writableNativeArray);
                throw th;
            }
        } catch (Exception e) {
            promise.reject(ERROR_UNABLE_TO_LOAD, "Could not get media", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void putPageInfo(Cursor cursor, WritableMap writableMap, int i, int i2) {
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putBoolean("has_next_page", i < cursor.getCount());
        if (i < cursor.getCount()) {
            writableNativeMap.putString("end_cursor", Integer.toString(i2 + i));
        }
        writableMap.putMap("page_info", writableNativeMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [com.facebook.react.bridge.WritableArray] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    public static void putEdges(ContentResolver contentResolver, Cursor cursor, WritableMap writableMap, int i, Set<String> set) {
        ?? r0;
        int i2;
        WritableNativeArray writableNativeArray = new WritableNativeArray();
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex("mime_type");
        int columnIndex2 = cursor.getColumnIndex("bucket_display_name");
        int columnIndex3 = cursor.getColumnIndex("datetaken");
        int columnIndex4 = cursor.getColumnIndex("date_added");
        int columnIndex5 = cursor.getColumnIndex("date_modified");
        int columnIndex6 = cursor.getColumnIndex("width");
        int columnIndex7 = cursor.getColumnIndex("height");
        int columnIndex8 = cursor.getColumnIndex("_size");
        int columnIndex9 = cursor.getColumnIndex("_data");
        int columnIndex10 = cursor.getColumnIndex("orientation");
        boolean contains = set.contains("location");
        boolean contains2 = set.contains("filename");
        boolean contains3 = set.contains(INCLUDE_FILE_SIZE);
        boolean contains4 = set.contains(INCLUDE_FILE_EXTENSION);
        boolean contains5 = set.contains(INCLUDE_IMAGE_SIZE);
        boolean contains6 = set.contains(INCLUDE_PLAYABLE_DURATION);
        boolean contains7 = set.contains("orientation");
        boolean contains8 = set.contains(INCLUDE_ALBUMS);
        int i3 = i;
        int i4 = 0;
        while (i4 < i3 && !cursor.isAfterLast()) {
            WritableNativeMap writableNativeMap = new WritableNativeMap();
            WritableNativeMap writableNativeMap2 = new WritableNativeMap();
            int i5 = i4;
            WritableNativeArray writableNativeArray2 = writableNativeArray;
            if (putImageInfo(contentResolver, cursor, writableNativeMap2, columnIndex6, columnIndex7, columnIndex8, columnIndex9, columnIndex10, columnIndex, contains2, contains3, contains4, contains5, contains6, contains7)) {
                putBasicNodeInfo(cursor, writableNativeMap2, columnIndex, columnIndex2, columnIndex3, columnIndex4, columnIndex5, contains8);
                putLocationInfo(cursor, writableNativeMap2, columnIndex9, contains, columnIndex, contentResolver);
                writableNativeMap.putMap("node", writableNativeMap2);
                r0 = writableNativeArray2;
                r0.pushMap(writableNativeMap);
                i2 = i5;
            } else {
                r0 = writableNativeArray2;
                i2 = i5 - 1;
            }
            cursor.moveToNext();
            i4 = i2 + 1;
            i3 = i;
            writableNativeArray = r0;
        }
        writableMap.putArray("edges", writableNativeArray);
    }

    private static void putBasicNodeInfo(Cursor cursor, WritableMap writableMap, int i, int i2, int i3, int i4, int i5, boolean z) {
        writableMap.putString("type", cursor.getString(i));
        writableMap.putArray("subTypes", Arguments.createArray());
        WritableArray createArray = Arguments.createArray();
        if (z) {
            createArray.pushString(cursor.getString(i2));
        }
        writableMap.putArray("group_name", createArray);
        long j = cursor.getLong(i3);
        if (j == 0) {
            j = cursor.getLong(i4) * 1000;
        }
        writableMap.putDouble("timestamp", j / 1000.0d);
        writableMap.putDouble("modificationTimestamp", cursor.getLong(i5));
    }

    private static boolean putImageInfo(ContentResolver contentResolver, Cursor cursor, WritableMap writableMap, int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        Uri parse = Uri.parse("file://" + cursor.getString(i4));
        writableNativeMap.putString(RNFetchBlobConst.DATA_ENCODE_URI, parse.toString());
        String string = cursor.getString(i6);
        boolean z7 = string != null && string.startsWith("video");
        boolean putImageSize = putImageSize(contentResolver, cursor, writableNativeMap, i, i2, i5, parse, z7, z4);
        boolean putPlayableDuration = putPlayableDuration(contentResolver, writableNativeMap, parse, z7, z5);
        if (z) {
            writableNativeMap.putString("filename", new File(cursor.getString(i4)).getName());
        } else {
            writableNativeMap.putNull("filename");
        }
        if (z2) {
            writableNativeMap.putDouble(INCLUDE_FILE_SIZE, cursor.getLong(i3));
        } else {
            writableNativeMap.putNull(INCLUDE_FILE_SIZE);
        }
        if (z3) {
            writableNativeMap.putString("extension", Utils.getExtension(string));
        } else {
            writableNativeMap.putNull("extension");
        }
        if (z6) {
            if (cursor.isNull(i5)) {
                writableNativeMap.putInt("orientation", cursor.getInt(i5));
            } else {
                writableNativeMap.putInt("orientation", 0);
            }
        } else {
            writableNativeMap.putNull("orientation");
        }
        writableMap.putMap("image", writableNativeMap);
        return putImageSize && putPlayableDuration;
    }

    private static boolean putPlayableDuration(ContentResolver contentResolver, WritableMap writableMap, Uri uri, boolean z, boolean z2) {
        AssetFileDescriptor assetFileDescriptor;
        writableMap.putNull(INCLUDE_PLAYABLE_DURATION);
        boolean z3 = true;
        if (z2 && z) {
            boolean z4 = false;
            Integer num = null;
            try {
                assetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r");
            } catch (FileNotFoundException e) {
                FLog.e(ReactConstants.TAG, "Could not open asset file " + uri.toString(), e);
                assetFileDescriptor = null;
                z3 = false;
            }
            if (assetFileDescriptor != null) {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                try {
                    mediaMetadataRetriever.setDataSource(assetFileDescriptor.getFileDescriptor());
                } catch (RuntimeException unused) {
                }
                try {
                    num = Integer.valueOf(Integer.parseInt(mediaMetadataRetriever.extractMetadata(9)) / 1000);
                    z4 = z3;
                } catch (NumberFormatException e2) {
                    FLog.e(ReactConstants.TAG, "Number format exception occurred while trying to fetch video metadata for " + uri.toString(), e2);
                }
                try {
                    mediaMetadataRetriever.release();
                } catch (Exception unused2) {
                }
                z3 = z4;
            }
            if (assetFileDescriptor != null) {
                try {
                    assetFileDescriptor.close();
                } catch (IOException unused3) {
                }
            }
            if (num != null) {
                writableMap.putInt(INCLUDE_PLAYABLE_DURATION, num.intValue());
            }
        }
        return z3;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static boolean putImageSize(ContentResolver contentResolver, Cursor cursor, WritableMap writableMap, int i, int i2, int i3, Uri uri, boolean z, boolean z2) {
        AssetFileDescriptor assetFileDescriptor;
        boolean z3;
        int i4;
        writableMap.putNull("width");
        writableMap.putNull("height");
        boolean z4 = true;
        if (!z2) {
            return true;
        }
        int i5 = cursor.getInt(i);
        int i6 = cursor.getInt(i2);
        if (i5 <= 0 || i6 <= 0) {
            boolean z5 = false;
            try {
                assetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r");
                z3 = true;
            } catch (FileNotFoundException e) {
                FLog.e(ReactConstants.TAG, "Could not open asset file " + uri.toString(), e);
                assetFileDescriptor = null;
                z3 = false;
            }
            if (assetFileDescriptor != null) {
                if (z) {
                    MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                    try {
                        mediaMetadataRetriever.setDataSource(assetFileDescriptor.getFileDescriptor());
                    } catch (RuntimeException unused) {
                    }
                    try {
                        i5 = Integer.parseInt(mediaMetadataRetriever.extractMetadata(18));
                        i6 = Integer.parseInt(mediaMetadataRetriever.extractMetadata(19));
                        z5 = z3;
                    } catch (NumberFormatException e2) {
                        FLog.e(ReactConstants.TAG, "Number format exception occurred while trying to fetch video metadata for " + uri.toString(), e2);
                    }
                    try {
                        mediaMetadataRetriever.release();
                    } catch (Exception unused2) {
                    }
                    z4 = z5;
                } else {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFileDescriptor(assetFileDescriptor.getFileDescriptor(), null, options);
                    int i7 = options.outWidth;
                    i6 = options.outHeight;
                    i5 = i7;
                    z4 = z3;
                }
                try {
                    assetFileDescriptor.close();
                } catch (IOException e3) {
                    FLog.e(ReactConstants.TAG, "Can't close media descriptor " + uri.toString(), e3);
                }
            } else {
                z4 = z3;
            }
        }
        if (!cursor.isNull(i3) && (i4 = cursor.getInt(i3)) >= 0 && i4 % RotationOptions.ROTATE_180 != 0) {
            int i8 = i6;
            i6 = i5;
            i5 = i8;
        }
        writableMap.putInt("width", i5);
        writableMap.putInt("height", i6);
        return z4;
    }

    private static void putLocationInfo(Cursor cursor, WritableMap writableMap, int i, boolean z, int i2, ContentResolver contentResolver) {
        writableMap.putNull("location");
        if (z) {
            try {
                String string = cursor.getString(i2);
                if (string != null && string.startsWith("video")) {
                    Uri parse = Uri.parse("file://" + cursor.getString(i));
                    AssetFileDescriptor assetFileDescriptor = null;
                    try {
                        assetFileDescriptor = contentResolver.openAssetFileDescriptor(parse, "r");
                    } catch (FileNotFoundException e) {
                        FLog.e(ReactConstants.TAG, "Could not open asset file " + parse.toString(), e);
                    }
                    if (assetFileDescriptor != null) {
                        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                        try {
                            mediaMetadataRetriever.setDataSource(assetFileDescriptor.getFileDescriptor());
                        } catch (RuntimeException unused) {
                        }
                        try {
                            String extractMetadata = mediaMetadataRetriever.extractMetadata(23);
                            if (extractMetadata != null) {
                                String replaceAll = extractMetadata.replaceAll("/", "");
                                WritableNativeMap writableNativeMap = new WritableNativeMap();
                                writableNativeMap.putDouble("latitude", Double.parseDouble(replaceAll.split("[+]|[-]")[1]));
                                writableNativeMap.putDouble("longitude", Double.parseDouble(replaceAll.split("[+]|[-]")[2]));
                                writableMap.putMap("location", writableNativeMap);
                            }
                        } catch (NumberFormatException e2) {
                            FLog.e(ReactConstants.TAG, "Number format exception occurred while trying to fetch video metadata for " + parse.toString(), e2);
                        }
                        try {
                            mediaMetadataRetriever.release();
                        } catch (Exception unused2) {
                        }
                    }
                    if (assetFileDescriptor != null) {
                        try {
                            assetFileDescriptor.close();
                            return;
                        } catch (IOException unused3) {
                            return;
                        }
                    }
                    return;
                }
                ExifInterface exifInterface = new ExifInterface(cursor.getString(i));
                float[] fArr = new float[2];
                if (exifInterface.getLatLong(fArr)) {
                    double d = fArr[1];
                    double d2 = fArr[0];
                    WritableNativeMap writableNativeMap2 = new WritableNativeMap();
                    writableNativeMap2.putDouble("longitude", d);
                    writableNativeMap2.putDouble("latitude", d2);
                    writableMap.putMap("location", writableNativeMap2);
                }
            } catch (IOException e3) {
                FLog.e(ReactConstants.TAG, "Could not read the metadata", e3);
            }
        }
    }

    @Override // com.reactnativecommunity.cameraroll.NativeCameraRollModuleSpec
    @ReactMethod
    public void deletePhotos(ReadableArray readableArray, Promise promise) {
        if (readableArray.size() == 0) {
            promise.reject(ERROR_UNABLE_TO_DELETE, "Need at least one URI to delete");
        } else {
            new DeletePhotos(getReactApplicationContext(), readableArray, promise).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    @Override // com.reactnativecommunity.cameraroll.NativeCameraRollModuleSpec
    @ReactMethod
    public void getPhotoByInternalID(String str, ReadableMap readableMap, Promise promise) {
        promise.reject("CameraRoll:getPhotoByInternalID", "getPhotoByInternalID is not supported on Android");
    }

    private static class DeletePhotos extends GuardedAsyncTask<Void, Void> {
        private final Context mContext;
        private final Promise mPromise;
        private final ReadableArray mUris;

        public DeletePhotos(ReactContext reactContext, ReadableArray readableArray, Promise promise) {
            super(reactContext);
            this.mContext = reactContext;
            this.mUris = readableArray;
            this.mPromise = promise;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.facebook.react.bridge.GuardedAsyncTask
        public void doInBackgroundGuarded(Void... voidArr) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            String[] strArr = {"_id"};
            String str = "?";
            for (int i = 1; i < this.mUris.size(); i++) {
                str = str + ", ?";
            }
            String str2 = "_data IN (" + str + ")";
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] strArr2 = new String[this.mUris.size()];
            int i2 = 0;
            for (int i3 = 0; i3 < this.mUris.size(); i3++) {
                strArr2[i3] = Uri.parse(this.mUris.getString(i3)).getPath();
            }
            Cursor query = contentResolver.query(uri, strArr, str2, strArr2, null);
            while (query.moveToNext()) {
                if (contentResolver.delete(ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, query.getLong(query.getColumnIndexOrThrow("_id"))), null, null) == 1) {
                    i2++;
                }
            }
            query.close();
            if (i2 == this.mUris.size()) {
                this.mPromise.resolve(true);
                return;
            }
            this.mPromise.reject(CameraRollModule.ERROR_UNABLE_TO_DELETE, "Could not delete all media, only deleted " + i2 + " photos.");
        }
    }

    @Override // com.reactnativecommunity.cameraroll.NativeCameraRollModuleSpec
    @ReactMethod
    public void getPhotoThumbnail(String str, ReadableMap readableMap, Promise promise) {
        promise.reject("CameraRoll:getPhotoThumbnail", "getPhotoThumbnail is not supported on Android");
    }
}
