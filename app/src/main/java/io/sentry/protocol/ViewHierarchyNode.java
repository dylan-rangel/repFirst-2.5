package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonObjectReader;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectWriter;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class ViewHierarchyNode implements JsonUnknown, JsonSerializable {
    private Double alpha;
    private List<ViewHierarchyNode> children;
    private Double height;
    private String identifier;
    private String renderingSystem;
    private String tag;
    private String type;
    private Map<String, Object> unknown;
    private String visibility;
    private Double width;
    private Double x;
    private Double y;

    public static final class JsonKeys {
        public static final String ALPHA = "alpha";
        public static final String CHILDREN = "children";
        public static final String HEIGHT = "height";
        public static final String IDENTIFIER = "identifier";
        public static final String RENDERING_SYSTEM = "rendering_system";
        public static final String TAG = "tag";
        public static final String TYPE = "type";
        public static final String VISIBILITY = "visibility";
        public static final String WIDTH = "width";
        public static final String X = "x";
        public static final String Y = "y";
    }

    public void setRenderingSystem(String str) {
        this.renderingSystem = str;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setIdentifier(String str) {
        this.identifier = str;
    }

    public void setTag(String str) {
        this.tag = str;
    }

    public void setWidth(Double d) {
        this.width = d;
    }

    public void setHeight(Double d) {
        this.height = d;
    }

    public void setX(Double d) {
        this.x = d;
    }

    public void setY(Double d) {
        this.y = d;
    }

    public void setVisibility(String str) {
        this.visibility = str;
    }

    public void setAlpha(Double d) {
        this.alpha = d;
    }

    public void setChildren(List<ViewHierarchyNode> list) {
        this.children = list;
    }

    public String getRenderingSystem() {
        return this.renderingSystem;
    }

    public String getType() {
        return this.type;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getTag() {
        return this.tag;
    }

    public Double getWidth() {
        return this.width;
    }

    public Double getHeight() {
        return this.height;
    }

    public Double getX() {
        return this.x;
    }

    public Double getY() {
        return this.y;
    }

    public String getVisibility() {
        return this.visibility;
    }

    public Double getAlpha() {
        return this.alpha;
    }

    public List<ViewHierarchyNode> getChildren() {
        return this.children;
    }

    @Override // io.sentry.JsonSerializable
    public void serialize(ObjectWriter objectWriter, ILogger iLogger) throws IOException {
        objectWriter.beginObject();
        if (this.renderingSystem != null) {
            objectWriter.name("rendering_system").value(this.renderingSystem);
        }
        if (this.type != null) {
            objectWriter.name("type").value(this.type);
        }
        if (this.identifier != null) {
            objectWriter.name(JsonKeys.IDENTIFIER).value(this.identifier);
        }
        if (this.tag != null) {
            objectWriter.name(JsonKeys.TAG).value(this.tag);
        }
        if (this.width != null) {
            objectWriter.name("width").value(this.width);
        }
        if (this.height != null) {
            objectWriter.name("height").value(this.height);
        }
        if (this.x != null) {
            objectWriter.name(JsonKeys.X).value(this.x);
        }
        if (this.y != null) {
            objectWriter.name(JsonKeys.Y).value(this.y);
        }
        if (this.visibility != null) {
            objectWriter.name(JsonKeys.VISIBILITY).value(this.visibility);
        }
        if (this.alpha != null) {
            objectWriter.name(JsonKeys.ALPHA).value(this.alpha);
        }
        List<ViewHierarchyNode> list = this.children;
        if (list != null && !list.isEmpty()) {
            objectWriter.name(JsonKeys.CHILDREN).value(iLogger, this.children);
        }
        Map<String, Object> map = this.unknown;
        if (map != null) {
            for (String str : map.keySet()) {
                objectWriter.name(str).value(iLogger, this.unknown.get(str));
            }
        }
        objectWriter.endObject();
    }

    @Override // io.sentry.JsonUnknown
    public Map<String, Object> getUnknown() {
        return this.unknown;
    }

    @Override // io.sentry.JsonUnknown
    public void setUnknown(Map<String, Object> map) {
        this.unknown = map;
    }

    public static final class Deserializer implements JsonDeserializer<ViewHierarchyNode> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // io.sentry.JsonDeserializer
        public ViewHierarchyNode deserialize(JsonObjectReader jsonObjectReader, ILogger iLogger) throws Exception {
            String nextName;
            ViewHierarchyNode viewHierarchyNode = new ViewHierarchyNode();
            jsonObjectReader.beginObject();
            HashMap hashMap = null;
            while (jsonObjectReader.peek() == JsonToken.NAME) {
                nextName = jsonObjectReader.nextName();
                nextName.hashCode();
                switch (nextName) {
                    case "rendering_system":
                        viewHierarchyNode.renderingSystem = jsonObjectReader.nextStringOrNull();
                        break;
                    case "identifier":
                        viewHierarchyNode.identifier = jsonObjectReader.nextStringOrNull();
                        break;
                    case "height":
                        viewHierarchyNode.height = jsonObjectReader.nextDoubleOrNull();
                        break;
                    case "x":
                        viewHierarchyNode.x = jsonObjectReader.nextDoubleOrNull();
                        break;
                    case "y":
                        viewHierarchyNode.y = jsonObjectReader.nextDoubleOrNull();
                        break;
                    case "tag":
                        viewHierarchyNode.tag = jsonObjectReader.nextStringOrNull();
                        break;
                    case "type":
                        viewHierarchyNode.type = jsonObjectReader.nextStringOrNull();
                        break;
                    case "alpha":
                        viewHierarchyNode.alpha = jsonObjectReader.nextDoubleOrNull();
                        break;
                    case "width":
                        viewHierarchyNode.width = jsonObjectReader.nextDoubleOrNull();
                        break;
                    case "children":
                        viewHierarchyNode.children = jsonObjectReader.nextListOrNull(iLogger, this);
                        break;
                    case "visibility":
                        viewHierarchyNode.visibility = jsonObjectReader.nextStringOrNull();
                        break;
                    default:
                        if (hashMap == null) {
                            hashMap = new HashMap();
                        }
                        jsonObjectReader.nextUnknown(iLogger, hashMap, nextName);
                        break;
                }
            }
            jsonObjectReader.endObject();
            viewHierarchyNode.setUnknown(hashMap);
            return viewHierarchyNode;
        }
    }
}
