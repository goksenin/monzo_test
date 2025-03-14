package com.monzo.headlines.data

import com.monzo.headlines.data.model.ApiArticleListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GuardianService {
    @GET("search?show-fields=headline,thumbnail&page-size=50")
    fun searchArticles(@Query("q") searchTerm: String): Single<ApiArticleListResponse>
}
