package visitor

import jsonElements.*

class SerializeVisitor: Visitor {

    private var stringToReturn : String = ""

    override fun visit(bol: JsonBoolean) {
        stringToReturn+=bol.serialize()
    }

    override fun visit(ch: JsonChar) {
        stringToReturn+=ch.serialize()
    }

    override fun visit(col: JsonArray) {
        stringToReturn+=col.serialize()
    }

    override fun visit(enum: JsonEnum) {
        stringToReturn+=enum.serialize()
    }

    override fun visit(int: JsonNumber) {
        stringToReturn+=int.serialize()
    }

    override fun visit(obj: JsonObject) {
        stringToReturn+=obj.serialize()
    }

    override fun visit(str: JsonString) {
        stringToReturn+=str.serialize()
    }
}