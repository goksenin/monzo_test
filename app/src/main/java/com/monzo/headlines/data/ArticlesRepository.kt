package com.monzo.headlines.data

import com.monzo.headlines.data.model.Article
import com.monzo.headlines.data.model.toDomain
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ArticlesRepository(
    private val guardianService: GuardianService,
) {

    fun fintechArticles(): Single<List<Article>> {
        return guardianService.searchArticles(searchTerm = "fintech,brexit")
            .map { it.response.results.map { article -> article.toDomain() } }
            .subscribeOn(Schedulers.io())
    }
}
