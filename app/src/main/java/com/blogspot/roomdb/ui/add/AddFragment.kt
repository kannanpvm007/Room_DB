package com.blogspot.roomdb.ui.add

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.blogspot.roomdb.R
import com.blogspot.roomdb.dbUtils.UserEntity
import com.blogspot.roomdb.ui.UserViewModel


class AddFragment : Fragment() {
    private val args by navArgs<AddFragmentArgs>()

    private var type = "add";
    private var userId = 0;
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        if (args != null && args.userDetilas?.id != null) {
            type = "update"

            view.findViewById<TextView>(R.id.editTextTextPersonName).text =
                args.userDetilas?.fristName
            view.findViewById<TextView>(R.id.editTextTextPersonName2).text =
                args.userDetilas?.lastName.toString().trim()
            view.findViewById<TextView>(R.id.editTextNumber).text =
                args.userDetilas?.age.toString().trim()
            if ( args.userDetilas?.id != null){
                userId= args.userDetilas?.id!!}

        } else {
            type = "add";

        }
        if (type.equals("add")){
            view.findViewById<Button>(R.id.button_delete).visibility=View.GONE
        }

        view.findViewById<Button>(R.id.button)
            .setOnClickListener {
                val fristName =
                    view.findViewById<TextView>(R.id.editTextTextPersonName).text.toString().trim()
                val lastname =
                    view.findViewById<TextView>(R.id.editTextTextPersonName2).text.toString().trim()
                val age = view.findViewById<TextView>(R.id.editTextNumber).text.toString().trim()
                if (type == "add") {
                    addUser(fristName, lastname, age)
                } else {
                    updateUser(fristName, lastname, age, userId)
                }
            }
        view.findViewById<Button>(R.id.button_delete)
            .setOnClickListener {
                val fristName =
                    view.findViewById<TextView>(R.id.editTextTextPersonName).text.toString().trim()
                val lastname =
                    view.findViewById<TextView>(R.id.editTextTextPersonName2).text.toString().trim()
                val age = view.findViewById<TextView>(R.id.editTextNumber).text.toString().trim()

                delete(fristName, lastname,age,userId)
            }

        return view
    }



    private fun inputCheck(fristName: String, lastname: String, age: String): Boolean {

        Log.d("system", "inputCheck: fristName=$fristName, lastname=$lastname,age=$age ")

        return (!TextUtils.isEmpty(fristName) && !TextUtils.isEmpty(lastname) && !TextUtils.isEmpty(
            age
        ))
    }


    private fun addUser(fristName: String, lastname: String, age: String) {
        if (inputCheck(fristName, lastname, age)) {
            Log.d("System", "addUser: ")
            val user = UserEntity(0, fristName, lastname, Integer.valueOf(age))
            viewModel.addUser(user)
            Toast.makeText(requireContext(), "user added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment2)
        } else {
            Toast.makeText(requireContext(), "check filed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUser(fristName: String, lastname: String, age: String, id: Int) {
        Log.d("Update", "updateUser: ")
        if (inputCheck(fristName, lastname, age)) {
            val user = UserEntity(id, fristName, lastname, Integer.valueOf(userId))
            viewModel.updateUser(user)
            Toast.makeText(requireContext(), "user added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment2)
        } else {
            Toast.makeText(requireContext(), "check filed", Toast.LENGTH_SHORT).show()
        }
    }
    private fun delete(fristName: String, lastname: String, age: String, id: Int) {
        Log.d("delete", "delete: ")
        if (inputCheck(fristName, lastname, age)) {
            val user = UserEntity(id, fristName, lastname, Integer.valueOf(userId))
            viewModel.deleteUser(user)
            Toast.makeText(requireContext(), "user deleted", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment2)
        } else {
            Toast.makeText(requireContext(), "deletion filed", Toast.LENGTH_SHORT).show()
        }
    }

}