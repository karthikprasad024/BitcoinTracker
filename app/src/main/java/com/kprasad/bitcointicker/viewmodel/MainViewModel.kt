package com.kprasad.bitcointicker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kprasad.bitcointicker.data.TickerInfo
import com.kprasad.bitcointicker.network.BitcoinGateway
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit


class MainViewModel constructor(private var bitcoinGateway: BitcoinGateway) :
    ViewModel() {

    private var disposable: Disposable? = null

    //this is the data that we will fetch asynchronously
    private var bitCoinPrices: MutableLiveData<Map<String, TickerInfo>>? = null

    //we will call this method to get the data
    fun getBitcoinPricesLiveData(): LiveData<Map<String, TickerInfo>>? {
        //if the list is null
        if (bitCoinPrices == null) {
            bitCoinPrices = MutableLiveData<Map<String, TickerInfo>>()

            setupTickerUpdates()
        }

        //finally we will return the list
        return bitCoinPrices
    }

    private fun setupTickerUpdates() {
        disposable = Observable.interval(
            INITIAL_DELAY, REFRESH_INTERVAL,
            TimeUnit.MILLISECONDS
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { bitcoinGateway.fetchBitcoinPrices(handleBitCoinPricesResponse()) }
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

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    companion object {
        const val INITIAL_DELAY = 1000L //delay 1 second before starting to fetch
        const val REFRESH_INTERVAL = 1000L //every 1 second
    }
}