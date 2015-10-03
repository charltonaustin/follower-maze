package com.follower.maze.event.events;

import com.follower.maze.event.events.factory.EventFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class EventTypes implements Map<String, EventFactory> {

    private final Map<String, EventFactory> delegate;

    public EventTypes(Map<String, EventFactory> delegate) {
        this.delegate = delegate;
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return delegate.containsValue(value);
    }

    @Override
    public EventFactory get(Object key) {
        return delegate.get(key);
    }

    @Override
    public EventFactory put(String key, EventFactory value) {
        return delegate.put(key, value);
    }

    @Override
    public EventFactory remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends EventFactory> m) {
        delegate.putAll(m);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public Set<String> keySet() {
        return delegate.keySet();
    }

    @Override
    public Collection<EventFactory> values() {
        return delegate.values();
    }

    @Override
    public Set<Entry<String, EventFactory>> entrySet() {
        return delegate.entrySet();
    }
}
