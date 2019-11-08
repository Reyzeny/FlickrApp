package com.reyzeny.br.Home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.reyzeny.br.Api.Response.PhotoResponse;
import com.reyzeny.br.PhotoRecyclerviewAdapter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class MainActivityViewModel extends ViewModel {
    private MainActivityRepository repository = new MainActivityRepository();   //Will be replaced with dependency injection
    MutableLiveData<Boolean> isProgressVisible = new MutableLiveData<>();
    PhotoRecyclerviewAdapter adapter = new PhotoRecyclerviewAdapter();


    public void fetchPhotos() {
        isProgressVisible.setValue(true);
        repository.LoadPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<PhotoResponse>() {
                    @Override
                    public void onNext(PhotoResponse photoResponse) {
                        isProgressVisible.setValue(false);
                        if (!photoResponse.stat.equalsIgnoreCase("ok")) return;
                        adapter.setPhotoList(photoResponse.photos.photoList);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



}
