package com.toolslab.tractorlocator.maps

import android.support.annotation.VisibleForTesting
import android.text.format.DateUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.toolslab.tractorlocator.R
import com.toolslab.tractorlocator.base_repository.model.CoordinateViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

internal class MapOperations @Inject constructor() {

    private val zoom = 11.5F

    @VisibleForTesting
    internal val fieldColor = R.color.green

    @VisibleForTesting
    internal lateinit var googleMap: GoogleMap

    @Inject
    internal lateinit var resourceOperations: ResourceOperations

    internal fun addField(coordinates: List<CoordinateViewModel>) {
        val polygonOptions = PolygonOptions()
        polygonOptions.fillColor(resourceOperations.color(fieldColor))
        polygonOptions.strokeWidth(0F)
        coordinates.forEach {
            polygonOptions.add(LatLng(it.latitude, it.longitude))
        }
        googleMap.addPolygon(polygonOptions)
    }

    internal fun addMarker(title: String, snippet: String, coordinate: CoordinateViewModel) {
        googleMap.addMarker(MarkerOptions()
                .title(title)
                .snippet(extractRelativeTimeSpan(snippet))
                .icon(BitmapDescriptorFactory.fromBitmap(resourceOperations.resourceAsBitmap(R.drawable.ic_launcher_foreground)))
                .position(LatLng(coordinate.latitude, coordinate.longitude)))
    }

    internal fun moveCamera(coordinate: CoordinateViewModel) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(coordinate.latitude, coordinate.longitude), zoom))
    }

    @VisibleForTesting
    internal fun extractRelativeTimeSpan(snippet: String): String {
        return try {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
            val time = simpleDateFormat.parse(snippet).time
            DateUtils.getRelativeTimeSpanString(time) as String
        } catch (e: Exception) {
            Timber.e(e)
            ""
        }
    }

}
