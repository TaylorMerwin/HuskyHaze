package edu.tacoma.uw.huskyhaze

import android.content.Intent
import android.os.Bundle
import android.view.View
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

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private var gMap: GoogleMap? = null
    private var locationTextView: TextView? = null
    private var latitude = 47.24 // Default latitude
    private var longitude = -122.43 // Default longitude

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_maps)

        // Retrieve the passed latitude and longitude
        latitude = intent.getDoubleExtra("latitude", 47.24)
        longitude = intent.getDoubleExtra("longitude", -122.43)

        locationTextView = findViewById<TextView>(R.id.locationTextView)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById<View>(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.id_map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

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
