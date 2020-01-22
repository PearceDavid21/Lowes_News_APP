package com.example.lowesapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lowesapi.R
import com.example.lowesapi.databinding.ItemLayoutBinding
import com.example.lowesapi.model.Article
import com.example.lowesapi.viewmodel.ArticleViewModel

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>(){
    private var items: List<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding: ItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.activity_main, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNewsList(items: List<Article>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ArticleViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private val characterViewModel = ArticleViewModel()

        fun bind(character: Article) {
            characterViewModel.bind(character)
            binding.viewmodel = characterViewModel
        }
    }
}