package kr.co.mz.tutorial.http.cache;

public class CacheEntry {
    private final String response;
    private final long timestamp;

    public CacheEntry(String response) {
        this.response = response;
        this.timestamp = System.currentTimeMillis();
    }

    public String getResponse() {
        return response;
    }

    public boolean isExpired() {
        return (System.currentTimeMillis() - timestamp) > 15000;
    }
}
