package com.eugene.spacecenter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * A simple {@link Fragment} subclass.
 */
public class ISSFragment extends Fragment {

    public ISSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView=inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.list_view);

        View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        listView.setEmptyView(loadingIndicator);

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("Space_Station")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getContext())
                .setTimeline(userTimeline)
                .build();

        listView.setAdapter(adapter);

        return rootView;

    }

}
