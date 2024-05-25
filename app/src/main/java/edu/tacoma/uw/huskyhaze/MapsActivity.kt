/**
 * Team 3 - TCSS 450 - Spring 2024
 */
package edu.tacoma.uw.huskyhaze

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * The MapsActivity class displays a map and allows users to interact with it.
 * Users can search for coordinates or click on the map to select a location.
 */
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private var gMap: GoogleMap? = null
    private var locationTextView: TextView? = null
    private var latitude = 47.24 // Default latitude
    private var longitude = -122.43 // Default longitude

    /**
     * Called when the activity is first created. Initializes the map and sets up the search view.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_maps)

        // Retrieve the passed latitude and longitude
        latitude = intent.getDoubleExtra("latitude", 47.24)
        longitude = intent.getDoubleExtra("longitude", -122.43)

        locationTextView = findViewById(R.id.locationTextView)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById<View>(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Initialize the map fragment and set the callback
        val mapFragment = supportFragmentManager.findFragmentById(R.id.id_map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        // Set up the search view for entering coordinates
        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val coordinates = it.split(",")
                    if (coordinates.size == 2) {
                        try {
                            val lat = coordinates[0].trim().toDouble()
                            val lng = coordinates[1].trim().toDouble()
                            updateMapLocation(lat, lng)
                        } catch (e: NumberFormatException) {
                            showErrorDialog("Invalid coordinates format. Please enter in the format: lat,lng")
                        }
                    } else {
                        showErrorDialog("Invalid input. Please enter coordinates in the format: lat,lng")
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
    /**
     * Updates the map location to the specified latitude and longitude, and places a marker there.
     *
     * @param lat The latitude of the new location.
     * @param lng The longitude of the new location.
     */
    private fun updateMapLocation(lat: Double, lng: Double) {
        latitude = lat
        longitude = lng
        val newLocation = LatLng(lat, lng)
        gMap?.clear()
        gMap?.addMarker(MarkerOptions().position(newLocation).title("Selected Location"))
        gMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 12f))
    }

    /**
     * Displays an error dialog with the specified message.
     *
     * @param message The error message to display.
     */
    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    /**
     * Called when the map is ready to be used.
     *
     * @param googleMap The GoogleMap object representing the map.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        val initialLocation = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(initialLocation).title("Selected Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 12f))

        googleMap.setOnMapClickListener { latLng ->
            latitude = latLng.latitude
            longitude = latLng.longitude
            val builder = AlertDialog.Builder(this@MapsActivity)
            builder.setMessage("Do you want to use this location?")
            builder.setPositiveButton("Yes") { dialog, which ->
                // Set latitude and longitude as result data
                val resultIntent = Intent()
                resultIntent.putExtra("latitude", latitude)
                resultIntent.putExtra("longitude", longitude)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
            builder.setNegativeButton("No") { dialog, which -> dialog.dismiss() }
            val dialog = builder.create()
            dialog.show()
        }
    }
}
