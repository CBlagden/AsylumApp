package g.cblagden.asylumapp

/**
 * Created by CBlagden on 1/30/2018.
 */
class GameTreeItem {
    var name: String?
    var location: String?
    var longitude: Double?
    var latitude: Double?
    var date: String?
    var theme: String?
    var time: String?
    var score: String?

    constructor(name: String?, location: String?, latitude: Double?, longitude: Double?, date: String?,
                theme: String?, time: String?, score: String?) {
        this.location = location
        this.longitude = longitude
        this.latitude = latitude
        this.date = date
        this.theme = theme
        this.time = time
        this.name = name
        this.score = score
    }

    override fun toString(): String {
        return "name: $name location: $location latitude: $latitude longitude: $longitude " +
                "date: $date theme: $theme time: $time score: $score"
    }
}