import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class BodyInfo constructor(
    val bodyWeight: Int,
    val bodyFatPercentage:Int,
    @PrimaryKey val infoDate: Calendar,
    val commentList: List<String>,
    val imageId: Int?): Serializable{
}