package com.kprasad.bitcointicker.data

import android.os.Parcel
import android.os.Parcelable

data class BitcoinPrices(private var prices: Map<String, TickerInfo> = hashMapOf()) : Parcelable {

    constructor(parcel: Parcel) : this() {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BitcoinPrices> {
        override fun createFromParcel(parcel: Parcel): BitcoinPrices {
            return BitcoinPrices(parcel)
        }

        override fun newArray(size: Int): Array<BitcoinPrices?> {
            return arrayOfNulls(size)
        }
    }
}