package jsonElements

import visitor.Visitor

abstract class JsonElement(private val value:Any, var name:String="") {
    abstract fun serialize(): String
    abstract fun accept(v: Visitor)
    fun getValue(): Any { return value }
}