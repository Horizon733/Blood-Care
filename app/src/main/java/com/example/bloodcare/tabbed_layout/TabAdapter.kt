package com.example.bloodcare.tabbed_layout

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.bloodcare.tabbed_layout.covid_stats.Covid_Stats_Fragment
import com.example.bloodcare.tabbed_layout.donate.DonateFragment
import com.example.bloodcare.tabbed_layout.profile.SettingsFragment


class TabAdapter(context: Context,fm:FragmentManager,internal var totalTabs: Int): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0-> return DonateFragment()
            1-> return  Covid_Stats_Fragment()
            2 -> return SettingsFragment()
            else -> return DonateFragment()
        }
    }
}