package com.reyzeny.br.Home;

import android.app.Application;

import com.reyzeny.br.Api.Api;
import com.reyzeny.br.Api.Response.PhotoResponse;

import io.reactivex.Flowable;

public class MainActivityRepository {

    Flowable<PhotoResponse> LoadPhotos() {
        return Api.getInstance().getPhotoList();
    }
}
