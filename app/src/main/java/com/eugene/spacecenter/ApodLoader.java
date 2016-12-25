package com.eugene.spacecenter;

/**
 * Created by Администратор on 25.10.2016.
 */
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by Администратор on 24.10.2016.
 */

public class ApodLoader extends AsyncTaskLoader<ApodBox> {

    private String url;

    public ApodLoader(Context context, String jsonUrl) {
        super(context);
        url=jsonUrl;

    }

    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ApodBox loadInBackground() {

        if (url == null) {
            return null;
        }

        ApodBox apodBox = QueryUtils.fetchAPODData(url);

        return apodBox;
    }
}

