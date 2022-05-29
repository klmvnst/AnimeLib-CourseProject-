package com.example.animelib.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.animelib.R;
import com.example.animelib.classes.DownloadImageTask;

public class ImageDialog extends Dialog {

    //диалог для показа изображения
    public ImageDialog(@NonNull Context context, String image) {
        super(context);
        this.setContentView(getLayoutInflater().inflate(R.layout.image_show_dialog, null));
        new DownloadImageTask(findViewById(R.id.ivDialogPicture)).execute(image);
        findViewById(R.id.bCancelDialog).setOnClickListener(view -> cancel());
    }
}
