package org.codejudge.application.api

import org.codejudge.application.model.Place
import org.codejudge.application.model.Result
import org.codejudge.application.sharedVariables.SharedVariables
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiInterface: ApiInterface) : ApiHelper {

    override suspend fun getNearbyPlaces(location:String,type:String,keyword:String?):
            Response<Place> = apiInterface.getNearbyPlaces(location,
                                                            SharedVariables.RADIUS,
                                                            type,
                                                            SharedVariables.API_KEY,
                                                            keyword)



}