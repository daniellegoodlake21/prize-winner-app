package danielle.projects.prizewinnergame

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PrizeDao {
    @Insert
    suspend fun insert(prizeEntity: PrizeEntity)

    @Update
    suspend fun update(prizeEntity: PrizeEntity)

    @Delete
    suspend fun delete(prizeEntity: PrizeEntity)

    @Query("SELECT * FROM `prize-table`")
    fun fetchAllPrizes(): Flow<List<PrizeEntity>>

    @Query("SELECT * FROM `prize-table` WHERE `prize-id`=:id")
    fun fetchPrizeById(id:Int):Flow<PrizeEntity>

    @Query("INSERT INTO `prize-table` (title) VALUES ('Default Prize Title')")
    suspend fun insertDefaultPrize()

    @Query("SELECT COUNT(`prize-id`) FROM `prize-table`")
    fun fetchPrizeCount():Flow<Int>

}