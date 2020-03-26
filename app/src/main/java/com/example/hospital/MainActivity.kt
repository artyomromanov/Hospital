package com.example.hospital

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hospitals = initializeHospitals()

        rv_info.layoutManager = LinearLayoutManager(this)

        rv_info.adapter = InfoAdapter(hospitals)

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
                        propertiesList.getOrNull(0),
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