package org.codejudge.application.mainActivity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import org.codejudge.application.R
import org.codejudge.application.databinding.MainActivityBinding
import org.codejudge.application.model.Location
import org.codejudge.application.model.Result
import org.codejudge.application.networkHandling.Status


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var mBinding:MainActivityBinding
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private var placeListAdapter:MainActivityAdapter? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location:String? = null
    private var type:String = "restaurant"
    private var keyword:String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        enableMyLocation()

        placeListAdapter = MainActivityAdapter(object : MainActivityClickListener{
            override fun onClick(model: Result) {

            }
        })


        mBinding.editSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                keyword = mBinding.editSearch.text.toString()
                getSearchResult()
            }
        })



        initRecentEarthquakeRecycler()
        initEarthquakeListLiveData()

    }


    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }else{
            getLocation()
        }
    }

    fun getSearchResult(){
        mainActivityViewModel.getNearbyPlaces(location!!,type,keyword)
    }

    @SuppressLint("MissingPermission")
    fun getLocation(){
        fusedLocationClient.lastLocation
            .addOnSuccessListener { locat : android.location.Location? ->
                location = locat?.latitude.toString() + "," + locat?.longitude.toString()
                getSearchResult()
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED)) {
                        getLocation()
                    }
                } else {
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                }
                return
            }
        }
    }



    private fun initRecentEarthquakeRecycler() {
        mBinding.recylerView.apply {
            adapter = placeListAdapter
        }
    }

    private fun initEarthquakeListLiveData() {
        mainActivityViewModel.nearbyPlacesLivedata.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { list ->
                        placeListAdapter?.submitList(it.data.result)
                        placeListAdapter?.notifyDataSetChanged()
                    }
                   mBinding.isShow= false;
                }
                Status.LOADING -> {
                    mBinding.isShow= true;
                }
                Status.ERROR -> {
                    //Handle Error
                    mBinding.isShow= false;
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }



}
