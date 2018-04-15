package com.kratochvil.kotobaten.viewmodel.infrastructure

import android.content.Context
import android.view.LayoutInflater
import android.widget.BaseAdapter

abstract class SimpleAdapterBase<T: Any>(
        context: Context,
        protected val items: List<T>)
    : BaseAdapter() {

    var inflater: LayoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun getItemTyped(position: Int): T {
        return items[position]
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.count()
    }

}