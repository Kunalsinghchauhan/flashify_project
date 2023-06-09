package com.example.flashify

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : AppCompatActivity() {
    lateinit var btnTorch: ImageView
    lateinit var cameraManager: CameraManager
    private var camraId: String = "0"
    var torchState = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnTorch = findViewById(R.id.btntorch)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        camraId = cameraManager.cameraIdList[0]

        Dexter.withContext(this).withPermission(android.Manifest.permission.CAMERA).withListener(
            object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    turnOnTorch()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(this@MainActivity, "Permission needed", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }

            }).check()
    }

    private fun turnOnTorch() {
        btnTorch.setOnClickListener {
            if (torchState == false) {
                cameraManager.setTorchMode(camraId, true)
                btnTorch.setImageResource(R.drawable.torch_on)
                torchState = true
                Toast.makeText(this, "Flash On", Toast.LENGTH_SHORT).show()
            } else {
                cameraManager.setTorchMode(camraId, false)
                btnTorch.setImageResource(R.drawable.torch_off)
                torchState = false
                Toast.makeText(this, "Flash Off", Toast.LENGTH_SHORT).show()
            }

        }

    }
}