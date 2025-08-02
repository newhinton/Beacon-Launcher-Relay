package de.felixnuesse.beaconlaunchermiddleware

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import de.felixnuesse.beaconlaunchermiddleware.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var gameId = ""
        val inputStream = contentResolver.openInputStream(intent.data!!)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        while ((reader.readLine().also { line = it }) != null) {
            if(line?.startsWith("[scummvm_id] ") == true) {
                gameId = line.replace("[scummvm_id] ", "")
            }
        }
        
        val intent = Intent()
        intent.setAction(Intent.ACTION_MAIN)
        intent.setClassName("org.scummvm.scummvm.debug", "org.scummvm.scummvm.SplashActivity")
        intent.data = gameId.toUri()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}