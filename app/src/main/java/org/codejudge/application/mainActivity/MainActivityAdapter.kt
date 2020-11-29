package org.codejudge.application.mainActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.codejudge.application.R
import org.codejudge.application.databinding.BindingLayoutItems
import org.codejudge.application.model.Result
class MainActivityAdapter(val event:MainActivityClickListener): ListAdapter<Result, MainActivityAdapter.MyViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.main_items_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.initView(getItem(position),event)
    }

        inner class MyViewHolder(val bind:BindingLayoutItems): RecyclerView.ViewHolder(bind.root){
            fun initView(item: Result,listener: MainActivityClickListener) {
                bind.setVariable(BR.list,item)
                bind.executePendingBindings()
                bind.container.setOnClickListener {
                    listener.onClick(item)
                }
            }
        }

        class DiffCallback: DiffUtil.ItemCallback<Result>(){
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

        }



}