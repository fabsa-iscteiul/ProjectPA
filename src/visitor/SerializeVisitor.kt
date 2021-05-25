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
        stringToReturn += "${addTabs()}{\n"
        numObj++
        stringToReturn += "${addTabs()}\"name\":\"${bol.name}\",\n${addTabs()}\"value\":${bol.getValue()}\n"
        numObj--
        stringToReturn += if (numObj == 0) "${addTabs()}}\n" else "${addTabs()}},\n"
    }

    override fun visit(ch: JsonChar) {
        stringToReturn += "${addTabs()}{\n"
        numObj++
        stringToReturn += "${addTabs()}\"name\":\"${ch.name}\",\n${addTabs()}\"value\":\"${ch.getValue()}\"\n"
        numObj--
        stringToReturn += if (numObj == 0) "${addTabs()}}\n" else "${addTabs()}},\n"
    }

    override fun visit(col: JsonArray) {
        stringToReturn+="${addTabs()}{\n"
        numObj++
        stringToReturn+="${addTabs()}\"name\":\"${col.name}\",\n${addTabs()}\"value\":\n${addTabs()}[\n"
    }

    override fun visit(enum: JsonEnum) {
        stringToReturn += "${addTabs()}{\n"
        numObj++
        stringToReturn += "${addTabs()}\"name\":\"${enum.name}\",\n${addTabs()}\"value\":\"${enum.getValue()}\"\n"
        numObj--
        stringToReturn += if (numObj == 0) "${addTabs()}}\n" else "${addTabs()}},\n"
    }

    override fun visit(num: JsonNumber) {
        stringToReturn+="${addTabs()}{\n"
        numObj++
        stringToReturn+="${addTabs()}\"name\":\"${num.name}\",\n${addTabs()}\"value\":${num.getValue()}\n"
        numObj--
        stringToReturn+=if(numObj==0)"${addTabs()}}\n" else "${addTabs()}},\n"
    }

    override fun visit(obj: JsonObject) {
        stringToReturn+="${addTabs()}{\n"
        numObj++
        stringToReturn+="${addTabs()}\"name\":\"${obj.name}\",\n${addTabs()}\"children\":\n${addTabs()}[\n"
    }

    override fun visit(str: JsonString) {
        stringToReturn+="${addTabs()}{\n"
        numObj++
        stringToReturn+="${addTabs()}\"name\":\"${str.name}\",\n${addTabs()}\"value\":\"${str.getValue()}\"\n"
        numObj--
        stringToReturn+=if(numObj==0)"${addTabs()}}\n" else "${addTabs()}},\n"
    }
}