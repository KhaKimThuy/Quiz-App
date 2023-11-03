package com.example.afinal.Dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.afinal.Activity.DetailFolderActivity
import com.example.afinal.Activity.FragmentLibrary
import com.example.afinal.Activity.MainActivity2
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AddFolderDialog : AppCompatDialogFragment() {
    private lateinit var folderName : EditText
    private lateinit var folderDesc : EditText
    private lateinit var db : MyDB

    @SuppressLint("MissingInflatedId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        db = MyDB()
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.add_folder_dialog, null)
        builder.setView(view)
            .setTitle("Tạo thư mục")
            .setNegativeButton("HỦY") { _, _ ->
            }
            .setPositiveButton("OK") { _, _ ->
                addFolder()
//                val intent = Intent(activity, DetailFolderActivity::class.java)
//                startActivity(intent)
                Toast.makeText(activity, "Created folder", Toast.LENGTH_SHORT).show();
            }
        folderName = view.findViewById(R.id.ed_folderName)
        folderDesc = view.findViewById(R.id.ed_folderDesc)
        return builder.create()
    }
    private fun addFolder(){
        val folderRef = db.GetFolder()
        val folder = FolderDomain()
        folder.folderName = folderName.text.toString()
        folder.folderPK = folderRef.push().key!!

        folderRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (folder.folderPK?.let { snapshot.hasChild(it) } == true){
                    Toast.makeText(activity,"Folder name already exists", Toast.LENGTH_SHORT).show()
                }else{
                    if (folder.folderPK != null) {
                        folderRef.child(folder.folderPK).setValue(folder)
                    }
                    var intent = Intent(activity, FragmentLibrary::class.java)
                    startActivity(intent)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity,"System error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}