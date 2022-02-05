package com.example.hw17timer

import android.content.Context
import android.os.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.example.hw17timer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private var timer: CountDownTimer? = null
    private var secondsNow = ""
    var flag = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btTimer.setOnClickListener() {
                when (flag) {
                    0 -> secondsNow = binding.edTimeForTimer.text.toString()+"000"
                    1 -> secondsNow = binding.tvTimer.text.toString()
                }
                Toast.makeText(baseContext,"Закончился через " + secondsNow + " миллисекунд",Toast.LENGTH_SHORT).show()
                startCountDawnTimer(secondsNow)
            }
            btCancel.setOnClickListener {
                flag = 0
                cancelCountDawnTimer()
            }

            btPause.setOnClickListener {
                flag = 1
                pauseCountDawnTimer()
            }
        }
    }

    private fun startCountDawnTimer(time: String){
        timer?.cancel()
        try {
            var seconds = Integer.valueOf(time)
            timer = object : CountDownTimer((seconds).toLong(), 1){

                override fun onTick(time: Long) {
                    binding.tvTimer.text = time.toString()
                }

                override fun onFinish() {
                    binding.tvTimer.text = "Finish!"
                    flag = 0
                    viber(this@MainActivity)
                }
            }.start()
        } catch (e: Exception) {
            binding.tvTimer.text = "Error"
        }
    }

    private fun cancelCountDawnTimer(){
        val alterDialog = AlertDialog.Builder(this)
        alterDialog.setTitle("Cancel?")
        alterDialog.setMessage("Are u sure?")
        alterDialog.setPositiveButton("Yep") {_,_ ->
            timer?.cancel()
            binding.tvTimer.text = "Cancel"
            flag = 0
        }
        alterDialog.setNegativeButton("NO"){_,_ -> }
        alterDialog.show()
    }

    private fun pauseCountDawnTimer(){
        timer?.cancel()
    }

    companion object {
        fun viber(context: Context) {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val canVibrate: Boolean = vibrator.hasVibrator()
            val milliseconds = 1000L

            if (canVibrate) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            milliseconds,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    vibrator.vibrate(milliseconds)
                }
            }
        }
    }
}