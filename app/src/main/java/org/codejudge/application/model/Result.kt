package org.codejudge.application.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Place(
    @SerializedName("results")  val result:MutableList<Result>?
):Serializable

data class Result(
@SerializedName("formatted_address")  val formattedAddress:String?,
@SerializedName("icon")  val icon:String?,
@SerializedName("id")  val id:String?,
@SerializedName("name")  val name:String?,
@SerializedName("opening_hours")  val openingHours:OpeningHours,
@SerializedName("place_id")  val placeId:String?,
@SerializedName("price_level")  val priceLevel:Int?,
@SerializedName("rating")  val rating:Double?,
@SerializedName("reference")  val reference:String?,
@SerializedName("types")  val types:MutableList<String>?,
@SerializedName("user_ratings_total")  val userRatingsTotal:Int?
):Serializable

data class OpeningHours(
    @SerializedName("open_now")  var openNow: Boolean?
):Serializable

data class Location(
    @SerializedName("latitude")  val latitude:Double?,
    @SerializedName("longitude")  val longitude:Double?
    ):Serializable