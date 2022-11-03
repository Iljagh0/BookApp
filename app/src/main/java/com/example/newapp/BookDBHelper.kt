package com.example.newapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class BookDBHelper(context: Context): SQLiteOpenHelper(context, Config.DB_NAME, null ,Config.DB_VERSION ) {
    override fun onCreate(db: SQLiteDatabase){
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1:Int, p2:Int){
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    companion object {
        private const val CREATE_TABLE = "CREATE TABLE ${BookDesc.TABLE_NAME} (" +
                "${BookDesc.BookColumns.PRICE} INTEGER PRIMARY KEY," +
                "${BookDesc.BookColumns.NAME} TEXT," +
                "${BookDesc.BookColumns.PAGES} REAL,"


        private const val DROP_TABLE = "DROP TABLE IF EXISTS ${BookDesc.TABLE_NAME}"


    }

    fun insert(carName: String, engine: Float, yearOfRelease: Int){
        val cv = ContentValues().apply{
            this.put(BookDesc.BookColumns.NAME, bookName)
            this.put(BookDesc.BookColumns.PRICE, price)
            this.put(BookDesc.BookColumns.PAGES, pages)
        }
        writableDatabase.insert(BookDesc.TABLE_NAME,  null, cv)
    }

    fun updateCarName (carId: Long, carName: String){
        val cv = ContentValues().apply {
            this.put(BookDesc.BookColumns.NAME, bookName)
        }
        val where = "${BookDesc.BookColumns.PRICE} = ?"
        val whereArgs = arrayOf(price.toString())
        writableDatabase.update(BookDesc.TABLE_NAME, cv, where , whereArgs)
    }
    fun deleteCar(carId: Long){
        val where = "${BookDesc.BookColumns.PRICE} = ?"
        val whereArgs = arrayOf(carId.toString())
        writableDatabase.delete(BookDesc.TABLE_NAME, where, whereArgs)
    }
    fun deleteAll(){

        writableDatabase.delete(BookDesc.TABLE_NAME, null, null)
    }

    @SuppressLint("Range")
    fun selectOver(yearOfRelease: Int) {
        val projection = arrayOf(
            BookDesc.BookColumns.PRICE,
            BookDesc.BookColumns.NAME,
            BookDesc.BookColumns.PAGES,

        )
        val where = "${BookDesc.BookColumns.PRICE} > ?"
        val whereArgs = arrayOf(yearOfRelease.toString())
        val orderBy = "${BookDesc.BookColumns.PRICE}"
        val cursor = readableDatabase.query(
            BookDesc.TABLE_NAME,
            projection,
            where,
            whereArgs,
            null,
            null,
            orderBy
        )

        while (cursor.moveToNext()){
            val name = cursor.getString(cursor.getColumnIndex(BookDesc.BookColumns.NAME))
            val engine = cursor.getFloat(cursor.getColumnIndex(BookDesc.BookColumns.PRICE))
            val id = cursor.getFloat(cursor.getColumnIndex(BookDesc.BookColumns.PAGES))
            Log.d("text", "selectOver: $name, $price, $page")
        }
    }


}