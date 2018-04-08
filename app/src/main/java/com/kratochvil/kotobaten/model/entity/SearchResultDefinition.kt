package com.kratochvil.kotobaten.model.entity

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SearchResultDefinition() : RealmObject(), Parcelable {
    @PrimaryKey var id: Long = 0

    var englishDefinitions: RealmList<String> = RealmList()
    var partsOfSpeech: RealmList<String> = RealmList()
    var tags: RealmList<String> = RealmList()
    var info: RealmList<String> = RealmList()

    fun getEnglishDefinitionsAsString(): String {
        return reduceToSemicolonDelimitedString(englishDefinitions)
    }

    fun getAdditionalInformationAsString(): String {
        return reduceToSemicolonDelimitedString(tags
                .union(info))
    }

    fun getPartsOfSpeechAsString(): String {
        return reduceToSemicolonDelimitedString(partsOfSpeech)
    }

    private fun reduceToSemicolonDelimitedString(values: Collection<String>): String {
        return if(values.any())
            values.reduce({ acc, curr -> "$acc; $curr" })
        else ""
    }

    //<editor-fold desc="Parcelable Implementation">

    constructor(parcel: Parcel) : this() {
        parcel.readStringList(englishDefinitions)
        parcel.readStringList(partsOfSpeech)
        parcel.readStringList(tags)
        parcel.readStringList(info)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(englishDefinitions)
        parcel.writeStringList(partsOfSpeech)
        parcel.writeStringList(tags)
        parcel.writeStringList(info)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchResultDefinition> {
        override fun createFromParcel(parcel: Parcel): SearchResultDefinition {
            return SearchResultDefinition(parcel)
        }

        override fun newArray(size: Int): Array<SearchResultDefinition?> {
            return arrayOfNulls(size)
        }
    }

    //</editor-fold>
}