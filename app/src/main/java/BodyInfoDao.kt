import androidx.room.Dao
import androidx.room.Query

@Dao
interface BodyInfoDao{
    @Query("SELECT * FROM BodyInfoEntity WHERE dateMonth == :month")
    fun searchName(month: Int): List<BodyInfoEntity>
}