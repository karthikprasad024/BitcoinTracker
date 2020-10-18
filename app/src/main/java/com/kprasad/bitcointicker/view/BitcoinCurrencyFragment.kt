package com.kprasad.bitcointicker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kprasad.bitcointicker.R
import com.kprasad.bitcointicker.data.CurrencyType
import com.kprasad.bitcointicker.data.TickerInfo
import com.kprasad.bitcointicker.viewmodel.MainViewModel
import com.robinhood.ticker.TickerUtils
import com.robinhood.ticker.TickerView
import dagger.hilt.android.AndroidEntryPoint
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class BitcoinCurrencyFragment : Fragment() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    lateinit var priceUpdatedView: TickerView
    var currencyType: CurrencyType = CurrencyType.UNKNOWN

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_bitcoin_currency, container, false)

        priceUpdatedView = view.findViewById(R.id.tv_bitcoinPrice)
        priceUpdatedView.setCharacterLists(TickerUtils.provideNumberList());

        val args = arguments
        args?.let {
            currencyType = when (it.getInt(BUNDLE_TAB_POS, -1)) {
                0 -> CurrencyType.CAD
                1 -> CurrencyType.USD
                else -> CurrencyType.EUR
            }

            mainViewModel.getBitcoinPricesLiveData()
                ?.observe(viewLifecycleOwner, object : Observer<Map<String, TickerInfo>> {
                    override fun onChanged(@Nullable bitcoinPrices: Map<String, TickerInfo>) {
                        if (bitcoinPrices.containsKey(currencyType.currencyName)) {
                            val tickerInfo: TickerInfo =
                                bitcoinPrices.getValue(currencyType.currencyName)
                            val df = DecimalFormat("#,###.00")
                            df.roundingMode = RoundingMode.FLOOR

                            val updatedText: String =
                                tickerInfo.symbol + df.format(tickerInfo.lastUpdatedPrice.toFloat())
                            priceUpdatedView.text = updatedText
                        }
                    }
                })
        }
        return view
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

