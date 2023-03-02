package com.example.menuapplication

import android.content.Intent
import android.graphics.Bitmap
import androidx.activity.result.ActivityResultLauncher
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.core.content.ContextCompat
import com.example.menuapplication.databinding.FragmentSignalementBinding
import android.content.pm.PackageManager
import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.media.MediaRecorder
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.AccessController.checkPermission
import java.util.*


class SignalementFragment : Fragment() {

    val requestCode = 400
    lateinit var imageBitmap:Bitmap
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var bindingSignalement : FragmentSignalementBinding
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var audioFilePath: String
    private var isRecording = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingSignalement = FragmentSignalementBinding.inflate( inflater , container , false )
        return bindingSignalement.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityResultLauncher=
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val intent = result.data
                if (result.resultCode == RESULT_OK && intent != null) {
                    imageBitmap = intent.extras?.get("data") as Bitmap
                    val resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, 300, 300, true)
                    //bindingSignalement.uploadedimage.setImageBitmap(resizedBitmap)
                    Toast.makeText(requireActivity(), "Image envoyée", Toast.LENGTH_SHORT).show();
                    // Mettre dans le serveur :



                }
            }

        bindingSignalement.imagefromcamera.setOnClickListener{
            if (!connected()){
                Toast.makeText(requireActivity(), "Veuiller vous connecter à internet d'abord", Toast.LENGTH_SHORT).show();
            }else {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED)  {
                    openCameraIntent()
                }
                else {
                    checkPermission()
                }
            }
        }
        bindingSignalement.form.setOnClickListener {
            view.findNavController().navigate(R.id.action_signalementFragment_to_formulaireFragment)
        }
        // Set up a button to start and stop recording
        bindingSignalement.vocal.setOnClickListener{
            audioFilePath = "${requireContext().externalCacheDir?.absolutePath}/recording.3gp"
            // Request permission to record audio
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO), 0)
            }
            // Create and prepare the MediaRecorder instance
            mediaRecorder = MediaRecorder()
            mediaRecorder.apply {

                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(audioFilePath)

                try {
                    prepare()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if (isRecording) {
                // Stop recording and release the MediaRecorder
                mediaRecorder.stop()
                mediaRecorder.release()
                isRecording = false
                bindingSignalement.vocaltxt.text = "Record"
            } else {
                // Create a new MediaRecorder instance and start recording
                mediaRecorder = MediaRecorder()
                mediaRecorder.apply {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                    setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                    setOutputFile(audioFilePath)

                    try {
                        prepare()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                mediaRecorder.start()
                isRecording = true
                bindingSignalement.vocaltxt.text = "Stop"
            }
        }
    }
    private fun connected(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }
    private fun openCameraIntent() {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activityResultLauncher.launch(pictureIntent)
    }
    private fun checkPermission() {
        val perms = arrayOf(Manifest.permission.CAMERA)
        ActivityCompat.requestPermissions(requireActivity(),perms, requestCode)
    }


    override fun onRequestPermissionsResult(permsRequestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grantResults)
        if (permsRequestCode == requestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCameraIntent()
        }

    }


    private fun Addimg(img : MultipartBody.Part) {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitService.endpoint.addimg(img)
                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        Toast.makeText(requireActivity() , "Envoyer" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(requireActivity() , "Message d'erreur " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch (_: InterruptedException) {
            } catch (_: IOException) {
            } catch (_: NullPointerException) {
            } catch (_: IllegalStateException) {
            }
        }
    }
}