package com.monzo.headlines.data.model

import java.time.ZonedDateTime

/**
 * Maps an API article model to a domain model
 */
fun ApiArticle.toDomain() = Article(
    id = id,
    url = apiUrl,
    thumbnail = fields?.thumbnail.orEmpty(),
    published = ZonedDateTime.parse(webPublicationDate).toLocalDateTime(),
    title = fields?.headline.orEmpty()
)