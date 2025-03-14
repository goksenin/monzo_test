package com.monzo.headlines.data.model


/**
 * Models returned by API when fetching a list of articles
 */

data class ApiArticle(
    val id: String,
    val webPublicationDate: String,
    val apiUrl: String,
    val fields: ApiArticleFields?
)

data class ApiArticleFields(
    val headline: String?,
    val main: String?,
    val body: String?,
    val thumbnail: String?
)

data class ApiArticleListResponse(val response: ApiArticleList)

data class ApiArticleList(val results: List<ApiArticle>)
