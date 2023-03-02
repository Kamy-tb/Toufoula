package com.example.menuapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.menuapplication.databinding.FragmentProfilBinding
import com.example.menuapplication.databinding.FragmentSignalementBinding


class ProfilFragment : Fragment() {
    lateinit var bindingProfil: FragmentProfilBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingProfil = FragmentProfilBinding.inflate( inflater , container , false )
        return bindingProfil.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingProfil.reglementation.setOnClickListener(){
            view.findNavController().navigate(R.id.action_profilFragment_to_reglementaionFragment)
        }

    }

}