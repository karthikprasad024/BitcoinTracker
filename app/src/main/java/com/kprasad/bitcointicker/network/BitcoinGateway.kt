package com.kprasad.bitcointicker.network

import com.kprasad.bitcointicker.data.BitcoinPrices
import com.kprasad.bitcointicker.data.TickerInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class BitcoinGateway @Inject constructor() {

    //This method is using Retrofit to get the JSON data from URL
    fun fetchBitcoinPrices(callBack: Callback<Map<String, TickerInfo>>) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(Api::class.java)
        val call: Call<Map<String, TickerInfo>> = api.ticker
        call.enqueue(callBack)
    }
}