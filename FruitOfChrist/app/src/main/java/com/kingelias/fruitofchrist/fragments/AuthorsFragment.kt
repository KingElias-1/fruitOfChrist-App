package com.kingelias.fruitofchrist.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.DialogInterface;
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kingelias.fruitofchrist.R
import com.kingelias.fruitofchrist.adapters.AuthorsAdapter
import com.kingelias.fruitofchrist.data.Author
import com.kingelias.fruitofchrist.databinding.FragmentAuthorsBinding
import com.kingelias.fruitofchrist.viewmodel.AuthorsVM

class AuthorsFragment : Fragment() {
    private lateinit var authorsBinding: FragmentAuthorsBinding
    private val authorsVM by lazy {
        ViewModelProvider(this)[AuthorsVM::class.java]
    }
    private val authorsAdapter = AuthorsAdapter(this@AuthorsFragment)

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
        //getting real time updates when a new author is added
        authorsVM.getRealTimeUpdates()

        //observing the data from the server to update the UI
        authorsVM.authors.observe(viewLifecycleOwner, Observer{
            authorsAdapter.setAuthors(it)
        })

        authorsVM.author.observe(viewLifecycleOwner, Observer{
            authorsAdapter.addAuthor(it)
        })
    }

    fun editAuthor(author: Author){
        findNavController().navigate(AuthorsFragmentDirections.actionAuthorsFragmentToAddAuthorFragment(author))
    }

    fun deleteAuthor(author:Author){
        AlertDialog.Builder(requireActivity()).also{
            it.setTitle(getString(R.string.delete_author))
            it.setMessage(getString(R.string.confirm_delete))
            it.setNegativeButton(getString(R.string.cancel)){
                    _, _ ->
            }
            it.setPositiveButton(getString(R.string.yes)){ _, _ ->
                authorsVM.deleteAuthor(author)
            }
        }.create().show()
    }
}