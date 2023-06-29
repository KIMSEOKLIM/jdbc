package kr.co.mz.tutorial.http.cache;

import java.util.*;


public class Cache {
    private final Map<String, CacheEntry> cacheMap;
    private CacheEntry cacheEntry;

    public Cache() {
        cacheMap = new HashMap<>();
    }

    public String get(String url) {
        cacheEntry = cacheMap.get(url);
        if (cacheEntry == null || cacheEntry.isExpired()) {
            return "";
        }
        return cacheEntry.getResponse();
    }

    public void put(String key, String value) {
        CacheEntry entry = new CacheEntry(value);
        cacheMap.put(key, entry);
    }

    public List<String> getKeyList() { //여기에 get메소드 if문 넣어야할듯
        List<String> keyList = new ArrayList<>();
        Set<String> Set = cacheMap.keySet();
        Iterator<String> keyIterator = Set.iterator();
        while (keyIterator.hasNext()) {
            var url = keyIterator.next();
            cacheEntry = cacheMap.get(url);
            if (cacheEntry == null || cacheEntry.isExpired()) {
                keyList.add("expired");
            } else {
                keyList.add(url);
            }
        }
        return keyList;
    }
}
