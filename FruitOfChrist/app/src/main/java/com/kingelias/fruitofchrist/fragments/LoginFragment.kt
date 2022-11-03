package com.kingelias.fruitofchrist.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kingelias.fruitofchrist.R
import com.kingelias.fruitofchrist.activities.MainActivity
import com.kingelias.fruitofchrist.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var loginBinding: FragmentLoginBinding

    //progressDialog
    private lateinit var progressDialog: ProgressDialog

    //firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""
    private var confirmPassword = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        loginBinding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser();

        //configure progress dialog
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)



        loginBinding.signupPromptTV.setOnClickListener{
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            )
        }

        loginBinding.loginBn.setOnClickListener{
            //before logging in, validate data
            validateData()
        }

        return loginBinding.root
    }

    private fun validateData() {
        //get data
        email = loginBinding.emailET.text.toString().trim()
        password = loginBinding.passwordET.text.toString().trim()

        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            loginBinding.emailTIL.error = "Invalid email format"
        }
        else if(TextUtils.isEmpty(password)){
            //no password entered
            loginBinding.passordTIL.error = "Please enter password"
        }
        else{
            //data is validated, begin login
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(requireActivity(), "Logged in as $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(requireActivity(), "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        //if user is already logged in, go to main activity
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            //user is already logged in
            startActivity(Intent(requireActivity(),MainActivity::class.java))
        }else{
            // if not logged in, make page visible
            loginBinding.pageContent.isVisible = true
        }
    }

}