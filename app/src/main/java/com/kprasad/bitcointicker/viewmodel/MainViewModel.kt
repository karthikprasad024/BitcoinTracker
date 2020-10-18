package com.kprasad.bitcointicker.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kprasad.bitcointicker.data.TickerInfo
import com.kprasad.bitcointicker.network.BitcoinGateway
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel constructor(private var bitcoinGateway: BitcoinGateway) :
    ViewModel() {

    //this is the data that we will fetch asynchronously
    private var bitCoinPrices: MutableLiveData<Map<String, TickerInfo>>? = null

    //we will call this method to get the data
    fun getBitcoinPricesLiveData(): LiveData<Map<String, TickerInfo>>? {
        //if the list is null
        if (bitCoinPrices == null) {
            bitCoinPrices = MutableLiveData<Map<String, TickerInfo>>()
            //we will load it asynchronously from server in this method
            bitcoinGateway.fetchBitcoinPrices(handleBitCoinPricesResponse())
        }

        //finally we will return the list
        return bitCoinPrices
    }

    private fun handleBitCoinPricesResponse(): Callback<Map<String, TickerInfo>> {
        return object : Callback<Map<String, TickerInfo>> {
            override fun onFailure(call: Call<Map<String, TickerInfo>>, t: Throwable) {
                bitCoinPrices?.value = hashMapOf()
            }

            override fun onResponse(
                call: Call<Map<String, TickerInfo>>,
                response: Response<Map<String, TickerInfo>>
            ) {
                bitCoinPrices?.value = response.body() ?: hashMapOf()
            }

        }
    }

}