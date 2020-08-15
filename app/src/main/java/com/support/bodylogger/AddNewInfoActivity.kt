package com.support.bodylogger

import android.Manifest
import android.R.attr
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_add_new_info.*
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar.*


class AddNewInfoActivity : AppCompatActivity() {
    private lateinit var db: BodyInfoDataBase
    private lateinit var dao: BodyInfoDao
    private var mUri: Uri? = null
    private var nowCommentList: List<String> = listOf()
    companion object{
        const val REQUEST_GET_IMAGE = 0
        const val REQUEST_EXTERNAL_PERM = 2000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_info)
        initDataBase()
        val adapter = EditTextAdapter(layoutInflater, this)
        writeCommentButton.setOnClickListener {
            val dialog = AddCommentDialogFragment(adapter,nowCommentList)
            dialog.show(supportFragmentManager, "AddCommentDialog")
        }
        addNewInfoButton.setOnClickListener{
            try{
                val entity = makeEntity()
                AsyncTask.execute{
                    dao.insertBodyInfo(entity)
                }
                finish()
            }catch (e: NumberFormatException){
                Toast.makeText(applicationContext, "体重と体脂肪率を記入して下さい",
                    Toast.LENGTH_SHORT).show()
            }
        }
        bodyImageButton.setOnClickListener{
            showGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_GET_IMAGE && resultCode == RESULT_OK){
            val copyUri = mUri
            if(copyUri != null){
                bodyImageButton.setImageURI(mUri)
                bodyImageButton.background = null
            }
        }else{
            val copyUri = mUri
            if(copyUri != null){
                contentResolver.delete(copyUri,null,null)
            }
        }
    }
    private fun showGallery(){
        if(ActivityCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions, REQUEST_EXTERNAL_PERM)
            return
        }
        val text = DateFormat.format("yyyyMMddHHmmss", getInstance())
        val fileName = "BodyLoggerSavedFile$text.jpg"
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, fileName)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        mUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri)
        startActivityForResult(intent, REQUEST_GET_IMAGE)
    }
    fun changeCommentList(newList: List<String>){
        nowCommentList = newList
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
       if(requestCode == REQUEST_EXTERNAL_PERM &&
           grantResults[0] == PackageManager.PERMISSION_GRANTED){
           showGallery()
       }
    }
    private fun initDataBase() {
        db = Room.databaseBuilder(
            applicationContext,
            BodyInfoDataBase::class.java, "com.support.bodylogger.BodyInfoDataBase"
        ).enableMultiInstanceInvalidation().build()
        dao = db.bodyInfoDao()
    }
    private fun saveImgFromBmp(bmp:Bitmap, outputFileName:String) {
        try {
            val byteArrOutputStream = ByteArrayOutputStream()
            val fileOutputStream: FileOutputStream = applicationContext.openFileOutput(outputFileName,
                Context.MODE_PRIVATE)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrOutputStream)
            fileOutputStream.write(byteArrOutputStream.toByteArray())
            fileOutputStream.close()
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
    private fun convertImgUriToBmp(uri: Uri): Bitmap?{
        val parcelFileDesc = this.contentResolver.openFileDescriptor(uri,"r")
        val fDesc = parcelFileDesc?.fileDescriptor
        val bmp = BitmapFactory.decodeFileDescriptor(fDesc)
        parcelFileDesc?.close()
        return bmp
    }

    private fun makeEntity(): BodyInfoEntity{
        val weightInfoStr = inputWeight.text.toString()
        val fatPerInfoStr = inputBodyPerFat.text.toString()
        val weightInfo = weightInfoStr.toInt()
        val fatPerInfo = fatPerInfoStr.toInt()
        val todayCalender = getInstance()
        val date = todayCalender.get(DATE)
        val month = todayCalender.get(MONTH)
        val year = todayCalender.get(YEAR)
        val commentList:MutableList<String> = nowCommentList.toMutableList()
        commentList.remove("")
        val strBuilder = StringBuilder()
        commentList.forEachIndexed{index: Int, s: String ->
            strBuilder.append(s)
            if(index != commentList.size-1){
                strBuilder.append("\n")
            }
        }
        val commentText = strBuilder.toString()
        val copyUri = mUri
        var imageName: String? = null
        if(copyUri != null){
            val newName = "$year$month$date"
            val bmp = convertImgUriToBmp(copyUri)
            if(bmp != null){
                saveImgFromBmp(bmp,newName)
                imageName = newName
            }
        }
        return BodyInfoEntity(
            dateStr = BodyInfoEntity.generateData(year,month,date),
            bodyWeight = weightInfo,
            bodyFatPercentage = fatPerInfo,
            infoMonth = month,
            infoYear = year,
            infoDate = date,
            commentText = commentText,
            imageName = imageName
        )
    }
}

