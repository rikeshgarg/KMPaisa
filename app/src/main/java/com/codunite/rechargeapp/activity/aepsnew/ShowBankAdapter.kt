package com.codunite.rechargeapp.activity.aepsnew

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codunite.rechargeapp.databinding.BankNameBinding

class ShowBankAdapter(
    private var list: List<FinoBankDataModel>,
    private val clickListener: (FinoBankDataModel) -> Unit
) :
        RecyclerView.Adapter<BankNameHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankNameHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BankNameBinding.inflate(inflater, parent, false)
        return BankNameHolder(binding)
    }

    override fun onBindViewHolder(holder: BankNameHolder, position: Int) {
        (holder).bind(list.get(position), clickListener)
    }

    override fun getItemCount(): Int = list.size
    fun filterList(filterdNames: ArrayList<FinoBankDataModel>) {
        this.list = filterdNames
        notifyDataSetChanged()
    }
}

class BankNameHolder(private val binding: BankNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
    fun bind(part: FinoBankDataModel, clickListener: (FinoBankDataModel) -> Unit) {
        binding.txtBankname.text = part.itemName

        binding.root.setOnClickListener { clickListener(part) }
    }
}