package com.blogspot.roomdb.ui.list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.roomdb.R
import com.blogspot.roomdb.ui.ListAdapter
import com.blogspot.roomdb.ui.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: ListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var close: ImageView
    private lateinit var   search: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
            .setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }
        view.findViewById<FloatingActionButton>(R.id.delete_all)
            .setOnClickListener {
                viewModel.deleteAll()
            }

        adapter = ListAdapter()

        callRecyclerView()

         search = view.findViewById<EditText>(R.id.search)
        close = view.findViewById(R.id.close)
        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (search.text.toString() != "") {
                    if (close.visibility == View.GONE) {
                        close.visibility = View.VISIBLE
                    }
                    search(search.text.toString())
                } else {
                    viewModel.readAllData()
                    recyclerView.adapter = adapter
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        close.setOnClickListener {
            search.setText("")
            close.visibility = View.GONE

            requireActivity().recreate()
        }
        close.visibility = View.GONE
        return view
    }

    private fun callRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.readAllData.observe(viewLifecycleOwner, Observer {
            println("read ---->" + it.size)
            adapter.setData(it)
        })
    }


    private fun search(string: String) {
        val search = "$string"
        Log.d("TAG", "search: ----------->" + search)
        if (!search.equals("")) {
            viewModel.searchByString(search)?.observe(this) { list ->

                list.let {
                    adapter.setData(it)
                }
            }
        }
    }


}