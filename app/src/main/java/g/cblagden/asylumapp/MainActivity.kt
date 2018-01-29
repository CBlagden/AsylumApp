package g.cblagden.asylumapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by CBlagden on 1/24/2018.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var gamesItem: AHBottomNavigationItem
    private lateinit var twitterItem: AHBottomNavigationItem
    private lateinit var infoItem: AHBottomNavigationItem
    private lateinit var database: FirebaseDatabase
    private lateinit var gamesRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLoad()
        bottom_navigation.setOnTabSelectedListener(
                { position, _ ->
                    when (position) {
                        0 -> {
                            reset()
                            webview.visibility = View.VISIBLE
                            true
                        }
                        1 -> {
                            reset()
                            gamesLinearLayout.visibility = View.VISIBLE
                            true
                        }
                        2 -> {
                            reset()
                            coordinator.background = resources.getDrawable(R.drawable.info_background)
                            true
                        }
                        else -> false
                    }
                })
    }

    private fun initLoad() {
        FirebaseMessaging.getInstance().subscribeToTopic("all")
        loadNavigationBar()
        loadGames()
        loadWebview()
    }

    private fun loadNavigationBar() {
        val gamesIcon = resources.getDrawable(R.drawable.games_icon)
        gamesItem = AHBottomNavigationItem("Games", gamesIcon)
        val twitterIcon = resources.getDrawable(R.drawable.twitter_icon)
        twitterItem = AHBottomNavigationItem("Twitter",
                twitterIcon)
        val infoIcon = resources.getDrawable(R.drawable.info_icon)
        infoItem = AHBottomNavigationItem("Info", infoIcon)
        bottom_navigation.addItem(twitterItem)
        bottom_navigation.addItem(gamesItem)
        bottom_navigation.addItem(infoItem)
        bottom_navigation.setBackgroundColor(Color.GRAY)
        bottom_navigation.currentItem = 1
        bottom_navigation.accentColor = Color.BLACK
        bottom_navigation.inactiveColor = Color.DKGRAY
    }

    private fun loadGames() {

        database = FirebaseDatabase.getInstance()
        gamesRef = database.getReference(Constants.games)
        gamesRef.addValueEventListener(object : ValueEventListener {

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                gamesLinearLayout.removeAllViews()
                for (game in dataSnapshot.children) {
                    val name = game.key
                    var location = ""
                    var latitude = 0.0
                    var longitude = 0.0
                    var inputDate = ""
                    var score  = ""
                    var theme = ""
                    var time = ""
                    for (data in game.children) {
                        val key = data.key
                        val value = data.value
                        when (key) {
                            Constants.location -> location = value.toString()
                            Constants.latitude -> latitude = value.toString().toDouble()
                            Constants.longitude -> longitude = value.toString().toDouble()
                            Constants.date -> inputDate = value.toString()
                            Constants.score -> score = value.toString()
                            Constants.theme -> theme = value.toString()
                            Constants.time -> time = value.toString()
                        }
                    }

                    val newGameButton = Button(applicationContext)
                    newGameButton.setAllCaps(true)
                    newGameButton.text = "$name vs. Servite"
                    newGameButton.setTextColor(Color.WHITE)
                    newGameButton.gravity = Gravity.LEFT
                    newGameButton.alpha = 0.8f
                    newGameButton.textSize = 25f
                    newGameButton.setBackgroundColor(Color.TRANSPARENT)
                    newGameButton.setPadding(100, 50, 0, 0)
                    newGameButton.setOnClickListener {
                        val intent = Intent(applicationContext, GameActivity::class.java)
                        intent.putExtra(Constants.name, name)
                        intent.putExtra(Constants.location, location)
                        intent.putExtra(Constants.latitude, latitude)
                        intent.putExtra(Constants.longitude, longitude)
                        intent.putExtra(Constants.date, inputDate)
                        intent.putExtra(Constants.score, score)
                        intent.putExtra(Constants.theme, theme)
                        intent.putExtra(Constants.time, time)
                        startActivity(intent)
                    }

                    val eventInfo = TextView(applicationContext)
                    eventInfo.text = "$inputDate | $location"
                    eventInfo.textSize = 16f
                    eventInfo.setBackgroundColor(Color.TRANSPARENT)
                    eventInfo.setTextColor(Color.WHITE)
                    eventInfo.setPadding(100, 25, 0, 0)
                    eventInfo.setAllCaps(true)
                    eventInfo.gravity = Gravity.LEFT

                    gamesLinearLayout.addView(newGameButton)
                    gamesLinearLayout.addView(eventInfo)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("database error", databaseError.message)
            }

        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebview() {
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = WebViewClient()
        webview.loadUrl("https://twitter.com/serviteasylum")
        webview.visibility = View.GONE
    }

    private fun reset() {
        coordinator.background = resources.getDrawable(R.drawable.asylum_background)
        webview.visibility = View.GONE
        gamesLinearLayout.visibility = View.GONE
    }

}

