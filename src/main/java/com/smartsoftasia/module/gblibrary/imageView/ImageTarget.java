package com.smartsoftasia.module.gblibrary.imageView;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.smartsoftasia.module.gblibrary.helper.Validator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by gregoire on 9/25/14.
 */
public class ImageTarget implements Target {
    protected String fileName;
    protected String dirName;

    public ImageTarget(String fileName, String dirName) {
        this.fileName = fileName;
        this.dirName = dirName;
    }

    @Override
    public void onBitmapLoaded(final Bitmap bitmap, final Picasso.LoadedFrom from) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (Validator.isValid(fileName)) {
                    File file;
                    if (Validator.isValid(dirName)) {
                        file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + dirName + "/" + fileName);
                    } else {
                        file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + fileName);
                    }

                    try {
                        file.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ostream);
                        ostream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        if (placeHolderDrawable != null) {
        }
    }
}

