package com.realkarim.apps.xfetcher;

import java.util.LinkedHashMap;

/**
 * Created by Karim Mostafa on 1/29/17.
 */

class CacheFactory {
    static LinkedHashMap<String, byte[]> cacheContainer = new LinkedHashMap<>();
    static int MAX_CACHE_SIZE = 50;


    static void cache(String url, byte[] bytes){
        if(cacheContainer.size() == MAX_CACHE_SIZE){    // check if cache size reached the maximum
            String key = cacheContainer.keySet().iterator().next(); // get first key inserted
            removeFromCache(key); // remove it
        }
        cacheContainer.put(url, bytes);
    }

    static void removeFromCache(String key){
        cacheContainer.remove(key);
    }

}
