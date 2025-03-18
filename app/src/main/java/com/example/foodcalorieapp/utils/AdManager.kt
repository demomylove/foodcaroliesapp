package com.example.foodcalorieapp.utils

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

object AdManager {

    fun initialize(context: Context) {
        MobileAds.initialize(context) {}
    }

    fun showBannerAd(adView: AdView) {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}
