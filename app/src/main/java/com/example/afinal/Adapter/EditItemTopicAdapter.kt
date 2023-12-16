package com.example.afinal.Adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Activity.EditTopicActivity
import com.example.afinal.DAL.ItemDAL
import com.example.afinal.Domain.Item
import com.example.afinal.R
import com.example.afinal.ViewHolder.AddItemTopicHolder
class EditItemTopicAdapter (
    private val itemList:ArrayList<Item>,
    private val activity : EditTopicActivity
) : RecyclerView.Adapter<EditItemTopicAdapter.EditItemTopicHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditItemTopicHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.viewholder_addtopic, parent, false)
        return EditItemTopicHolder(view)
    }

    override fun onBindViewHolder(holder: EditItemTopicHolder, @SuppressLint("RecyclerView") position: Int) {

        // Edit topic activity
        if (!itemList[position].engLanguage.isNullOrEmpty() &&
            !itemList[position].vnLanguage.isNullOrEmpty()) {
            holder.eng_lang.text = itemList[position].engLanguage
            holder.vn_lang.text = itemList[position].vnLanguage
        }

        // Create new item
        holder.eng_lang.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                itemList[position].engLanguage = p0.toString()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                itemList[position].engLanguage = p0.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                itemList[position].engLanguage = s.toString()
            }
        })

        holder.vn_lang.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                itemList[position].vnLanguage = p0.toString()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                itemList[position].vnLanguage = p0.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                itemList[position].vnLanguage = s.toString()
            }
        })

        holder.itemView.setOnLongClickListener(View.OnLongClickListener {

            false
        })

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class EditItemTopicHolder(view : View) : RecyclerView.ViewHolder(view) {
        val eng_lang = view.findViewById<TextView>(R.id.edt_eng)
        val vn_lang = view.findViewById<TextView>(R.id.edt_vn)
        val del = view.findViewById<ImageView>(R.id.imgView_delItem)
        init {
            eng_lang.text = ""
            vn_lang.text = ""
            del.visibility = View.VISIBLE
            del.setOnClickListener(View.OnClickListener {
                ItemDAL().DeleteFC(itemList[adapterPosition])
                itemList.removeAt(adapterPosition)
                notifyDataSetChanged()
            })
        }
    }

//    private fun showOptionsMenu(anchorView: View) {
//        val popupMenu = PopupMenu(this, anchorView)
//        val inflater = popupMenu.menuInflater
//        inflater.inflate(R.menu.item_menu, popupMenu.menu)
//
//        popupMenu.setOnMenuItemClickListener { item ->
//            when (item.itemId) {
//                R.id.delItem -> {
//                    // Handle edit action
//                    itemList.remove()
//                    true
//                }
//                else -> false
//            }
//        }
//        popupMenu.show()
//    }

}


