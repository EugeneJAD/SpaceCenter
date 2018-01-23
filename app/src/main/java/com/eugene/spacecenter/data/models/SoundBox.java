package com.eugene.spacecenter.data.models;

import com.eugene.spacecenter.utils.AppConstants;

/**
 * Created by Администратор on 20.09.2016.
 */
public class SoundBox {

    private String title;
    private String streamUrl;
    private String downloadUrl;
    private long duration;


    public SoundBox(String title, String streamUrl, long duration, String downloadUrl){
        this.title=title;
        this.streamUrl=streamUrl+"?client_id="+AppConstants.SOUND_CLOUD_ID;
        this.duration=duration;
        this.downloadUrl=downloadUrl+"?client_id="+AppConstants.SOUND_CLOUD_ID;
    }

    public String getTitle(){return title;}
    public long getDuration(){return duration;}
    public String getStreamURLstring(){return streamUrl;}
    public String getDownloadUrlstring(){return downloadUrl;}


}
