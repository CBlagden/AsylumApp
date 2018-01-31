package g.cblagden.asylumapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.unnamed.b.atv.model.TreeNode


/**
 * Created by CBlagden on 1/30/2018.
 */
class GameInfoHolder(context: Context) : TreeNode.BaseNodeViewHolder<GameTreeItem>(context) {

    override fun createNodeView(node: TreeNode?, value: GameTreeItem?): View {
        val gameInfoLayout = LinearLayout(context)
        gameInfoLayout.orientation = LinearLayout.VERTICAL
        val gameScore = GameInfoTextView(context)
        gameScore.text = value?.score
        val gameTheme = GameInfoTextView(context)
        gameTheme.text = "Theme: ${value?.theme}"
        val gameDate = GameInfoTextView(context)
        gameDate.text = "${value?.date} @ ${value?.time}"
        val gameLocation = GameInfoTextView(context)
        gameLocation.text = value?.location
        val directionsButton = GameInfoTextView(context)
        directionsButton.text = "Directions"
        directionsButton.setOnClickListener {
            val uri = "https://www.google.com/maps/dir/?api=1&destination=${value?.latitude},${value?.longitude}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            context.startActivity(intent)
        }
        gameInfoLayout.addView(gameScore)
        gameInfoLayout.addView(gameTheme)
        gameInfoLayout.addView(gameDate)
        gameInfoLayout.addView(gameLocation)
        gameInfoLayout.addView(directionsButton)
        return gameInfoLayout
    }

    class GameInfoTextView(context: Context) : TextView(context) {

        init {
            setTextColor(Color.WHITE)
            setPadding(140, 12, 0, 0)
            textSize = 27f
            setAllCaps(true)
        }

    }


}