package com.example.uasis510522154

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateActivity : AppCompatActivity() {
    private val api by lazy { ApiRetrofit().endpoint }
    private lateinit var etName: EditText
    private lateinit var etNumber: EditText
    private lateinit var etJabatan: EditText
    private lateinit var etKeahlian: EditText
    private lateinit var etEmail: EditText
    private lateinit var mbCreate: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupView()
        setupListener()
    }

    private fun setupView() {
        etName = findViewById(R.id.EditTextName)
        etNumber = findViewById(R.id.EditTextNumber)
        etJabatan = findViewById(R.id.EditTextJabatan)
        etKeahlian = findViewById(R.id.EditTextKeahlian)
        etEmail = findViewById(R.id.EditTextEmail)
        mbCreate = findViewById(R.id.mbCreate)
    }

    private fun setupListener() {
        mbCreate.setOnClickListener {
            if (etName.text.toString().isNotEmpty() && etNumber.text.toString().isNotEmpty()) {
                Log.i("PPM", "nama_lengkap: $etName")
                Log.i("PPM", "usia: $etNumber")
                api.create(etName.text.toString(), etNumber.text.toString(), etJabatan.text.toString(), etKeahlian.text.toString(), etEmail.text.toString())
                    .enqueue(object : Callback<SubmitModel> {
                        override fun onResponse(call: Call<SubmitModel>, response: Response<SubmitModel>) {
                            val submit = response.body()
                            if (submit != null) {
                                Toast.makeText(applicationContext, submit.message, Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(applicationContext, "Submission failed", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                            Toast.makeText(applicationContext, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
            } else {
                Toast.makeText(applicationContext, "Nama atau Usia tidak bisa kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}