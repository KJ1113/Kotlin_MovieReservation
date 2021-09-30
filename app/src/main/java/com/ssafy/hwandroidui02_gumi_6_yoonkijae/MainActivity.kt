package com.ssafy.hwandroidui02_gumi_6_yoonkijae

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hwandroidui02_gumi_6_yoonkijae.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val movieItemMgr = MovieItemMgr ()
    private var itemDao = ItemDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActivity()

        var basicAdapter = RecyclerViewAdapter(this, movieItemMgr.getList())
        binding.recyclerView.apply {
            adapter = basicAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        basicAdapter.itemsetOnClicklistener = object : RecyclerViewAdapter.ItemClickListener{
            override fun onClick(item: Item, position: Int) {
                var intent = Intent(this@MainActivity, MovieRegistActivity::class.java).apply {
                    if(item.regist == "ok")putExtra("mode", "no_regist") //예약 되어있다면
                    else putExtra("mode", "regist")
                    putExtra("position", position.toString())
                    putExtra("title", item.title)
                    putExtra("content",item.content)
                    putExtra("date",item.date)
                }
                requestActivity.launch(intent)
            }
        }

        basicAdapter.menusetOnClicklistener = object : RecyclerViewAdapter.menuClickListener{
            override fun onLongClick(mode: String, menuItem: MenuItem, position: Int) {
                var movieItem = movieItemMgr.getList().get(position)

                if(mode == "등록"){
                    if(movieItem.regist == "ok"){
                        Toast.makeText(this@MainActivity , "이미 예약됨", Toast.LENGTH_SHORT).show()
                    }else{
                        binding.recyclerView.get(position).setBackgroundColor(Color.YELLOW)
                        movieItem.regist = "ok"
                    }
                }else{
                    if(movieItem.regist == "no"){
                        Toast.makeText(this@MainActivity , "이미 취소됨", Toast.LENGTH_SHORT).show()
                    }else{
                        binding.recyclerView.get(position).setBackgroundColor(Color.parseColor("#CFD1DD"))
                        movieItem.regist = "no"
                    }
                }
                itemDao.updateItem(movieItem)
            }
        }
    }

    private val requestActivity : ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result : ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK){
            val intent = result.data
            val returnValue = intent?.getStringExtra("mode")
            val position  = intent?.getStringExtra("position")

            if(returnValue == "regist"){ // 예매모드였음
                if (position != null) {
                    binding.recyclerView.get(position.toInt()).setBackgroundColor(Color.YELLOW)
                    movieItemMgr.getList().get(position.toInt()).regist = "ok" // movieItemMgr 업데이트
                    itemDao.updateItem(movieItemMgr.getList().get(position.toInt())) // SQL 업데이트
                }
            }else{
                if (position != null) {
                    binding.recyclerView.get(position.toInt()).setBackgroundColor(Color.parseColor("#CFD1DD"))
                    movieItemMgr.getList().get(position.toInt()).regist = "no"
                    itemDao.updateItem(movieItemMgr.getList().get(position.toInt()))
                }
            }
        }
    }

    private fun initActivity (){ // 초기화
        itemDao.DbOpenHelper(this)
        itemDao.open()

        val prefs = getPreferences(MODE_PRIVATE) // SharePreferences 로 최초실행 여부 체크
        val v = prefs.getInt("KEY", 0)
        if(v == 0){ // 앱 최초 실행이라면
            var item0 = Item ("인셉션" , "꿈속의 꿈속의 꿈" , "10:31" ,  "no")
            var item1 = Item ("크루엘라"  , "크루엘라 이뻐요" , "11:30",  "no")
            var item2 = Item ("오징어게임"  , "우린 깐부 잖아~" , "12:32",  "no")

            itemDao.setItem(item0)
            itemDao.setItem(item1)
            itemDao.setItem(item2)
        }

        val editor = prefs.edit()
        editor.putInt("KEY", v.toInt()+1)
        editor.commit()

        movieItemMgr.setList(itemDao.getItemList())
    }
}

