import jsonElements.*

class JsonVisitor(private var op: String): Visitor {

    fun setOperation(str: String){
        op = str
    }

    override fun getOperation():String{
        return op
    }

    override fun visit(bol: JsonBoolean) {
        if(op.toLowerCase() == "serialize"){
            bol.serialize()
        }
    }

    override fun visit(ch: JsonChar) {
        if(op.toLowerCase() == "serialize"){
            ch.serialize()
        }
    }

    override fun visit(col: JsonCollection) {
        if(op.toLowerCase() == "serialize"){
            col.serialize()
        }
    }

    override fun visit(enum: JsonEnum) {
        if(op.toLowerCase() == "serialize"){
            enum.serialize()
        }
    }

    override fun visit(int: JsonInteger) {
        if(op.toLowerCase() == "serialize"){
            int.serialize()
        }
    }

    override fun visit(obj: JsonObject) {
        if(op.toLowerCase() == "serialize"){
            obj.serialize()
        }
    }

    override fun visit(str: JsonString) {
        if(op.toLowerCase() == "serialize"){
            str.serialize()
        }
    }

}