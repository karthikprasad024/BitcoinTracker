package com.kprasad.bitcointicker.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kprasad.bitcointicker.R
import com.kprasad.bitcointicker.data.CurrencyType
import com.kprasad.bitcointicker.data.TickerInfo
import com.kprasad.bitcointicker.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BitcoinCurrencyFragment : Fragment() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    lateinit var priceUpdatedView: TextView
    var currencyType: CurrencyType = CurrencyType.UNKNOWN

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_bitcoin_currency, container, false)
        priceUpdatedView = view.findViewById(R.id.tv_bitcoinPrice)

        mainViewModel.getBitcoinPricesLiveData()
            ?.observe(viewLifecycleOwner, object : Observer<Map<String, TickerInfo>> {
                override fun onChanged(@Nullable bitcoinPrices: Map<String, TickerInfo>) {
                    if (bitcoinPrices.containsKey(currencyType.currencyName)) {
                        val tickerInfo: TickerInfo = bitcoinPrices.getValue(currencyType.currencyName)
                        val updatedText: String =
                            tickerInfo.symbol + tickerInfo.lastUpdatedPrice
                        priceUpdatedView.text = updatedText
                    }
                }
            })
        return view
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        val args = arguments

        args?.let {
            currencyType = when (it.getInt(BUNDLE_TAB_POS, -1)) {
                0 -> CurrencyType.CAD
                1 -> CurrencyType.USD
                else -> CurrencyType.EUR
            }
        }

    }

    companion object {
        private const val BUNDLE_TAB_POS = "BUNDLE_TAB_POSITION"

        fun newInstance(position: Int): BitcoinCurrencyFragment {
            val fragment = BitcoinCurrencyFragment()

            val bundle = Bundle()
            bundle.putInt(BUNDLE_TAB_POS, position)

            fragment.arguments = bundle
            return fragment
        }
    }
}

