package com.example.hospital.view

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hospital.model.database.Hospital
import com.example.hospital.R
import com.example.hospital.di.components.DaggerAppComponent
import com.example.hospital.di.components.DaggerViewModelComponent
import com.example.hospital.di.modules.HospitalViewModelModule
import com.example.hospital.util.MyApp
import com.example.hospital.viewmodel.HospitalViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel : HospitalViewModel

    //val downloadsDir : File? = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_info.layoutManager = LinearLayoutManager(this)

        initializeViewModel()

        with(viewModel){
            getAllHospitals()

            getHospitalsData().observe(this@MainActivity, Observer {
                //rv_info.adapter = InfoAdapter(it)
                tv_status.visibility = View.VISIBLE
                tv_status.text = it
            })
            getErrorData().observe(this@MainActivity, Observer {
                tv_status.text = it
                tv_status.visibility = View.VISIBLE
            })
        }
        val hospitals = initializeHospitals()
    }

    private fun initializeViewModel() {
        DaggerViewModelComponent
            .builder()
            .appComponent((application as MyApp).component())
            .hospitalViewModelModule(HospitalViewModelModule(this))
            .build()
            .injectActivity(this)
    }

    private fun initializeHospitals() : List<Hospital> {
        val inputStream: InputStream = resources.openRawResource(R.raw.hospital)
        val streamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
        val reader = BufferedReader(streamReader)
        val hospitals = mutableListOf<Hospital>()
        try {
            while (reader.readLine() != null) {
                val line = reader.readLine().replace('\uFFFD', ';').replace(";;", ";")
                val propertiesList = line.split(';')
                hospitals.add(
                    Hospital(
                        propertiesList[0],
                        propertiesList.getOrNull(1),
                        propertiesList.getOrNull(2),
                        propertiesList.getOrNull(3),
                        propertiesList.getOrNull(4),
                        propertiesList.getOrNull(5),
                        propertiesList.getOrNull(6),
                        propertiesList.getOrNull(7),
                        propertiesList.getOrNull(8),
                        propertiesList.getOrNull(9),
                        propertiesList.getOrNull(10),
                        propertiesList.getOrNull(11),
                        propertiesList.getOrNull(12),
                        propertiesList.getOrNull(13),
                        propertiesList.getOrNull(14),
                        propertiesList.getOrNull(15),
                        propertiesList.getOrNull(16),
                        propertiesList.getOrNull(17),
                        propertiesList.getOrNull(18),
                        propertiesList.getOrNull(19),
                        propertiesList.getOrNull(20),
                        propertiesList.getOrNull(21)
                    )
                )
                println(hospitals.size)
            }
        } catch (e: RuntimeException) {
            println(e.message)
        }
        return hospitals
    }
}