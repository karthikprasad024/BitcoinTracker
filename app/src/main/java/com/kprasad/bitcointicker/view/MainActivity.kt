package com.kprasad.bitcointicker.view

import android.os.Bundle
import android.util.Log
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.kprasad.bitcointicker.R
import com.kprasad.bitcointicker.data.TickerInfo
import com.kprasad.bitcointicker.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()

        mainViewModel.getBitcoinPricesLiveData()
            ?.observe(this, object : Observer<Map<String, TickerInfo>> {
                override fun onChanged(@Nullable bitcoinPrices: Map<String, TickerInfo>) {
                    Log.d(TAG, "bitcoinPrices received : \n" + bitcoinPrices.toString())
                }
            })
    }


    private fun setupViews() {
        this.viewPager = findViewById(R.id.pager)
        this.tabLayout = findViewById(R.id.tabs)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = BitcoinPagerAdapter(this)
        this.viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager,
            TabConfigurationStrategy { tab, position ->
                tab.text = when (position) {
                    0 -> "CAD"
                    1 -> "USD"
                    else -> "EUR"
                }
            }).attach()
    }

    /**
     * A simple pager adapter that represents 3 BitcoinCurrencyFragment objects
     */
    private inner class BitcoinPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        /**
         * We want to show 3 tabs for CAD, USD, and EUR
         */
        private var NUM_PAGES = 3

        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment =
            BitcoinCurrencyFragment.newInstance(position)

    }

    companion object {
        const val TAG: String = "MainActivity"
    }

}