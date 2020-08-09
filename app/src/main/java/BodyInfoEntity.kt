import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*
import java.util.Calendar.DAY_OF_MONTH

@Entity
data class BodyInfoEntity constructor(
    val bodyWeight: Int,
    val bodyFatPercentage:Int,
    @PrimaryKey val infoDate: Calendar,
    val commentList: List<String>,
    val imageId: Int?): Serializable{
    val dateMonth = infoDate.get(DAY_OF_MONTH)
}