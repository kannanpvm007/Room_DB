package com.blogspot.roomdb.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.roomdb.R
import com.blogspot.roomdb.dbUtils.UserEntity
import com.blogspot.roomdb.ui.list.ListFragmentDirections

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var user = emptyList<UserEntity>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userId: TextView = itemView.findViewById(R.id.user_id)
        var fistName: TextView = itemView.findViewById(R.id.fristName)
        var lastName: TextView = itemView.findViewById(R.id.lastName)
        var age: TextView = itemView.findViewById(R.id.age)
        var totalLayout: ConstraintLayout = itemView.findViewById(R.id.total_view)
        var addressLayout: LinearLayout = itemView.findViewById(R.id.addressLayout)
        var street: TextView = itemView.findViewById(R.id.street)
        var zipcode: TextView = itemView.findViewById(R.id.zipCode)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = user[position]

        Log.d("system", "onBindViewHolder: read---> "+currentUser)

        holder.userId.text = currentUser.id.toString()
        holder.fistName.text = currentUser.fristName
        holder.lastName.text = currentUser.lastName
        holder.age.text = currentUser.age.toString()
        if (currentUser.address !=null){
            holder.addressLayout.visibility=View.VISIBLE
            holder.street.text=currentUser.address.street
            holder.zipcode.text=currentUser.address.zipCode
        }else{
            holder.addressLayout.visibility=View.GONE
        }
        holder.totalLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToAddFragment(currentUser)
            holder.itemView.findNavController().navigate(action)

        }


    }

    override fun getItemCount() = user.size

    fun setData(user: List<UserEntity>) {
        this.user = user
        Log.d("system", "setData: read---> "+user.size)
        notifyDataSetChanged()
    }
}