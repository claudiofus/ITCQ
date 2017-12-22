package com.software.claudiofus.itcq.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.software.claudiofus.itcq.model.Answer
import com.software.claudiofus.itcq.model.Level
import com.software.claudiofus.itcq.model.Question
import com.software.claudiofus.itcq.model.Score
import com.software.claudiofus.itcq.utility.Strings.DB_NAME
import com.software.claudiofus.itcq.utility.Strings.DB_VERSION
import com.software.claudiofus.itcq.utility.Strings.SQL_FILENAME
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.dropTable

class DbHelper(ctx : Context) : ManagedSQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION)
{
	companion object
	{
		private var instance : DbHelper? = null

		@Synchronized fun getInstance(ctx : Context) : DbHelper
		{
			if (instance == null)
			{
				instance = DbHelper(ctx.applicationContext)
			}
			return instance!!
		}
	}

	override fun onCreate(db : SQLiteDatabase)
	{
		val fileContent = DbHelper::class.java.getResource("/res/raw/" + SQL_FILENAME).readText()
		val queries = fileContent.split(";")
		for (query in queries)
		{
			if (query.isNotEmpty()) db.execSQL(query)
			else print(query)
		}
	}

	override fun onUpgrade(db : SQLiteDatabase, oldVersion : Int, newVersion : Int)
	{
		db.dropTable(Level.TABLE_NAME, true)
		db.dropTable(Question.TABLE_NAME, true)
		db.dropTable(Answer.TABLE_NAME, true)
		db.dropTable(Score.TABLE_NAME, true)
		onCreate(db)
	}
}

// Access property for Context
val Context.database : DbHelper
	get() = DbHelper.getInstance(applicationContext)