package com.example.lab_12_moviles

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapScreen() {

    val context = LocalContext.current
    val activity = context as Activity

    // Solicitar permisos si no están concedidos
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )
    }

    val arequipaLocation = LatLng(-16.4040102, -71.559611)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(arequipaLocation, 12f)
    }

    // Habilita ubicación en el mapa
    var mapProperties by remember {
        mutableStateOf(MapProperties(
            mapType = MapType.NORMAL,
            isMyLocationEnabled = true  // muestra punto azul
        ))
    }

    // Guardar ubicación actual
    var userLocation by remember { mutableStateOf<LatLng?>(null) }

    // Obtener ubicación (GPS / WiFi / triangulación)
    LaunchedEffect(Unit) {
        val fusedClient = LocationServices.getFusedLocationProviderClient(context)

        fusedClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                userLocation = LatLng(location.latitude, location.longitude)
            }
        }
    }


    Column {

        Row {
            Button(onClick = { mapProperties = mapProperties.copy(mapType = MapType.NORMAL) }) { Text("Normal") }
            Button(onClick = { mapProperties = mapProperties.copy(mapType = MapType.HYBRID) }) { Text("Híbrido") }
            Button(onClick = { mapProperties = mapProperties.copy(mapType = MapType.TERRAIN) }) { Text("Terreno") }
            Button(onClick = { mapProperties = mapProperties.copy(mapType = MapType.SATELLITE) }) { Text("Satélite") }
        }

        Box(modifier = Modifier.fillMaxSize()) {

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = mapProperties
            ) {

                Marker(
                    state = rememberMarkerState(position = arequipaLocation),
                    title = "Arequipa, Perú",
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.icono_montania)
                )

                userLocation?.let { location ->
                    Marker(
                        state = rememberMarkerState(position = location),
                        title = "Mi ubicación"
                    )

                    LaunchedEffect(location) {
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.newLatLngZoom(location, 16f),
                            durationMs = 2000
                        )
                    }
                }
            }
        }
    }
}
