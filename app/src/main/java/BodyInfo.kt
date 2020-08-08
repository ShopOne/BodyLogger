import java.io.Serializable
import java.util.*

class BodyInfo(val bodyWeight: Int, val bodyFatPercentage:Int,
               val infoDate: Calendar,_commentList: List<String>,
               val imageId: Int?): Serializable{
    val commentList:List<String> = _commentList.toList()
}