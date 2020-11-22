package com.example.bloodcare.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.bloodcare.HomeActivity
import com.example.bloodcare.R
import com.example.bloodcare.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var registerBinding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = FragmentRegisterBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val loginFragment = LoginFragment()
        registerBinding.loginLinkBtn.setOnClickListener {
           view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment2)
        }
        registerBinding.signUpBtn.setOnClickListener {
            registerUser()
        }
        return registerBinding.root
    }
    fun registerUser(){
        val email = registerBinding.emailEtRg.text.toString().trim()
        val pass1 = registerBinding.passwordEtRg.text.toString().trim()
        val pass2 = registerBinding.passwordConfirmEtRg.text.toString().trim()
        if(pass1.equals(pass2)){
            auth.createUserWithEmailAndPassword(email,pass2).addOnCompleteListener {
                    task ->
                if( !task.isSuccessful){
                    Log.e("Firebase","${task.result}")
                    Toast.makeText(context,"Error occured", Toast.LENGTH_SHORT).show()
                }else{
                    view?.findNavController()?.navigate(R.id.action_registerFragment_to_profileFragment)
                    (activity as MainActivity).finish()
                }
            }
        }else{
            registerBinding.passwordConfirmTextInput.error = "Both Passwords Should Match"
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}