package g.cblagden.asylumapp

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.unnamed.b.atv.model.TreeNode

/**
 * Created by CBlagden on 1/29/2018.
 */
class GamesHolder(context: Context) : TreeNode.BaseNodeViewHolder<GameTreeItem>(context) {



    override fun createNodeView(node: TreeNode?, value: GameTreeItem?): View {
        val gameInfo = TextView(context)
        gameInfo.setPadding(100, 25, 0,0)
        gameInfo.text = value?.name
        gameInfo.setTextColor(Color.WHITE)
        gameInfo.textSize = 25f
        gameInfo.setBackgroundColor(Color.TRANSPARENT)
        val newNode = TreeNode(value).setViewHolder(GameInfoHolder(context))
        treeView.addNode(node, newNode)
        return gameInfo
    }

}