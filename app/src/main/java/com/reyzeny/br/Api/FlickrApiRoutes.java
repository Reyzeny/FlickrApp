package com.reyzeny.br.Api;

import com.reyzeny.br.Api.Response.PhotoResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApiRoutes {
    String METHOD = "flickr.galleries.getPhotos";
    String FLICKR_SEARCH_METHOD = "flickr.photos.search";
    String GALLERY_ID = "66911286-72157647277042064";
    String RESPONSE_FORMAT = "json";
    int NO_JSON_CALL_BACK = 1;
    int PHOTOS_PER_PAGE = 25;

    @GET("rest/")
    Flowable<PhotoResponse> getPhotos(@Query("method") String method, @Query("api_key") String api_key, @Query("gallery_id") String gallery_id, @Query("format") String format, @Query("nojsoncallback") int nojsoncallback);

    @GET("rest/")
    Observable<PhotoResponse> searchPhoto(@Query("method") String method, @Query("api_key") String api_key, @Query("format") String format, @Query("nojsoncallback") int nojsoncallback, @Query("text") String searchText, @Query("per_page") int per_page, @Query("page") int page);

}