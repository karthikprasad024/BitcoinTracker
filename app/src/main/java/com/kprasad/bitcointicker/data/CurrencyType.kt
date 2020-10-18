package com.kprasad.bitcointicker.data

import android.os.Parcel
import android.os.Parcelable

enum class CurrencyType(var currencyName: String) : Parcelable {
    USD("USD"),
    EUR("EUR"),
    CAD("CAD"),
    UNKNOWN("");

    constructor(parcel: Parcel) : this(parcel.readString() ?: "") {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(currencyName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CurrencyType> {
        override fun createFromParcel(parcel: Parcel): CurrencyType {
            return valueOf(parcel.readString() ?: "")
        }

        override fun newArray(size: Int): Array<CurrencyType?> {
            return arrayOfNulls(size)
        }
    }
}