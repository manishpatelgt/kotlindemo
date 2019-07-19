package com.kotlindemo.activity.otherthings.shimmer.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlindemo.R
import com.kotlindemo.activity.architecture.databinding2.picasso
import com.kotlindemo.activity.otherthings.shimmer.models.Recipe
import com.kotlindemo.utility.inflate
import kotlinx.android.synthetic.main.recipe_list_item.view.*

/**
 * Created by Manish Patel on 7/19/2019.
 */
class RecipeAdapter(private var recipes: List<Recipe>, val mContext: Context) :
    RecyclerView.Adapter<RecipeAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyViewHolder {
        val view = parent.inflate(R.layout.recipe_list_item, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recipe = recipes[position]
        mContext.picasso.load(recipe.thumbnail)
            .fit()
            .centerCrop()
            .into(holder.thumbnail)

        holder.name.text = recipe.name
        holder.description.text = recipe.description
        holder.timestamp.text = recipe.timestamp

        holder.price.text = "Price: ₹" + recipe.price
        holder.chef.text = "By " + recipe.chef
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = recipes.size

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val thumbnail = v.thumbnail
        val name = v.name
        val chef = v.chef
        val timestamp = v.timestamp
        val description = v.description
        val price = v.price
    }

}