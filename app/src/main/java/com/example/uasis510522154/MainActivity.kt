package com.example.uasis510522154

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.uasis510522154.detail.DetailDialogFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val api by lazy { ApiRetrofit().endpoint }
    private lateinit var dataContainer: DataContainer
    private lateinit var listData: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupView()
        setupList()
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        getContact()
    }

    private fun setupView() {
        listData = findViewById(R.id.RecyclerViewData)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
    }

    private fun setupList() {
        dataContainer = DataContainer(arrayListOf(), object : DataContainer.OnAdapterListener {
            override fun onUpdate(pegawai: ReadModel.Data) {
                startActivity(Intent(this@MainActivity, UpdateActivity::class.java).putExtra("pegawai", pegawai))
            }

            override fun onDelete(pegawai: ReadModel.Data) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                builder.setMessage("Yakin akan menghapus data ${pegawai.nama_lengkap} ?")
                    .setTitle("Hapus Data")
                    .setPositiveButton("Hapus") { dialog, which ->
                        api.delete(pegawai.id_pegawai!!).enqueue(object : Callback<SubmitModel> {
                            override fun onResponse(call: Call<SubmitModel>, response: Response<SubmitModel>) {
                                if (response.isSuccessful) {
                                    val submit = response.body()
                                    Toast.makeText(applicationContext, submit!!.message, Toast.LENGTH_LONG).show()
                                    getContact()
                                }
                            }

                            override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                                Log.e("PPM", t.toString())
                            }
                        })
                    }
                    .setNegativeButton("Batal") { dialog, which ->
                        // Do something else.
                    }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }

            override fun onDetail(pegawai: ReadModel.Data) {
                val dialogFragment = DetailDialogFragment.newInstance(
                    pegawai.nama_lengkap ?: "",
                    pegawai.jabatan ?: "",
                    pegawai.usia ?: "",
                    pegawai.keahlian ?: "",
                    pegawai.email ?: ""
                )
                dialogFragment.show(supportFragmentManager, "DetailDialogFragment")
            }
        })
        listData.adapter = dataContainer
    }

    private fun setupListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_create -> {
                    startActivity(Intent(this, CreateActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getContact() {
        api.data().enqueue(object : Callback<ReadModel> {
            override fun onResponse(call: Call<ReadModel>, response: Response<ReadModel>) {
                if (response.isSuccessful) {
                    val listData = response.body()!!.result
                    dataContainer.setData(listData)
                }
            }

            override fun onFailure(call: Call<ReadModel>, t: Throwable) {
                Log.e("PPM", t.toString())
            }
        })
    }
}
