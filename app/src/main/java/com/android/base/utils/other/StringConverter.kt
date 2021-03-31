package com.android.base.utils.other

import com.google.gson.*
import java.lang.reflect.Type

/**
 * 对于非常规式json解析补充工具
 */
class StringConverter:JsonSerializer<String>,JsonDeserializer<String>{
    override fun serialize(
        src: String?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return if (src == null || src == "{}") {
            JsonPrimitive("")
        } else {
            JsonPrimitive(src.toString())
        }
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): String {
        return json.asJsonPrimitive.asString
    }

}