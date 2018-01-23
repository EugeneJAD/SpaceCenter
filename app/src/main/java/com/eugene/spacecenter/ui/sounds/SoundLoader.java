package com.eugene.spacecenter.ui.sounds;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.eugene.spacecenter.utils.QueryUtils;
import com.eugene.spacecenter.data.models.SoundBox;

import java.util.List;

/**
 * Created by Администратор on 04.10.2016.
 */

public class SoundLoader extends AsyncTaskLoader<List<SoundBox>> {


    String url;

    public SoundLoader(Context context, String url) {
        super(context);
        this.url=url;
    }

    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<SoundBox> loadInBackground() {

        if (url == null) {
            return null;
        }

        List<SoundBox> soundBoxes = QueryUtils.fetchSoundsData(url);
        return soundBoxes;
    }
}
