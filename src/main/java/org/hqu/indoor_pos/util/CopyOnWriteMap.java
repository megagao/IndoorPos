package org.hqu.indoor_pos.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
 
/**
 * created on 2016年8月24日
 *
 * 仿造CopyOnWriteList构造的工具类
 *
 * @author  megagao
 * @version  0.0.1
 */
public class CopyOnWriteMap<K, V> implements Map<K, V>, Cloneable {
	
    private volatile Map<K, V> internalMap;
 
    public CopyOnWriteMap() {
        internalMap = new HashMap<K, V>();
    }
 
    public V put(K key, V value) {
 
        synchronized (this) {
            Map<K, V> newMap = new HashMap<K, V>(internalMap);
            V val = newMap.put(key, value);
            internalMap = newMap;
            return val;
        }
    }
 
    public V get(Object key) {
        return internalMap.get(key);
    }
 
    public void putAll(Map<? extends K, ? extends V> newData) {
        synchronized (this) {
            Map<K, V> newMap = new HashMap<K, V>(internalMap);
            newMap.putAll(newData);
            internalMap = newMap;
        }
    }

	@Override
	public int size() {
		
		return this.internalMap.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.internalMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
}