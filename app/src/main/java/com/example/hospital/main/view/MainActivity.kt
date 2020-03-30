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
import com.example.hospital.model.database.Hospital
import com.example.hospital.util.ITEM_ID
import com.example.hospital.util.MyApp
import com.example.hospital.viewmodel.HospitalViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var hospitalAdapter : HospitalAdapter
    @Inject
    lateinit var viewModel: HospitalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_info.layoutManager = LinearLayoutManager(this)

        initializeViewModel()

        initializeSearchView()

        with(viewModel) {
            downloadAllHospitalsOnline()

            getHospitalLiveData().observe(this@MainActivity, Observer {
                when (it) {
                    is HospitalViewModel.HospitalLoadingState.IN_PROGRESS -> displayProgress()
                    is HospitalViewModel.HospitalLoadingState.SUCCESS -> {
                        hospitalAdapter =
                            HospitalAdapter(it.hospitalsData.toMutableList(), { hospital -> onHospitalItemClicked(hospital) }, { onNothingFound() })
                        rv_info.adapter = hospitalAdapter
                        tv_status.visibility = View.GONE
                    }
                    is HospitalViewModel.HospitalLoadingState.ERROR -> {tv_status.text = it.message; tv_status.visibility = View.VISIBLE}
                }
            })

            getDatabaseUpdateData().observe(this@MainActivity, Observer {
                if (it) {
                    displayToast(getString(R.string.txt_success_caching))
                }else{
                    displayToast(getString(R.string.txt_error_caching))
                }
            })
        }
    }

    private fun initializeSearchView() {

        sv_search.apply {
            isSubmitButtonEnabled = true

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String): Boolean {
                    //hospitalAdapter.filter.filter(query)
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    hospitalAdapter.filter.filter(newText)
                    return false
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

    private fun onHospitalItemClicked(hospital: Hospital) {
        startActivity(Intent(this, DetailsActivity::class.java).also { it.putExtra(ITEM_ID, hospital) })
    }

    private fun displayProgress() {
        displayToast(getString(R.string.txt_fetching))
    }

    private fun displayToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun onNothingFound(){
        tv_status.text = getString(R.string.txt_nothing_found, sv_search.query)
        tv_status.visibility = View.VISIBLE
    }
}