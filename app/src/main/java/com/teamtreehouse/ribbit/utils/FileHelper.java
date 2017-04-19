package com.teamtreehouse.ribbit.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.teamtreehouse.ribbit.models.purgatory.Message;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class FileHelper {

    public static final String TAG = FileHelper.class.getSimpleName();

    public static final int SHORT_SIDE_TARGET = 1280;

    public static byte[] getByteArrayFromFile(Context context, Uri uri) {
        byte[] fileBytes = null;
        InputStream inStream = null;
        ByteArrayOutputStream outStream = null;

        if (uri.getScheme().equals("content")) {
            try {
                inStream = context.getContentResolver().openInputStream(uri);
                outStream = new ByteArrayOutputStream();

                byte[] bytesFromFile = new byte[1024 * 1024]; // buffer size (1 MB)
                int bytesRead = inStream.read(bytesFromFile);
                while (bytesRead != -1) {
                    outStream.write(bytesFromFile, 0, bytesRead);
                    bytesRead = inStream.read(bytesFromFile);
                }

                fileBytes = outStream.toByteArray();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } finally {
                try {
                    inStream.close();
                    outStream.close();
                } catch (IOException e) { /*( Intentionally blank */ }
            }
        } else {
            try {
                java.net.URI tempUri = new URI(uri.toString());
                fileBytes = IOUtils.toByteArray(tempUri);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            catch (URISyntaxException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return fileBytes;
    }

    public static byte[] reduceImageForUpload(byte[] imageData) {
        Bitmap bitmap = ImageResizer.resizeImageMaintainAspectRatio(imageData, SHORT_SIDE_TARGET);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] reducedData = outputStream.toByteArray();
        try {
            outputStream.close();
        } catch (IOException e) {
            // Intentionally blank
        }

        return reducedData;
    }

    public static String getFileName(Context context, Uri uri, String fileType) {
        String fileName = "uploaded_file.";

        if (fileType.equals(Message.TYPE_IMAGE)) {
            fileName += "png";
        } else {
            // For video, we want to getValue the actual file extension
            if (uri.getScheme().equals("content")) {
                // do it using the mime type
                String mimeType = context.getContentResolver().getType(uri);
                int slashIndex = mimeType.indexOf("/");
                String fileExtension = mimeType.substring(slashIndex + 1);
                fileName += fileExtension;
            } else {
                fileName = uri.getLastPathSegment();
            }
        }

        return fileName;
    }

    public static Uri resizeUri(Context context, Uri originalUri) {

        byte[] data = FileHelper.getByteArrayFromFile(context, originalUri);
        byte[] newData = FileHelper.reduceImageForUpload(data);

        String path = getPath(newData, context);
        return Uri.parse(path);
    }

    public static String getPath(byte[] data, Context context) {

        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "temp", null);
    }

    public static String getPath(Bitmap bitmap, Context context){

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "temp", null);
    }
}