package com.soar.music.adapter

import android.content.Context
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soar.common.util.CommonUtils
import com.soar.common.util.ToastUtils
import com.soar.music.R
import com.soar.music.databinding.ItemNaviBinding
import com.soar.music.databinding.ItemNaviContentBinding
import com.soar.network.bean.response.ArticlesBean
import com.soar.network.bean.response.NaviBean
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

class NaviContentAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context

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
        context = parent.context

        val itemBinding = ItemNaviContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (dataList==null)
            return
        val bindHolder = holder as MViewHolder
        val item = dataList!![position]

        showTagView(bindHolder.itemBinding.tflContent, item.articles)
    }

    private fun showTagView(tflContent: TagFlowLayout, articles: List<ArticlesBean>?) {
        tflContent.adapter=object : TagAdapter<ArticlesBean>(articles){
            override fun getView(parent: FlowLayout?, position: Int, t: ArticlesBean): View {
                val textView: TextView = getTextView()
                textView.text = Html.fromHtml(t.title)
                return textView
            }
        }

        tflContent.setOnTagClickListener{ v: View, position:Int, parent: FlowLayout ->
            ToastUtils.showToast(articles!![position].title)
            true
        }
    }

    private fun getTextView(): TextView {
        val hotText = TextView(context)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        hotText.layoutParams = lp
        hotText.textSize = 13f
        val left: Int
        val top: Int
        val right: Int
        val bottom: Int
        hotText.maxLines = 1
        bottom = CommonUtils.dip2px(5f)
        right = bottom
        top = right
        left = top
        hotText.setBackgroundResource(R.drawable.shape_navi_tag)
        hotText.gravity = Gravity.CENTER
        lp.setMargins(left, top, right, bottom)
        return hotText
    }

    class MViewHolder(val itemBinding: ItemNaviContentBinding) : RecyclerView.ViewHolder(itemBinding.root)
}

