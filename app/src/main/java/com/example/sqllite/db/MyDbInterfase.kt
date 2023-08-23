package com.example.sqllite.db

import com.example.sqllite.models.MyContact

interface MyDbInterfase {
    fun addContact(myContact: MyContact)
    fun getAllContact():List<MyContact>
    fun deleteContact(myContact: MyContact)
    fun updateContact(myContact: MyContact)
}