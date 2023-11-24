package com.example.familycoin.recyclerView

class FamilyItem {
    var name: String? = null
    var imageRes: Int? = null
    var type: Int? = null

    constructor(name: String?, imageRes: Int?, type: Int?) {
        this.name = name
        this.imageRes = imageRes
        this.type = type
    }
}