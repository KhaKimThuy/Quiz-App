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
import com.example.afinal.Fragment.FragmentLibrary
import com.example.afinal.Activity.MainActivity2
import com.example.afinal.DB.MyDB
import com.example.afinal.R

class FolderDialog(
    title: String,
    fName: String = "",
    fDes: String = "",
    activity: DetailFolderActivity? = null) : AppCompatDialogFragment() {
    private lateinit var folderName : EditText
    private lateinit var folderDesc : EditText
    private lateinit var db : MyDB
    private val title = title
    private val fName = fName
    private val fDes = fDes
    private val activity = activity

    @SuppressLint("MissingInflatedId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        db = MyDB()
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.add_folder_dialog, null)
        builder.setView(view)
            .setTitle(title)
            .setNegativeButton("HỦY") { _, _ ->
            }
            .setPositiveButton("OK") { _, _ ->
                if (fName.isEmpty()) {
                    addFolder()
                } else {
                    editFolder()
                }
            }
        folderName = view.findViewById(R.id.ed_folderName)
        folderDesc = view.findViewById(R.id.ed_folderDesc)

        if (fName.isNotEmpty()) {
            folderName.setText(fName)
            folderDesc.setText(fDes)
        }
        return builder.create()
    }

    private fun editFolder() {
        if (activity != null) {
            val folder = activity?.curFolder
            if (folder != null) {
                if (folderName.text.toString() != folder.folderName  ||
                    folderDesc.text.toString() != folder.folderDesc)
                {
                    db.EditFolder(folder, folderName.text.toString(), folderDesc.text.toString())
                    folder.folderName = folderName.text.toString()
                    folder.folderDesc = folderDesc.text.toString()
                    activity.updateUI()
                }
            }
        }
    }

    private fun addFolder(){
        db.CreateFolder(folderName.text.toString(), folderDesc.text.toString())
//        var intent = Intent(activity, MainActivity2::class.java)
//        startActivity(intent)
    }
}