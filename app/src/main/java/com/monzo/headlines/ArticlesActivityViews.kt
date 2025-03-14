package com.monzo.headlines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.monzo.headlines.common.AppModule
import com.monzo.headlines.data.model.Article
import java.time.LocalDateTime

class ArticlesActivityViews : AppCompatActivity() {
    private val viewModel by lazy { AppModule.articlesViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles_views)

        val recyclerView = findViewById<RecyclerView>(R.id.articlesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        val adapter = ArticlesAdapter().also { recyclerView.adapter = it }

        viewModel.state.observe(this) {
            adapter.setArticles(it.articles)
        }
    }
}


class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    private val articles = mutableListOf<Article>()

    fun setArticles(articles: List<Article>) {
        val list = listOf<Article>(
            Article(
                "val id : String",
            "val thumbnail : String",
        LocalDateTime.now(),
        "val title: String",
       " val url: String"
        ))
        this.articles.clear()
        this.articles.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_article, parent, false)
        )
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    class ArticleViewHolder(view: View) : ViewHolder(view) {

        private val imageView = view.findViewById<ImageView>(R.id.article_image)
        private val titleTextView = view.findViewById<TextView>(R.id.article_title)
        private val subtitleTextView = view.findViewById<TextView>(R.id.article_subtitle)

        fun bind(article: Article) {
         //   imageView.load(article.thumbnail)
            titleTextView.text = "article.title"
            subtitleTextView.text = "article.published.toString()"
        }
    }
}

