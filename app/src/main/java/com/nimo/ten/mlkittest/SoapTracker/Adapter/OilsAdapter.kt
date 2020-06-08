package com.nimo.ten.mlkittest.SoapTracker.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapLyeLiquidsPojo

class OilsAdapter(private val context: Context, private val oilList: ArrayList<SoapLyeLiquidsPojo>)
    :RecyclerView.Adapter<OilsAdapter.OilsViewHolder>(){


    class OilsViewHolder(inflater: LayoutInflater, parent:ViewGroup):
            RecyclerView.ViewHolder(inflater.inflate(R.layout.oils_list_choose, parent, false)){

        private var checkbox : CheckBox? =null
        private var tvOilName : TextView? =null

        init {

            checkbox = itemView.findViewById(R.id.checkbox)
            tvOilName = itemView.findViewById(R.id.tvOilName)

        }

        fun Assign(soapLyeLiquidsPojo: SoapLyeLiquidsPojo, context: Context, position: Int){

            val oilName : String = soapLyeLiquidsPojo.liquids
            tvOilName!!.text = oilName
//            checkbox!!.text = oilName

            itemView.setOnClickListener {

                checkbox!!.isEnabled = true

                println("-*-*-*-*")
                println(position)

            }
        }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OilsViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return OilsViewHolder(inflater, parent)
    }

    override fun getItemCount() = oilList.size

    override fun onBindViewHolder(holder: OilsViewHolder, position: Int) {

        val oils : SoapLyeLiquidsPojo = oilList[position]
        holder.Assign(oils, context, position)




    }

}