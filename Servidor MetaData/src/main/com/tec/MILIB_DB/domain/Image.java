package main.com.tec.MILIB_DB.domain;

/**
 * The kind of image is the implementation of an image.
 * Receive metadata and create; It also allows you to modify it.
 */
public class Image {

    private Metadata metaData;

    /**
     * Image Constructor
     * @param myMetadata Receive the metadata and create an image with the associated information
     */
    public Image(Metadata myMetadata){
        this.metaData = myMetadata;
    }

    /**
     * Provides the metadata associated with an image
     * @return Return the metadata
     */
    public Metadata getMyMetadata() {
        return metaData;
    }

    /**
     * It allows associating metadata to an image
     * @param myMetadata Receive the metadata and update the information of the image
     */
    public void setMyMetadata(Metadata myMetadata) {
        metaData = myMetadata;
    }

}
