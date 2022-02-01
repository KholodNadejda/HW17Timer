package com.example.hw17timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.hw17timer.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private var timer: CountDownTimer? = null
    var seconds = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btTimer.setOnClickListener(){
                if(binding.tvTimer.text.toString().isNotEmpty()){
                    seconds = Integer.valueOf(binding.edTimeForTimer.text.toString())
                    timerFun()
                }

            }
        }
    }

    private fun timerFun() {
        if(binding.tvTimer.text.toString().isNotEmpty()){
            seconds = Integer.valueOf(binding.edTimeForTimer.text.toString())
            startCountDawnTimer(seconds)
        }
    }

    private fun startCountDawnTimer(timeMillis: Int){
        timer?.cancel()
        timer = object : CountDownTimer((timeMillis*1000).toLong(), 1){

            override fun onTick(time: Long) {
                binding.tvTimer.text = time.toString()
            }

            override fun onFinish() {
                binding.tvTimer.text = "Finish!"
            }
        }.start()
    }
}