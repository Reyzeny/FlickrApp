package com.reyzeny.br.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reyzeny.br.Api.Response.PhotoResponse;
import com.reyzeny.br.BuildConfig;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static Api INSTANCE;
    private FlickrApiRoutes flickrApi;
    private static Retrofit retrofit;

    private Api() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        retrofit = new Retrofit.Builder()
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .baseUrl(BuildConfig.BASE_URL)
                                .client(okHttpClient)
                                .build();
        flickrApi = retrofit.create(FlickrApiRoutes.class);
    }

    public static Api getInstance() {
        if (INSTANCE==null)
            INSTANCE = new Api();
        System.out.println("base url  is " + retrofit.baseUrl().toString());
        return INSTANCE;
    }

    public Flowable<PhotoResponse> getPhotoList() {
        return flickrApi.getPhotos(FlickrApiRoutes.METHOD, BuildConfig.API_KEY, FlickrApiRoutes.GALLERY_ID, FlickrApiRoutes.RESPONSE_FORMAT, FlickrApiRoutes.NO_JSON_CALL_BACK);
    }
    public Observable<PhotoResponse> getPhotoSearchResultList(String searchQuery, int page) {
        return flickrApi.searchPhoto(FlickrApiRoutes.FLICKR_SEARCH_METHOD, BuildConfig.API_KEY, FlickrApiRoutes.RESPONSE_FORMAT, FlickrApiRoutes.NO_JSON_CALL_BACK, searchQuery, FlickrApiRoutes.PHOTOS_PER_PAGE, page);
    }

}
