package com.alltools.toolbox.utility.calculator.EssentialTools.VoiceRecorder.SavedRecords;

import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alltools.toolbox.utility.calculator.R;

public class SavedRecordingsActivity extends AppCompatActivity {
    private static final String ARG_POSITION = "position";
    private static final String LOG_TAG = "FileViewerFragment";
    private Adapter mFileViewerAdapter;
    FileObserver observer = new FileObserver(Environment.getExternalStorageDirectory().toString() + "/All In One Tools") {
        @Override
        public void onEvent(int i, String str) {
            if (i == 512) {
                Log.d(SavedRecordingsActivity.LOG_TAG, "File deleted [" + Environment.getExternalStorageDirectory().toString() + "/All In One Tools" + str + "]");
                SavedRecordingsActivity.this.mFileViewerAdapter.removeOutOfApp(Environment.getExternalStorageDirectory().toString() + "/All In One Tools" + str + "]");
            }
        }
    };
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recordings);
        this.observer.startWatching();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Adapter fileViewerAdapter = new Adapter(this, linearLayoutManager);
        this.mFileViewerAdapter = fileViewerAdapter;
        recyclerView.setAdapter(fileViewerAdapter);

    }
}