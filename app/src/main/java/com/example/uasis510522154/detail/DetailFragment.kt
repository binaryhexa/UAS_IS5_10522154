package com.example.uasis510522154.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.uasis510522154.R

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        val textViewNamaLengkap = view.findViewById<TextView>(R.id.textViewName)
        val textViewJabatan = view.findViewById<TextView>(R.id.textViewJabatan)
        val textViewUsia = view.findViewById<TextView>(R.id.textViewUsia)
        val textViewKeahlian = view.findViewById<TextView>(R.id.textViewKeahlian)
        val textViewEmail = view.findViewById<TextView>(R.id.textViewEmail)

        val namaLengkap = arguments?.getString("nama_lengkap")
        val jabatan = arguments?.getString("jabatan")
        val usia = arguments?.getString("usia")
        val keahlian = arguments?.getString("keahlian")
        val email = arguments?.getString("email")

        textViewNamaLengkap.text = "Nama Lengkap: $namaLengkap"
        textViewJabatan.text = "Jabatan: $jabatan"
        textViewUsia.text = "Usia: $usia"
        textViewKeahlian.text = "Keahlian: $keahlian"
        textViewEmail.text = "Email: $email"

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(namaLengkap: String, jabatan: String, usia: String, keahlian: String, email: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString("nama_lengkap", namaLengkap)
                    putString("jabatan", jabatan)
                    putString("usia", usia)
                    putString("keahlian", keahlian)
                    putString("email", email)
                }
            }
    }
}