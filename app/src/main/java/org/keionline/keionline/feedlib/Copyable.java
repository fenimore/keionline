package org.keionline.keionline.feedlib;

public interface Copyable<T> {
    T copy();
    T createForCopy();
    void copyTo(T dest);
}
