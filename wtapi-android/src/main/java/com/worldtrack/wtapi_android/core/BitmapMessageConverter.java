package com.worldtrack.wtapi_android.core;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BitmapMessageConverter implements HttpMessageConverter<Bitmap> {

    private static final int BUFFER_SIZE = 8 * 1024;
    private List<MediaType> imageMediaTypes;

    public BitmapMessageConverter() {
        imageMediaTypes = new ArrayList<MediaType>();
        imageMediaTypes.add(new MediaType("image", "*"));
        imageMediaTypes.add(new MediaType("image", "png"));
        imageMediaTypes.add(new MediaType("image", "jpeg"));
    }


    private boolean isRegisteredMediaType(MediaType mediaType) {
        return imageMediaTypes.contains(mediaType);
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return true;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return true;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return imageMediaTypes;
    }

    @Override
    public Bitmap read(Class<? extends Bitmap> classArg, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        BufferedInputStream bis = new BufferedInputStream(inputMessage.getBody(), BUFFER_SIZE);
        Bitmap result = BitmapFactory.decodeStream(bis);
        return result;
    }

    @Override
    public void write(Bitmap bitmap, MediaType mediaType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        BufferedOutputStream bos = new BufferedOutputStream(outputMessage.getBody(), BUFFER_SIZE);
        bitmap.compress(CompressFormat.JPEG, 100, bos);
    }
}
