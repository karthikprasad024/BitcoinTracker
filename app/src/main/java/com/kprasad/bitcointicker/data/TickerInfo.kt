package com.kprasad.bitcointicker.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class TickerInfo(
    @SerializedName("last") var lastUpdatedPrice: String = "",
    @SerializedName("symbol")  var symbol: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this() {
        lastUpdatedPrice = parcel.readString() ?: ""
        symbol = parcel.readString() ?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(lastUpdatedPrice)
        parcel.writeString(symbol)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TickerInfo> {
        override fun createFromParcel(parcel: Parcel): TickerInfo {
            return TickerInfo(parcel)
        }

        override fun newArray(size: Int): Array<TickerInfo?> {
            return arrayOfNulls(size)
        }
    }
}