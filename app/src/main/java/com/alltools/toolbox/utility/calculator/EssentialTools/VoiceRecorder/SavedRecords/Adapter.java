package com.alltools.toolbox.utility.calculator.EssentialTools.VoiceRecorder.SavedRecords;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alltools.toolbox.utility.calculator.EssentialTools.VoiceRecorder.DBHelper;
import com.alltools.toolbox.utility.calculator.EssentialTools.VoiceRecorder.OnDatabaseChangedListener;
import com.alltools.toolbox.utility.calculator.EssentialTools.VoiceRecorder.RecordingItem;
import com.alltools.toolbox.utility.calculator.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class Adapter extends RecyclerView.Adapter<Adapter.RecordingsViewHolder> implements OnDatabaseChangedListener {
    private static final String LOG_TAG = "FileViewerAdapter";
    RecordingItem item;
    LinearLayoutManager llm;
    Context mContext;
    private DBHelper mDatabase;

    @Override
    public void onDatabaseEntryRenamed() {
    }

    public void removeOutOfApp(String str) {
    }

    public Adapter(Context context, LinearLayoutManager linearLayoutManager) {
        this.mContext = context;
        this.mDatabase = new DBHelper(this.mContext);
        DBHelper.setOnDatabaseChangedListener(this);
        this.llm = linearLayoutManager;
    }

    @Override
    public void onBindViewHolder(final RecordingsViewHolder recordingsViewHolder, int i) {
        RecordingItem item = getItem(i);
        this.item = item;
        long length = item.getLength();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(length);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(length) - TimeUnit.MINUTES.toSeconds(minutes);
        recordingsViewHolder.vName.setText(this.item.getName());
        recordingsViewHolder.vLength.setText(String.format("%02d:%02d", Long.valueOf(minutes), Long.valueOf(seconds)));
        recordingsViewHolder.vDateAdded.setText(DateUtils.formatDateTime(this.mContext, this.item.getTime(), 131093));
        recordingsViewHolder.llParent.setOnClickListener(new View.OnClickListener() { // from class: com.droidfoundry.tools.sound.audio.adapters.FileViewerAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    new PlaybackFragment().newInstance(Adapter.this.getItem(recordingsViewHolder.getPosition())).show(((FragmentActivity) Adapter.this.mContext).getSupportFragmentManager().beginTransaction(), "dialog_playback");
                } catch (Exception e) {
                    Log.e(Adapter.LOG_TAG, "exception", e);
                }
            }
        });
        recordingsViewHolder.llParent.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.droidfoundry.tools.sound.audio.adapters.FileViewerAdapter.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(Adapter.this.mContext.getString(R.string.dialog_file_share));
                arrayList.add(Adapter.this.mContext.getString(R.string.dialog_file_rename));
                arrayList.add(Adapter.this.mContext.getString(R.string.dialog_file_delete));
                AlertDialog.Builder builder = new AlertDialog.Builder(Adapter.this.mContext);
                builder.setTitle(Adapter.this.mContext.getString(R.string.dialog_title_options));
                builder.setItems((CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]), new DialogInterface.OnClickListener() { // from class: com.droidfoundry.tools.sound.audio.adapters.FileViewerAdapter.2.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        if (i2 == 0) {
                            Adapter.this.shareFileDialog(recordingsViewHolder.getPosition());
                        }
                        if (i2 == 1) {
                            Adapter.this.renameFileDialog(recordingsViewHolder.getPosition());
                        } else if (i2 == 2) {
                            Adapter.this.deleteFileDialog(recordingsViewHolder.getPosition());
                        }
                    }
                });
                builder.setCancelable(true);
                builder.setNegativeButton(Adapter.this.mContext.getString(R.string.dialog_action_cancel), new DialogInterface.OnClickListener() { // from class: com.droidfoundry.tools.sound.audio.adapters.FileViewerAdapter.2.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
                return false;
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecordingsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.audio_record_row, viewGroup, false);
        this.mContext = viewGroup.getContext();
        return new RecordingsViewHolder(inflate);
    }


    public static class RecordingsViewHolder extends RecyclerView.ViewHolder {
        protected LinearLayout llParent;
        protected TextView vDateAdded;
        protected TextView vLength;
        protected TextView vName;

        public RecordingsViewHolder(View view) {
            super(view);
            this.vName = (TextView) view.findViewById(R.id.file_name_text);
            this.vLength = (TextView) view.findViewById(R.id.file_length_text);
            this.vDateAdded = (TextView) view.findViewById(R.id.file_date_added_text);
            this.llParent = (LinearLayout) view.findViewById(R.id.card_view);
        }
    }

    @Override
    public int getItemCount() {
        return this.mDatabase.getCount();
    }

    public RecordingItem getItem(int i) {
        return this.mDatabase.getItemAt(i);
    }

    @Override
    public void onNewDatabaseEntryAdded() {
        notifyItemInserted(getItemCount() - 1);
        this.llm.scrollToPosition(getItemCount() - 1);
    }

    public void remove(int i) {
        new File(getItem(i).getFilePath()).delete();
        Context context = this.mContext;
        Toast.makeText(context, String.format(context.getString(R.string.toast_file_delete), getItem(i).getName()), Toast.LENGTH_SHORT).show();
        this.mDatabase.removeItemWithId(getItem(i).getId());
        notifyItemRemoved(i);
    }

    public void rename(int i, String str) {
        String str2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SoundRecorder/" + str;
        File file = new File(str2);
        if (file.exists() && !file.isDirectory()) {
            Context context = this.mContext;
            Toast.makeText(context, String.format(context.getString(R.string.toast_file_exists), str), Toast.LENGTH_SHORT).show();
            return;
        }
        new File(getItem(i).getFilePath()).renameTo(file);
        this.mDatabase.renameItem(getItem(i), str, str2);
        notifyItemChanged(i);
    }

    public void shareFileDialog(int i) {
        File file = new File(getItem(i).getFilePath());
        Context applicationContext = this.mContext.getApplicationContext();
        Uri uriForFile = FileProvider.getUriForFile(applicationContext, this.mContext.getPackageName() + ".provider", file);
        if (Build.VERSION.SDK_INT >= 24) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("audio/*");
            intent.putExtra("android.intent.extra.STREAM", uriForFile);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Context context = this.mContext;
            context.startActivity(Intent.createChooser(intent, context.getText(R.string.send_to)));
        }
    }

    public void renameFileDialog(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.dialog_audio_rename_file, (ViewGroup) null);
        final TextInputEditText textInputEditText = (TextInputEditText) inflate.findViewById(R.id.new_name);
        TextInputLayout textInputLayout = (TextInputLayout) inflate.findViewById(R.id.tip_new_name);
        try {
            Field declaredField = TextInputLayout.class.getDeclaredField("defaultStrokeColor");
            declaredField.setAccessible(true);
            declaredField.set(textInputLayout, Integer.valueOf(ContextCompat.getColor(this.mContext, R.color.tools_edit_text_primary_color)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.setTitle(this.mContext.getString(R.string.dialog_title_rename));
        builder.setCancelable(true);
        builder.setPositiveButton(this.mContext.getString(R.string.dialog_action_ok), new DialogInterface.OnClickListener() { // from class: com.droidfoundry.tools.sound.audio.adapters.FileViewerAdapter.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                try {
                    Adapter.this.rename(i, textInputEditText.getText().toString().trim() + ".mp4");
                } catch (Exception e2) {
                    Log.e(Adapter.LOG_TAG, "exception", e2);
                }
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton(this.mContext.getString(R.string.dialog_action_cancel), new DialogInterface.OnClickListener() { // from class: com.droidfoundry.tools.sound.audio.adapters.FileViewerAdapter.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.cancel();
            }
        });
        builder.setView(inflate);
        builder.create().show();
    }

    public void deleteFileDialog(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle(this.mContext.getString(R.string.dialog_title_delete));
        builder.setMessage(this.mContext.getString(R.string.dialog_text_delete));
        builder.setCancelable(true);
        builder.setPositiveButton(this.mContext.getString(R.string.dialog_action_yes), new DialogInterface.OnClickListener() { // from class: com.droidfoundry.tools.sound.audio.adapters.FileViewerAdapter.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                try {
                    Adapter.this.remove(i);
                } catch (Exception e) {
                    Log.e(Adapter.LOG_TAG, "exception", e);
                }
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton(this.mContext.getString(R.string.dialog_action_no), new DialogInterface.OnClickListener() { // from class: com.droidfoundry.tools.sound.audio.adapters.FileViewerAdapter.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }
}