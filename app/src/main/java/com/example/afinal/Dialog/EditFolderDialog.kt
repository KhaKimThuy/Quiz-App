package com.example.afinal.Dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.afinal.Activity.DetailFolderActivity
import com.example.afinal.Activity.MainActivity2
import com.example.afinal.DAL.FolderDAL
import com.example.afinal.DAL.MyDB
import com.example.afinal.DTO.FolderDTO
import com.example.afinal.Domain.Folder
import com.example.afinal.R

class EditFolderDialog(
    title: String,
    folder : Folder,
    activity: DetailFolderActivity) : AppCompatDialogFragment() {
    private lateinit var folderName : EditText
    private lateinit var folderDesc : EditText
    private val title = title
    private val activity = activity
    private var folder = folder

    @SuppressLint("MissingInflatedId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.add_folder_dialog, null)

        folderName = view.findViewById(R.id.ed_folderName)
        folderDesc = view.findViewById(R.id.ed_folderDesc)

        folderName.setText(folder.folderName)
        folderDesc.setText(folder.folderDesc)

        builder.setView(view)
            .setTitle(title)
            .setNegativeButton("HỦY") { _, _ ->
            }
            .setPositiveButton("OK") { dialog, _ ->
                editFolder()
            }
        return builder.create()
    }

    private fun editFolder() {
        if (folder != null) {
            if (folderName.text.toString() != folder.folderName  ||
                folderDesc.text.toString() != folder.folderDesc)
            {
                folder.folderName = folderName.text.toString()
                folder.folderDesc = folderDesc.text.toString()

                FolderDAL().UpdateFolder(folder)
                activity.updateUI()
            }
        }
    }
}