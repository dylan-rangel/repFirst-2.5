package net.time4j.format.expert;

import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import net.time4j.PlainDate;
import net.time4j.PlainTime;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoException;

/* loaded from: classes3.dex */
class ParsedValues extends ParsedEntity<ParsedValues> {
    private static final Set<ChronoElement<?>> INDEXED_ELEMENTS;
    private static final int INT_PHI = -1640531527;
    private static final float LOAD_FACTOR = 0.75f;
    private int count;
    private int[] ints;
    private Object[] keys;
    private int len;
    private Map<ChronoElement<?>, Object> map;
    private int mask;
    private int threshold;
    private Object[] values;
    private boolean duplicateKeysAllowed = false;
    private int position = -1;

    private static int mix(int i) {
        int i2 = i * INT_PHI;
        return i2 ^ (i2 >>> 16);
    }

    private static int nextPowerOfTwo(int i) {
        if (i == 0) {
            return 1;
        }
        int i2 = i - 1;
        int i3 = i2 | (i2 >> 1);
        int i4 = i3 | (i3 >> 2);
        int i5 = i4 | (i4 >> 4);
        int i6 = i5 | (i5 >> 8);
        return (i6 | (i6 >> 16)) + 1;
    }

    @Override // net.time4j.format.expert.ParsedEntity
    <E> E getResult() {
        return null;
    }

    @Override // net.time4j.format.expert.ParsedEntity
    void setResult(Object obj) {
    }

    static {
        HashSet hashSet = new HashSet();
        hashSet.add(PlainDate.YEAR);
        hashSet.add(PlainDate.MONTH_AS_NUMBER);
        hashSet.add(PlainDate.DAY_OF_MONTH);
        hashSet.add(PlainTime.DIGITAL_HOUR_OF_DAY);
        hashSet.add(PlainTime.MINUTE_OF_HOUR);
        hashSet.add(PlainTime.SECOND_OF_MINUTE);
        hashSet.add(PlainTime.NANO_OF_SECOND);
        INDEXED_ELEMENTS = Collections.unmodifiableSet(hashSet);
    }

    ParsedValues(int i, boolean z) {
        if (z) {
            this.len = Integer.MIN_VALUE;
            this.mask = Integer.MIN_VALUE;
            this.threshold = Integer.MIN_VALUE;
            this.count = Integer.MIN_VALUE;
            this.keys = null;
            this.values = null;
            this.ints = new int[3];
            for (int i2 = 0; i2 < 3; i2++) {
                this.ints[i2] = Integer.MIN_VALUE;
            }
        } else {
            int arraySize = arraySize(i);
            this.len = arraySize;
            this.mask = arraySize - 1;
            this.threshold = maxFill(arraySize);
            int i3 = this.len;
            this.keys = new Object[i3];
            this.values = null;
            this.ints = new int[i3];
            this.count = 0;
        }
        this.map = null;
    }

    @Override // net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoDisplay
    public boolean contains(ChronoElement<?> chronoElement) {
        Object obj;
        if (chronoElement == null) {
            return false;
        }
        Object[] objArr = this.keys;
        if (objArr == null) {
            if (chronoElement == PlainDate.YEAR) {
                return this.ints[0] != Integer.MIN_VALUE;
            }
            if (chronoElement == PlainDate.MONTH_AS_NUMBER) {
                return this.ints[1] != Integer.MIN_VALUE;
            }
            if (chronoElement == PlainDate.DAY_OF_MONTH) {
                return this.ints[2] != Integer.MIN_VALUE;
            }
            if (chronoElement == PlainTime.DIGITAL_HOUR_OF_DAY) {
                return this.len != Integer.MIN_VALUE;
            }
            if (chronoElement == PlainTime.MINUTE_OF_HOUR) {
                return this.mask != Integer.MIN_VALUE;
            }
            if (chronoElement == PlainTime.SECOND_OF_MINUTE) {
                return this.threshold != Integer.MIN_VALUE;
            }
            if (chronoElement == PlainTime.NANO_OF_SECOND) {
                return this.count != Integer.MIN_VALUE;
            }
            Map<ChronoElement<?>, Object> map = this.map;
            return map != null && map.containsKey(chronoElement);
        }
        int mix = mix(chronoElement.hashCode()) & this.mask;
        Object obj2 = objArr[mix];
        if (obj2 == null) {
            return false;
        }
        if (chronoElement.equals(obj2)) {
            return true;
        }
        do {
            mix = (mix + 1) & this.mask;
            obj = objArr[mix];
            if (obj == null) {
                return false;
            }
        } while (!chronoElement.equals(obj));
        return true;
    }

    @Override // net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoDisplay
    public <V> V get(ChronoElement<V> chronoElement) {
        int mix;
        Object obj;
        Object obj2;
        Class<V> type = chronoElement.getType();
        if (type == Integer.class) {
            int int0 = getInt0(chronoElement);
            if (int0 == Integer.MIN_VALUE) {
                throw new ChronoException("No value found for: " + chronoElement.name());
            }
            return type.cast(Integer.valueOf(int0));
        }
        Object[] objArr = this.keys;
        if (objArr == null) {
            Map<ChronoElement<?>, Object> map = this.map;
            if (map != null && map.containsKey(chronoElement)) {
                return chronoElement.getType().cast(map.get(chronoElement));
            }
            throw new ChronoException("No value found for: " + chronoElement.name());
        }
        if (this.values == null || (obj = objArr[(mix = mix(chronoElement.hashCode()) & this.mask)]) == null) {
            throw new ChronoException("No value found for: " + chronoElement.name());
        }
        if (chronoElement.equals(obj)) {
            return type.cast(this.values[mix]);
        }
        do {
            mix = (mix + 1) & this.mask;
            obj2 = objArr[mix];
            if (obj2 == null) {
                throw new ChronoException("No value found for: " + chronoElement.name());
            }
        } while (!chronoElement.equals(obj2));
        return type.cast(this.values[mix]);
    }

    @Override // net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoDisplay
    public int getInt(ChronoElement<Integer> chronoElement) {
        return getInt0(chronoElement);
    }

    @Override // net.time4j.engine.ChronoEntity
    public Set<ChronoElement<?>> getRegisteredElements() {
        if (this.keys == null) {
            HashSet hashSet = new HashSet();
            if (this.ints[0] != Integer.MIN_VALUE) {
                hashSet.add(PlainDate.YEAR);
            }
            if (this.ints[1] != Integer.MIN_VALUE) {
                hashSet.add(PlainDate.MONTH_AS_NUMBER);
            }
            if (this.ints[2] != Integer.MIN_VALUE) {
                hashSet.add(PlainDate.DAY_OF_MONTH);
            }
            if (this.len != Integer.MIN_VALUE) {
                hashSet.add(PlainTime.DIGITAL_HOUR_OF_DAY);
            }
            if (this.mask != Integer.MIN_VALUE) {
                hashSet.add(PlainTime.MINUTE_OF_HOUR);
            }
            if (this.threshold != Integer.MIN_VALUE) {
                hashSet.add(PlainTime.SECOND_OF_MINUTE);
            }
            if (this.count != Integer.MIN_VALUE) {
                hashSet.add(PlainTime.NANO_OF_SECOND);
            }
            Map<ChronoElement<?>, Object> map = this.map;
            if (map != null) {
                hashSet.addAll(map.keySet());
            }
            return Collections.unmodifiableSet(hashSet);
        }
        return new KeySet();
    }

    void setPosition(int i) {
        this.position = i;
    }

    int getPosition() {
        return this.position;
    }

    void setNoAmbivalentCheck() {
        this.duplicateKeysAllowed = true;
    }

    static boolean isIndexed(ChronoElement<?> chronoElement) {
        return INDEXED_ELEMENTS.contains(chronoElement);
    }

    void putAll(ParsedValues parsedValues) {
        int i = 0;
        if (this.keys == null) {
            int i2 = parsedValues.len;
            if (i2 != Integer.MIN_VALUE) {
                int i3 = this.len;
                if (i3 == Integer.MIN_VALUE || this.duplicateKeysAllowed || i3 == i2) {
                    this.len = i2;
                } else {
                    throw new AmbivalentValueException(PlainTime.DIGITAL_HOUR_OF_DAY);
                }
            }
            int i4 = parsedValues.mask;
            if (i4 != Integer.MIN_VALUE) {
                int i5 = this.mask;
                if (i5 == Integer.MIN_VALUE || this.duplicateKeysAllowed || i5 == i4) {
                    this.mask = i4;
                } else {
                    throw new AmbivalentValueException(PlainTime.MINUTE_OF_HOUR);
                }
            }
            int i6 = parsedValues.threshold;
            if (i6 != Integer.MIN_VALUE) {
                int i7 = this.threshold;
                if (i7 == Integer.MIN_VALUE || this.duplicateKeysAllowed || i7 == i6) {
                    this.threshold = i6;
                } else {
                    throw new AmbivalentValueException(PlainTime.SECOND_OF_MINUTE);
                }
            }
            int i8 = parsedValues.count;
            if (i8 != Integer.MIN_VALUE) {
                int i9 = this.count;
                if (i9 == Integer.MIN_VALUE || this.duplicateKeysAllowed || i9 == i8) {
                    this.count = i8;
                } else {
                    throw new AmbivalentValueException(PlainTime.NANO_OF_SECOND);
                }
            }
            while (i < 3) {
                int i10 = parsedValues.ints[i];
                if (i10 != Integer.MIN_VALUE) {
                    int[] iArr = this.ints;
                    int i11 = iArr[i];
                    if (i11 == Integer.MIN_VALUE || this.duplicateKeysAllowed || i11 == i10) {
                        iArr[i] = i10;
                    } else {
                        throw new AmbivalentValueException(getIndexedElement(i));
                    }
                }
                i++;
            }
            Map<ChronoElement<?>, Object> map = parsedValues.map;
            if (map != null) {
                for (ChronoElement<?> chronoElement : map.keySet()) {
                    put(chronoElement, map.get(chronoElement));
                }
                return;
            }
            return;
        }
        Object[] objArr = parsedValues.keys;
        while (i < objArr.length) {
            Object obj = objArr[i];
            if (obj != null) {
                ChronoElement<?> chronoElement2 = (ChronoElement) ChronoElement.class.cast(obj);
                if (chronoElement2.getType() == Integer.class) {
                    put(chronoElement2, parsedValues.ints[i]);
                } else {
                    put(chronoElement2, parsedValues.values[i]);
                }
            }
            i++;
        }
    }

    @Override // net.time4j.format.expert.ParsedEntity
    void put(ChronoElement<?> chronoElement, int i) {
        Object obj;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        Object[] objArr = this.keys;
        if (objArr == null) {
            if (chronoElement == PlainDate.YEAR) {
                if (this.duplicateKeysAllowed || (i8 = this.ints[0]) == Integer.MIN_VALUE || i8 == i) {
                    this.ints[0] = i;
                    return;
                }
                throw new AmbivalentValueException(chronoElement);
            }
            if (chronoElement == PlainDate.MONTH_AS_NUMBER) {
                if (this.duplicateKeysAllowed || (i7 = this.ints[1]) == Integer.MIN_VALUE || i7 == i) {
                    this.ints[1] = i;
                    return;
                }
                throw new AmbivalentValueException(chronoElement);
            }
            if (chronoElement == PlainDate.DAY_OF_MONTH) {
                if (this.duplicateKeysAllowed || (i6 = this.ints[2]) == Integer.MIN_VALUE || i6 == i) {
                    this.ints[2] = i;
                    return;
                }
                throw new AmbivalentValueException(chronoElement);
            }
            if (chronoElement == PlainTime.DIGITAL_HOUR_OF_DAY) {
                if (this.duplicateKeysAllowed || (i5 = this.len) == Integer.MIN_VALUE || i5 == i) {
                    this.len = i;
                    return;
                }
                throw new AmbivalentValueException(chronoElement);
            }
            if (chronoElement == PlainTime.MINUTE_OF_HOUR) {
                if (this.duplicateKeysAllowed || (i4 = this.mask) == Integer.MIN_VALUE || i4 == i) {
                    this.mask = i;
                    return;
                }
                throw new AmbivalentValueException(chronoElement);
            }
            if (chronoElement == PlainTime.SECOND_OF_MINUTE) {
                if (this.duplicateKeysAllowed || (i3 = this.threshold) == Integer.MIN_VALUE || i3 == i) {
                    this.threshold = i;
                    return;
                }
                throw new AmbivalentValueException(chronoElement);
            }
            if (chronoElement == PlainTime.NANO_OF_SECOND) {
                if (this.duplicateKeysAllowed || (i2 = this.count) == Integer.MIN_VALUE || i2 == i) {
                    this.count = i;
                    return;
                }
                throw new AmbivalentValueException(chronoElement);
            }
            Map map = this.map;
            if (map == null) {
                map = new HashMap();
                this.map = map;
            }
            Integer valueOf = Integer.valueOf(i);
            if (this.duplicateKeysAllowed || !map.containsKey(chronoElement) || valueOf.equals(map.get(chronoElement))) {
                map.put(chronoElement, valueOf);
                return;
            }
            throw new AmbivalentValueException(chronoElement);
        }
        int mix = mix(chronoElement.hashCode()) & this.mask;
        Object obj2 = objArr[mix];
        if (obj2 != null) {
            if (obj2.equals(chronoElement)) {
                if (this.duplicateKeysAllowed || this.ints[mix] == i) {
                    this.ints[mix] = i;
                    return;
                }
                throw new AmbivalentValueException(chronoElement);
            }
            do {
                mix = (mix + 1) & this.mask;
                obj = objArr[mix];
                if (obj != null) {
                }
            } while (!obj.equals(chronoElement));
            if (this.duplicateKeysAllowed || this.ints[mix] == i) {
                this.ints[mix] = i;
                return;
            }
            throw new AmbivalentValueException(chronoElement);
        }
        objArr[mix] = chronoElement;
        this.ints[mix] = i;
        int i9 = this.count;
        int i10 = i9 + 1;
        this.count = i10;
        if (i9 >= this.threshold) {
            rehash(arraySize(i10));
        }
    }

    @Override // net.time4j.format.expert.ParsedEntity
    void put(ChronoElement<?> chronoElement, Object obj) {
        Object obj2;
        if (obj == null) {
            remove(chronoElement);
            return;
        }
        if (chronoElement.getType() == Integer.class) {
            put(chronoElement, ((Integer) Integer.class.cast(obj)).intValue());
            return;
        }
        Object[] objArr = this.keys;
        if (objArr == null) {
            Map map = this.map;
            if (map == null) {
                map = new HashMap();
                this.map = map;
            }
            if (this.duplicateKeysAllowed || !map.containsKey(chronoElement) || obj.equals(map.get(chronoElement))) {
                map.put(chronoElement, obj);
                return;
            }
            throw new AmbivalentValueException(chronoElement);
        }
        if (this.values == null) {
            this.values = new Object[this.len];
        }
        int mix = mix(chronoElement.hashCode()) & this.mask;
        Object obj3 = objArr[mix];
        if (obj3 != null) {
            if (obj3.equals(chronoElement)) {
                if (this.duplicateKeysAllowed || obj.equals(this.values[mix])) {
                    this.values[mix] = obj;
                    return;
                }
                throw new AmbivalentValueException(chronoElement);
            }
            do {
                mix = (mix + 1) & this.mask;
                obj2 = objArr[mix];
                if (obj2 != null) {
                }
            } while (!obj2.equals(chronoElement));
            if (this.duplicateKeysAllowed || obj.equals(this.values[mix])) {
                this.values[mix] = obj;
                return;
            }
            throw new AmbivalentValueException(chronoElement);
        }
        objArr[mix] = chronoElement;
        this.values[mix] = obj;
        int i = this.count;
        int i2 = i + 1;
        this.count = i2;
        if (i >= this.threshold) {
            rehash(arraySize(i2));
        }
    }

    void reset() {
        Object[] objArr = this.keys;
        if (objArr == null) {
            this.len = Integer.MIN_VALUE;
            this.mask = Integer.MIN_VALUE;
            this.threshold = Integer.MIN_VALUE;
            this.count = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                this.ints[i] = Integer.MIN_VALUE;
            }
            this.map = null;
        } else {
            this.keys = new Object[objArr.length];
        }
        this.count = 0;
    }

    private int getInt0(ChronoElement<?> chronoElement) {
        Object obj;
        Object[] objArr = this.keys;
        if (objArr == null) {
            if (chronoElement == PlainDate.YEAR) {
                return this.ints[0];
            }
            if (chronoElement == PlainDate.MONTH_AS_NUMBER) {
                return this.ints[1];
            }
            if (chronoElement == PlainDate.DAY_OF_MONTH) {
                return this.ints[2];
            }
            if (chronoElement == PlainTime.DIGITAL_HOUR_OF_DAY) {
                return this.len;
            }
            if (chronoElement == PlainTime.MINUTE_OF_HOUR) {
                return this.mask;
            }
            if (chronoElement == PlainTime.SECOND_OF_MINUTE) {
                return this.threshold;
            }
            if (chronoElement == PlainTime.NANO_OF_SECOND) {
                return this.count;
            }
            Map<ChronoElement<?>, Object> map = this.map;
            if (map == null || !map.containsKey(chronoElement)) {
                return Integer.MIN_VALUE;
            }
            return ((Integer) Integer.class.cast(map.get(chronoElement))).intValue();
        }
        int mix = mix(chronoElement.hashCode()) & this.mask;
        Object obj2 = objArr[mix];
        if (obj2 == null) {
            return Integer.MIN_VALUE;
        }
        if (chronoElement.equals(obj2)) {
            return this.ints[mix];
        }
        do {
            mix = (mix + 1) & this.mask;
            obj = objArr[mix];
            if (obj == null) {
                return Integer.MIN_VALUE;
            }
        } while (!chronoElement.equals(obj));
        return this.ints[mix];
    }

    private void remove(Object obj) {
        Object obj2;
        Object[] objArr = this.keys;
        if (objArr == null) {
            if (obj == PlainDate.YEAR) {
                this.ints[0] = Integer.MIN_VALUE;
                return;
            }
            if (obj == PlainDate.MONTH_AS_NUMBER) {
                this.ints[1] = Integer.MIN_VALUE;
                return;
            }
            if (obj == PlainDate.DAY_OF_MONTH) {
                this.ints[2] = Integer.MIN_VALUE;
                return;
            }
            if (obj == PlainTime.DIGITAL_HOUR_OF_DAY) {
                this.len = Integer.MIN_VALUE;
                return;
            }
            if (obj == PlainTime.MINUTE_OF_HOUR) {
                this.mask = Integer.MIN_VALUE;
                return;
            }
            if (obj == PlainTime.SECOND_OF_MINUTE) {
                this.threshold = Integer.MIN_VALUE;
                return;
            }
            if (obj == PlainTime.NANO_OF_SECOND) {
                this.count = Integer.MIN_VALUE;
                return;
            }
            Map<ChronoElement<?>, Object> map = this.map;
            if (map != null) {
                map.remove(obj);
                return;
            }
            return;
        }
        int mix = mix(obj.hashCode()) & this.mask;
        Object obj3 = objArr[mix];
        if (obj3 == null) {
            return;
        }
        if (obj.equals(obj3)) {
            removeEntry(mix);
            return;
        }
        do {
            mix = (mix + 1) & this.mask;
            obj2 = objArr[mix];
            if (obj2 == null) {
                return;
            }
        } while (!obj.equals(obj2));
        removeEntry(mix);
    }

    private void removeEntry(int i) {
        Object obj;
        this.count--;
        Object[] objArr = this.keys;
        while (true) {
            int i2 = (i + 1) & this.mask;
            while (true) {
                obj = objArr[i2];
                if (obj == null) {
                    objArr[i] = null;
                    return;
                }
                int mix = mix(obj.hashCode());
                int i3 = this.mask;
                int i4 = mix & i3;
                if (i > i2) {
                    if (i >= i4 && i4 > i2) {
                        break;
                    }
                    i2 = (i2 + 1) & i3;
                } else if (i >= i4 || i4 > i2) {
                    break;
                } else {
                    i2 = (i2 + 1) & i3;
                }
            }
            objArr[i] = obj;
            Object[] objArr2 = this.values;
            if (objArr2 != null) {
                objArr2[i] = objArr2[i2];
            }
            int[] iArr = this.ints;
            iArr[i] = iArr[i2];
            i = i2;
        }
    }

    private static int arraySize(int i) {
        return Math.max(2, nextPowerOfTwo((int) Math.ceil(i / 0.75f)));
    }

    private static int maxFill(int i) {
        return Math.min((int) Math.ceil(i * 0.75f), i - 1);
    }

    private void rehash(int i) {
        Object obj;
        Object[] objArr = this.keys;
        Object[] objArr2 = this.values;
        int[] iArr = this.ints;
        int i2 = i - 1;
        Object[] objArr3 = new Object[i];
        Object[] objArr4 = objArr2 == null ? null : new Object[i];
        int[] iArr2 = new int[i];
        int i3 = this.len;
        int i4 = this.count;
        for (int i5 = 0; i5 < i4; i5++) {
            do {
                i3--;
                obj = objArr[i3];
            } while (obj == null);
            int mix = mix(obj.hashCode()) & i2;
            if (objArr3[mix] != null) {
                do {
                    mix = (mix + 1) & i2;
                } while (objArr3[mix] != null);
            }
            objArr3[mix] = objArr[i3];
            if (objArr2 != null) {
                objArr4[mix] = objArr2[i3];
            }
            iArr2[mix] = iArr[i3];
        }
        this.len = i;
        this.mask = i2;
        this.threshold = maxFill(i);
        this.keys = objArr3;
        this.values = objArr4;
        this.ints = iArr2;
    }

    private static ChronoElement<Integer> getIndexedElement(int i) {
        switch (i) {
            case 0:
                return PlainDate.YEAR;
            case 1:
                return PlainDate.MONTH_AS_NUMBER;
            case 2:
                return PlainDate.DAY_OF_MONTH;
            case 3:
                return PlainTime.DIGITAL_HOUR_OF_DAY;
            case 4:
                return PlainTime.MINUTE_OF_HOUR;
            case 5:
                return PlainTime.SECOND_OF_MINUTE;
            case 6:
                return PlainTime.NANO_OF_SECOND;
            default:
                throw new IllegalStateException("No element index: " + i);
        }
    }

    private class KeyIterator implements Iterator<ChronoElement<?>> {
        int c;
        int pos;

        private KeyIterator() {
            this.pos = ParsedValues.this.len;
            this.c = ParsedValues.this.count;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.c > 0;
        }

        @Override // java.util.Iterator
        public ChronoElement<?> next() {
            Object obj;
            if (this.c > 0) {
                Object[] objArr = ParsedValues.this.keys;
                do {
                    int i = this.pos - 1;
                    this.pos = i;
                    if (i >= 0) {
                        obj = objArr[i];
                    }
                } while (obj == null);
                this.c--;
                return (ChronoElement) ChronoElement.class.cast(obj);
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    private class KeySet extends AbstractSet<ChronoElement<?>> {
        private KeySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<ChronoElement<?>> iterator() {
            return new KeyIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return ParsedValues.this.count;
        }
    }
}
