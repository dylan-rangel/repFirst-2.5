package net.time4j.engine;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes3.dex */
public interface TimeSpan<U> {
    <T extends TimePoint<? super U, T>> T addTo(T t);

    boolean contains(U u);

    long getPartialAmount(U u);

    List<Item<U>> getTotalLength();

    boolean isEmpty();

    boolean isNegative();

    boolean isPositive();

    <T extends TimePoint<? super U, T>> T subtractFrom(T t);

    public static final class Item<U> implements Serializable {
        private static final long serialVersionUID = 1564804888291509484L;
        private final long amount;
        private final U unit;

        private Item(long j, U u) {
            if (u == null) {
                throw new NullPointerException("Missing chronological unit.");
            }
            if (j < 0) {
                throw new IllegalArgumentException("Temporal amount must be positive or zero: " + j);
            }
            this.amount = j;
            this.unit = u;
        }

        public static <U> Item<U> of(long j, U u) {
            return new Item<>(j, u);
        }

        public long getAmount() {
            return this.amount;
        }

        public U getUnit() {
            return this.unit;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Item)) {
                return false;
            }
            Item item = (Item) Item.class.cast(obj);
            return this.amount == item.amount && this.unit.equals(item.unit);
        }

        public int hashCode() {
            int hashCode = this.unit.hashCode() * 29;
            long j = this.amount;
            return hashCode + ((int) (j ^ (j >>> 32)));
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('P');
            sb.append(this.amount);
            sb.append('{');
            sb.append(this.unit);
            sb.append('}');
            return sb.toString();
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            if (this.unit == null || this.amount < 0) {
                throw new InvalidObjectException("Inconsistent state.");
            }
        }
    }
}
