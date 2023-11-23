package com.example.familycoin.gridView

class TaskItem {
    var name: String? = null
    var image: Int? = null
    var assigned: Boolean = false


    constructor(name: String?, image: Int?, boolean: Boolean) {
        this.name = name
        this.image = image
        this.assigned = boolean
    }
}