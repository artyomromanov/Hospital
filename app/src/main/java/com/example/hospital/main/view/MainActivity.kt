package com.example.hospital.main.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hospital.R
import com.example.hospital.di.components.DaggerViewModelComponent
import com.example.hospital.di.modules.HospitalViewModelModule
import com.example.hospital.util.ITEM_ID
import com.example.hospital.util.MyApp
import com.example.hospital.viewmodel.HospitalViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), RecyclerViewClickListener{

    @Inject
    lateinit var viewModel: HospitalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_info.layoutManager = LinearLayoutManager(this)

        initializeViewModel()

        sv_search.isSubmitButtonEnabled = true
        sv_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getHospitalData(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        with(viewModel) {
            downloadAllHospitalsOnline()
            getHospitalData()
            getHospitalLiveData().observe(this@MainActivity, Observer {
                rv_info.adapter = InfoAdapter(it, this@MainActivity)
                tv_status.visibility = View.GONE
            })
            getErrorData().observe(this@MainActivity, Observer {
                tv_status.text = it
                tv_status.visibility = View.VISIBLE
            })
            getDatabaseCacheData().observe(this@MainActivity, Observer {
                if(it){
                    Toast.makeText(baseContext, "Successfully cached data to database!", Toast.LENGTH_SHORT).show()
                }
            })
            getNetworkDownloadData().observe(this@MainActivity, Observer {
                if(it){
                    Toast.makeText(baseContext, "Successfully downloaded hospitals data", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun initializeViewModel() {
        DaggerViewModelComponent
            .builder()
            .appComponent((application as MyApp).component())
            .hospitalViewModelModule(HospitalViewModelModule(this))
            .build()
            .injectActivity(this)
    }

    override fun onRecyclerItemClicked(itemId: String) {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        val putExtra = intent.putExtra(ITEM_ID, itemId)
        startActivity(intent)
    }
}