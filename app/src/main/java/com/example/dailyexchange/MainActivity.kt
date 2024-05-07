package com.example.dailyexchange

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.dailyexchange.databinding.ActivityMainBinding
import com.example.dailyexchange.databinding.FragmentExchangeCalculatorBinding
import com.example.dailyexchange.network.ExchangeApi
import com.example.dailyexchange.viewmodels.ExchangeViewModel
import com.example.dailyexchange.viewmodels.ExchangeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.ServiceConfigurationError

class MainActivity : AppCompatActivity() {
    private val CAMERA_PERMISSION_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val mNavController = navHostFragment.navController
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.navigation)
        bottomNavigationView.setupWithNavController(mNavController)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            checkPermissions()
        }
    }
    private var cameraManager: CameraManager? = null
    private var cameraId: String? = null
    private var controllFlash :Boolean=true
    private fun checkPermissions() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // 已經有相機權限，執行對應function
            if(controllFlash){
                turnOnFlashlight()
                controllFlash=false
            }else{
                turnOffFlashlight()
                controllFlash=true
            }
        } else {
            // 沒有相機權限，就要求
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        }
    }
    private fun turnOnFlashlight() {
        try {
            cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
            cameraId = cameraManager?.cameraIdList?.find { id ->
                cameraManager?.getCameraCharacteristics(id)?.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
            }

            cameraManager?.setTorchMode(cameraId!!, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun turnOffFlashlight() {
        try {
            cameraManager?.setTorchMode(cameraId!!, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}