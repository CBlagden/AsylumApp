package g.cblagden.asylumapp

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Created by CBlagden on 1/28/2018.
 */
class AsylumIdService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }
}