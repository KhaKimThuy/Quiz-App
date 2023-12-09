package com.example.afinal.Dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.afinal.Activity.MainActivity2
import com.example.afinal.DAL.FolderDAL
import com.example.afinal.DAL.MyDB
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.R

class FolderDialog(
    title: String,
    activity: MainActivity2) : AppCompatDialogFragment() {
    private lateinit var folderName : EditText
    private lateinit var folderDesc : EditText
    private lateinit var db : MyDB
    private val title = title
    private val activity = activity

    @SuppressLint("MissingInflatedId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        db = MyDB()
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.add_folder_dialog, null)

        folderName = view.findViewById(R.id.ed_folderName)
        folderDesc = view.findViewById(R.id.ed_folderDesc)

        builder.setView(view)
            .setTitle(title)
            .setNegativeButton("HỦY") { _, _ ->
            }
            .setPositiveButton("OK") { dialog, _ ->
                if (!TextUtils.isEmpty(folderName.text)) {
                    addFolder()
                } else {
                    Toast.makeText(activity, "Tên folder không được trống", Toast.LENGTH_SHORT).show()
                    folderName.error = "Tên folder không được trống"
                    folderName.requestFocus()
                }
            }
//        if (fName.isNotEmpty()) {
//            folderName.setText(fName)
//            folderDesc.setText(fDes)
//        }
        return builder.create()
    }

//    private fun editFolder() {
//        if (activity != null) {
//            val folder = activity?.curFolder
//            if (folder != null) {
//                if (folderName.text.toString() != folder.folderName  ||
//                    folderDesc.text.toString() != folder.folderDesc)
//                {
//                    db.EditFolder(folder, folderName.text.toString(), folderDesc.text.toString())
//                    folder.folderName = folderName.text.toString()
//                    folder.folderDesc = folderDesc.text.toString()
//                    activity.updateUI()
//                }
//            }
//        }
//    }

    private fun addFolder(){
        var folder = FolderDomain()
        folder.folderName = folderName.text.toString()
        folder.folderDesc = folderDesc.text.toString()
        FolderDAL().AddFolder(folder, activity)
    }
}