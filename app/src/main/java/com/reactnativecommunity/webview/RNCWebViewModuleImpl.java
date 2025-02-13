package com.reactnativecommunity.webview;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.util.Pair;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes2.dex */
public class RNCWebViewModuleImpl implements ActivityEventListener {
    public static final int FILE_DOWNLOAD_PERMISSION_REQUEST = 1;
    public static final String NAME = "RNCWebView";
    public static final int PICKER = 1;
    public static final int PICKER_LEGACY = 3;
    protected static final ShouldOverrideUrlLoadingLock shouldOverrideUrlLoadingLock = new ShouldOverrideUrlLoadingLock();
    private final ReactApplicationContext mContext;
    private DownloadManager.Request mDownloadRequest;
    private ValueCallback<Uri[]> mFilePathCallback;
    private ValueCallback<Uri> mFilePathCallbackLegacy;
    private File mOutputImage;
    private File mOutputVideo;

    public boolean isFileUploadSupported() {
        return true;
    }

    @Override // com.facebook.react.bridge.ActivityEventListener
    public void onNewIntent(Intent intent) {
    }

    public RNCWebViewModuleImpl(ReactApplicationContext reactApplicationContext) {
        this.mContext = reactApplicationContext;
        reactApplicationContext.addActivityEventListener(this);
    }

    @Override // com.facebook.react.bridge.ActivityEventListener
    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        if (this.mFilePathCallback == null && this.mFilePathCallbackLegacy == null) {
            return;
        }
        File file = this.mOutputImage;
        boolean z = file != null && file.length() > 0;
        File file2 = this.mOutputVideo;
        boolean z2 = file2 != null && file2.length() > 0;
        if (i != 1) {
            if (i == 3) {
                if (i2 != -1) {
                    this.mFilePathCallbackLegacy.onReceiveValue(null);
                } else if (z) {
                    this.mFilePathCallbackLegacy.onReceiveValue(getOutputUri(this.mOutputImage));
                } else if (z2) {
                    this.mFilePathCallbackLegacy.onReceiveValue(getOutputUri(this.mOutputVideo));
                } else {
                    this.mFilePathCallbackLegacy.onReceiveValue(intent.getData());
                }
            }
        } else if (i2 != -1) {
            ValueCallback<Uri[]> valueCallback = this.mFilePathCallback;
            if (valueCallback != null) {
                valueCallback.onReceiveValue(null);
            }
        } else if (z) {
            this.mFilePathCallback.onReceiveValue(new Uri[]{getOutputUri(this.mOutputImage)});
        } else if (z2) {
            this.mFilePathCallback.onReceiveValue(new Uri[]{getOutputUri(this.mOutputVideo)});
        } else {
            this.mFilePathCallback.onReceiveValue(getSelectedFiles(intent, i2));
        }
        File file3 = this.mOutputImage;
        if (file3 != null && !z) {
            file3.delete();
        }
        File file4 = this.mOutputVideo;
        if (file4 != null && !z2) {
            file4.delete();
        }
        this.mFilePathCallback = null;
        this.mFilePathCallbackLegacy = null;
        this.mOutputImage = null;
        this.mOutputVideo = null;
    }

    protected static class ShouldOverrideUrlLoadingLock {
        private double nextLockIdentifier = 1.0d;
        private final HashMap<Double, AtomicReference<ShouldOverrideCallbackState>> shouldOverrideLocks = new HashMap<>();

        protected enum ShouldOverrideCallbackState {
            UNDECIDED,
            SHOULD_OVERRIDE,
            DO_NOT_OVERRIDE
        }

        protected ShouldOverrideUrlLoadingLock() {
        }

        public synchronized Pair<Double, AtomicReference<ShouldOverrideCallbackState>> getNewLock() {
            double d;
            AtomicReference<ShouldOverrideCallbackState> atomicReference;
            d = this.nextLockIdentifier;
            this.nextLockIdentifier = 1.0d + d;
            atomicReference = new AtomicReference<>(ShouldOverrideCallbackState.UNDECIDED);
            this.shouldOverrideLocks.put(Double.valueOf(d), atomicReference);
            return new Pair<>(Double.valueOf(d), atomicReference);
        }

        public synchronized AtomicReference<ShouldOverrideCallbackState> getLock(Double d) {
            return this.shouldOverrideLocks.get(d);
        }

        public synchronized void removeLock(Double d) {
            this.shouldOverrideLocks.remove(d);
        }
    }

    private enum MimeType {
        DEFAULT("*/*"),
        IMAGE("image"),
        VIDEO("video");

        private final String value;

        MimeType(String str) {
            this.value = str;
        }
    }

    private PermissionListener getWebviewFileDownloaderPermissionListener(final String str, final String str2) {
        return new PermissionListener() { // from class: com.reactnativecommunity.webview.RNCWebViewModuleImpl.1
            @Override // com.facebook.react.modules.core.PermissionListener
            public boolean onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
                if (i != 1) {
                    return false;
                }
                if (iArr.length <= 0 || iArr[0] != 0) {
                    Toast.makeText(RNCWebViewModuleImpl.this.mContext, str2, 1).show();
                } else if (RNCWebViewModuleImpl.this.mDownloadRequest != null) {
                    RNCWebViewModuleImpl.this.downloadFile(str);
                }
                return true;
            }
        };
    }

    public void shouldStartLoadWithLockIdentifier(boolean z, double d) {
        AtomicReference<ShouldOverrideUrlLoadingLock.ShouldOverrideCallbackState> lock = shouldOverrideUrlLoadingLock.getLock(Double.valueOf(d));
        if (lock != null) {
            synchronized (lock) {
                lock.set(z ? ShouldOverrideUrlLoadingLock.ShouldOverrideCallbackState.DO_NOT_OVERRIDE : ShouldOverrideUrlLoadingLock.ShouldOverrideCallbackState.SHOULD_OVERRIDE);
                lock.notify();
            }
        }
    }

    public Uri[] getSelectedFiles(Intent intent, int i) {
        if (intent == null) {
            return null;
        }
        if (intent.getClipData() != null) {
            int itemCount = intent.getClipData().getItemCount();
            Uri[] uriArr = new Uri[itemCount];
            for (int i2 = 0; i2 < itemCount; i2++) {
                uriArr[i2] = intent.getClipData().getItemAt(i2).getUri();
            }
            return uriArr;
        }
        if (intent.getData() == null || i != -1 || Build.VERSION.SDK_INT < 21) {
            return null;
        }
        return WebChromeClient.FileChooserParams.parseResult(i, intent);
    }

    public void startPhotoPickerIntent(String str, ValueCallback<Uri> valueCallback) {
        Intent videoIntent;
        Intent photoIntent;
        this.mFilePathCallbackLegacy = valueCallback;
        Activity currentActivity = this.mContext.getCurrentActivity();
        Intent createChooser = Intent.createChooser(getFileChooserIntent(str), "");
        ArrayList arrayList = new ArrayList();
        if (acceptsImages(str).booleanValue() && (photoIntent = getPhotoIntent()) != null) {
            arrayList.add(photoIntent);
        }
        if (acceptsVideo(str).booleanValue() && (videoIntent = getVideoIntent()) != null) {
            arrayList.add(videoIntent);
        }
        createChooser.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) arrayList.toArray(new Parcelable[0]));
        if (createChooser.resolveActivity(currentActivity.getPackageManager()) != null) {
            currentActivity.startActivityForResult(createChooser, 3);
        } else {
            Log.w("RNCWebViewModule", "there is no Activity to handle this Intent");
        }
    }

    public boolean startPhotoPickerIntent(String[] strArr, boolean z, ValueCallback<Uri[]> valueCallback, boolean z2) {
        Intent videoIntent;
        this.mFilePathCallback = valueCallback;
        Activity currentActivity = this.mContext.getCurrentActivity();
        ArrayList arrayList = new ArrayList();
        Intent intent = null;
        if (!needsCameraPermission()) {
            if (acceptsImages(strArr).booleanValue() && (intent = getPhotoIntent()) != null) {
                arrayList.add(intent);
            }
            if (acceptsVideo(strArr).booleanValue() && (videoIntent = getVideoIntent()) != null) {
                arrayList.add(videoIntent);
            }
        }
        Intent intent2 = new Intent("android.intent.action.CHOOSER");
        if (!z2) {
            intent2.putExtra("android.intent.extra.INTENT", getFileChooserIntent(strArr, z));
            intent2.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) arrayList.toArray(new Parcelable[0]));
            intent = intent2;
        }
        if (intent != null) {
            if (intent.resolveActivity(currentActivity.getPackageManager()) != null) {
                currentActivity.startActivityForResult(intent, 1);
            } else {
                Log.w("RNCWebViewModule", "there is no Activity to handle this Intent");
            }
        } else {
            Log.w("RNCWebViewModule", "there is no Camera permission");
        }
        return true;
    }

    public void setDownloadRequest(DownloadManager.Request request) {
        this.mDownloadRequest = request;
    }

    public void downloadFile(String str) {
        try {
            ((DownloadManager) this.mContext.getSystemService("download")).enqueue(this.mDownloadRequest);
            Toast.makeText(this.mContext, str, 1).show();
        } catch (IllegalArgumentException | SecurityException e) {
            Log.w("RNCWebViewModule", "Unsupported URI, aborting download", e);
        }
    }

    public boolean grantFileDownloaderPermissions(String str, String str2) {
        Activity currentActivity = this.mContext.getCurrentActivity();
        if (Build.VERSION.SDK_INT > 28) {
            return true;
        }
        boolean z = ContextCompat.checkSelfPermission(currentActivity, "android.permission.WRITE_EXTERNAL_STORAGE") == 0;
        if (!z && Build.VERSION.SDK_INT >= 23) {
            getPermissionAwareActivity().requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1, getWebviewFileDownloaderPermissionListener(str, str2));
        }
        return z;
    }

    protected boolean needsCameraPermission() {
        Activity currentActivity = this.mContext.getCurrentActivity();
        try {
            if (Arrays.asList(currentActivity.getPackageManager().getPackageInfo(currentActivity.getApplicationContext().getPackageName(), 4096).requestedPermissions).contains("android.permission.CAMERA")) {
                if (ContextCompat.checkSelfPermission(currentActivity, "android.permission.CAMERA") != 0) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            return true;
        }
    }

    public Intent getPhotoIntent() {
        Intent intent;
        Exception e;
        try {
            File capturedFile = getCapturedFile(MimeType.IMAGE);
            this.mOutputImage = capturedFile;
            Uri outputUri = getOutputUri(capturedFile);
            intent = new Intent("android.media.action.IMAGE_CAPTURE");
            try {
                intent.putExtra("output", outputUri);
            } catch (IOException e2) {
                e = e2;
                Log.e("CREATE FILE", "Error occurred while creating the File", e);
                e.printStackTrace();
                return intent;
            } catch (IllegalArgumentException e3) {
                e = e3;
                Log.e("CREATE FILE", "Error occurred while creating the File", e);
                e.printStackTrace();
                return intent;
            }
        } catch (IOException | IllegalArgumentException e4) {
            intent = null;
            e = e4;
        }
        return intent;
    }

    public Intent getVideoIntent() {
        Intent intent;
        Exception e;
        try {
            File capturedFile = getCapturedFile(MimeType.VIDEO);
            this.mOutputVideo = capturedFile;
            Uri outputUri = getOutputUri(capturedFile);
            intent = new Intent("android.media.action.VIDEO_CAPTURE");
            try {
                intent.putExtra("output", outputUri);
            } catch (IOException e2) {
                e = e2;
                Log.e("CREATE FILE", "Error occurred while creating the File", e);
                e.printStackTrace();
                return intent;
            } catch (IllegalArgumentException e3) {
                e = e3;
                Log.e("CREATE FILE", "Error occurred while creating the File", e);
                e.printStackTrace();
                return intent;
            }
        } catch (IOException | IllegalArgumentException e4) {
            intent = null;
            e = e4;
        }
        return intent;
    }

    private Intent getFileChooserIntent(String str) {
        String str2 = str.isEmpty() ? MimeType.DEFAULT.value : str;
        if (str.matches("\\.\\w+")) {
            str2 = getMimeTypeFromExtension(str.replace(".", ""));
        }
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType(str2);
        return intent;
    }

    private Intent getFileChooserIntent(String[] strArr, boolean z) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType(MimeType.DEFAULT.value);
        intent.putExtra("android.intent.extra.MIME_TYPES", getAcceptedMimeType(strArr));
        intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", z);
        return intent;
    }

    private Boolean acceptsImages(String str) {
        if (str.matches("\\.\\w+")) {
            str = getMimeTypeFromExtension(str.replace(".", ""));
        }
        return Boolean.valueOf(str.isEmpty() || str.toLowerCase().contains(MimeType.IMAGE.value));
    }

    private Boolean acceptsImages(String[] strArr) {
        String[] acceptedMimeType = getAcceptedMimeType(strArr);
        return Boolean.valueOf(arrayContainsString(acceptedMimeType, MimeType.DEFAULT.value).booleanValue() || arrayContainsString(acceptedMimeType, MimeType.IMAGE.value).booleanValue());
    }

    private Boolean acceptsVideo(String str) {
        if (Build.VERSION.SDK_INT < 23) {
            return false;
        }
        if (str.matches("\\.\\w+")) {
            str = getMimeTypeFromExtension(str.replace(".", ""));
        }
        return Boolean.valueOf(str.isEmpty() || str.toLowerCase().contains(MimeType.VIDEO.value));
    }

    private Boolean acceptsVideo(String[] strArr) {
        if (Build.VERSION.SDK_INT < 23) {
            return false;
        }
        String[] acceptedMimeType = getAcceptedMimeType(strArr);
        return Boolean.valueOf(arrayContainsString(acceptedMimeType, MimeType.DEFAULT.value).booleanValue() || arrayContainsString(acceptedMimeType, MimeType.VIDEO.value).booleanValue());
    }

    private Boolean arrayContainsString(String[] strArr, String str) {
        for (String str2 : strArr) {
            if (str2.contains(str)) {
                return true;
            }
        }
        return false;
    }

    private String[] getAcceptedMimeType(String[] strArr) {
        if (noAcceptTypesSet(strArr).booleanValue()) {
            return new String[]{MimeType.DEFAULT.value};
        }
        String[] strArr2 = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            String str = strArr[i];
            if (str.matches("\\.\\w+")) {
                String mimeTypeFromExtension = getMimeTypeFromExtension(str.replace(".", ""));
                if (mimeTypeFromExtension != null) {
                    strArr2[i] = mimeTypeFromExtension;
                } else {
                    strArr2[i] = str;
                }
            } else {
                strArr2[i] = str;
            }
        }
        return strArr2;
    }

    private String getMimeTypeFromExtension(String str) {
        if (str != null) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(str);
        }
        return null;
    }

    public Uri getOutputUri(File file) {
        if (Build.VERSION.SDK_INT < 23) {
            return Uri.fromFile(file);
        }
        String packageName = this.mContext.getPackageName();
        return FileProvider.getUriForFile(this.mContext, packageName + ".fileprovider", file);
    }

    /* renamed from: com.reactnativecommunity.webview.RNCWebViewModuleImpl$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$reactnativecommunity$webview$RNCWebViewModuleImpl$MimeType;

        static {
            int[] iArr = new int[MimeType.values().length];
            $SwitchMap$com$reactnativecommunity$webview$RNCWebViewModuleImpl$MimeType = iArr;
            try {
                iArr[MimeType.IMAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$reactnativecommunity$webview$RNCWebViewModuleImpl$MimeType[MimeType.VIDEO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.io.File getCapturedFile(com.reactnativecommunity.webview.RNCWebViewModuleImpl.MimeType r7) throws java.io.IOException {
        /*
            r6 = this;
            int[] r0 = com.reactnativecommunity.webview.RNCWebViewModuleImpl.AnonymousClass2.$SwitchMap$com$reactnativecommunity$webview$RNCWebViewModuleImpl$MimeType
            int r7 = r7.ordinal()
            r7 = r0[r7]
            r0 = 1
            java.lang.String r1 = ""
            if (r7 == r0) goto L1a
            r0 = 2
            if (r7 == r0) goto L13
            r7 = r1
            r0 = r7
            goto L23
        L13:
            java.lang.String r1 = android.os.Environment.DIRECTORY_MOVIES
            java.lang.String r7 = "video-"
            java.lang.String r0 = ".mp4"
            goto L20
        L1a:
            java.lang.String r1 = android.os.Environment.DIRECTORY_PICTURES
            java.lang.String r7 = "image-"
            java.lang.String r0 = ".jpg"
        L20:
            r5 = r1
            r1 = r7
            r7 = r5
        L23:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
            long r3 = java.lang.System.currentTimeMillis()
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 23
            if (r3 >= r4) goto L4d
            java.io.File r7 = android.os.Environment.getExternalStoragePublicDirectory(r7)
            java.io.File r0 = new java.io.File
            r0.<init>(r7, r2)
            goto L58
        L4d:
            com.facebook.react.bridge.ReactApplicationContext r7 = r6.mContext
            r2 = 0
            java.io.File r7 = r7.getExternalFilesDir(r2)
            java.io.File r0 = java.io.File.createTempFile(r1, r0, r7)
        L58:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.reactnativecommunity.webview.RNCWebViewModuleImpl.getCapturedFile(com.reactnativecommunity.webview.RNCWebViewModuleImpl$MimeType):java.io.File");
    }

    private Boolean noAcceptTypesSet(String[] strArr) {
        String str;
        boolean z = false;
        if (strArr.length == 0 || (strArr.length == 1 && (str = strArr[0]) != null && str.length() == 0)) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    private PermissionAwareActivity getPermissionAwareActivity() {
        ComponentCallbacks2 currentActivity = this.mContext.getCurrentActivity();
        if (currentActivity == null) {
            throw new IllegalStateException("Tried to use permissions API while not attached to an Activity.");
        }
        if (!(currentActivity instanceof PermissionAwareActivity)) {
            throw new IllegalStateException("Tried to use permissions API but the host Activity doesn't implement PermissionAwareActivity.");
        }
        return (PermissionAwareActivity) currentActivity;
    }
}
