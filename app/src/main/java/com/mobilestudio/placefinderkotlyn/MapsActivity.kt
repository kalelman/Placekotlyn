package com.mobilestudio.placefinderkotlyn

import android.net.Uri
import android.support.v4.app.FragmentActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.JsonParseException
import mx.mobilestudio.placefinderkotlin.model.ApiFourSquareResponse
import mx.mobilestudio.placefinderkotlin.model.Venue

class MapsActivity : FragmentActivity(), OnMapReadyCallback, Response.Listener<String> , Response.ErrorListener {


    private lateinit var  mMap: GoogleMap

    var latitud: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val mobileStudio = LatLng(19.395209, -99.1544203)
        mMap!!.addMarker(MarkerOptions().position(mobileStudio).title("Marker in Mobile Studio")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(mobileStudio,13f))

        callFourSquareApi("bar")

    }

    private fun callFourSquareApi(query : String ){
        val queue = Volley.newRequestQueue(this) // inicializamos queue
        val response: String? = null
        val finalResponse = response

        val locationStringF = "19.395209" + "," + "-99.1544203"

        val URL = Uri.parse("https://api.foursquare.com/v2/venues/search").buildUpon()

                .appendQueryParameter("client_id", "L4UK14EMS0MCEZOVVUYX2UO5ULFHJN3EHOFVQFSW0Z1MSFSR")

                .appendQueryParameter("client_secret", "YKJB0JRFDPPSGTHALFOEP5O1NDDATHKQ2IZ5RO2GOX452SFA").appendQueryParameter("v", "20130815").appendQueryParameter("ll", locationStringF)

                .appendQueryParameter("query", query).build().toString()

        val myFourSquareRequest =   StringRequest(Request.Method.GET,URL,this,this)

        queue.add(myFourSquareRequest)

    }

    override fun onErrorResponse(p0: VolleyError?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResponse(response: String?) {
        val gson = Gson()

        try {
            // EN ESTA LINEA DE CODIGO EFECTUO  EL PARSEO DEL JSON (TEXTO PLANO) A MIS CLASES KOTLYN

            val apiFourSquareResponse = gson.fromJson<ApiFourSquareResponse>(response, ApiFourSquareResponse::class.java!!)
            var unwrappedVenues : List<Venue>? = null

            if (response != null) {
                Toast.makeText(this, "response", Toast.LENGTH_LONG).show();

                // Force unWrap
                //unwrappedVenues = apiFourSquareResponse.response!!.venues // el truco para realiza para un uwrap en poner dos signos de admiracion !! (unwrap forzoso)

                // para hacer el unwrapp inseguro
                val unwrappedResponse = apiFourSquareResponse.response?.let { it } ?: return

                unwrappedVenues = unwrappedResponse.venues?.let { it } ?: return

            }


            piantFourSquareMarkersinMap(unwrappedVenues)

        } catch (e: JsonParseException) {

        }

    }

    fun piantFourSquareMarkersinMap(venues: List<Venue>?) {


        for (currentPlace in venues!!) {

            val lat = currentPlace.location!!.lat

            val lon = currentPlace.location!!.lng

            val myOptions = MarkerOptions()

            myOptions.position(LatLng(lat as Double, lon as Double))

            myOptions.title(currentPlace.name)

            mMap?.addMarker(myOptions)

        }

    }


}
