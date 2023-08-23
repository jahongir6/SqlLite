package com.example.sqllite.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqllite.db.MyConstant.ID
import com.example.sqllite.db.MyConstant.NAME
import com.example.sqllite.db.MyConstant.NUMBER
import com.example.sqllite.db.MyConstant.TABLE_NAME
import com.example.sqllite.models.MyContact

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, MyConstant.DB_NAME, null, MyConstant.DB_VERSION),
    MyDbInterfase {
    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "create table ${TABLE_NAME} (${ID} integer not null primary key autoincrement,${NAME} text not null,${NUMBER} text not null)"
        db?.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    override fun addContact(myContact: MyContact) {
        val database =this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MyConstant.NAME,myContact.name)
        contentValues.put(MyConstant.NUMBER,myContact.number)
        database.insert(MyConstant.TABLE_NAME,null,contentValues)
        database.close()
    }

    override fun getAllContact(): List<MyContact> {
        val list = ArrayList<MyContact>()
        val query = "select * from ${MyConstant.TABLE_NAME}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query,null)
        if (cursor.moveToFirst()){
            do {
                val myContact = MyContact(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
                )
                list.add(myContact)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun deleteContact(myContact: MyContact) {
        val database = this.writableDatabase
        database.delete(MyConstant.TABLE_NAME,"id=?", arrayOf(myContact.id.toString()))
        database.close()
    }

    override fun updateContact(myContact: MyContact) {
        val database = this.writableDatabase
        val contactValues = ContentValues()
        contactValues.put(MyConstant.ID,myContact.id)
        contactValues.put(MyConstant.NAME,myContact.name)
        contactValues.put(MyConstant.NUMBER,myContact.number)
        database.update(MyConstant.TABLE_NAME,contactValues,"id=?", arrayOf(myContact.id.toString()))
        database.close()
    }
}