import java.util.*

class BodyInfo(val bodyWeight: Int, val bodyFatPercentage:Int,
               val infoDate: Date,_commentList: List<String>){
    val commentList:List<String> = _commentList.toList()
}