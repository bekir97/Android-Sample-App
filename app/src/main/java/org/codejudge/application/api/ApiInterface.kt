package org.codejudge.application.api

import org.codejudge.application.model.Location
import org.codejudge.application.model.Place
import org.codejudge.application.model.Result
import org.codejudge.application.sharedVariables.SharedVariables
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {



    @GET("nearbysearch/json")
    suspend fun getNearbyPlaces(@Query("location") location:String?,
                                @Query("radius") radius:Int?,
                                @Query("type") type:String?,
                                @Query("key") key:String,
                                @Query("keyword") keyword:String?
                                ): Response<Place>
}