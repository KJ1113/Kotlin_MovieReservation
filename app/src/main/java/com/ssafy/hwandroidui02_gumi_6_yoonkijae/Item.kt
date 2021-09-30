package com.ssafy.hwandroidui02_gumi_6_yoonkijae

import kotlin.properties.Delegates

class Item {
    var id :Int
    var title : String
    var content : String
    var date : String
    var regist : String

    constructor( id : Int, title: String, content: String, date: String , regist : String) {
        this.id = id
        this.title = title
        this.content = content
        this.date = date
        this.regist = regist
    }

    constructor( title: String, content: String, date: String , regist : String) {
        this.id = 0
        this.title = title
        this.content = content
        this.date = date
        this.regist = regist
    }

    override fun toString(): String {
        return "$title $date"
    }
}