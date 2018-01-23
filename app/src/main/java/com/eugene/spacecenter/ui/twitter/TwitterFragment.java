package com.eugene.spacecenter.ui.twitter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eugene.spacecenter.R;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwitterFragment extends Fragment {

    public static final String KEY_TWITTER_ACCOUNT = "twitterAccount";
    String twitterAccount;

    public TwitterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_list, container, false);

        twitterAccount = getArguments().getString(KEY_TWITTER_ACCOUNT);

        ListView listView = (ListView) rootView.findViewById(R.id.list_view);

        View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        listView.setEmptyView(loadingIndicator);

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(twitterAccount)
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getContext())
                .setTimeline(userTimeline)
                .build();


        listView.setAdapter(adapter);


        return rootView;


    }

}
