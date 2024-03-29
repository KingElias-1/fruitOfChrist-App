package com.kingelias.fruitofchrist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kingelias.fruitofchrist.R
import com.kingelias.fruitofchrist.viewmodel.AuthorsVM
import com.kingelias.fruitofchrist.data.Author
import com.kingelias.fruitofchrist.databinding.FragmentAddAuthorBinding

class AddAuthorFragment : DialogFragment() {
    private lateinit var addBinding: FragmentAddAuthorBinding
    private val authorsVM by lazy {
        ViewModelProvider(this)[AuthorsVM::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
            addBinding = FragmentAddAuthorBinding.inflate(layoutInflater, container, false)
            return addBinding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            authorsVM.result.observe(viewLifecycleOwner, Observer {
            val message = if (it == null){
                getString(R.string.sucess)
            }else{
                getString(R.string.fail)
            }
            Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
        })

        addBinding.addBn.setOnClickListener {
            val name = addBinding.authorET.text.toString()

            if(name.isEmpty()){
                addBinding.authorET.error = getString(R.string.auth_empty_error)
            }else{
                val author = Author()
                author.name = name
                authorsVM.addAuthor(author)

                findNavController().navigate(AddAuthorFragmentDirections.actionAddAuthorFragmentToAuthorsFragment())
            }

        }

    }

}