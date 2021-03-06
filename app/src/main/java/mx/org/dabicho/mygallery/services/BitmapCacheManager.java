package mx.org.dabicho.mygallery.services;


import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static android.util.Log.e;
import static android.util.Log.i;
import static android.util.Log.d;
import static android.util.Log.v;


/**
 * simple bitmap cache manager that also holds a count for external references to the bitmap to
 * recycle them when no longer needed.
 */
public class BitmapCacheManager {
    private static final String TAG = "BitmapCacheManager";

    static private BitmapCacheManager cacheManager;

    private final LruCache<String, Bitmap> lruCache;


    private BitmapCacheManager() {
        lruCache = new LruCache<String, Bitmap>(32 * 1024 * 1024) {
            /**
             * Remove a bitmap from the cache
             * If its referenced 0 times, it should be recycled.
             * Else, it is added to the referenced bitmaps list
             * @see android.util.LruCache#entryRemoved(boolean, Object, Object, Object)
             */
            @Override
            protected synchronized void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {

                super.entryRemoved(evicted, key, oldValue, newValue);
                System.gc();

            }

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    /**
     * @return An instance of this cache manager
     */
    static synchronized public BitmapCacheManager getInstance() {
        if (cacheManager == null) {
            cacheManager = new BitmapCacheManager();
        }
        return cacheManager;
    }

    /**
     * Adds a bitmap to the cache and sets its ref. count to 0
     *
     * @param key   the key to the bitmap (usually a full path)
     * @param value the corresponding bitmap
     */
    public void put(String key, Bitmap value) {
        synchronized (lruCache) {
            lruCache.put(key, value);
        }
    }

    /**
     * Gets an entry from the cache
     *
     * @param key the key to the bitmap (usually a full path)
     * @return the corresponding bitmap
     */
    public Bitmap get(String key) {
        synchronized (lruCache) {
            return lruCache.get(key);
        }
    }

    /**
     * Removes and Recycles a bitmap from the cache
     *
     * @param key the key to the bitmap (usually a full path)
     */
    public void remove(String key) {

        Bitmap bitmap;
        synchronized (lruCache) {
            bitmap = lruCache.remove(key);
        }
        if (bitmap != null)
            bitmap.recycle();
    }
}
