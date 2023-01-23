package id.fakhri_khairi.core.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.fakhri_khairi.core.databinding.ActivityCoreBinding

@AndroidEntryPoint
class CoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}