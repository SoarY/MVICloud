package com.soar.music.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.soar.music.R
import com.soar.music.databinding.ItemAndroidPlayBinding
import com.soar.network.bean.response.ArticlesBean

class AndroidPlayAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList: List<ArticlesBean>? = null


    private var itemClickListener: ItemClickListener? = null


    fun setDataList(dataList: List<ArticlesBean>?) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    fun getItem(position: Int): ArticlesBean? {
        return dataList?.get(position)
    }

    override fun getItemCount(): Int {
        return if (dataList == null) 0 else dataList!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val itemBinding = ItemAndroidPlayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (dataList==null)
            return
        val bindHolder = holder as MViewHolder
        val item = dataList!![position]

        bindHolder.itemBinding.ivNew.visibility=if (item.isFresh) View.VISIBLE else View.GONE
        bindHolder.itemBinding.tvClassify.text=item.chapterName
        bindHolder.itemBinding.tvTitle.text=item.title
        bindHolder.itemBinding.textView2.text=item.niceDate
        bindHolder.itemBinding.textView3.text=String.format(bindHolder.itemBinding.textView3.context.getString(
            R.string.string_dot),item.author)

        val options = RequestOptions()
            .placeholder(com.soar.commonres.R.mipmap.ic_place_welfare)
            .error(com.soar.commonres.R.mipmap.ic_place_welfare)
        Glide.with(bindHolder.itemBinding.ivImage.context)
            .load(item.envelopePic)
            .apply(options)
            .into(bindHolder.itemBinding.ivImage)


    }

    fun interface ItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener?) {
        this.itemClickListener = itemClickListener
    }

    class MViewHolder(val itemBinding: ItemAndroidPlayBinding) : RecyclerView.ViewHolder(itemBinding.root)
}

