package tests

import OtherOther
import jsonElements.JsonObject
import jsonElements.JsonString
import junit.framework.TestCase

class JsonObjectTest : TestCase() {
    fun test(){
        val jo = JsonObject("val", OtherOther("other"))
        assertEquals("{\n" +
                "\t\"o\":\"other\"\n" +
                "}\n", jo.serialize())
        assertEquals("other", jo.map["o"]?.getValue())
        assertTrue("", jo.map["o"] is JsonString)
        assertEquals(listOf("other"), jo.getAllStrings())
    }
}