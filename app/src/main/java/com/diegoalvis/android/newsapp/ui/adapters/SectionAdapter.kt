package com.diegoalvis.android.newsapp.ui.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.diegoalvis.android.newsapp.R
import com.diegoalvis.android.newsapp.databinding.ItemSectionBinding
import com.diegoalvis.android.newsapp.utils.inflate
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by diegoalvis on 9/13/17.
 */

class SectionAdapter(sections: Array<String>): RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    val sectionSelected = PublishSubject.create<String>()

    var items: Array<String> = sections
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.binding.sectionName = items[position]
        holder.binding.selected = sectionSelected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder =
        SectionViewHolder(parent.inflate(R.layout.item_section))

    override fun getItemCount(): Int = items.size

    fun sectionSelected(): Observable<String> = sectionSelected

    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemSectionBinding = DataBindingUtil.bind(itemView)
    }
}