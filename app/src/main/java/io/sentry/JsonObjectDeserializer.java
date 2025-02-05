package io.sentry;

import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes3.dex */
public final class JsonObjectDeserializer {
    private final ArrayList<Token> tokens = new ArrayList<>();

    /* JADX INFO: Access modifiers changed from: private */
    interface NextValue {
        Object nextValue() throws IOException;
    }

    private interface Token {
        Object getValue();
    }

    static /* synthetic */ Object lambda$parse$3() throws IOException {
        return null;
    }

    private static final class TokenName implements Token {
        final String value;

        TokenName(String str) {
            this.value = str;
        }

        @Override // io.sentry.JsonObjectDeserializer.Token
        public Object getValue() {
            return this.value;
        }
    }

    private static final class TokenPrimitive implements Token {
        final Object value;

        TokenPrimitive(Object obj) {
            this.value = obj;
        }

        @Override // io.sentry.JsonObjectDeserializer.Token
        public Object getValue() {
            return this.value;
        }
    }

    private static final class TokenArray implements Token {
        final ArrayList<Object> value;

        private TokenArray() {
            this.value = new ArrayList<>();
        }

        /* synthetic */ TokenArray(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // io.sentry.JsonObjectDeserializer.Token
        public Object getValue() {
            return this.value;
        }
    }

    private static final class TokenMap implements Token {
        final HashMap<String, Object> value;

        private TokenMap() {
            this.value = new HashMap<>();
        }

        /* synthetic */ TokenMap(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // io.sentry.JsonObjectDeserializer.Token
        public Object getValue() {
            return this.value;
        }
    }

    public Object deserialize(JsonObjectReader jsonObjectReader) throws IOException {
        parse(jsonObjectReader);
        Token currentToken = getCurrentToken();
        if (currentToken != null) {
            return currentToken.getValue();
        }
        return null;
    }

    /* renamed from: io.sentry.JsonObjectDeserializer$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$sentry$vendor$gson$stream$JsonToken;

        static {
            int[] iArr = new int[JsonToken.values().length];
            $SwitchMap$io$sentry$vendor$gson$stream$JsonToken = iArr;
            try {
                iArr[JsonToken.BEGIN_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$sentry$vendor$gson$stream$JsonToken[JsonToken.END_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$sentry$vendor$gson$stream$JsonToken[JsonToken.BEGIN_OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$sentry$vendor$gson$stream$JsonToken[JsonToken.END_OBJECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$io$sentry$vendor$gson$stream$JsonToken[JsonToken.NAME.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$io$sentry$vendor$gson$stream$JsonToken[JsonToken.STRING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$io$sentry$vendor$gson$stream$JsonToken[JsonToken.NUMBER.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$io$sentry$vendor$gson$stream$JsonToken[JsonToken.BOOLEAN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$io$sentry$vendor$gson$stream$JsonToken[JsonToken.NULL.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$io$sentry$vendor$gson$stream$JsonToken[JsonToken.END_DOCUMENT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void parse(final JsonObjectReader jsonObjectReader) throws IOException {
        boolean z;
        AnonymousClass1 anonymousClass1 = null;
        switch (AnonymousClass1.$SwitchMap$io$sentry$vendor$gson$stream$JsonToken[jsonObjectReader.peek().ordinal()]) {
            case 1:
                jsonObjectReader.beginArray();
                pushCurrentToken(new TokenArray(anonymousClass1));
                z = false;
                break;
            case 2:
                jsonObjectReader.endArray();
                z = handleArrayOrMapEnd();
                break;
            case 3:
                jsonObjectReader.beginObject();
                pushCurrentToken(new TokenMap(anonymousClass1));
                z = false;
                break;
            case 4:
                jsonObjectReader.endObject();
                z = handleArrayOrMapEnd();
                break;
            case 5:
                pushCurrentToken(new TokenName(jsonObjectReader.nextName()));
                z = false;
                break;
            case 6:
                z = handlePrimitive(new NextValue() { // from class: io.sentry.JsonObjectDeserializer$$ExternalSyntheticLambda0
                    @Override // io.sentry.JsonObjectDeserializer.NextValue
                    public final Object nextValue() {
                        Object nextString;
                        nextString = JsonObjectReader.this.nextString();
                        return nextString;
                    }
                });
                break;
            case 7:
                z = handlePrimitive(new NextValue() { // from class: io.sentry.JsonObjectDeserializer$$ExternalSyntheticLambda1
                    @Override // io.sentry.JsonObjectDeserializer.NextValue
                    public final Object nextValue() {
                        return JsonObjectDeserializer.this.m233lambda$parse$1$iosentryJsonObjectDeserializer(jsonObjectReader);
                    }
                });
                break;
            case 8:
                z = handlePrimitive(new NextValue() { // from class: io.sentry.JsonObjectDeserializer$$ExternalSyntheticLambda2
                    @Override // io.sentry.JsonObjectDeserializer.NextValue
                    public final Object nextValue() {
                        Object valueOf;
                        valueOf = Boolean.valueOf(JsonObjectReader.this.nextBoolean());
                        return valueOf;
                    }
                });
                break;
            case 9:
                jsonObjectReader.nextNull();
                z = handlePrimitive(new NextValue() { // from class: io.sentry.JsonObjectDeserializer$$ExternalSyntheticLambda3
                    @Override // io.sentry.JsonObjectDeserializer.NextValue
                    public final Object nextValue() {
                        return JsonObjectDeserializer.lambda$parse$3();
                    }
                });
                break;
            case 10:
                z = true;
                break;
            default:
                z = false;
                break;
        }
        if (z) {
            return;
        }
        parse(jsonObjectReader);
    }

    private boolean handleArrayOrMapEnd() {
        if (hasOneToken()) {
            return true;
        }
        Token currentToken = getCurrentToken();
        popCurrentToken();
        if (getCurrentToken() instanceof TokenName) {
            TokenName tokenName = (TokenName) getCurrentToken();
            popCurrentToken();
            TokenMap tokenMap = (TokenMap) getCurrentToken();
            if (tokenName == null || currentToken == null || tokenMap == null) {
                return false;
            }
            tokenMap.value.put(tokenName.value, currentToken.getValue());
            return false;
        }
        if (!(getCurrentToken() instanceof TokenArray)) {
            return false;
        }
        TokenArray tokenArray = (TokenArray) getCurrentToken();
        if (currentToken == null || tokenArray == null) {
            return false;
        }
        tokenArray.value.add(currentToken.getValue());
        return false;
    }

    private boolean handlePrimitive(NextValue nextValue) throws IOException {
        Object nextValue2 = nextValue.nextValue();
        if (getCurrentToken() == null && nextValue2 != null) {
            pushCurrentToken(new TokenPrimitive(nextValue2));
            return true;
        }
        if (getCurrentToken() instanceof TokenName) {
            TokenName tokenName = (TokenName) getCurrentToken();
            popCurrentToken();
            ((TokenMap) getCurrentToken()).value.put(tokenName.value, nextValue2);
            return false;
        }
        if (!(getCurrentToken() instanceof TokenArray)) {
            return false;
        }
        ((TokenArray) getCurrentToken()).value.add(nextValue2);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: nextNumber, reason: merged with bridge method [inline-methods] */
    public Object m233lambda$parse$1$iosentryJsonObjectDeserializer(JsonObjectReader jsonObjectReader) throws IOException {
        try {
            try {
                return Integer.valueOf(jsonObjectReader.nextInt());
            } catch (Exception unused) {
                return Long.valueOf(jsonObjectReader.nextLong());
            }
        } catch (Exception unused2) {
            return Double.valueOf(jsonObjectReader.nextDouble());
        }
    }

    private Token getCurrentToken() {
        if (this.tokens.isEmpty()) {
            return null;
        }
        return this.tokens.get(r0.size() - 1);
    }

    private void pushCurrentToken(Token token) {
        this.tokens.add(token);
    }

    private void popCurrentToken() {
        if (this.tokens.isEmpty()) {
            return;
        }
        this.tokens.remove(r0.size() - 1);
    }

    private boolean hasOneToken() {
        return this.tokens.size() == 1;
    }
}
