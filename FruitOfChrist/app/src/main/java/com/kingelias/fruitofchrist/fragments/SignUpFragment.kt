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
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kingelias.fruitofchrist.R
import com.kingelias.fruitofchrist.activities.MainActivity
import com.kingelias.fruitofchrist.databinding.FragmentSignUpBinding
import kotlin.math.sign

class SignUpFragment : Fragment() {
    private lateinit var signUpBinding: FragmentSignUpBinding

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
        signUpBinding = FragmentSignUpBinding.inflate(layoutInflater, container, false)

        //configure progress dialog
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating Account...")
        progressDialog.setCanceledOnTouchOutside(false)

        signUpBinding.loginPromptTV.setOnClickListener{
            findNavController().navigate(
                SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            )
        }


        firebaseAuth = FirebaseAuth.getInstance()

        signUpBinding.signUpBn.setOnClickListener{
            validateData()
        }

        return signUpBinding.root
    }

    private fun validateData() {
        //get data
        email = signUpBinding.emailET.text.toString().trim()
        password = signUpBinding.passwordET.text.toString().trim()
        confirmPassword = signUpBinding.passwordET.text.toString().trim()

        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid format
            signUpBinding.emailTIL.error = "Invalid email"
        }
        else if(TextUtils.isEmpty(password)){
            //invalid format
            signUpBinding.passordTIL.error = "Please enter password"
        }
        else if(password.length <6){
            //password length 6 char
            signUpBinding.passordTIL.error = "Password must be atleast 6 characters long\nand include both cases and numbers"
        }else if(password != confirmPassword){
            //both have to match
            signUpBinding.confirmPassordTIL.error = "Passwords must match"
        }
        else{
            //data is valid, sign up
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        //show progress
        progressDialog.show()

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //signup success
                progressDialog.dismiss()
                //get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(requireActivity(),"Account created with email $email", Toast.LENGTH_SHORT).show()

                //open profile
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener{e ->
                Toast.makeText(requireActivity(),"SignUp failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}