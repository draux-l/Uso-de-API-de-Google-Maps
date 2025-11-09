package com.example.lab_12_moviles

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.room.util.copy
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen() {
    val arequipaLocation = LatLng(-16.4040102, -71.559611)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(arequipaLocation, 12f)
    }

    // Variable de estado para tipo de mapa
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.NORMAL
            )
        )
    }

    // Lista de ubicaciones adicionales
    val locations = listOf(
        LatLng(-16.433415, -71.5442652),
        LatLng(-16.4205151, -71.4945209),
        LatLng(-16.3524187, -71.5675994)
    )

    Column {
        // Botones para cambiar tipo de mapa
        Row {
            Button(onClick = { mapProperties = mapProperties.copy(mapType = MapType.NORMAL) }) {
                Text("Normal")
            }
            Button(onClick = { mapProperties = mapProperties.copy(mapType = MapType.HYBRID) }) {
                Text("Híbrido")
            }
            Button(onClick = { mapProperties = mapProperties.copy(mapType = MapType.TERRAIN) }) {
                Text("Terreno")
            }
            Button(onClick = { mapProperties = mapProperties.copy(mapType = MapType.SATELLITE) }) {
                Text("Satélite")
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = mapProperties
            ) {
                // MARCADOR CON ICONO
                Marker(
                    state = rememberMarkerState(position = arequipaLocation),
                    title = "Arequipa, Perú",
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.icono_montania)
                )
                locations.forEach { location ->
                    Marker(
                        state = rememberMarkerState(position = location),
                        title = "Ubicación",
                        snippet = "Punto de interés"
                    )
                }
            }
        }
    }
}
