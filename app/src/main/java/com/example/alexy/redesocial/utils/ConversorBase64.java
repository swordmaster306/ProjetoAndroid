package com.example.alexy.redesocial.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ConversorBase64 {

    public static final ConversorBase64 INSTANCE = new ConversorBase64();

    private ConversorBase64(){ }

    public static Bitmap b64tobitmap(String base64){
        byte[] decodedImage = Base64.decode(base64,Base64.DEFAULT);
        Bitmap retorno = BitmapFactory.decodeByteArray(decodedImage,0,decodedImage.length);

        return retorno;
    }

    public static String bitmaptob64(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

}
