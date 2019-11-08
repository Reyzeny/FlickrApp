package com.reyzeny.br.PhotoSearch

import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxSearchObservable {
    companion object {
        fun fromView(searchView: SearchView): Observable<String> {
            val subject: PublishSubject<String> = PublishSubject.create()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    println("You finished typing $query")
                    subject.onComplete()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    println("You typed $newText")
                    subject.onNext(newText!!)
                    return true
                }
            })
            return subject
        }
    }
}