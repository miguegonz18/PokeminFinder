package com.bytemain.pokemonfinder.ui.pokemon.types

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bytemain.pokemonfinder.R
import com.bytemain.pokemonfinder.helper.LoadImage
import com.bytemain.pokemonfinder.models.Type
import kotlinx.android.synthetic.main.fragment_type.view.*

/**
 * @author rorogarcete
 * @version 0.0.1
 * Adapter of the [TypeFragment]
 * Copyright 2019 Bytemain Inc. All rights reserved
 */
class TypeAdapter(private val mListener: TypeFragment.onClickListener?):
    RecyclerView.Adapter<TypeAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var types: List<Type> = emptyList()

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Type
            mListener?.onClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_type, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = types[position]
        holder.txtType.text = item.name.capitalize()

        LoadImage.setImageUrl(holder.imgType, item.thumbnailImage)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = types.size

    fun setList(types: List<Type>) {
        this.types = mutableListOf()
        this.types = types
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View): RecyclerView.ViewHolder(mView) {
        val txtType: TextView = mView.txt_name_type
        val imgType: ImageView = mView.img_type_pokemon
    }
}
