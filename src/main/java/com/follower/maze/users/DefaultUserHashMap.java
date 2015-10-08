package com.follower.maze.users;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class DefaultUserHashMap implements Map<Integer, User> {
    private final Map<Integer, User> delegate;

    public DefaultUserHashMap(Map<Integer, User> delegate) {
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

    /**
     * Returns the value to which the specified key is mapped,
     * or a new user with the a userId of the key.
     *
     * @throws java.lang.ClassCastException if the specified key is not an int
     */
    @Override
    public User get(Object key) {
        User newUser = delegate.get(key);
        final int userId = (int) key;
        if (newUser == null) {
            final User noUserYet = new User(userId, new LinkedList<String>(), new ConcurrentSkipListSet<User>());
            delegate.put(userId, noUserYet);
            newUser = noUserYet;
        }
        return newUser;
    }

    @Override
    public User put(Integer key, User value) {
        return delegate.put(key, value);
    }

    @Override
    public User remove(Object key) {
        return delegate.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends User> m) {
        delegate.putAll(m);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return delegate.keySet();
    }

    @Override
    public Collection<User> values() {
        return delegate.values();
    }

    @Override
    public Set<Entry<Integer, User>> entrySet() {
        return delegate.entrySet();
    }
}
