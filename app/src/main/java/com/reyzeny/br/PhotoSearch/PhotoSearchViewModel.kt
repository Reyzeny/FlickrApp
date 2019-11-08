package com.reyzeny.br.PhotoSearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reyzeny.br.Api.Api
import com.reyzeny.br.Api.Response.PhotoResponse
import com.reyzeny.br.PhotoRecyclerviewAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.searchable_layout.*

class PhotoSearchViewModel: ViewModel() {
    private val totalPage = MutableLiveData<Int>()
    private var query = ""
    val photoSearchAdapter = PhotoRecyclerviewAdapter()
    val pageInfo = MutableLiveData<String>()
    private val currentPage = MutableLiveData<Int>()

    init {
        currentPage.value = 1
    }

    fun searchForPhotoWithQuery(query: String): Disposable? {
        this.query = query
        return Api.getInstance().getPhotoSearchResultList(query, currentPage.value!!)
                ?.doOnError{ throwable ->

                }?.onErrorReturn { throwable ->
                    PhotoResponse()
                }
                 ?.subscribeOn(Schedulers.io())
                 ?.observeOn(AndroidSchedulers.mainThread())
                 ?.subscribe(object : Consumer<PhotoResponse> {
                     override fun accept(result: PhotoResponse) {
                         if (result.photos.photoList == null) return
                         photoSearchAdapter.setPhotoList(result.photos.photoList)
                         totalPage.value = result.photos.totalPages
                         pageInfo.value = "Page ${result.photos.page} of ${result.photos.totalPages}"
                     }
                 })
    }

    fun nextPage() {
        if (currentPage.value!! >= totalPage.value!!) return
        currentPage.value = currentPage.value!! + 1
        searchForPhotoWithQuery(query)
    }

    fun previousPage() {
        if (currentPage.value!! <= 1) return
        currentPage.value = currentPage.value!! - 1
        searchForPhotoWithQuery(query)
    }
}