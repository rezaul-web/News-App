package com.example.dailynews.model

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        sourceId = this.source?.id,
        sourceName = this.source?.name,
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content
    )
}

fun ArticleEntity.toDomain(): Article {
    return Article(
        source = Source(this.sourceId, this.sourceName),
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content
    )
}
