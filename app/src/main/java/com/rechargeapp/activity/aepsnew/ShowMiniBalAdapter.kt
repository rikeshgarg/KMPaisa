package com.rechargeapp.activity.aepsnew

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codunite.rechargeapp.databinding.MiniBalBinding

class ShowMiniBalAdapter(
    private var list: List<MiniBalDataModel>,
    private var isMiniBal: Boolean,
    private val clickListener: (MiniBalDataModel) -> Unit
) :
        RecyclerView.Adapter<MiniBalHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniBalHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MiniBalBinding.inflate(inflater, parent, false)
        return MiniBalHolder(binding)
    }

    override fun onBindViewHolder(holder: MiniBalHolder, position: Int) {
        (holder).bind(list.get(position), isMiniBal, clickListener)
    }

    override fun getItemCount(): Int = list.size
    fun filterList(filterdNames: ArrayList<MiniBalDataModel>) {
        this.list = filterdNames
        notifyDataSetChanged()
    }
}

class MiniBalHolder(private val binding: MiniBalBinding) :
        RecyclerView.ViewHolder(binding.root) {
    fun bind(part: MiniBalDataModel, isMiniBal: Boolean, clickListener: (MiniBalDataModel) -> Unit) {
        if(isMiniBal){
            binding.txtDate.text = "Date: "+ part.date
            binding.txtCrdr.text = "CR/DR: "+ part.crdr
            binding.txtAmount.text = "Amount: "+ part.amount
            binding.txtDesc.text = "Description: "+ part.desc
        }else{
            binding.txtDate.text = "Trascation Status: "+ part.date
            binding.txtCrdr.text = "Amount: "+ part.crdr
            binding.txtAmount.text = "Balance Amount: "+ part.amount
            binding.txtDesc.text = "Bank RRN: "+ part.desc
        }

        binding.root.setOnClickListener { clickListener(part) }
    }
}