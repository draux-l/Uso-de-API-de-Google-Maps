package com.example.lab_12_moviles

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen() {

    val arequipaLocation = LatLng(-16.4040102, -71.559611)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(arequipaLocation, 12f)
    }

    //  Lista de ubicaciones adicionales
    val locations = listOf(
        LatLng(-16.433415, -71.5442652),  // JLByR
        LatLng(-16.4205151, -71.4945209), // Paucarpata
        LatLng(-16.3524187, -71.5675994)  // Zamacola
    )
    // Polilinea entre los tres puntos
    val rutaMarcadores = listOf(
        LatLng(-16.433415, -71.5442652),
        LatLng(-16.4205151, -71.4945209),
        LatLng(-16.3524187, -71.5675994)
    )
    val mallAventuraPolygon = listOf(
        LatLng(-16.432292, -71.509145),
        LatLng(-16.432757, -71.509626),
        LatLng(-16.433013, -71.509310),
        LatLng(-16.432566, -71.508853)
    )

    val parqueLambramaniPolygon = listOf(
        LatLng(-16.422704, -71.530830),
        LatLng(-16.422920, -71.531340),
        LatLng(-16.423264, -71.531110),
        LatLng(-16.423050, -71.530600)
    )

    val plazaDeArmasPolygon = listOf(
        LatLng(-16.398866, -71.536961),
        LatLng(-16.398744, -71.536529),
        LatLng(-16.399178, -71.536289),
        LatLng(-16.399299, -71.536721)
    )

    // Polilinea Principal
    val rutaSimple = listOf(
        LatLng(-16.40, -71.55),
        LatLng(-16.405, -71.545),
        LatLng(-16.41, -71.54)
    )

    val rutaZigZag = listOf(
        LatLng(-16.41, -71.56),
        LatLng(-16.42, -71.55),
        LatLng(-16.41, -71.54),
        LatLng(-16.42, -71.53)
    )



    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {

            // Marcador principal con icono de montaña
            Marker(
                state = rememberMarkerState(position = arequipaLocation),
                title = "Arequipa, Perú",
                icon = BitmapDescriptorFactory.fromResource(R.drawable.icono_montania)
            )

            //  Marcadores adicionales
            locations.forEach { location ->
                Marker(
                    state = rememberMarkerState(position = location),
                    title = "Ubicación",
                    snippet = "Punto de interés"
                )
            }

            // Animacion de camara hacia Yura
            LaunchedEffect(Unit) {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(
                        LatLng(-16.2520984, -71.6836503), 12f
                    ),
                    durationMs = 3000
                )
            }

            //Polilinea entre los 3 marcadores
            Polyline(
                points = rutaMarcadores,
                color = Color.Red,
                width = 12f
            )
            // Poligonos
            Polygon(
                points = plazaDeArmasPolygon,
                strokeColor = Color.Red,
                fillColor = Color.Blue.copy(alpha = 0.3f),
                strokeWidth = 5f
            )
            Polygon(
                points = parqueLambramaniPolygon,
                strokeColor = Color.Green,
                fillColor = Color.Green.copy(alpha = 0.3f),
                strokeWidth = 5f
            )
            Polygon(
                points = mallAventuraPolygon,
                strokeColor = Color.Magenta,
                fillColor = Color.Magenta.copy(alpha = 0.3f),
                strokeWidth = 5f
            )

            // Ruta ZigZag
            Polyline(
                points = rutaZigZag,
                color = Color.Green,
                width = 10f
            )
        }
    }
}
