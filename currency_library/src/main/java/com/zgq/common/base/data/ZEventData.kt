package com.zgq.common.base.data

class ZEventData{
    var id: Int = -1
    var int: Int = -1
    var str: String? = null
    var any: Any? = null

    constructor(id: Int){
        this.id = id
    }

    constructor(id: Int, int: Int){
        this.id = id
        this.int = int
    }

    constructor(id: Int, str: String?){
        this.id = id
        this.str = str
    }

    constructor(id: Int, any: Any?){
        this.id = id
        this.any = any
    }

    constructor(id: Int, int: Int, str: String?){
        this.id = id
        this.int = int
        this.str = str
    }

    constructor(id: Int, int: Int, any: Any?){
        this.id = id
        this.int = int
        this.any = any
    }

    constructor(id: Int, str: String?, any: Any?){
        this.id = id
        this.str = str
        this.any = any
    }

}