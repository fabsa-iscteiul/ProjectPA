package visitor

import jsonElements.*

interface Visitor {
    fun visit(bol: JsonBoolean)
    fun visit(ch: JsonChar)
    fun visit(col: JsonArray)
    fun visit(enum: JsonEnum)
    fun visit(int: JsonNumber)
    fun visit(obj: JsonObject)
    fun visit(str: JsonString)
}