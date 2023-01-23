package id.fakhri_khairi.skeleton.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.fakhri_khairi.core.presentation.CoreActivity
import id.fakhri_khairi.data.misc.Constants
import id.fakhri_khairi.skeleton.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, CoreActivity::class.java)
            startActivity(intent)
            finish()
        }, Constants.ONE_SECOND)
    }
}