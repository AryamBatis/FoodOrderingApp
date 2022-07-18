package com.example.foodorderingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.R
import com.example.foodorderingapp.models.Hours
import com.example.foodorderingapp.models.RestuarantModel
import java.text.SimpleDateFormat
import java.util.*

class RestuarantListAdapter (val restuarantList: List<RestuarantModel?>?, val clickListener: RestuarantListClickListener): RecyclerView.Adapter<RestuarantListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder
                (parent: ViewGroup, viewType: Int
    ):RestuarantListAdapter.MyViewHolder {
       val view:View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_restuarant_list_row, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestuarantListAdapter.MyViewHolder, position: Int) {
      holder.bind(restuarantList?.get(position))
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(restuarantList?.get(position)!!)
        }
    }

    override fun getItemCount(): Int {
      return restuarantList?.size!!
    }

    inner class MyViewHolder (view: View): RecyclerView.ViewHolder(view)
    {

        val thumbImage: ImageView = view.findViewById(R.id.thumbImage)
        val tvRestuarantName: TextView = view.findViewById(R.id.tvRestuarantName)
        val tvRestuarantAddress: TextView = view.findViewById(R.id.tvRestuarantAddress)
        val tvRestuarantHours: TextView = view.findViewById(R.id.tvRestuarantHours)


        fun bind(restuarantModel: RestuarantModel?){
            tvRestuarantName.text = restuarantModel?.name
            tvRestuarantAddress.text = "Address: "+restuarantModel?.address
            tvRestuarantHours.text = "Today's Hours: "+ getTodaysHours(restuarantModel?.hours!!)

            Glide.with(thumbImage)
                .load(restuarantModel?.image)
                .into(thumbImage)
        }
    }

    private fun getTodaysHours(hours: Hours): String? {
        val calender: Calendar = Calendar.getInstance()
        val date: Date = calender.time
        val day: String = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)
        return when(day) {
            "Sunday" -> hours.Sunday
            "Monday" -> hours.Monday
            "Tuesday" -> hours.Tuesday
            "Wednesday" -> hours.Wednesday
            "Thursday" -> hours.Thursday
            "Friday" -> hours.Friday
            "Saturday" -> hours.Saturday
            else -> hours.Sunday
        }
    }

    interface RestuarantListClickListener{
        fun onItemClick(restuarantModel: RestuarantModel)
    }

}


