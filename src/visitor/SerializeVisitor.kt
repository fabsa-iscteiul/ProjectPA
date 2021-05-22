package visitor

import jsonElements.*

class SerializeVisitor: Visitor {

    var stringToReturn : String = ""
    var numObj = 0
    fun addTabs(): String {
        var tabs = ""
        for (i: Int in 1..numObj)
            tabs+="\t"
        return tabs
    }
    override fun visit(bol: JsonBoolean) {
        stringToReturn+="${addTabs()}{\n"
        numObj++
        stringToReturn+="${addTabs()}\"name\":\"${bol.name}\",\n${addTabs()}\"value\":${bol.getValue()}\n"
        numObj--
        stringToReturn+="${addTabs()}},\n"
    }

    override fun visit(ch: JsonChar) {
        //stringToReturn+="{\n${addTabs()}\"name\":\"${ch.name}\",\n${addTabs()}\"value\":\"${ch.getValue()}\"\n${addTabs()}}\n"
        stringToReturn+="${addTabs()}{\n"
        numObj++
        stringToReturn+="${addTabs()}\"name\":\"${ch.name}\",\n${addTabs()}\"value\":\"${ch.getValue()}\"\n"
        numObj--
        stringToReturn+="${addTabs()}},\n"
    }

    override fun visit(col: JsonArray) {
        //stringToReturn+=col.serialize()
    }

    override fun visit(enum: JsonEnum) {
        //stringToReturn+="{\n${addTabs()}\"name\":\"${enum.name}\",\n${addTabs()}\"value\":${enum.getValue()}\n${addTabs()}}\n"
        stringToReturn+="${addTabs()}{\n"
        numObj++
        stringToReturn+="${addTabs()}\"name\":\"${enum.name}\",\n${addTabs()}\"value\":${enum.getValue()}\n"
        numObj--
        stringToReturn+="${addTabs()}},\n"
    }

    override fun visit(num: JsonNumber) {
        //stringToReturn+="{\n${addTabs()}\"name\":\"${num.name}\",\n${addTabs()}\"value\":${num.getValue()}\n${addTabs()}}\n"
        stringToReturn+="${addTabs()}{\n"
        numObj++
        stringToReturn+="${addTabs()}\"name\":\"${num.name}\",\n${addTabs()}\"value\":${num.getValue()}\n"
        numObj--
        stringToReturn+="${addTabs()}},\n"
    }

    override fun visit(obj: JsonObject) {
        //stringToReturn+="{\n${addTabs()}\"name\":\"${obj.name}\",\n${addTabs()}\"children\":\n${addTabs()}[\n${addTabs()}"
        stringToReturn+="${addTabs()}{\n"
        numObj++
        stringToReturn+="${addTabs()}\"name\":\"${obj.name}\",\n${addTabs()}\"children\":\n${addTabs()}[\n"

        //stringToReturn+="${addTabs()}}\n"
    }

    override fun visit(str: JsonString) {
        //stringToReturn+="{\n${addTabs()}\"name\":\"${str.name}\",\n${addTabs()}\"value\":\"${str.getValue()}\"\n${addTabs()}}\n"
        stringToReturn+="${addTabs()}{\n"
        numObj++
        stringToReturn+="${addTabs()}\"name\":\"${str.name}\",\n${addTabs()}\"value\":\"${str.getValue()}\"\n"
        numObj--
        stringToReturn+="${addTabs()}},\n"
    }
}