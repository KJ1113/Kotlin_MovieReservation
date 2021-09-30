package com.ssafy.hwandroidui02_gumi_6_yoonkijae

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


private const val TAG = "DBHelper_싸피"
private const val TABLE = "mytable"


class DBHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    private lateinit var db: SQLiteDatabase
    override fun onCreate(db: SQLiteDatabase) {
        // 테이블 생성 쿼리
        val query: String = "CREATE TABLE if not exists $TABLE ( _id integer primary key autoincrement, title text ,content text , date text, regist text );";
        db.execSQL(query)
    }

    //  upgrade 가 필요한 경우 기존 테이블 drop 후 onCreate로 새롭게 생성
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql: String = "DROP TABLE if exists $TABLE"
        db.execSQL(sql)
        onCreate(db)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        this.db = db!!
        Log.d(TAG, "onOpen: database 준비 완료")
    }

    fun insert(item: Item) {
        val contentValues = ContentValues()
        contentValues.put("title", item.title)
        contentValues.put("content", item.content)
        contentValues.put("date", item.date)
        contentValues.put("regist", item.regist)

        db.beginTransaction()
        val result = db.insert(TABLE, null, contentValues)

        if (result > 0) {
            db.setTransactionSuccessful()
        }
        db.endTransaction()
    }

    fun list(): String {
        var result = ""

        return result
    }

    fun update(item: Item) {
        val contentValues = ContentValues()
        contentValues.put("title", item.title)
        contentValues.put("content", item.content)
        contentValues.put("date", item.date)
        contentValues.put("regist", item.regist)

        db.beginTransaction()
        val result = db.update(TABLE, contentValues, "_id=?", arrayOf(item.id.toString()))
        if (result > 0) {
            db.setTransactionSuccessful()
        }
        db.endTransaction()
    }

    fun delete(id: String) {

    }

    fun select(id: String): Map<String, Any> {
        var result = mutableMapOf<String, Any>()

        return result
    }

    fun selectAll(): MutableList<Item> {
        var result : MutableList<Item> = arrayListOf()
        val cursor = db.rawQuery("select * from $TABLE", null)
        while (cursor.moveToNext()) {
            var item : Item = Item( cursor.getInt(0), cursor.getString(1) , cursor.getString(2), cursor.getString(3) ,cursor.getString(4))
            result.add(item)
        }
        return result
    }
}