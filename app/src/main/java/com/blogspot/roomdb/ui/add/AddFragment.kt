package com.blogspot.roomdb.ui.add

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.blogspot.roomdb.R
import com.blogspot.roomdb.dbUtils.Address
import com.blogspot.roomdb.dbUtils.UserEntity
import com.blogspot.roomdb.ui.UserViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL


class AddFragment : Fragment() {
    private val args by navArgs<AddFragmentArgs>()

    private var type = "add";
    private var userId = 0;
    private lateinit var viewModel: UserViewModel
    private lateinit var zipCode:EditText
    private lateinit var street:EditText
    private lateinit var toggleButton:ToggleButton
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
        if (Build.VERSION.SDK_INT > 9) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
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
                var address:Address?=null
                if(toggleButton.isChecked){
                    var zipcodeString=""
                    var streetString=""
                if (street.text.toString().trim() == "")
                {
                    street.setError("Enter Street name")
                    street.requestFocus()
                    return@setOnClickListener
                }else{
                    streetString=street.text.toString().trim()
                }
                if (zipCode.text.toString().trim() == "")
                {
                    zipCode.setError("Enter zip Code")
                    zipCode.requestFocus()
                    return@setOnClickListener
                }else{
                    zipcodeString=zipCode.text.toString().trim()

                }
                    address= Address(streetString,zipcodeString)
                }
                Log.d("TAG", "onCreateView: --------->"+address)

                if (type == "add") {


                    addUser(fristName, lastname, age, address)
                } else {
                    updateUser(fristName, lastname, age, userId,address)
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

        toggleButton=  view.findViewById<ToggleButton>(R.id.toggleButton)
        zipCode = view.findViewById(R.id.zipCode)
        street = view.findViewById(R.id.street)
        toggleButton.setOnCheckedChangeListener{ v, ischecke->
            if (ischecke){
                zipCode.visibility=View.VISIBLE
                street.visibility=View.VISIBLE
            }else{
                zipCode.visibility=View.GONE
                street.visibility=View.GONE
            }
        }


        return view
    }



    private fun inputCheck(fristName: String, lastname: String, age: String): Boolean {

        Log.d("system", "inputCheck: fristName=$fristName, lastname=$lastname,age=$age ")

        return (!TextUtils.isEmpty(fristName) && !TextUtils.isEmpty(lastname) && !TextUtils.isEmpty(
            age
        ))
    }


    private fun addUser(fristName: String, lastname: String, age: String,address: Address?) {
        if (inputCheck(fristName, lastname, age)) {
            Log.d("System", "addUser: ")
            val user = UserEntity(0, fristName, lastname, Integer.valueOf(age),address,getBitMapFromUrl())
            lifecycleScope.launch{

            viewModel.addUser(user)

            }
            Toast.makeText(requireContext(), "user added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment2)
        } else {
            Toast.makeText(requireContext(), "check filed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUser(fristName: String, lastname: String, age: String, id: Int,address: Address?) {
        Log.d("Update", "updateUser: ")
        if (inputCheck(fristName, lastname, age)) {
            val user = UserEntity(id, fristName, lastname, Integer.valueOf(userId),address)
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


    fun getBitMapFromUrl(): Bitmap? {
        var image:Bitmap?=null
        try {
            val url = URL("https://yt3.ggpht.com/ytc/AKedOLQXP5X35TVGHxfzAlMNkL7IMRQZSckKuH676ViRGw=s900-c-k-c0x00ffffff-no-rj")
//            val url = URL("https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png")
              image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            return image
        } catch (e: IOException) {
           e.printStackTrace()
        }
        return image
    }
}