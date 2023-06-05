package com.example.gymattendancetracker.Fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.gymattendancetracker.R
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_q_r.*
import java.io.IOException


class QRFragment : Fragment() {

    private lateinit var cameraSource: CameraSource
    private var isScanning = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_q_r, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupScanner()
    }

    private fun setupScanner() {
        val barcodeDetector = BarcodeDetector.Builder(requireContext())
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()

        cameraSource = CameraSource.Builder(requireContext(), barcodeDetector)
            .setRequestedPreviewSize(640, 480)
            .setAutoFocusEnabled(true)
            .build()

        cameraPreview.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Request camera permission if not granted
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.CAMERA),
                        CAMERA_PERMISSION_REQUEST
                    )
                    return
                }
                try {
                    cameraSource.start(cameraPreview.holder)
                } catch (e: IOException) {
                    Log.e(TAG, "Failed to start camera source: ${e.message}")
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int,
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val qrCodes = detections.detectedItems
                if (qrCodes.size() > 0 && !isScanning) {
                    // QR code detected, do something with the result
                    isScanning = true
                    val qrCode = qrCodes.valueAt(0)
                    val qrText = qrCode.displayValue
                    // Pass the QR code result to another fragment or activity
                    //findNavController().navigate(QRScannerFragmentDirections.actionQRScannerFragmentToResultFragment(qrText))

                    if (qrText.equals("GymAttendance")) {
                        val dbref =
                            FirebaseDatabase.getInstance().getReference("Data").child("LiveCounter")


                        val postListener = object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                var count = snapshot.getValue(Int::class.java)
                                if (count != null) {
                                    count = count + 1
                                } else {
                                    count = 1
                                }
                                dbref.setValue(count).addOnSuccessListener {
                                    val fragmentManager = requireFragmentManager()
                                    fragmentManager.beginTransaction()
                                        .replace(R.id.frameLayout, HomeFragment())
                                        .commit()
                                }
                                    .addOnFailureListener {
                                        Log.e("deep", it.localizedMessage as String)
                                    }

                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.w("deep", "loadPost:onCancelled", error.toException())
                            }
                        }
                        dbref.addListenerForSingleValueEvent(postListener)

                    } else {
                        Toast.makeText(context, "QR doesn't match", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraSource.release()
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST = 100
        private const val TAG = "QRScannerFragment"
    }
}

