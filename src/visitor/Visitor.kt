package visitor

import jsonElements.*

interface Visitor {
    fun visit(bol: JsonBoolean)
    fun visit(ch: JsonChar)
    fun visit(col: JsonCollection)
    fun visit(enum: JsonEnum)
    fun visit(int: JsonInteger)
    fun visit(obj: JsonObject)
    fun visit(str: JsonString)
}