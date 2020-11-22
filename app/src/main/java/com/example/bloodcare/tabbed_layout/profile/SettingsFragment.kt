package com.example.bloodcare.tabbed_layout.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.bloodcare.HomeActivity
import com.example.bloodcare.R
import com.example.bloodcare.databinding.FragmentSettingsBinding
import com.example.bloodcare.tabbed_layout.donate.AddInfoFragment
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : Fragment() {
    private lateinit var fragmentSettingsBinding: FragmentSettingsBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentSettingsBinding = FragmentSettingsBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentSettingsBinding.signOut.setOnClickListener{
            auth.signOut()
            view?.findNavController()?.navigate(R.id.action_settingsFragment_to_loginFragment)
        }
        fragmentSettingsBinding.bloodDonation.setOnClickListener {
            val addInfoFragment = AddInfoFragment()
            (activity as HomeActivity).supportFragmentManager.beginTransaction().add(addInfoFragment,"Info").commit()
        }
        fragmentSettingsBinding.profileBtn.setOnClickListener { view?.findNavController()?.navigate(R.id.action_settingsFragment_to_profileFragment)
            val profileFragment = ProfileFragment()
            (activity as HomeActivity).supportFragmentManager.beginTransaction().add(profileFragment,"Profile").commit()}
        return fragmentSettingsBinding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
            }
    }
}