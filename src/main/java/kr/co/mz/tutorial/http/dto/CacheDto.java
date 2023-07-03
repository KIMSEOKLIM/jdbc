package kr.co.mz.tutorial.http.dto;

public class CacheDto {

    private final String url;
    private final byte[] cacheData;

    public CacheDto(String url, byte[] cacheData) {
        this.url = url;
        this.cacheData = cacheData;
    }

    public String getUrl() {
        return url;
    }

    public byte[] getCacheData() {
        return cacheData;
    }
}
