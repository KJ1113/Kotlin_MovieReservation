package com.ssafy.hwandroidui02_gumi_6_yoonkijae

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.*
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val context: Context, private val userDataList: MutableList<Item>)
    : RecyclerView.Adapter<RecyclerViewAdapter.UserInfoHolder>() {

    var imgNameList : Array<Int> = arrayOf(R.drawable.img1 , R.drawable.img2 ,R.drawable.img3)

    interface ItemClickListener{ fun onClick(item: Item , position: Int) }
    interface menuClickListener{ fun onLongClick( mode: String  ,menuItem: MenuItem , position: Int) }

    lateinit var itemsetOnClicklistener : ItemClickListener
    lateinit var menusetOnClicklistener : menuClickListener


    // 개별 데이터(UserData)를 item_basic_recyclerview.xml과 연결하는 holder 구성
    inner class UserInfoHolder(private val itemview: View) : RecyclerView.ViewHolder(itemview) , View.OnCreateContextMenuListener{
        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        fun bindInfo(item: Item) {
            itemview.findViewById<TextView>(R.id.title_tv).text = item.title
            itemview.findViewById<TextView>(R.id.content_tv).text = item.content
            itemview.findViewById<ImageView>(R.id.logo_iv).setImageResource(imgNameList[item.id-1])

            if(item.regist == "ok") itemview.setBackgroundColor(Color.YELLOW)
            else itemview.setBackgroundColor(Color.parseColor("#CFD1DD"))

            // 1. interface 로 ItemClickListener 정의
            // 2. bindInfo 에서 itemview Listener binding하고?
            // 3. 메인에서 onClick 오버라이딩 구현
            itemview.setOnClickListener {
                itemsetOnClicklistener.onClick(item , layoutPosition)
            }
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(0,0,0,"등록")?.setOnMenuItemClickListener {
                menusetOnClicklistener.onLongClick("등록",it,layoutPosition)
                true
            }
            menu?.add(0,0,0,"취소")?.setOnMenuItemClickListener {
                menusetOnClicklistener.onLongClick("취소",it,layoutPosition)
                true
            }
        }
    }

    // 아이템과 아이템 레이아웃을 바인딩 하는 UserInfoHolder 생성 및 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ) : UserInfoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_basic_recyclerview, parent, false)
        return UserInfoHolder(view)
    }
    // ViewHolder에 실제 데이터를 binding 하는 메서드
    override fun onBindViewHolder(holder: UserInfoHolder, position: Int ) {
        holder.bindInfo(userDataList[position])
    }
    override fun getItemCount(): Int = userDataList.size
}