package com.neocite.eaiblza.utils;

/**
 * Created by paulo-silva on 6/2/15.
 */
public interface Subscriber<T> {
    void notify(T message);
}
