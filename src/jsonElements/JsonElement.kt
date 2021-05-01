package jsonElements

import visitor.Visitor

abstract class JsonElement(private val value:Any, private val name:String="") {
    abstract fun serialize(): String
    fun getObjectName():String{ return name }
    abstract fun accept(v: Visitor)
    fun getValue(): Any { return value }
}