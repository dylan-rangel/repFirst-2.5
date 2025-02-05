package com.mattermost.pasteinput;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.RNFetchBlob.RNFetchBlobConst;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.views.textinput.ReactTextInputShadowNode;
import java.io.File;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: RealPathUtil.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0006H\u0002J5\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\n2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u0011H\u0002¢\u0006\u0002\u0010\u0012J\u0014\u0010\u0013\u001a\u0004\u0018\u00010\n2\b\u0010\r\u001a\u0004\u0018\u00010\nH\u0002J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0015\u001a\u00020\u0006H\u0002J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0016\u001a\u00020\nH\u0002J\u001a\u0010\u0017\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0014\u0010\u001e\u001a\u0004\u0018\u00010\n2\b\u0010\u001f\u001a\u0004\u0018\u00010\nH\u0002¨\u0006 "}, d2 = {"Lcom/mattermost/pasteinput/RealPathUtil;", "", "()V", "deleteRecursive", "", "fileOrDirectory", "Ljava/io/File;", "deleteTempFiles", "dir", "getDataColumn", "", "context", "Landroid/content/Context;", RNFetchBlobConst.DATA_ENCODE_URI, "Landroid/net/Uri;", ReactTextInputShadowNode.PROP_SELECTION, "selectionArgs", "", "(Landroid/content/Context;Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;", "getExtension", "getMimeType", "file", "filePath", "getPathFromSavingTempFile", "getRealPathFromURI", "isDownloadsDocument", "", "isExternalStorageDocument", "isGooglePhotosUri", "isMediaDocument", "sanitizeFilename", "filename", "mattermost_react-native-paste-input_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class RealPathUtil {
    public static final RealPathUtil INSTANCE;

    private RealPathUtil() {
    }

    static {
        RealPathUtil realPathUtil = new RealPathUtil();
        INSTANCE = realPathUtil;
        realPathUtil.deleteTempFiles(new File(PasteInputManager.CACHE_DIR_NAME));
    }

    public final String getRealPathFromURI(Context context, Uri uri) {
        List emptyList;
        Uri uri2;
        List emptyList2;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uri, "uri");
        if ((Build.VERSION.SDK_INT >= 19) && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                Intrinsics.checkNotNullExpressionValue(docId, "docId");
                List<String> split = new Regex(":").split(docId, 0);
                if (!split.isEmpty()) {
                    ListIterator<String> listIterator = split.listIterator(split.size());
                    while (listIterator.hasPrevious()) {
                        if (!(listIterator.previous().length() == 0)) {
                            emptyList2 = CollectionsKt.take(split, listIterator.nextIndex() + 1);
                            break;
                        }
                    }
                }
                emptyList2 = CollectionsKt.emptyList();
                Object[] array = emptyList2.toArray(new String[0]);
                if (array != null) {
                    String[] strArr = (String[]) array;
                    if (StringsKt.equals("primary", strArr[0], true)) {
                        File externalFilesDir = context.getExternalFilesDir(strArr[1]);
                        if (externalFilesDir == null) {
                            return null;
                        }
                        return externalFilesDir.getAbsolutePath();
                    }
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
                }
            } else if (isDownloadsDocument(uri)) {
                String id = DocumentsContract.getDocumentId(uri);
                String str = id;
                if (!TextUtils.isEmpty(str)) {
                    Intrinsics.checkNotNullExpressionValue(id, "id");
                    if (StringsKt.startsWith$default(id, "raw:", false, 2, (Object) null)) {
                        return new Regex("raw:").replaceFirst(str, "");
                    }
                    try {
                        return getPathFromSavingTempFile(context, uri);
                    } catch (NumberFormatException unused) {
                        Log.e(ReactConstants.TAG, Intrinsics.stringPlus("DownloadsProvider unexpected uri ", uri));
                        return null;
                    }
                }
            } else if (isMediaDocument(uri)) {
                String docId2 = DocumentsContract.getDocumentId(uri);
                Intrinsics.checkNotNullExpressionValue(docId2, "docId");
                List<String> split2 = new Regex(":").split(docId2, 0);
                if (!split2.isEmpty()) {
                    ListIterator<String> listIterator2 = split2.listIterator(split2.size());
                    while (listIterator2.hasPrevious()) {
                        if (!(listIterator2.previous().length() == 0)) {
                            emptyList = CollectionsKt.take(split2, listIterator2.nextIndex() + 1);
                            break;
                        }
                    }
                }
                emptyList = CollectionsKt.emptyList();
                Object[] array2 = emptyList.toArray(new String[0]);
                if (array2 != null) {
                    String[] strArr2 = (String[]) array2;
                    String str2 = strArr2[0];
                    if (Intrinsics.areEqual("image", str2)) {
                        uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if (Intrinsics.areEqual("video", str2)) {
                        uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else {
                        uri2 = Intrinsics.areEqual("audio", str2) ? MediaStore.Audio.Media.EXTERNAL_CONTENT_URI : null;
                    }
                    String[] strArr3 = {strArr2[1]};
                    if (uri2 == null) {
                        return null;
                    }
                    return INSTANCE.getDataColumn(context, uri2, "_id=?", strArr3);
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
            }
        }
        if (StringsKt.equals("content", uri.getScheme(), true)) {
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }
            return getPathFromSavingTempFile(context, uri);
        }
        if (StringsKt.equals("file", uri.getScheme(), true)) {
            return uri.getPath();
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0063 A[Catch: IOException -> 0x00b6, TryCatch #0 {IOException -> 0x00b6, blocks: (B:30:0x003c, B:12:0x0052, B:14:0x0063, B:15:0x0066, B:18:0x0072, B:21:0x008e, B:27:0x008a, B:28:0x006d), top: B:29:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008a A[Catch: IOException -> 0x00b6, TryCatch #0 {IOException -> 0x00b6, blocks: (B:30:0x003c, B:12:0x0052, B:14:0x0063, B:15:0x0066, B:18:0x0072, B:21:0x008e, B:27:0x008a, B:28:0x006d), top: B:29:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006d A[Catch: IOException -> 0x00b6, TryCatch #0 {IOException -> 0x00b6, blocks: (B:30:0x003c, B:12:0x0052, B:14:0x0063, B:15:0x0066, B:18:0x0072, B:21:0x008e, B:27:0x008a, B:28:0x006d), top: B:29:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x003c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final java.lang.String getPathFromSavingTempFile(android.content.Context r11, android.net.Uri r12) {
        /*
            r10 = this;
            r0 = 0
            android.content.ContentResolver r1 = r11.getContentResolver()     // Catch: java.lang.Exception -> L38
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r2 = r12
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Exception -> L38
            if (r1 != 0) goto L12
            r2 = r0
            goto L1c
        L12:
            java.lang.String r2 = "_display_name"
            int r2 = r1.getColumnIndex(r2)     // Catch: java.lang.Exception -> L38
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Exception -> L38
        L1c:
            if (r1 != 0) goto L1f
            goto L22
        L1f:
            r1.moveToFirst()     // Catch: java.lang.Exception -> L38
        L22:
            if (r2 != 0) goto L26
        L24:
            r1 = r0
            goto L33
        L26:
            java.lang.Number r2 = (java.lang.Number) r2     // Catch: java.lang.Exception -> L38
            int r2 = r2.intValue()     // Catch: java.lang.Exception -> L38
            if (r1 != 0) goto L2f
            goto L24
        L2f:
            java.lang.String r1 = r1.getString(r2)     // Catch: java.lang.Exception -> L38
        L33:
            java.lang.String r1 = r10.sanitizeFilename(r1)     // Catch: java.lang.Exception -> L38
            goto L3a
        L38:
            r1 = r0
        L3a:
            if (r1 != 0) goto L52
            java.lang.String r1 = r12.getLastPathSegment()     // Catch: java.io.IOException -> Lb6
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch: java.io.IOException -> Lb6
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1     // Catch: java.io.IOException -> Lb6
            java.lang.CharSequence r1 = kotlin.text.StringsKt.trim(r1)     // Catch: java.io.IOException -> Lb6
            java.lang.String r1 = r1.toString()     // Catch: java.io.IOException -> Lb6
            java.lang.String r1 = r10.sanitizeFilename(r1)     // Catch: java.io.IOException -> Lb6
        L52:
            java.io.File r2 = new java.io.File     // Catch: java.io.IOException -> Lb6
            java.io.File r3 = r11.getCacheDir()     // Catch: java.io.IOException -> Lb6
            java.lang.String r4 = "mmPasteInput"
            r2.<init>(r3, r4)     // Catch: java.io.IOException -> Lb6
            boolean r3 = r2.exists()     // Catch: java.io.IOException -> Lb6
            if (r3 != 0) goto L66
            r2.mkdirs()     // Catch: java.io.IOException -> Lb6
        L66:
            java.lang.String r3 = r12.getPath()     // Catch: java.io.IOException -> Lb6
            if (r3 != 0) goto L6d
            goto L72
        L6d:
            com.mattermost.pasteinput.RealPathUtil r4 = com.mattermost.pasteinput.RealPathUtil.INSTANCE     // Catch: java.io.IOException -> Lb6
            r4.getMimeType(r3)     // Catch: java.io.IOException -> Lb6
        L72:
            java.io.File r3 = new java.io.File     // Catch: java.io.IOException -> Lb6
            r3.<init>(r2, r1)     // Catch: java.io.IOException -> Lb6
            r3.createNewFile()     // Catch: java.io.IOException -> Lb6
            android.content.ContentResolver r11 = r11.getContentResolver()     // Catch: java.io.IOException -> Lb6
            java.lang.String r1 = "r"
            android.os.ParcelFileDescriptor r11 = r11.openFileDescriptor(r12, r1)     // Catch: java.io.IOException -> Lb6
            java.io.FileInputStream r12 = new java.io.FileInputStream     // Catch: java.io.IOException -> Lb6
            if (r11 != 0) goto L8a
            r11 = r0
            goto L8e
        L8a:
            java.io.FileDescriptor r11 = r11.getFileDescriptor()     // Catch: java.io.IOException -> Lb6
        L8e:
            r12.<init>(r11)     // Catch: java.io.IOException -> Lb6
            java.nio.channels.FileChannel r11 = r12.getChannel()     // Catch: java.io.IOException -> Lb6
            java.io.FileOutputStream r12 = new java.io.FileOutputStream     // Catch: java.io.IOException -> Lb6
            r12.<init>(r3)     // Catch: java.io.IOException -> Lb6
            java.nio.channels.FileChannel r12 = r12.getChannel()     // Catch: java.io.IOException -> Lb6
            r5 = r11
            java.nio.channels.ReadableByteChannel r5 = (java.nio.channels.ReadableByteChannel) r5     // Catch: java.io.IOException -> Lb6
            r6 = 0
            long r8 = r11.size()     // Catch: java.io.IOException -> Lb6
            r4 = r12
            r4.transferFrom(r5, r6, r8)     // Catch: java.io.IOException -> Lb6
            r11.close()     // Catch: java.io.IOException -> Lb6
            r12.close()     // Catch: java.io.IOException -> Lb6
            java.lang.String r11 = r3.getAbsolutePath()
            return r11
        Lb6:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mattermost.pasteinput.RealPathUtil.getPathFromSavingTempFile(android.content.Context, android.net.Uri):java.lang.String");
    }

    private final String sanitizeFilename(String filename) {
        if (filename == null) {
            return null;
        }
        return new File(filename).getName();
    }

    private final String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        try {
            Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, selection, selectionArgs, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        String string = query.getString(query.getColumnIndexOrThrow("_data"));
                        query.close();
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private final boolean isExternalStorageDocument(Uri uri) {
        return Intrinsics.areEqual("com.android.externalstorage.documents", uri.getAuthority());
    }

    private final boolean isDownloadsDocument(Uri uri) {
        return Intrinsics.areEqual("com.android.providers.downloads.documents", uri.getAuthority());
    }

    private final boolean isMediaDocument(Uri uri) {
        return Intrinsics.areEqual("com.android.providers.media.documents", uri.getAuthority());
    }

    private final boolean isGooglePhotosUri(Uri uri) {
        return Intrinsics.areEqual("com.google.android.apps.photos.content", uri.getAuthority());
    }

    private final String getExtension(String uri) {
        if (uri == null) {
            return null;
        }
        int lastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) uri, ".", 0, false, 6, (Object) null);
        if (lastIndexOf$default < 0) {
            return "";
        }
        String substring = uri.substring(lastIndexOf$default);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
        return substring;
    }

    private final String getMimeType(File file) {
        String extension = getExtension(file.getName());
        Integer valueOf = extension == null ? null : Integer.valueOf(extension.length());
        Intrinsics.checkNotNull(valueOf);
        if (valueOf.intValue() <= 0) {
            return "application/octet-stream";
        }
        MimeTypeMap singleton = MimeTypeMap.getSingleton();
        String substring = extension.substring(1);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
        return singleton.getMimeTypeFromExtension(substring);
    }

    private final String getMimeType(String filePath) {
        return getMimeType(new File(filePath));
    }

    private final void deleteTempFiles(File dir) {
        try {
            if (dir.isDirectory()) {
                deleteRecursive(dir);
            }
        } catch (Exception unused) {
        }
    }

    private final void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            File[] listFiles = fileOrDirectory.listFiles();
            Intrinsics.checkNotNullExpressionValue(listFiles, "fileOrDirectory.listFiles()");
            int i = 0;
            int length = listFiles.length;
            while (i < length) {
                File child = listFiles[i];
                i++;
                Intrinsics.checkNotNullExpressionValue(child, "child");
                deleteRecursive(child);
            }
        }
        fileOrDirectory.delete();
    }
}
