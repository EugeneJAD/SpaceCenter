package com.eugene.spacecenter;


import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SoundsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<SoundBox>>, MediaPlayer.OnPreparedListener  {

    //BETA VERSION

    private final static String LOG_TAG = SoundsFragment.class.getSimpleName();
    private final static String JSON_URL = "https://api.nasa.gov/planetary/sounds?";
    private final static String API_KEY = "&api_key=YAurBnDkk9eId7of823JM3MgW2ZptbrQGXGaD81w";
    private final static String SOUNDS_LIMIT = "&limit=64"; //There are 64 sounds in NASA's sounds
    private final static String SEARCH_QUERY = "q=";  // Search doesn't work yet
    private static final int SOUND_LOADER_ID = 1;
    String url;
    String playingTrack ="";
    ImageView lastClickedPlayButton=null;
    boolean isPlaying = false;

    private SoundListAdapter soundListAdapter;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    private Toast toast;


    public SoundsFragment() {
        // Required empty public constructor
    }

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ) {
                        mediaPlayer.pause();
                    }
                    else if(focusChange==AudioManager.AUDIOFOCUS_GAIN) {
                        mediaPlayer.start();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                }
            };

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    private void releaseMediaPlayer() {

        if (mediaPlayer != null)
            mediaPlayer.release();

        mediaPlayer = null;

        if (toast!=null)
            toast.cancel();

        playingTrack="";
        isPlaying = false;

        if (lastClickedPlayButton!=null) {
            lastClickedPlayButton.setImageResource(R.drawable.ic_play_arrow_white_36dp);
            lastClickedPlayButton = null;
        }

        audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_sounds, container, false);


        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        url = JSON_URL+SOUNDS_LIMIT+API_KEY;

        final ListView listView = (ListView) rootView.findViewById(R.id.list_view);

        soundListAdapter = new SoundListAdapter(getActivity(), new ArrayList<SoundBox>());
        listView.setAdapter(soundListAdapter);

        View loadingIndicator = (View) rootView.findViewById(R.id.loading_indicator);
        listView.setEmptyView(loadingIndicator);

        LoaderManager loader = getLoaderManager();
        loader.initLoader(SOUND_LOADER_ID, null, this);

        return rootView;
    }

    @Override
    public Loader<List<SoundBox>> onCreateLoader(int id, Bundle args) {
        return new SoundLoader(getContext(), url);
    }

    @Override
    public void onLoadFinished(Loader<List<SoundBox>> loader, List<SoundBox> data) {

        soundListAdapter.clear();

        if (data != null && !data.isEmpty())
            soundListAdapter.addAll(data);

    }

    @Override
    public void onLoaderReset(Loader<List<SoundBox>> loader) {
        soundListAdapter.clear();
        releaseMediaPlayer();

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
        isPlaying = true;
        if(toast!=null)
            toast.cancel();

    }


    private class SoundListAdapter extends ArrayAdapter<SoundBox> {


        public SoundListAdapter(Activity context, ArrayList<SoundBox> soundBoxes) {
            super(context, 0, soundBoxes);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = new ViewHolder();
            SoundBox current = getItem(position);

            if (convertView==null){

                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_sounds,parent,false);

                viewHolder.playButton = (ImageView) convertView.findViewById(R.id.image_play_button);
                viewHolder.titleText = (TextView) convertView.findViewById(R.id.title_sound);
                viewHolder.durationLength = (TextView) convertView.findViewById(R.id.duration_sec);

                viewHolder.playButton.setTag(position);
                convertView.setTag(viewHolder);

            } else {

                viewHolder = (ViewHolder) convertView.getTag();
                viewHolder.playButton.setTag(position);

            }

            if (playingTrack.equals(current.getTitle())){
                viewHolder.playButton.setImageResource(R.drawable.ic_stop_white_36dp);
            } else {
                viewHolder.playButton.setImageResource(R.drawable.ic_play_arrow_white_36dp);
            }

            viewHolder.titleText.setText(current.getTitle());

            String formattedDuration = getFormattedDuration(current.getDuration()) + " " + getResources().getString(R.string.second);
            viewHolder.durationLength.setText(formattedDuration);

            final ImageView playButton = viewHolder.playButton;
            viewHolder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int pos = (Integer) playButton.getTag();
                    SoundBox soundClicked = soundListAdapter.getItem(pos);

                    if (isPlaying && playingTrack.equals(soundClicked.getTitle())) {
                        releaseMediaPlayer();
                    }
                    else {

                        releaseMediaPlayer();

                        playingTrack = soundClicked.getTitle();

                        lastClickedPlayButton = playButton;

                        int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                            playButton.setImageResource(R.drawable.ic_stop_white_36dp);
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.setOnPreparedListener(SoundsFragment.this);

                            try {
                                mediaPlayer.setDataSource(soundClicked.getStreamURLstring());
                                toast = Toast.makeText(getContext(), "Loading track " + (pos + 1), Toast.LENGTH_LONG);
                                toast.show();
                                mediaPlayer.prepareAsync();
                            } catch (IOException e) {
                                Log.e(LOG_TAG, "Media player Error " + soundClicked.getStreamURLstring(), e);
                            }

                            mediaPlayer.setOnCompletionListener(mOnCompletionListener);

                        }

                    }
                }
            });


            return convertView;


        }

        private String getFormattedDuration(long duration) {

            int seconds = (int) (duration / 1000);
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(seconds);

        }

        private class ViewHolder {

            ImageView playButton;
            TextView titleText;
            TextView durationLength;
        }
    }

}
