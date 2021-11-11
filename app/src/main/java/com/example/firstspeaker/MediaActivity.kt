package com.example.firstspeaker

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import com.example.firstspeaker.databinding.ActivityMediaBinding

class MediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaBinding

    private var mediaPlayer: MediaPlayer? = null
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i("LifeCycle", "OnCreate")
    }

    override fun onStart() {
        super.onStart()

        Log.i("LifeCycle", "OnStart")

        mediaPlayer = MediaPlayer.create(this, R.raw.ai_2)
    }

    override fun onResume() {
        super.onResume()

        Log.i("LifeCycle", "OnResume")

        // Reiniciamos la canción desde el punto en el que se pausó
        mediaPlayer?.seekTo(position)
        mediaPlayer?.start()
    }

    override fun onPause() {
        super.onPause()

        Log.i("LifeCycle", "OnPause")

        mediaPlayer?.pause()
        // Guardamos el punto en el que se pausó la canción
        if (mediaPlayer != null) position = mediaPlayer!!.currentPosition
    }

    override fun onStop() {
        super.onStop()

        Log.i("LifeCycle", "OnStop")

        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onRestart() {
        super.onRestart()

        Log.i("LifeCycle", "OnRestart")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i("LifeCycle", "OnDestroy")
    }
}