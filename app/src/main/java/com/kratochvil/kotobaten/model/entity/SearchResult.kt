package com.kratochvil.kotobaten.model.entity

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class SearchResult() : RealmObject(), Parcelable {
    @PrimaryKey var id: Long = 0

    var isCommon: Boolean = false
    var japaneseWord: String = ""
    var japaneseReading: String = ""
    var definitions: RealmList<SearchResultDefinition> = RealmList()

    var isFavorited: Boolean = false
    var visitsCount: Int = 0
    var lastVisited = Calendar.getInstance().time

    //<editor-fold desc="Parcelable Implementation">

    constructor(parcel: Parcel) : this() {
        isCommon = parcel.readByte() != 0.toByte()
        japaneseWord = parcel.readString()
        japaneseReading = parcel.readString()
        parcel.readTypedList(definitions, SearchResultDefinition.CREATOR)
        lastVisited = Date(parcel.readLong())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (isCommon) 1 else 0)
        parcel.writeString(japaneseWord)
        parcel.writeString(japaneseReading)
        parcel.writeTypedList(definitions)
        parcel.writeLong(lastVisited.time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchResult> {
        val AUTOFAVORITE_THRESHOLD = 3

        override fun createFromParcel(parcel: Parcel): SearchResult {
            return SearchResult(parcel)
        }

        override fun newArray(size: Int): Array<SearchResult?> {
            return arrayOfNulls(size)
        }
    }

    //</editor-fold>
}

