package com.facebook.imagepipeline.memory;

import android.util.SparseArray;
import java.util.LinkedList;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class BucketMap<T> {

    @Nullable
    LinkedEntry<T> mHead;
    protected final SparseArray<LinkedEntry<T>> mMap = new SparseArray<>();

    @Nullable
    LinkedEntry<T> mTail;

    static class LinkedEntry<I> {
        int key;

        @Nullable
        LinkedEntry<I> next;

        @Nullable
        LinkedEntry<I> prev;
        LinkedList<I> value;

        private LinkedEntry(@Nullable LinkedEntry<I> prev, int key, LinkedList<I> value, @Nullable LinkedEntry<I> next) {
            this.prev = prev;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public String toString() {
            return "LinkedEntry(key: " + this.key + ")";
        }
    }

    @Nullable
    public synchronized T acquire(int key) {
        LinkedEntry<T> linkedEntry = this.mMap.get(key);
        if (linkedEntry == null) {
            return null;
        }
        T pollFirst = linkedEntry.value.pollFirst();
        moveToFront(linkedEntry);
        return pollFirst;
    }

    public synchronized void release(int key, T value) {
        LinkedEntry<T> linkedEntry = this.mMap.get(key);
        if (linkedEntry == null) {
            linkedEntry = new LinkedEntry<>(null, key, new LinkedList(), null);
            this.mMap.put(key, linkedEntry);
        }
        linkedEntry.value.addLast(value);
        moveToFront(linkedEntry);
    }

    synchronized int valueCount() {
        int i;
        i = 0;
        for (LinkedEntry linkedEntry = this.mHead; linkedEntry != null; linkedEntry = linkedEntry.next) {
            if (linkedEntry.value != null) {
                i += linkedEntry.value.size();
            }
        }
        return i;
    }

    private synchronized void prune(LinkedEntry<T> linkedEntry) {
        LinkedEntry linkedEntry2 = (LinkedEntry<T>) linkedEntry.prev;
        LinkedEntry linkedEntry3 = (LinkedEntry<T>) linkedEntry.next;
        if (linkedEntry2 != null) {
            linkedEntry2.next = linkedEntry3;
        }
        if (linkedEntry3 != null) {
            linkedEntry3.prev = linkedEntry2;
        }
        linkedEntry.prev = null;
        linkedEntry.next = null;
        if (linkedEntry == this.mHead) {
            this.mHead = linkedEntry3;
        }
        if (linkedEntry == this.mTail) {
            this.mTail = linkedEntry2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void moveToFront(LinkedEntry<T> linkedEntry) {
        if (this.mHead == linkedEntry) {
            return;
        }
        prune(linkedEntry);
        LinkedEntry<T> linkedEntry2 = this.mHead;
        if (linkedEntry2 == 0) {
            this.mHead = linkedEntry;
            this.mTail = linkedEntry;
        } else {
            linkedEntry.next = linkedEntry2;
            this.mHead.prev = linkedEntry;
            this.mHead = linkedEntry;
        }
    }

    @Nullable
    public synchronized T removeFromEnd() {
        LinkedEntry<T> linkedEntry = this.mTail;
        if (linkedEntry == null) {
            return null;
        }
        T pollLast = linkedEntry.value.pollLast();
        maybePrune(linkedEntry);
        return pollLast;
    }

    private void maybePrune(LinkedEntry<T> bucket) {
        if (bucket == null || !bucket.value.isEmpty()) {
            return;
        }
        prune(bucket);
        this.mMap.remove(bucket.key);
    }
}
