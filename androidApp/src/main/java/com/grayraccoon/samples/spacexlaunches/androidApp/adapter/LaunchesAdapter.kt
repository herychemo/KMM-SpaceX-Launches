package com.grayraccoon.samples.spacexlaunches.androidApp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.grayraccoon.samples.spacexlaunches.androidApp.R
import com.grayraccoon.samples.spacexlaunches.shared.entity.RocketLaunch

class LaunchesAdapter(
    private val items: List<RocketLaunch>
): RecyclerView.Adapter<LaunchesAdapter.LaunchViewHolder>() {

    private lateinit var ctx: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        if (!this::ctx.isInitialized) this.ctx = parent.context

        val itemView = LayoutInflater.from(ctx)
            .inflate(R.layout.item_launch, parent, false)
        return LaunchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        val item = items[position]

        holder.missionName.text = ctx.getString(R.string.mission_name_field, item.missionName)
        holder.launchYear.text = ctx.getString(R.string.launch_year_field, item.launchYear.toString())
        holder.launchDetails.text = ctx.getString(R.string.details_field, item.details  ?: "")

        when (item.launchSuccess) {
            null -> {
                holder.launchSuccess.text = ctx.getString(R.string.no_data)
                holder.launchSuccess.setTextColor(ContextCompat.getColor(ctx, R.color.colorNoData))
            }
            true -> {
                holder.launchSuccess.text = ctx.getString(R.string.successful)
                holder.launchSuccess.setTextColor(ContextCompat.getColor(ctx, R.color.colorSuccessful))
            }
            else -> {
                holder.launchSuccess.text = ctx.getString(R.string.unsuccessful)
                holder.launchSuccess.setTextColor(ContextCompat.getColor(ctx, R.color.colorUnsuccessful))
            }
        }

    }

    override fun getItemCount(): Int = this.items.size

    inner class LaunchViewHolder: RecyclerView.ViewHolder {
        val rootView: View

        val missionName: TextView
        val launchSuccess: TextView
        val launchYear: TextView
        val launchDetails: TextView

        constructor(itemView: View) : super(itemView) {
            rootView = itemView

            missionName = rootView.findViewById(R.id.mission_name)
            launchSuccess = rootView.findViewById(R.id.launch_success)
            launchYear = rootView.findViewById(R.id.launch_year)
            launchDetails = rootView.findViewById(R.id.details)
        }

    }

}