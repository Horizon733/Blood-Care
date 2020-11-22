package com.example.bloodcare.tabbed_layout.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bloodcare.R
import com.example.bloodcare.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.toString as toString1


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var fragmentProfileBinding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentProfileBinding = FragmentProfileBinding.inflate(layoutInflater)
        auth=FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
           // startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
        databaseReference = FirebaseDatabase.getInstance().getReference()
        val firebaseUser = auth.currentUser
        fragmentProfileBinding.emailEtPro.setText(firebaseUser?.email)
        getUserInformation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val gender = arrayOf("Male", "Female", "Trans")
        fragmentProfileBinding.submitPro.setOnClickListener {
        sendUserInformation()
        //view?.findNavController()?.navigate(R.id.action_profileFragment_to_homeActivity)
        }
        return fragmentProfileBinding.root
    }

    fun sendUserInformation(){
        val user = auth.getCurrentUser()
        val name = fragmentProfileBinding.nameEtPro.text.toString().trim()
        val email = fragmentProfileBinding.emailEtPro.text.toString().trim()
        val phone = fragmentProfileBinding.phoneEtPro.text.toString().trim()
        val age = fragmentProfileBinding.ageEtPro.text.toString().trim()
        val dob = fragmentProfileBinding.dobEtPro.text.toString().trim()
        val weight = fragmentProfileBinding.bodyWeightEtPro.text.toString().trim()
        val address = fragmentProfileBinding.addressEtPro.text.toString().trim()
        val gender_radio = fragmentProfileBinding.root.findViewById<RadioGroup>(R.id.genderGroup)
        val gender = gender_radio.checkedRadioButtonId
        val blood_group_radio = fragmentProfileBinding.root.findViewById<RadioGroup>(R.id.blood_group)
        val blood_group = blood_group_radio.checkedRadioButtonId
        if(name.isEmpty()){
            fragmentProfileBinding.nameEtPro.error = "Enter your name"
        }
        else if(email.isEmpty()){
            fragmentProfileBinding.emailEtPro.error = "Enter your email"
        }
        else if(phone.isEmpty()){
            fragmentProfileBinding.phoneEtPro.error = "Enter your phone number"
        }
        else if(age.isEmpty()){
            fragmentProfileBinding.ageEtPro.error = "Enter your age"
        }
        else if(dob.isEmpty()){
            fragmentProfileBinding.dobEtPro.error = "Enter your Date of Birth"
        }
        else if(weight.isEmpty()){
            fragmentProfileBinding.bodyWeightEtPro.error = "Enter your weight"
        }
        else if(address.isEmpty()){
            fragmentProfileBinding.addressEtPro.error = "Enter your Address"
        }
        else if(gender == null){
            Toast.makeText(context,"Please select your Gender",Toast.LENGTH_SHORT).show()
        }
        else if(blood_group == null){
            Toast.makeText(context,"Please select your Blood group",Toast.LENGTH_SHORT).show()
        }
        else {
            val userInformation = UserInformation(name, user?.email.toString1(), dob,gender,phone,age,weight,address,blood_group )
            user?.uid?.let { databaseReference.child(it).setValue(userInformation) }

            Toast.makeText(context, "User information updated", Toast.LENGTH_LONG).show()
            getUserInformation()
        }
    }

    fun getUserInformation() {
        val user = auth.getCurrentUser()
        if (user != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference()
            databaseReference.child(user.uid).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    fragmentProfileBinding.nameEtPro.setText(snapshot.child("name").value?.toString())
                    fragmentProfileBinding.emailEtPro.setText(snapshot.child("email").value?.toString())
                    fragmentProfileBinding.dobEtPro.setText(snapshot.child("dob").value?.toString())
                    fragmentProfileBinding.ageEtPro.setText(snapshot.child("age").value?.toString())
                    fragmentProfileBinding.bodyWeightEtPro.setText(snapshot.child("body_weight").value?.toString())
                    fragmentProfileBinding.addressEtPro.setText(snapshot.child("address").value?.toString())
                    fragmentProfileBinding.phoneEtPro.setText(snapshot.child("phoneNo").value?.toString())
                    fragmentProfileBinding.genderGroup.check(snapshot.child("gender").value.toString1().toInt())
                    fragmentProfileBinding.bloodGroup.check(snapshot.child("blood_group").value.toString1().toInt())

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Data", "${error.message}")
                }
            })


        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {

            }
    }
}