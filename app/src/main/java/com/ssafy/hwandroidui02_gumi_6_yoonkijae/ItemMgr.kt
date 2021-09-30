package com.ssafy.hwandroidui02_gumi_6_yoonkijae

interface ItemMgr {
    var memos : ArrayList<Item>
    fun getList() : ArrayList<Item>
    fun setList(list : ArrayList<Item>)
    fun size() : Int
    fun search(index : Int) : Item
    fun add(m: Item)
    fun update(index: Int, item : Item)
    fun remove(index: Int)
    fun clear()
}