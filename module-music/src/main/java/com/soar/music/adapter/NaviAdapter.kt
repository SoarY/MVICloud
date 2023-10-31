package com.soar.music.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.soar.music.R
import com.soar.music.databinding.ItemAndroidPlayBinding
import com.soar.music.databinding.ItemNaviBinding
import com.soar.network.bean.response.NaviBean

class NaviAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listener: OnSelectListener? = null

    private var dataList: List<NaviBean>? = null

    fun setDataList(dataList: List<NaviBean>?) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    fun getItem(position: Int): NaviBean? {
        return dataList?.get(position)
    }

    override fun getItemCount(): Int {
        return if (dataList == null) 0 else dataList!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val itemBinding = ItemNaviBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (dataList==null)
            return
        val bindHolder = holder as MViewHolder
        val item = dataList!![position]

        bindHolder.itemBinding.tvTitle.isSelected = item.selected
        bindHolder.itemBinding.tvTitle.text = item.name
        bindHolder.itemBinding.tvTitle.setOnClickListener { v ->
            setSelected(position)
            if (listener != null)
                listener!!.onSelected(position)
        }
    }

    fun setSelected(position: Int) {
        for (i in 0 until dataList!!.size)
            dataList!![i].selected = i == position
        notifyDataSetChanged()
    }

    fun setOnSelectListener(listener: OnSelectListener?) {
        this.listener = listener
    }

    fun interface OnSelectListener {
        fun onSelected(position: Int)
    }

    class MViewHolder(val itemBinding: ItemNaviBinding) : RecyclerView.ViewHolder(itemBinding.root)
}

