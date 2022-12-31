package com.kingelias.fruitofchrist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kingelias.fruitofchrist.adapters.AuthorsAdapter
import com.kingelias.fruitofchrist.databinding.FragmentAuthorsBinding
import com.kingelias.fruitofchrist.viewmodel.AuthorsVM

class AuthorsFragment : Fragment() {
    private lateinit var authorsBinding: FragmentAuthorsBinding
    private val authorsVM by lazy {
        ViewModelProvider(this)[AuthorsVM::class.java]
    }
    private val authorsAdapter = AuthorsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authorsBinding = FragmentAuthorsBinding.inflate(layoutInflater, container, false)
        authorsBinding.goToAddBn.setOnClickListener {
            findNavController().navigate(AuthorsFragmentDirections.actionAuthorsFragmentToAddAuthorFragment())
        }
        return authorsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setting addapter for the recycler view
        authorsBinding.authorsRV.adapter = authorsAdapter

        //calling function from viewModel to get authors
        authorsVM.fetchAuthors()

        //observing the data from the server to update the UI
        authorsVM.authors.observe(viewLifecycleOwner, Observer{
            authorsAdapter.setAuthors(it)
        })
    }

    override fun onResume() {
        super.onResume()
    }
}