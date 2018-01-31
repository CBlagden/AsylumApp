package g.cblagden.asylumapp

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import com.unnamed.b.atv.model.TreeNode

/**
 * Created by CBlagden on 1/29/2018.
 */
class GameHolder(context: Context) : TreeNode.BaseNodeViewHolder<GameTreeItem>(context) {

    override fun createNodeView(node: TreeNode?, value: GameTreeItem?): View {
        val gameInfo = TextView(context)
        gameInfo.setPadding(100, 20, 0,0)
        gameInfo.text = value?.name
        gameInfo.setTypeface(gameInfo.typeface, Typeface.BOLD)
        gameInfo.setTextColor(Color.WHITE)
        gameInfo.textSize = 38f
        gameInfo.setBackgroundColor(Color.TRANSPARENT)
        val newNode = TreeNode(value).setViewHolder(GameInfoHolder(context))
        treeView.addNode(node, newNode)
        return gameInfo
    }

}