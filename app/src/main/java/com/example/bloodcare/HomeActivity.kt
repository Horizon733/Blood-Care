package com.example.bloodcare

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.bloodcare.databinding.ActivityHomeBinding
import com.example.bloodcare.signup.MainActivity
import com.example.bloodcare.tabbed_layout.TabAdapter
import com.example.bloodcare.tabbed_layout.donate.AddInfoFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var homeBinding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        val firebaseAuth = FirebaseAuth.getInstance()
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser == null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        homeBinding.tablayout.addTab(homeBinding.tablayout.newTab())
        homeBinding.tablayout.getTabAt(0)?.setIcon(R.drawable.ic_donations)
        homeBinding.tablayout.addTab(homeBinding.tablayout.newTab())
        homeBinding.tablayout.getTabAt(1)?.setIcon(R.drawable.ic_virus)
        homeBinding.tablayout.addTab(homeBinding.tablayout.newTab())
        homeBinding.tablayout.getTabAt(2)?.setIcon(R.drawable.ic_user)
        homeBinding.viewpager.adapter =
            TabAdapter(this, supportFragmentManager, homeBinding.tablayout.tabCount)
        homeBinding.viewpager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                homeBinding.tablayout
            )
        )
        homeBinding.tablayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    homeBinding.viewpager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val addInfoFragment = AddInfoFragment()
            supportFragmentManager.beginTransaction().replace(R.id.parent,addInfoFragment).commit()
            homeBinding.viewpager.visibility = View.INVISIBLE
            homeBinding.tablayout.visibility= View.INVISIBLE
            homeBinding.fab.visibility = View.INVISIBLE

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}