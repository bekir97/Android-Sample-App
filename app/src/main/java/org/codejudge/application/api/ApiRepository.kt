package org.codejudge.application.api

import org.codejudge.application.api.ApiHelper
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getNearbyPlaces(location:String,type:String,keyword:String?) =  apiHelper.getNearbyPlaces(location,type,keyword)

}