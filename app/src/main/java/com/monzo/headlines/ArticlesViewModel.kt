package com.monzo.headlines

import android.util.Log
import com.monzo.headlines.common.BaseViewModel
import com.monzo.headlines.common.plusAssign
import com.monzo.headlines.data.ArticlesRepository
import com.monzo.headlines.data.model.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class ArticlesViewModel(
    private val repository: ArticlesRepository
) : BaseViewModel<ArticlesState>(ArticlesState()) {
    init {
        loadData()
    }

    private fun loadData() {
        disposables += repository.fintechArticles()
            .delay(2, TimeUnit.SECONDS) // Added so it's easier to test loading indicator
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { articles ->
                    setState { copy(articles = articles) }
                },
                {
                    Log.e("ArticlesViewModel", "Error fetching articles", it)
                }
            )
    }
}

data class ArticlesState(
    val articles: List<Article> = emptyList()
)
