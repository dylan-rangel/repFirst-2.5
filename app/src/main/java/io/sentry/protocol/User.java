package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonObjectReader;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectWriter;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.protocol.Geo;
import io.sentry.util.CollectionUtils;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public final class User implements JsonUnknown, JsonSerializable {
    private Map<String, String> data;
    private String email;
    private Geo geo;
    private String id;
    private String ipAddress;
    private String name;
    private String segment;
    private Map<String, Object> unknown;
    private String username;

    public static final class JsonKeys {
        public static final String DATA = "data";
        public static final String EMAIL = "email";
        public static final String GEO = "geo";
        public static final String ID = "id";
        public static final String IP_ADDRESS = "ip_address";
        public static final String NAME = "name";
        public static final String OTHER = "other";
        public static final String SEGMENT = "segment";
        public static final String USERNAME = "username";
    }

    public User() {
    }

    public User(User user) {
        this.email = user.email;
        this.username = user.username;
        this.id = user.id;
        this.ipAddress = user.ipAddress;
        this.segment = user.segment;
        this.name = user.name;
        this.geo = user.geo;
        this.data = CollectionUtils.newConcurrentHashMap(user.data);
        this.unknown = CollectionUtils.newConcurrentHashMap(user.unknown);
    }

    public static User fromMap(Map<String, Object> map, SentryOptions sentryOptions) {
        Object value;
        Map<String, String> map2;
        User user = new User();
        ConcurrentHashMap concurrentHashMap = null;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            value = entry.getValue();
            String key = entry.getKey();
            key.hashCode();
            switch (key) {
                case "username":
                    user.username = value instanceof String ? (String) value : null;
                    break;
                case "id":
                    user.id = value instanceof String ? (String) value : null;
                    break;
                case "geo":
                    Map map3 = value instanceof Map ? (Map) value : null;
                    if (map3 != null) {
                        ConcurrentHashMap concurrentHashMap2 = new ConcurrentHashMap();
                        for (Map.Entry entry2 : map3.entrySet()) {
                            if ((entry2.getKey() instanceof String) && entry2.getValue() != null) {
                                concurrentHashMap2.put((String) entry2.getKey(), entry2.getValue());
                            } else {
                                sentryOptions.getLogger().log(SentryLevel.WARNING, "Invalid key type in gep map.", new Object[0]);
                            }
                        }
                        user.geo = Geo.fromMap(concurrentHashMap2);
                        break;
                    } else {
                        break;
                    }
                case "data":
                    Map map4 = value instanceof Map ? (Map) value : null;
                    if (map4 != null) {
                        ConcurrentHashMap concurrentHashMap3 = new ConcurrentHashMap();
                        for (Map.Entry entry3 : map4.entrySet()) {
                            if ((entry3.getKey() instanceof String) && entry3.getValue() != null) {
                                concurrentHashMap3.put((String) entry3.getKey(), entry3.getValue().toString());
                            } else {
                                sentryOptions.getLogger().log(SentryLevel.WARNING, "Invalid key or null value in data map.", new Object[0]);
                            }
                        }
                        user.data = concurrentHashMap3;
                        break;
                    } else {
                        break;
                    }
                    break;
                case "name":
                    user.name = value instanceof String ? (String) value : null;
                    break;
                case "email":
                    user.email = value instanceof String ? (String) value : null;
                    break;
                case "other":
                    Map map5 = value instanceof Map ? (Map) value : null;
                    if (map5 != null && ((map2 = user.data) == null || map2.isEmpty())) {
                        ConcurrentHashMap concurrentHashMap4 = new ConcurrentHashMap();
                        for (Map.Entry entry4 : map5.entrySet()) {
                            if ((entry4.getKey() instanceof String) && entry4.getValue() != null) {
                                concurrentHashMap4.put((String) entry4.getKey(), entry4.getValue().toString());
                            } else {
                                sentryOptions.getLogger().log(SentryLevel.WARNING, "Invalid key or null value in other map.", new Object[0]);
                            }
                        }
                        user.data = concurrentHashMap4;
                        break;
                    }
                    break;
                case "ip_address":
                    user.ipAddress = value instanceof String ? (String) value : null;
                    break;
                case "segment":
                    user.segment = value instanceof String ? (String) value : null;
                    break;
                default:
                    if (concurrentHashMap == null) {
                        concurrentHashMap = new ConcurrentHashMap();
                    }
                    concurrentHashMap.put(entry.getKey(), entry.getValue());
                    break;
            }
        }
        user.unknown = concurrentHashMap;
        return user;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String str) {
        this.username = str;
    }

    public String getSegment() {
        return this.segment;
    }

    public void setSegment(String str) {
        this.segment = str;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String str) {
        this.ipAddress = str;
    }

    @Deprecated
    public Map<String, String> getOthers() {
        return getData();
    }

    @Deprecated
    public void setOthers(Map<String, String> map) {
        setData(map);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public Geo getGeo() {
        return this.geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public Map<String, String> getData() {
        return this.data;
    }

    public void setData(Map<String, String> map) {
        this.data = CollectionUtils.newConcurrentHashMap(map);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return Objects.equals(this.email, user.email) && Objects.equals(this.id, user.id) && Objects.equals(this.username, user.username) && Objects.equals(this.segment, user.segment) && Objects.equals(this.ipAddress, user.ipAddress);
    }

    public int hashCode() {
        return Objects.hash(this.email, this.id, this.username, this.segment, this.ipAddress);
    }

    @Override // io.sentry.JsonUnknown
    public Map<String, Object> getUnknown() {
        return this.unknown;
    }

    @Override // io.sentry.JsonUnknown
    public void setUnknown(Map<String, Object> map) {
        this.unknown = map;
    }

    @Override // io.sentry.JsonSerializable
    public void serialize(ObjectWriter objectWriter, ILogger iLogger) throws IOException {
        objectWriter.beginObject();
        if (this.email != null) {
            objectWriter.name("email").value(this.email);
        }
        if (this.id != null) {
            objectWriter.name("id").value(this.id);
        }
        if (this.username != null) {
            objectWriter.name("username").value(this.username);
        }
        if (this.segment != null) {
            objectWriter.name("segment").value(this.segment);
        }
        if (this.ipAddress != null) {
            objectWriter.name("ip_address").value(this.ipAddress);
        }
        if (this.name != null) {
            objectWriter.name("name").value(this.name);
        }
        if (this.geo != null) {
            objectWriter.name(JsonKeys.GEO);
            this.geo.serialize(objectWriter, iLogger);
        }
        if (this.data != null) {
            objectWriter.name("data").value(iLogger, this.data);
        }
        Map<String, Object> map = this.unknown;
        if (map != null) {
            for (String str : map.keySet()) {
                Object obj = this.unknown.get(str);
                objectWriter.name(str);
                objectWriter.value(iLogger, obj);
            }
        }
        objectWriter.endObject();
    }

    public static final class Deserializer implements JsonDeserializer<User> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // io.sentry.JsonDeserializer
        public User deserialize(JsonObjectReader jsonObjectReader, ILogger iLogger) throws Exception {
            String nextName;
            jsonObjectReader.beginObject();
            User user = new User();
            ConcurrentHashMap concurrentHashMap = null;
            while (jsonObjectReader.peek() == JsonToken.NAME) {
                nextName = jsonObjectReader.nextName();
                nextName.hashCode();
                switch (nextName) {
                    case "username":
                        user.username = jsonObjectReader.nextStringOrNull();
                        break;
                    case "id":
                        user.id = jsonObjectReader.nextStringOrNull();
                        break;
                    case "geo":
                        user.geo = new Geo.Deserializer().deserialize(jsonObjectReader, iLogger);
                        break;
                    case "data":
                        user.data = CollectionUtils.newConcurrentHashMap((Map) jsonObjectReader.nextObjectOrNull());
                        break;
                    case "name":
                        user.name = jsonObjectReader.nextStringOrNull();
                        break;
                    case "email":
                        user.email = jsonObjectReader.nextStringOrNull();
                        break;
                    case "other":
                        if (user.data != null && !user.data.isEmpty()) {
                            break;
                        } else {
                            user.data = CollectionUtils.newConcurrentHashMap((Map) jsonObjectReader.nextObjectOrNull());
                            break;
                        }
                    case "ip_address":
                        user.ipAddress = jsonObjectReader.nextStringOrNull();
                        break;
                    case "segment":
                        user.segment = jsonObjectReader.nextStringOrNull();
                        break;
                    default:
                        if (concurrentHashMap == null) {
                            concurrentHashMap = new ConcurrentHashMap();
                        }
                        jsonObjectReader.nextUnknown(iLogger, concurrentHashMap, nextName);
                        break;
                }
            }
            user.setUnknown(concurrentHashMap);
            jsonObjectReader.endObject();
            return user;
        }
    }
}
