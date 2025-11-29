package com.bitnic.broadcast_manager;

/**
 * The interface Action receiver.
 *
 * @param <T> the type parameter
 */
public interface IActionReceiver<T> {
    /**
     * Invoke.
     *
     * @param t the t
     */
    void invoke(T t);
}
