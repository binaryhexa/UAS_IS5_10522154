package com.example.uasis510522154

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataContainer(
    val pegawai: ArrayList<ReadModel.Data>,
    val listener: OnAdapterListener
) : RecyclerView.Adapter<DataContainer.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_data, parent, false)
        )

    override fun onBindViewHolder(
        holder: DataContainer.ViewHolder,
        position: Int
    ) {
        val data = pegawai[position]
        holder.tvName.text = data.nama_lengkap
        holder.tvJabatan.text = data.jabatan
        holder.itemView.setOnClickListener {
            listener.onUpdate(data)
        }
        holder.imgDelete.setOnClickListener {
            listener.onDelete(data)
        }
        holder.imgDetail.setOnClickListener {
            listener.onDetail(data)
        }
    }

    override fun getItemCount() = pegawai.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.TextViewName)
        val tvJabatan = view.findViewById<TextView>(R.id.TextViewJabatan)
        val imgDelete = view.findViewById<ImageView>(R.id.ImageViewDelete)
        val imgDetail = view.findViewById<ImageView>(R.id.ImageViewDetail)
    }

    fun setData(data: List<ReadModel.Data>) {
        pegawai.clear()
        pegawai.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onUpdate(contact: ReadModel.Data)
        fun onDelete(contact: ReadModel.Data)
        fun onDetail(contact: ReadModel.Data)
    }
}
