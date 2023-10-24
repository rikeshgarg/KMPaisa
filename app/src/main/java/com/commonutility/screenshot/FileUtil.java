package com.commonutility.screenshot;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.annotation.RequiresApi;

import com.commonutility.ShowCustomToast;
import com.codunite.rechargeapp.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtil {

    private final Context context;
    private final ShowCustomToast customToast;
    public static final String TAG = FileUtil.class.getSimpleName();

    private FileUtil(Context context) {
        this.context = context;
        customToast = new ShowCustomToast(context);
    }

    public static FileUtil getInstance(Context context) {
        return new FileUtil(context);
    }


    public static File getOutputMediaFile(Context context) {
        // Create a media folder
        File createDir = new File(Environment.getExternalStorageDirectory(), getFolderName(context, 0));
        if (!createDir.exists()) {
            if (!createDir.mkdirs()) return null;
        }

        // Create a media file name
        File photoFile = new File(Environment.getExternalStorageDirectory(), getFolderName(context, 1));

        return new File(photoFile.getAbsolutePath());
    }

    public static Uri getFileUriToSave(Context context, String fileName) {
        return getFileUriToSave(context, fileName, MediaType.Other);
    }

    public static Uri getFileUriToSave(Context context, String fileName, MediaType mediaType) {
        // if targeting API 29 and upper than custom dir not allowed! you must use default storage dirs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return getFileUriToSaveQ(context, fileName, mediaType);

        } else {
            ContentValues values = new ContentValues();
            String appDir = context.getResources().getString(R.string.app_name) + "/";

            File directory = new File(Environment.getExternalStorageDirectory(), appDir);
            File savedFile = new File(directory, fileName);
            if (!directory.exists()) directory.mkdir();

            values.put(MediaStore.MediaColumns.DATA, savedFile.getAbsolutePath());
            //finalUri = context.getContentResolver().insert(storeUri, values);

            return Uri.fromFile(savedFile);
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    public static Uri getFileUriToSaveQ(Context context, String fileName, MediaType mediaType) {
        try {
            Uri finalUri;
            ContentResolver resolver = context.getContentResolver();
            ContentValues values = new ContentValues();

            String appDir = context.getResources().getString(R.string.app_name) + "/";
            String mimeType = "", dirName = "";
            Uri storeUri = null;

            if (mediaType == MediaType.Image) {
                mimeType = "image/jpg";
                dirName = Environment.DIRECTORY_PICTURES + "/";
                values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
                values.put(MediaStore.Images.Media.MIME_TYPE, mimeType);

                storeUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            } else if (mediaType == MediaType.Video) {
                mimeType = "video/mp4";
                dirName = Environment.DIRECTORY_MOVIES + "/";
                values.put(MediaStore.Video.Media.DISPLAY_NAME, fileName);
                values.put(MediaStore.Video.Media.MIME_TYPE, mimeType);

                storeUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

            } else if (mediaType == MediaType.Audio) {
                mimeType = "audio/*";
                dirName = Environment.DIRECTORY_MUSIC + "/";
                values.put(MediaStore.Audio.Media.DISPLAY_NAME, fileName);
                values.put(MediaStore.Audio.Media.MIME_TYPE, mimeType);

                storeUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

            } else if (mediaType == MediaType.Other) {
                mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(getExtension(fileName));
                dirName = Environment.DIRECTORY_DOWNLOADS + "/";
                values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
                values.put(MediaStore.Downloads.MIME_TYPE, mimeType);

                storeUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI;

            } else {
                return null;
            }

            values.put(MediaStore.MediaColumns.RELATIVE_PATH, dirName + appDir);
            values.put(MediaStore.Images.Media.IS_PENDING, 0);
            finalUri = resolver.insert(storeUri, values);

            return finalUri;
        } catch (Exception e) {
            // java.io.IOException: Operation not permitted
            Log.d(TAG, e.toString());
            return null;
        }
    }

    public static void storeImage(Context context, Bitmap bitmap) {
        ShowCustomToast customToast = new ShowCustomToast(context);
        String savedFilePath = "";

        try {
            Uri finalUri;
            ContentResolver resolver = context.getContentResolver();
            String fileName = FileUtil.getFileName("jpg");

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");

            // if targeting API 29 and upper than custom dir not allowed! you must use default storage dirs
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + getAppDir(context));
                values.put(MediaStore.Images.Media.IS_PENDING, 0);
                savedFilePath = Environment.DIRECTORY_PICTURES + "/" + getAppDir(context) + fileName;
            } else {
                File createDir = new File(Environment.getExternalStorageDirectory(), getAppDir(context));
                if (!createDir.exists()) {
                    createDir.mkdirs();
                }

                File savedFile = new File(createDir, fileName);
                savedFilePath = savedFile.getAbsolutePath();
                values.put(MediaStore.MediaColumns.DATA, savedFilePath);
            }

            finalUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            try (OutputStream output = resolver.openOutputStream(finalUri)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                customToast.showCustomToast(context, "Saved! " + savedFilePath, customToast.ToastySuccess);
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            customToast.showCustomToast(context, "Please grant storage permission to download image.", customToast.ToastyError);
        }
    }

    public static void storeTextInFile(Context context, String text) {
        storeTextInFile(context, text, FileUtil.getFileName("txt"));
    }

    public static void storeTextInFile(Context context, String text, String fileName) {
        ShowCustomToast customToast = new ShowCustomToast(context);
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);
            FileWriter out = new FileWriter(file);
            out.write(text);
            out.close();
            customToast.showCustomToast(context, "Saved! " + file.getAbsolutePath(), customToast.ToastySuccess);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            customToast.showCustomToast(context, e.toString(), customToast.ToastyError);
        }
    }

    public static File createTmpImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = context.getString(R.string.app_name) + "_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        String currentPhotoPath = image.getAbsolutePath();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

        } else {
            File createDir = new File(Environment.getExternalStorageDirectory(), FileUtil.getFolderName(context, 0));
            File photoFile = new File(Environment.getExternalStorageDirectory(), FileUtil.getFolderName(context, 1));
            if (!createDir.exists()) {
                createDir.mkdirs();
            }
        }

        return image;
    }

    public static String getFolderName(Context context, int type) {
        if (type == 0) {
            return "/" + context.getResources().getString(R.string.app_name) + "/";
        } else {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            return "/" + context.getResources().getString(R.string.app_name) + "/" + "photo_" + timeStamp + ".jpg";
        }
    }

    public static String getFileName(String ext) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return "File_" + timeStamp + "." + ext;
    }

    public static String getAppDir(Context context) {
        return context.getResources().getString(R.string.app_name) + "/";
    }

    public static String getExtension(String fileName) {
        char ch;
        int len;
        if (fileName == null ||
                (len = fileName.length()) == 0 ||
                (ch = fileName.charAt(len - 1)) == '/' || ch == '\\' || //in the case of a directory
                ch == '.') //in the case of . or ..
            return "";
        int dotInd = fileName.lastIndexOf('.'),
                sepInd = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        if (dotInd <= sepInd)
            return "";
        else
            return fileName.substring(dotInd + 1).toLowerCase();
    }

}