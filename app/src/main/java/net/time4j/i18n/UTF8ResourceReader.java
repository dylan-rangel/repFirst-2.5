package net.time4j.i18n;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;

/* loaded from: classes3.dex */
class UTF8ResourceReader extends Reader {
    private BufferedReader internal = null;
    private final PushbackInputStream pis;

    UTF8ResourceReader(InputStream inputStream) {
        this.pis = new PushbackInputStream(inputStream, 3);
    }

    public String readLine() throws IOException {
        init();
        return this.internal.readLine();
    }

    @Override // java.io.Reader
    public int read(char[] cArr, int i, int i2) throws IOException {
        init();
        return this.internal.read(cArr, i, i2);
    }

    @Override // java.io.Reader
    public boolean ready() throws IOException {
        init();
        return this.internal.ready();
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        BufferedReader bufferedReader = this.internal;
        if (bufferedReader == null) {
            this.pis.close();
        } else {
            bufferedReader.close();
        }
    }

    private void init() throws IOException {
        if (this.internal != null) {
            return;
        }
        byte[] bArr = new byte[3];
        int read = this.pis.read(bArr, 0, 3);
        if (!(read == 3 && bArr[0] == -17 && bArr[1] == -69 && bArr[2] == -65) && read > 0) {
            this.pis.unread(bArr, 0, read);
        }
        this.internal = new BufferedReader(new InputStreamReader(this.pis, "UTF-8"));
    }
}
