import jsonElements.*

class JsonVisitor(private var op: String): Visitor {

    lateinit var search: (String) -> Boolean

    init {
        op = op.toLowerCase()
    }

    fun setOperation(str: String){
        op = str.toLowerCase()
    }

    override fun visit(bol: JsonBoolean) {
        if(op == "serialize")
            println(bol.serialize())

    }

    override fun visit(ch: JsonChar) {
        if(op == "serialize")
            println(ch.serialize())

    }

    override fun visit(col: JsonCollection) {
        if(op == "serialize")
            println(col.serialize())

    }

    override fun visit(enum: JsonEnum) {
        if(op == "serialize")
            println(enum.serialize())

    }

    override fun visit(int: JsonInteger) {
        if(op == "serialize")
            println(int.serialize())

    }

    override fun visit(obj: JsonObject) {
        if(op == "serialize")
            println(obj.serialize())

    }

    override fun visit(str: JsonString) {
        if (op == "serialize")
            println(str.serialize())

    }

}