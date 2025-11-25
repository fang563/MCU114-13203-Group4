package com.example.lab14


//Add these import
// Add these new imports at the top of your file
import com.google.maps.android.PolyUtil
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import kotlinx.coroutines.launch
import com.google.maps.model.TravelMode

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

// 繼承 OnMapReadyCallback
class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    // --- NEW: Add variables for map, polyline, and locations ---
    private var map: GoogleMap? = null
    private var currentPolyline: Polyline? = null
    private val taipei101 = LatLng(25.033611, 121.565000)
    private val taipeiMainStation = LatLng(25.047924, 121.517081)
    // --- END OF NEW VARIABLES ---

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && requestCode == 0) {
            val allGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            if (allGranted) {
                loadMap()
            } else {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- NEW: Setup button click listeners ---
        findViewById<Button>(R.id.btnDriving).setOnClickListener {
            drawRoute(TravelMode.DRIVING)
        }
        findViewById<Button>(R.id.btnWalking).setOnClickListener {
            drawRoute(TravelMode.WALKING)
        }
        findViewById<Button>(R.id.btnBicycling).setOnClickListener {
            drawRoute(TravelMode.BICYCLING)
        }
        // --- END OF BUTTON LISTENERS ---

        loadMap()
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map // --- MODIFIED: Store map instance ---

        val isAccessFineLocationGranted =
            ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        val isAccessCoarseLocationGranted =
            ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        if (isAccessFineLocationGranted && isAccessCoarseLocationGranted) {
            map.isMyLocationEnabled = true

            // Add markers for the locations
            map.addMarker(MarkerOptions().position(taipei101).title("台北101"))
            map.addMarker(MarkerOptions().position(taipeiMainStation).title("台北車站"))

            // --- MODIFIED: Draw the initial route (e.g., DRIVING) ---
            drawRoute(TravelMode.DRIVING)

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.04, 121.54), 13f))
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0
            )
        }
    }

    // --- NEW: Function to draw the route ---
    private fun drawRoute(travelMode: TravelMode) {
        // Ensure map is not null
        val map = this.map ?: return

        // 1. Get API Key
        val key = "com.google.android.geo.API_KEY"
        val apiKey = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            .metaData.getString(key)

        if (apiKey.isNullOrEmpty()) {
            return
        }

        // 2. Create GeoApiContext
        val geoApiContext = GeoApiContext.Builder().apiKey(apiKey).build()

        // 3. Make API call on a background thread
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val directionsResult = DirectionsApi.newRequest(geoApiContext)
                    .origin(com.google.maps.model.LatLng(taipei101.latitude, taipei101.longitude))
                    .destination(com.google.maps.model.LatLng(taipeiMainStation.latitude, taipeiMainStation.longitude))
                    .mode(travelMode) // Use the passed travelMode
                    .await()

                // 5. Switch back to the Main thread to draw on the map
                CoroutineScope(Dispatchers.Main).launch {
                    // Remove the old polyline before drawing a new one
                    currentPolyline?.remove()

                    if (directionsResult.routes.isNotEmpty()) {
                        val route = directionsResult.routes[0]
                        val decodedPath = PolyUtil.decode(route.overviewPolyline.encodedPath)
                        val polylineOptions = PolylineOptions()
                            .addAll(decodedPath)
                            .color(Color.RED)
                            .width(15f)

                        // Add the new polyline and store it
                        currentPolyline = map.addPolyline(polylineOptions)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    // --- END OF NEW FUNCTION ---

    private fun loadMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
}
