package com.alltools.toolbox.utility.calculator.EssentialTools.VoiceRecorder.SavedRecords;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.LightingColorFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.alltools.toolbox.utility.calculator.EssentialTools.VoiceRecorder.RecordingItem;
import com.alltools.toolbox.utility.calculator.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PlaybackFragment extends DialogFragment {
    private static final String ARG_ITEM = "recording_item";
    private static final String LOG_TAG = "PlaybackFragment";
    private RecordingItem item;
    private Handler mHandler = new Handler();
    private MediaPlayer mMediaPlayer = null;
    private SeekBar mSeekBar = null;
    private FloatingActionButton mPlayButton = null;
    private TextView mCurrentProgressTextView = null;
    private TextView mFileNameTextView = null;
    private TextView mFileLengthTextView = null;
    private boolean isPlaying = false;
    long minutes = 0;
    long seconds = 0;
    private Runnable mRunnable = new Runnable() { // from class: com.droidfoundry.tools.sound.audio.fragments.PlaybackFragment.6
        @Override // java.lang.Runnable
        public void run() {
            if (PlaybackFragment.this.mMediaPlayer != null) {
                int currentPosition = PlaybackFragment.this.mMediaPlayer.getCurrentPosition();
                PlaybackFragment.this.mSeekBar.setProgress(currentPosition);
                long j = currentPosition;
                long minutes = TimeUnit.MILLISECONDS.toMinutes(j);
                PlaybackFragment.this.mCurrentProgressTextView.setText(String.format("%02d:%02d", Long.valueOf(minutes), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(minutes))));
                PlaybackFragment.this.updateSeekBar();
            }
        }
    };

    public PlaybackFragment newInstance(RecordingItem recordingItem) {
        PlaybackFragment playbackFragment = new PlaybackFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_ITEM, recordingItem);
        playbackFragment.setArguments(bundle);
        return playbackFragment;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        RecordingItem recordingItem = (RecordingItem) getArguments().getParcelable(ARG_ITEM);
        this.item = recordingItem;
        long length = recordingItem.getLength();
        this.minutes = TimeUnit.MILLISECONDS.toMinutes(length);
        this.seconds = TimeUnit.MILLISECONDS.toSeconds(length) - TimeUnit.MINUTES.toSeconds(this.minutes);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        Dialog onCreateDialog = super.onCreateDialog(bundle);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.fragment_media_playback, (ViewGroup) null);
        this.mFileNameTextView = (TextView) inflate.findViewById(R.id.file_name_text_view);
        this.mFileLengthTextView = (TextView) inflate.findViewById(R.id.file_length_text_view);
        this.mCurrentProgressTextView = (TextView) inflate.findViewById(R.id.current_progress_text_view);
        this.mSeekBar = (SeekBar) inflate.findViewById(R.id.seekbar);
        LightingColorFilter lightingColorFilter = new LightingColorFilter(getResources().getColor(R.color.black), getResources().getColor(R.color.black));
        this.mSeekBar.getProgressDrawable().setColorFilter(lightingColorFilter);
        this.mSeekBar.getThumb().setColorFilter(lightingColorFilter);
        this.mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.droidfoundry.tools.sound.audio.fragments.PlaybackFragment.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (PlaybackFragment.this.mMediaPlayer == null || !z) {
                    if (PlaybackFragment.this.mMediaPlayer == null && z) {
                        PlaybackFragment.this.prepareMediaPlayerFromPoint(i);
                        PlaybackFragment.this.updateSeekBar();
                        return;
                    }
                    return;
                }
                PlaybackFragment.this.mMediaPlayer.seekTo(i);
                PlaybackFragment.this.mHandler.removeCallbacks(PlaybackFragment.this.mRunnable);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(PlaybackFragment.this.mMediaPlayer.getCurrentPosition());
                PlaybackFragment.this.mCurrentProgressTextView.setText(String.format("%02d:%02d", Long.valueOf(minutes), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(PlaybackFragment.this.mMediaPlayer.getCurrentPosition()) - TimeUnit.MINUTES.toSeconds(minutes))));
                PlaybackFragment.this.updateSeekBar();
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (PlaybackFragment.this.mMediaPlayer != null) {
                    PlaybackFragment.this.mHandler.removeCallbacks(PlaybackFragment.this.mRunnable);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (PlaybackFragment.this.mMediaPlayer != null) {
                    PlaybackFragment.this.mHandler.removeCallbacks(PlaybackFragment.this.mRunnable);
                    PlaybackFragment.this.mMediaPlayer.seekTo(seekBar.getProgress());
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(PlaybackFragment.this.mMediaPlayer.getCurrentPosition());
                    PlaybackFragment.this.mCurrentProgressTextView.setText(String.format("%02d:%02d", Long.valueOf(minutes), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(PlaybackFragment.this.mMediaPlayer.getCurrentPosition()) - TimeUnit.MINUTES.toSeconds(minutes))));
                    PlaybackFragment.this.updateSeekBar();
                }
            }
        });
        FloatingActionButton floatingActionButton = inflate.findViewById(R.id.fab_play);
        this.mPlayButton = floatingActionButton;
        floatingActionButton.setOnClickListener(new View.OnClickListener() { // from class: com.droidfoundry.tools.sound.audio.fragments.PlaybackFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PlaybackFragment playbackFragment = PlaybackFragment.this;
                playbackFragment.onPlay(playbackFragment.isPlaying);
                PlaybackFragment playbackFragment2 = PlaybackFragment.this;
                playbackFragment2.isPlaying = !playbackFragment2.isPlaying;
            }
        });
        this.mFileNameTextView.setText(this.item.getName());
        this.mFileLengthTextView.setText(String.format("%02d:%02d", Long.valueOf(this.minutes), Long.valueOf(this.seconds)));
        builder.setView(inflate);
        onCreateDialog.getWindow().requestFeature(1);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setBackgroundDrawableResource(17170445);
        AlertDialog alertDialog = (AlertDialog) getDialog();
        alertDialog.getButton(-1).setEnabled(false);
        alertDialog.getButton(-2).setEnabled(false);
        alertDialog.getButton(-3).setEnabled(false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.mMediaPlayer != null) {
            stopPlaying();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.mMediaPlayer != null) {
            stopPlaying();
        }
    }

    public void onPlay(boolean z) {
        if (!z) {
            if (this.mMediaPlayer == null) {
                startPlaying();
                return;
            } else {
                resumePlaying();
                return;
            }
        }
        pausePlaying();
    }

    private void startPlaying() {
        this.mPlayButton.setImageResource(R.drawable.ic_action_pause);
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mMediaPlayer = mediaPlayer;
        try {
            mediaPlayer.setDataSource(this.item.getFilePath());
            this.mMediaPlayer.prepare();
            this.mSeekBar.setMax(this.mMediaPlayer.getDuration());
            this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.droidfoundry.tools.sound.audio.fragments.PlaybackFragment.3
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mediaPlayer2) {
                    PlaybackFragment.this.mMediaPlayer.start();
                }
            });
        } catch (IOException unused) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        this.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.droidfoundry.tools.sound.audio.fragments.PlaybackFragment.4
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer2) {
                PlaybackFragment.this.stopPlaying();
            }
        });
        updateSeekBar();
        getActivity().getWindow().addFlags(128);
    }

    public void prepareMediaPlayerFromPoint(int i) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mMediaPlayer = mediaPlayer;
        try {
            mediaPlayer.setDataSource(this.item.getFilePath());
            this.mMediaPlayer.prepare();
            this.mSeekBar.setMax(this.mMediaPlayer.getDuration());
            this.mMediaPlayer.seekTo(i);
            this.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.droidfoundry.tools.sound.audio.fragments.PlaybackFragment.5
                @Override // android.media.MediaPlayer.OnCompletionListener
                public void onCompletion(MediaPlayer mediaPlayer2) {
                    PlaybackFragment.this.stopPlaying();
                }
            });
        } catch (IOException unused) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        getActivity().getWindow().addFlags(128);
    }

    private void pausePlaying() {
        this.mPlayButton.setImageResource(R.drawable.ic_action_play);
        this.mHandler.removeCallbacks(this.mRunnable);
        this.mMediaPlayer.pause();
    }

    private void resumePlaying() {
        this.mPlayButton.setImageResource(R.drawable.ic_action_pause);
        this.mHandler.removeCallbacks(this.mRunnable);
        this.mMediaPlayer.start();
        updateSeekBar();
    }

    public void stopPlaying() {
        this.mPlayButton.setImageResource(R.drawable.ic_action_play);
        this.mHandler.removeCallbacks(this.mRunnable);
        this.mMediaPlayer.stop();
        this.mMediaPlayer.reset();
        this.mMediaPlayer.release();
        this.mMediaPlayer = null;
        SeekBar seekBar = this.mSeekBar;
        seekBar.setProgress(seekBar.getMax());
        this.isPlaying = !this.isPlaying;
        this.mCurrentProgressTextView.setText(this.mFileLengthTextView.getText());
        SeekBar seekBar2 = this.mSeekBar;
        seekBar2.setProgress(seekBar2.getMax());
        getActivity().getWindow().clearFlags(128);
    }

    public void updateSeekBar() {
        this.mHandler.postDelayed(this.mRunnable, 1000L);
    }

}
