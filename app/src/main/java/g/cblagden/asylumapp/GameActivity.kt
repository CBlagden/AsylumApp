package g.cblagden.asylumapp

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.content_game.*

class GameActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private var locationName = ""
    private var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameToolbar.title = "BACK"
        gameToolbar.setBackgroundColor(Color.DKGRAY)
        gameToolbar.setOnClickListener {
            super.onBackPressed()
        }
        setSupportActionBar(gameToolbar)

        locationName = intent.getStringExtra(Constants.location)
        name = intent.getStringExtra(Constants.name)
        val theme = intent.getStringExtra(Constants.theme)
        val score = intent.getStringExtra(Constants.score)
        val date = intent.getStringExtra(Constants.date)
        val time = intent.getStringExtra(Constants.time)
        val mapFragment = this.supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        scoreTextView.text = score
        themeTextView.text = "THEME: $theme"
        dateTextView.text = "$date @ $time"
        titleTextView.text = name + " vs. Servite"
        locationTextView.text = locationName
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val latitude = intent.getDoubleExtra(Constants.latitude, 0.0)
        val longitude = intent.getDoubleExtra(Constants.longitude, 0.0)
        val location = LatLng(latitude, longitude)
        mMap.addMarker(MarkerOptions().position(location).title(locationName))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14f), 2000, null)
    }

}
