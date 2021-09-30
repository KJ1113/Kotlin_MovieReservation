package com.ssafy.hwandroidui02_gumi_6_yoonkijae

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ssafy.hwandroidui02_gumi_6_yoonkijae.databinding.ActivityMainBinding
import com.ssafy.hwandroidui02_gumi_6_yoonkijae.databinding.ActivityMovieRegistBinding

class MovieRegistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieRegistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieRegistBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Toast.makeText(this , intent.getStringExtra("mode"), Toast.LENGTH_SHORT).show()

        var title = intent.getStringExtra("title")
        var content = intent.getStringExtra("content")
        var date = intent.getStringExtra("date")


        if(title == "인셉션") binding.imageView2.setImageResource(R.drawable.img1)
        if(title == "크루엘라") binding.imageView2.setImageResource(R.drawable.img2)
        if(title == "오징어게임") binding.imageView2.setImageResource(R.drawable.img3)

        if(intent.getStringExtra("mode") == "regist"){
            binding.buttonRun.text = "예매"
        }else{
            binding.buttonRun.text = "예매취소"
        }

        binding.textView2.setText(title)
        binding.textView4.setText(content)
        binding.textView6.setText(date)
        binding.buttonRun.setOnClickListener {
            val intent = Intent().apply {
                var position : String = intent.getStringExtra("position").toString()
                var mode : String = intent.getStringExtra("mode").toString()

                putExtra("position", position)
                putExtra("mode" , mode)
            }
            setResult(Activity.RESULT_OK,intent)
            finish()
        }

        binding.buttonNo.setOnClickListener {
            finish()
        }
    }
}

