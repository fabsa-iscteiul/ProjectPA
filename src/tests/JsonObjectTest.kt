package tests

import OtherOther
import jsonElements.JsonObject
import jsonElements.JsonString
import junit.framework.TestCase
import kotlin.test.assertNotEquals

class JsonObjectTest : TestCase() {
    fun test(){
        val jo = JsonObject(OtherOther("other"))
        assertNotEquals("{\n" +
                "\t\"o\":\"other\"\n" +
                "}\n", "")
        assertEquals("other", jo.map["o"]?.getValue())
        assertTrue("", jo.map["o"] is JsonString)
    }
}