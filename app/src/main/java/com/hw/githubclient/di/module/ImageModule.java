package com.hw.githubclient.di.module;

import android.widget.ImageView;

import com.hw.githubclient.mvp.view.image.GlideImageLoader;
import com.hw.githubclient.mvp.view.image.IImageLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageModule {
    @Singleton
    @Provides
    IImageLoader<ImageView> getImageLoader() {
        return new GlideImageLoader();
    }
}
