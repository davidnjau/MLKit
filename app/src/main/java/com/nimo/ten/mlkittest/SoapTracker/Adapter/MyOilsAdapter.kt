package com.nimo.ten.mlkittest.SoapTracker.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapLyeLiquidsPojo

class MyOilsAdapter(private val context: Context, private var oilList: ArrayList<SoapLyeLiquidsPojo>)
    :RecyclerView.Adapter<MyOilsAdapter.OilsViewHolder>(){


    class OilsViewHolder(inflater: LayoutInflater, parent:ViewGroup):
            RecyclerView.ViewHolder(inflater.inflate(R.layout.oil_list, parent, false)){

        private var tvOilName : TextView? =null
        private var delete : ImageButton? =null


        init {

            tvOilName = itemView.findViewById(R.id.tvOilName)
            delete = itemView.findViewById(R.id.delete)


        }

        fun Assign(soapLyeLiquidsPojo: SoapLyeLiquidsPojo, context: Context, position: Int){

            val oilName : String = soapLyeLiquidsPojo.liquids
            tvOilName!!.text = oilName

            delete!!.setOnClickListener {

                ShowDialog(oilName, context)

            }
        }



        private fun ShowDialog(oilName: String, context: Context) {

            var databaseHelper = DatabaseHelper(context)

            val builder = AlertDialog.Builder(context)
            //set title for alert dialog
            builder.setTitle("Delete Confirmation")
            //set message for alert dialog
            builder.setMessage("This will delete the oil from your selection")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton("Yes"){dialogInterface, which ->

                databaseHelper.deleteMySoap(oilName, context)
                Toast.makeText(context, "Oil has been deleted.. $oilName",Toast.LENGTH_LONG).show()

            }

            //performing negative action
            builder.setNegativeButton("No"){dialogInterface, which ->

                dialogInterface.cancel()
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
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