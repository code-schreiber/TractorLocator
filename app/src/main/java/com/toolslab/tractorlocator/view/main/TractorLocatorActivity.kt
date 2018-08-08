package com.toolslab.tractorlocator.view.main

import android.os.Bundle
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.toolslab.tractorlocator.R
import com.toolslab.tractorlocator.base_repository.model.CoordinateViewModel
import com.toolslab.tractorlocator.base_repository.model.DriverViewModel
import com.toolslab.tractorlocator.base_repository.model.FieldViewModel
import com.toolslab.tractorlocator.maps.MapOperations
import com.toolslab.tractorlocator.view.base.BaseActivity
import javax.inject.Inject

class TractorLocatorActivity : BaseActivity(), TractorLocatorContract.View, OnMapReadyCallback {

    @BindView(R.id.activity_tractor_locator_loading)
    internal lateinit var loadingView: View

    @Inject
    internal lateinit var mapOperations: MapOperations

    @Inject
    internal lateinit var presenter: TractorLocatorContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tractor_locator)
        ButterKnife.bind(this)
        presenter.bind(this)
    }

    override fun onDestroy() {
        presenter.unbind(this)
        super.onDestroy()
    }

    override fun getMapAsync() {
        // Get notified when the map is ready to be used via onMapReady().
        (supportFragmentManager.findFragmentById(R.id.activity_tractor_locator_map_fragment) as SupportMapFragment).getMapAsync(this)
    }

    /**
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mapOperations.googleMap = googleMap
        presenter.onMapReady()
    }

    override fun addMapField(fieldViewModel: FieldViewModel) {
        mapOperations.addField(fieldViewModel.coordinates)
    }

    override fun addMapDriver(driverViewModel: DriverViewModel) {
        mapOperations.addMarker(driverViewModel.name, driverViewModel.lastSeenDate, driverViewModel.coordinate)
    }

    override fun moveCamera(coordinate: CoordinateViewModel) {
        mapOperations.moveCamera(coordinate)
    }

    override fun showLoading() {
        loadingView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingView.visibility = View.GONE
    }

}
