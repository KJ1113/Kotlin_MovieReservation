package com.ssafy.hwandroidui02_gumi_6_yoonkijae

class MovieItemMgr () : ItemMgr {
    override var memos: ArrayList<Item> = arrayListOf<Item>()

    override fun getList(): ArrayList<Item> {
        return memos;
    }

    override fun setList( list : ArrayList<Item>) {
        memos = list
    }

    override fun size(): Int {
        return memos.size;
    }
    override fun search(index: Int): Item {
        return memos.get(index);
    }
    override fun add(m: Item) {
        memos.add(m)
    }

    override fun update(index: Int, item: Item) {
        memos.get(index).content = item.content
        memos.get(index).date = item.date
        memos.get(index).title = item.title
    }

    override fun remove(index: Int) {
        memos.removeAt(index)
    }

    override fun clear(){
        memos.clear()
    }
}