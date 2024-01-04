package com.github2136.ledger.model.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github2136.ledger.common.Constants
import com.github2136.ledger.model.entity.Record

@Database(
    entities = [
        Record::class
    ],
    version = 1,
    // autoMigrations = [AutoMigration(from = 4, to = 5, spec = DB4_5::class)]
)
@TypeConverters(Converters::class)
abstract class DBHelper : RoomDatabase() {
    abstract fun recordDao(): RecordDao

    companion object {
        @Volatile
        private var instance: DBHelper? = null

        fun getInstance(context: Context): DBHelper {
            if (instance == null) {
                synchronized(DBHelper::class) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(context, DBHelper::class.java, Constants.SQLITE_NAME)
                            // .addMigrations(object : Migration(1, 2) {
                            //     override fun migrate(database: SupportSQLiteDatabase) {
                            //         database.execSQL("ALTER TABLE PatrolTrailFlag ADD COLUMN Id Text NOT NULL DEFAULT '';")
                            //     }
                            // })
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return instance!!
        }
    }
}

// @DeleteColumn(tableName = "PatrolTrack", columnName = "SSId")
// @DeleteColumn(tableName = "PatrolTrack", columnName = "TaskId")
// @RenameColumn(tableName = "PatrolTrack", fromColumnName = "_extra", toColumnName = "_extra0")
// class DB4_5 : AutoMigrationSpec {}