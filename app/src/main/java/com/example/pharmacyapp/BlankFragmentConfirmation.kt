package com.example.pharmacyapp

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class BlankFragmentConfirmation : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var driverMarker: Marker? = null
    private var clientMarker: Marker? = null

    private val driverName = "driver1" // Nom du livreur
    private val TAG = "BlankFragmentConfirmation"
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blank_confirmation, container, false)

        Log.d(TAG, "Fragment view is being created.")

        // Initialiser FusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        // Initialiser la MapView
        mapView = view.findViewById(R.id.driverMap)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Message de confirmation
        val confirmationMessage: TextView = view.findViewById(R.id.confirmationText)
        confirmationMessage.text = "Votre commande sera reçue dans quelques minutes."

        return view
    }

    private fun listenForDriverLocationUpdates() {
        Log.d(TAG, "Listening for driver location updates...")
        val database = FirebaseDatabase.getInstance("https://devpha-9007b-default-rtdb.europe-west1.firebasedatabase.app/")
        val ordersRef = database.getReference("delivery_locations")

        ordersRef.child(driverName).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG, "Data received from Firebase.")

                val latitude = snapshot.child("latitude").getValue(Double::class.java)
                val longitude = snapshot.child("longitude").getValue(Double::class.java)

                if (latitude != null && longitude != null) {
                    val driverLocation = LatLng(latitude, longitude)
                    Log.d(TAG, "Driver location: ($latitude, $longitude)")

                    updateDriverMarker(driverLocation)
                } else {
                    Log.e(TAG, "Invalid location data: latitude or longitude is null.")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Firebase error: ${error.message}")
            }
        })
    }
    private fun getScaledBitmap(resourceId: Int, width: Int, height: Int): Bitmap {
        val bitmap = BitmapFactory.decodeResource(resources, resourceId)
        return Bitmap.createScaledBitmap(bitmap, width, height, false)
    }


    private fun updateDriverMarker(location: LatLng) {
        if (googleMap == null) {
            Log.e(TAG, "GoogleMap is not initialized yet.")
            return
        }

        if (driverMarker == null) {
            // Redimensionner l'icône à une taille appropriée (par exemple 100x100 pixels)
            val driverIcon = BitmapDescriptorFactory.fromBitmap(getScaledBitmap(R.drawable.moteurrm, 150, 200))

            // Créer un marqueur pour le livreur avec l'icône redimensionnée
            driverMarker = googleMap?.addMarker(
                MarkerOptions()
                    .position(location)
                    .title("Position de $driverName")
                    .icon(driverIcon)
            )
            Log.d(TAG, "Driver marker created.")
        } else {
            // Mettre à jour la position du marqueur
            driverMarker?.position = location
            Log.d(TAG, "Driver marker updated.")
        }

        // Centrer la caméra sur le livreur
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14f))
    }



    private fun updateClientMarker(location: LatLng) {
        if (googleMap == null) {
            Log.e(TAG, "GoogleMap is not initialized yet.")
            return
        }

        if (clientMarker == null) {
            // Créer un marqueur pour le client
            clientMarker = googleMap?.addMarker(
                MarkerOptions()
                    .position(location)
                    .title("Votre position")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )
            Log.d(TAG, "Client marker created.")
        } else {
            // Mettre à jour la position du marqueur
            clientMarker?.position = location
            Log.d(TAG, "Client marker updated.")
        }
    }

    private fun getClientLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val clientLocation = LatLng(location.latitude, location.longitude)
                        Log.d(TAG, "Client location: (${location.latitude}, ${location.longitude})")

                        updateClientMarker(clientLocation)
                        // Centrer la caméra sur la position du client
                        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(clientLocation, 14f))
                    } else {
                        Log.e(TAG, "Client location is null.")
                    }
                }
        } else {
            // Demander la permission si elle n'est pas accordée
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onMapReady(map: GoogleMap) {
        Log.d(TAG, "Map is ready.")
        googleMap = map
        googleMap?.uiSettings?.isZoomControlsEnabled = true
        listenForDriverLocationUpdates()

        // Récupérer la localisation actuelle du client
        getClientLocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission accordée, récupérer la localisation du client
                getClientLocation()
            } else {
                Log.e(TAG, "Location permission denied")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}