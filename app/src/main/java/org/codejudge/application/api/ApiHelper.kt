package org.codejudge.application.api

import org.codejudge.application.model.Place
import org.codejudge.application.model.Result
import retrofit2.Response

interface ApiHelper {
    suspend fun getNearbyPlaces(location:String,type:String,keyword:String?): Response<Place>

}