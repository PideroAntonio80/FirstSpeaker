package com.example.firstspeaker

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firstspeaker.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityMainBinding

    private var tts: TextToSpeech? = null
    private var errorMessage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tts = TextToSpeech(this, this)

        binding.tietUserWriting.onFocusChangeListener = View.OnFocusChangeListener { view, focused ->
            if (focused) {
                errorMessage = null
                binding.tilUserWriting.error = errorMessage
            }
        }

        binding.tietUserWriting.setOnClickListener {
            errorMessage = null
            binding.tilUserWriting.error = errorMessage
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Atención!")
            .setMessage("Si no seleccionas un idioma, se configurará en español")
            .setPositiveButton("Entendido") { view, _ ->
                view.dismiss()
                speak(binding.tietUserWriting.text.toString())
            }
            .setCancelable(false)
            .create()

        binding.bPlay.setOnClickListener {
            when {
                binding.rbSpanish.isChecked -> {
                    selectLanguage("ES")
                    speak(binding.tietUserWriting.text.toString())
                }
                binding.rbEnglish.isChecked -> {
                    selectLanguage("EN")
                    speak(binding.tietUserWriting.text.toString())
                }
                binding.rbFrench.isChecked -> {
                    selectLanguage("FR")
                    speak(binding.tietUserWriting.text.toString())
                }
                binding.rbItalian.isChecked -> {
                    selectLanguage("IT")
                    speak(binding.tietUserWriting.text.toString())
                }
                binding.rbGerman.isChecked -> {
                    selectLanguage("DE")
                    speak(binding.tietUserWriting.text.toString())
                }
                binding.rbPortuguese.isChecked -> {
                    selectLanguage("PT")
                    speak(binding.tietUserWriting.text.toString())
                }
                else -> {
                    selectLanguage("ES")
                    if(!binding.tietUserWriting.text.isNullOrEmpty()) {
                        dialog.show()
                    } else {
                        dialog.show()
                    }
                }
            }
        }

        binding.bGo.setOnClickListener {
            startActivity(Intent(this, MediaActivity::class.java))
        }
    }

    private fun speak(message: String) {
        if (!binding.tietUserWriting.text.isNullOrEmpty()) {
            errorMessage = null
            binding.tilUserWriting.error = errorMessage

            tts?.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        } else {
            errorMessage = "Rellena este campo"
            binding.tilUserWriting.error = errorMessage

            tts?.speak("No has escrito nada", TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            selectLanguage("ES")
        } else {
            Toast.makeText(this,"Servicio no disponible ", Toast.LENGTH_SHORT).show()
        }
    }

    fun selectLanguage(language: String) {
        tts?.setLanguage(Locale(language))
    }

    override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}