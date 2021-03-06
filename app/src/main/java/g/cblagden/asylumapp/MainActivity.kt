package g.cblagden.asylumapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import android.widget.TextView
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.unnamed.b.atv.model.TreeNode
import com.unnamed.b.atv.view.AndroidTreeView
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
                            infoLinearLayout.visibility = View.VISIBLE
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
        loadInfoPage()
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

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                gamesLinearLayout.removeAllViews()
                val root = TreeNode.root()
                for (game in dataSnapshot.children) {
                    val name = game.key
                    var location = ""
                    var latitude = 0.0
                    var longitude = 0.0
                    var inputDate = ""
                    var score = ""
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
                    val gameTreeItem = GameTreeItem(
                            name,
                            location,
                            latitude,
                            longitude,
                            inputDate,
                            theme,
                            time,
                            score)

                    val treeNode = TreeNode(gameTreeItem).setViewHolder(GameHolder(applicationContext))
                    root.addChild(treeNode)
                }
                val treeView = AndroidTreeView(applicationContext, root)
                root.setClickListener({
                    node, value ->
                    treeView.expandLevel(node.level)
                })
                gamesLinearLayout.addView(treeView.view)
            }


            override fun onCancelled(databaseError: DatabaseError) {
                val errorMess = TextView(applicationContext)
                errorMess.setPadding(100, 25, 0,0)
                errorMess.textSize = 25f
                errorMess.text = "Could not load games. Please connect to Internet."
                errorMess.setTextColor(Color.WHITE)
                gamesLinearLayout.addView(errorMess)
            }

        })
    }

    private fun loadInfoPage() {

        val titleTextView = TextView(applicationContext)
        titleTextView.textSize = 35f
        titleTextView.setAllCaps(true)
        titleTextView.setPadding(100, 25, 0, 0)
        titleTextView.text = "info"
        titleTextView.text
        titleTextView.setTextColor(Color.WHITE)

        val subTitleTextView = TextView(applicationContext)
        subTitleTextView.text = "created by campus life"
        subTitleTextView.textSize = 30f
        subTitleTextView.setTextColor(Color.WHITE)
        subTitleTextView.setAllCaps(true)
        subTitleTextView.setPadding(100, 25, 0, 0)
        subTitleTextView.setTypeface(subTitleTextView.typeface, Typeface.BOLD)

        val producedTextView = TextView(applicationContext)
        producedTextView.text = "Programmer"
        producedTextView.setPadding(100, 25, 0, 0)
        producedTextView.textSize = 30f
        producedTextView.setAllCaps(true)
        producedTextView.setTypeface(producedTextView.typeface, Typeface.BOLD)
        producedTextView.setTextColor(Color.WHITE)

        val producerTextView = TextView(applicationContext)
        producerTextView.text = "Chase Blagden"
        producerTextView.textSize = 30f
        producerTextView.setAllCaps(true)
        producerTextView.setTextColor(Color.WHITE)
        producerTextView.setPadding(150, 25, 0, 0)

        val photoTextView = TextView(applicationContext)
        photoTextView.text = "Photos"
        photoTextView.setTypeface(producedTextView.typeface, Typeface.BOLD)
        photoTextView.textSize = 30f
        photoTextView.setAllCaps(true)
        photoTextView.setTextColor(Color.WHITE)
        photoTextView.setPadding(100, 25, 0,0 )

        val photoTakerTextView = TextView(applicationContext)
        photoTakerTextView.textSize = 30f
        photoTakerTextView.text = "mmsportsphoto"
        photoTakerTextView.setAllCaps(true)
        photoTakerTextView.setPadding(150, 25, 0, 0)
        photoTakerTextView.setTextColor(Color.WHITE)

        infoLinearLayout.addView(titleTextView)
        infoLinearLayout.addView(subTitleTextView)
        infoLinearLayout.addView(producedTextView)
        infoLinearLayout.addView(producerTextView)
        infoLinearLayout.addView(photoTextView)
        infoLinearLayout.addView(photoTakerTextView)
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
        infoLinearLayout.visibility = View.GONE
    }

}

