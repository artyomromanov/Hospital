package com.example.hospital

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hospital.util.FULL_URL
import com.example.hospital.view.MainActivity
import kotlinx.android.synthetic.main.activity_download.*
import java.io.File


class DownloadActivity : AppCompatActivity() {

    private val STORAGE_PERMISSION_CODE = 1000
    private var downloadComplete = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        tv_url.text = FULL_URL

        val onComplete: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(ctxt: Context, intent: Intent) {
                btn_download.text = getString(R.string.txt_proceed)
                Toast.makeText(ctxt, "Complete!", Toast.LENGTH_LONG).show()
                downloadComplete = true
            }
        }
        registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        btn_download.setOnClickListener {
            if (downloadComplete) {
                val intent = Intent(this@DownloadActivity, MainActivity::class.java)
                startActivity(intent)
            } else {
                //handle write permission for system storage if system OS is  >= Marshmallow
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission denied, request permission
                        //show popup
                        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
                    } else {
                        //permission granted, perform download
                        startDownload()
                    }
                } else {
                    //system is less than marshmallow, runtime permission not required, perform download
                    startDownload()
                }
            }
        }
    }

    //handle permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted, perform download
                    startDownload()
                } else {
                    //permission was not granted, show error
                    Toast.makeText(this@DownloadActivity, "Permission denied :(", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startDownload() {
        val url = "https://media.nhschoices.nhs.uk/data/foi/Hospital.csv"
        val request = DownloadManager.Request(Uri.parse(url))
        with(request) {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            setTitle("Download")
            setDescription("Downloading...")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "${System.currentTimeMillis()}"
            )

            val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            manager.enqueue(request)
        }
    }
}
