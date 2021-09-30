package com.ssafy.hwandroidui02_gumi_6_yoonkijae

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.sql.SQLException

class ItemDao {

    lateinit var helper: DBHelper
    lateinit var sqlDB: SQLiteDatabase
    private var mCtx: Context? = null

    fun DbOpenHelper ( context: Context ){
        mCtx = context
    }

    @Throws(SQLException::class)
    fun open() {
        helper = DBHelper(mCtx!! , "newdb.db", null, 1)
        sqlDB = helper.writableDatabase
    }

    fun getItemList() : ArrayList<Item>{
        var res =  ArrayList<Item>()
        for(i in helper.selectAll()){
            res.add(i)
        }
        return res
    }

    fun setItem(item : Item ) {
        helper.insert(item)
    }

    fun updateItem(item : Item ) {
        helper.update(item)
    }
}