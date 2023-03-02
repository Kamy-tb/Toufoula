package com.example.menuapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.menuapplication.databinding.FragmentUrgenceBinding
import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class UrgenceFragment : Fragment() {


    lateinit var bindingUrgence : FragmentUrgenceBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingUrgence = FragmentUrgenceBinding.inflate( inflater , container , false )
        return bindingUrgence.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bindingUrgence.call1111.setOnClickListener {
            val phoneNumber = "1111"
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "No app found to make phone call", Toast.LENGTH_SHORT).show()
            }
        }

        bindingUrgence.call1021.setOnClickListener {
            val phoneNumber = "1021"
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "No app found to make phone call", Toast.LENGTH_SHORT).show()
            }
        }

        bindingUrgence.call1548.setOnClickListener {
            val phoneNumber = "1548"
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "No app found to make phone call", Toast.LENGTH_SHORT).show()
            }
        }





    }




}