package com.reyzeny.br.PhotoSearch

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.reyzeny.br.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.searchable_layout.*


class PhotoSearchActivity : AppCompatActivity() {
    private var photoSearchViewModel: PhotoSearchViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchable_layout)
        photoSearchViewModel = ViewModelProviders.of(this)[PhotoSearchViewModel::class.java]
        setupUI()
    }

    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        constraint_page_info.visibility = View.INVISIBLE
        searchable_recyclerview.layoutManager = LinearLayoutManager(this)
        searchable_recyclerview.adapter = photoSearchViewModel?.photoSearchAdapter
        imagebutton_arrow_right.setOnClickListener { photoSearchViewModel?.nextPage() }
        imagebutton_arrow_left.setOnClickListener { photoSearchViewModel?.previousPage() }
        photoSearchViewModel?.pageInfo?.observe(this, Observer<String> { pageInfo->
            constraint_page_info.visibility = View.VISIBLE
            text_page_number.text = pageInfo
        })
    }

    @SuppressLint("CheckResult")
    private fun setupSearch(menu: Menu?) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.onActionViewExpanded()
        searchView.setOnCloseListener {
            finish()
            true
        }
        RxSearchObservable.fromView(searchView)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .filter { text ->
                    return@filter text.isNotEmpty()
                }
                .distinctUntilChanged()
                .doOnNext{query->
                    photoSearchViewModel?.searchForPhotoWithQuery(query)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        setupSearch(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == android.R.id.home) finish()
        return true
    }
}
