package danielle.projects.prizewinnergame

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prize-table")
data class PrizeEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="prize-id")
    var prizeId: Int = 0,
    var title: String = "",
)
