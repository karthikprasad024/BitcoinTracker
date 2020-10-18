package com.kprasad.bitcointicker.network

import com.kprasad.bitcointicker.data.BitcoinPrices
import com.kprasad.bitcointicker.data.TickerInfo
import retrofit2.Call
import retrofit2.http.GET


interface Api {
    @get:GET("ticker")
    val ticker: Call<Map<String, TickerInfo>>

    companion object {
        const val BASE_URL = "https://blockchain.info"
    }
}