package org.codejudge.application.mainActivity

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import org.codejudge.application.api.ApiRepository
import org.codejudge.application.model.Place
import org.codejudge.application.networkHandling.NetworkHelper
import org.codejudge.application.networkHandling.Resource

class MainActivityViewModel @ViewModelInject constructor(
    private val apiRepository: ApiRepository,
    private val networkHelper: NetworkHelper,
    @Assisted private val savedState: SavedStateHandle
) : ViewModel() {

     val nearbyPlacesLivedata = MutableLiveData<Resource<Place>>()


    fun getNearbyPlaces(location:String,type:String,keyword:String?) {
        viewModelScope.launch {
            nearbyPlacesLivedata.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                apiRepository.getNearbyPlaces(location,type,keyword).let {
                    if (it.isSuccessful) {
                        nearbyPlacesLivedata.postValue(Resource.success(it.body()))
                    } else nearbyPlacesLivedata.postValue(
                        Resource.error(
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else nearbyPlacesLivedata.postValue(Resource.error("No internet connection", null))
        }
    }


}