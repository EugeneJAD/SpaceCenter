package com.eugene.spacecenter;

/**
 * Created by Администратор on 20.09.2016.
 */
public class SoundBox {

    private final static String LOG_TAG = SoundBox.class.getSimpleName();
    private final static String SOUND_CLOUD_ID = "?client_id=57IllgTW2UcI0w2Bk4TN1glR8L7EqUP4";
    private String title;
    private String streamUrl;
    private String downloadUrl;
    private long duration;


    public SoundBox(String title, String streamUrl, long duration, String downloadUrl){
        this.title=title;
        this.streamUrl=streamUrl+SOUND_CLOUD_ID;
        this.duration=duration;
        this.downloadUrl=downloadUrl+SOUND_CLOUD_ID;
    }

    public String getTitle(){return title;}
    public long getDuration(){return duration;}
    public String getStreamURLstring(){return streamUrl;}
    public String getDownloadUrlstring(){return downloadUrl;}


}
