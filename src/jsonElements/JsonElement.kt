package jsonElements

import Visitor

abstract class JsonElement(private val name:String, private val value:Any) {
    abstract fun serialize(): String
    fun getObjectName():String{ return name }
    abstract fun accept(v: Visitor)
    fun getValue(): Any { return value }
}