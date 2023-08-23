package com.example.sqllite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.sqllite.adapters.MyContactAdapter
import com.example.sqllite.adapters.RvAction
import com.example.sqllite.databinding.ActivityMainBinding
import com.example.sqllite.databinding.ItemDialogBinding
import com.example.sqllite.db.MyDbHelper
import com.example.sqllite.models.MyContact

///object ochib constanta qilib olsang mydbhelperdagi narsalaniosonro boladi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var list = ArrayList<MyContact>()
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var myContactAdapter: MyContactAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()
        binding.apply {
            btn.setOnClickListener {
                val dialog = AlertDialog.Builder(this@MainActivity).create()
                val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
                dialog.setView(itemDialogBinding.root)
                dialog.show()
                itemDialogBinding.btnSave.setOnClickListener {
                    val myContact = MyContact(
                        itemDialogBinding.name.text.toString(),
                        itemDialogBinding.number.text.toString()
                    )
                    myDbHelper.addContact(myContact)
                    loadData()
                    dialog.cancel()
                }
            }
        }
    }

    private fun loadData() {
        list = ArrayList()
        myDbHelper = MyDbHelper(this)
        list.addAll(myDbHelper.getAllContact())
        myContactAdapter = MyContactAdapter(list, object : RvAction {
            override fun deleteContact(myContact: MyContact, position: Int) {
                myDbHelper.deleteContact(myContact)
                list.remove(myContact)
                myContactAdapter.notifyItemRemoved(position)
            }

            override fun updateContact(myContact: MyContact, position: Int) {
                val dialog = AlertDialog.Builder(this@MainActivity).create()
                val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
                dialog.setView(itemDialogBinding.root)
                dialog.show()
                itemDialogBinding.name.setText(myContact.name)
                itemDialogBinding.number.setText(myContact.number)
                itemDialogBinding.btnSave.setOnClickListener {
                    myContact.name = itemDialogBinding.name.text.toString()
                    myContact.number = itemDialogBinding.number.text.toString()
                    myDbHelper.updateContact(myContact)
                    list[position] = myContact
                    myContactAdapter.notifyItemChanged(position)
                    dialog.cancel()
                }

            }
        })
        binding.rv.adapter = myContactAdapter
    }
}