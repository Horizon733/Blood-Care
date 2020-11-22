package com.example.bloodcare.tabbed_layout.covid_stats

import com.google.gson.annotations.SerializedName

class Data( var statewise: List<Statewise>) {
    class Statewise(
        var active: String, var deaths: String,
        var recovered: String,
        var state: String
    ) {
    }
}