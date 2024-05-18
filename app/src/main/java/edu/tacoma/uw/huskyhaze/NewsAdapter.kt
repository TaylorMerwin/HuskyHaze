package edu.tacoma.uw.huskyhaze

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.tacoma.uw.huskyhaze.models.NewsData

class NewsAdapter(
    private val newsList: List<NewsData.ArticleData>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(url: String)
    }

    private val filteredNewsList = newsList.filter { it.title != "[Removed]" }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.headline_list_items, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = filteredNewsList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return filteredNewsList.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val article = newsList[position]
                listener.onItemClick(article.url)
            }
        }

        private val titleTextView: TextView = itemView.findViewById(R.id.text_title)
        private val sourceTextView: TextView = itemView.findViewById(R.id.text_source)
        private val imageView: ImageView = itemView.findViewById(R.id.img_headline)

        fun bind(article: NewsData.ArticleData) {
            titleTextView.text = article.title
            sourceTextView.text = article.author

            if (article.urlToImage != null) {
                Picasso.get().load(article.urlToImage).into(imageView)
            }
            else {
                Picasso.get().load(R.drawable.not_available).into(imageView)
            }
        }
    }
}