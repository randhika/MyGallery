package mx.org.dabicho.mygallery.model;

import android.graphics.Bitmap;

import mx.org.dabicho.mygallery.GalleriesManagerFragment;

import static android.util.Log.i;

/**
 * A gallery defines a group of images.
 * This class holds the definition of a gallery
 *
 * There are various kinds of gallery, the first one which always exist are the galleries that
 * come from android's images content provider. Each bucket is a gallery.
 *
 * Galleries are represented visually by it's name and various data about it like how many images
 * it contains, and a BitMap generated by its {@link mx.org.dabicho.mygallery.model.Cover}
 *
 *
 */
public abstract class Gallery {
    private static final String TAG =  "Gallery";
    /**
     * Nombre de la galeria
     */
    private String mName;
    /**
     * Cuantas imagenes componen esta galeria
     */
    private long mCount;
    /**
     * id de la galeria
     */
    private long mGalleryId;
    /**
     * Cubierta de la galeria
     */
    private Cover mCover;

    public abstract GalleryType getGalleryType();

    /**
     * @return el nombre de la galería
     */
    public String getName() {
        return mName;
    }

    /**
     * @param name El nombre de la galería
     */
    public void setName(String name) {
        mName = name;
    }

    /**
     * @return la cantidad de imágenes que contiene la galería
     */
    public long getCount() {
        return mCount;
    }

    /**
     * @param count la cantidad de imágenes que contiene la galería
     */
    public void setCount(long count) {
        mCount = count;
    }

    /**
     * @return id de la galería
     */
    public long getGalleryId() {
        return mGalleryId;
    }

    /**
     * @param galleryId de la galería
     */
    public void setGalleryId(long galleryId) {
        mGalleryId = galleryId;
    }

    /**
     *
     * @param cover constructor de cubierta
     */
    public void setCover(Cover cover) {
        mCover = cover;
    }

    public boolean hasCover(){
        return mCover!=null;
    }

    /**
     * Ordena a la cubierta que dibuje el ImageView. Si no tiene cubierta, regresa false
     * @param imageView
     * @return false si no tiene cubierta
     */
    public boolean paintCover(GalleriesManagerFragment.GalleryItemViewHolder imageView){

        if(mCover!=null) {
            i(TAG, "paintCover: Cover");
            return mCover.paintCover(imageView);
        }
        else {
            i(TAG, "paintCover: no cover");
            return false;
        }
    }

    public Bitmap loadCover(int preferredWidth, int preferredHeight){
        i(TAG, "loadCover: for "+mName+" size: "+preferredWidth+" x "+preferredHeight);
        return mCover.generateCover(preferredWidth,preferredHeight);
    }


}

