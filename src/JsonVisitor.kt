import jsonElements.*

enum class Operation {
    GETALLSTRINGS,
    SERIALIZE
}
class JsonVisitor(private var op: Operation): Visitor {

    fun setOperation(operation: Operation){
        op = operation
    }

    override fun visit(bol: JsonBoolean) {
        if(op == Operation.SERIALIZE)
            println(bol.serialize())

    }

    override fun visit(ch: JsonChar) {
        if(op == Operation.SERIALIZE)
            println(ch.serialize())

    }

    override fun visit(col: JsonCollection) {
        if(op == Operation.SERIALIZE)
            println(col.serialize())
        else if(op == Operation.GETALLSTRINGS) {
            val list = col.getAllStrings()
            list.forEach {
                println(it)
            }
        }

    }

    override fun visit(enum: JsonEnum) {
        if(op == Operation.SERIALIZE)
            println(enum.serialize())

    }

    override fun visit(int: JsonInteger) {
        if(op == Operation.SERIALIZE)
            println(int.serialize())

    }

    override fun visit(obj: JsonObject) {
        if(op == Operation.SERIALIZE)
            println(obj.serialize())
        else if(op == Operation.GETALLSTRINGS) {
            val list = obj.getAllStrings()
            list.forEach {
                println(it)
            }
        }
    }

    override fun visit(str: JsonString) {
        if (op == Operation.SERIALIZE)
            println(str.serialize())

    }

}