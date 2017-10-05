package org.minyodev.commons.lang

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

/**
  * JsonUtils provides functionality for reading JSON records, as well as
  * related functionality for performing conversions.
  * */
object JSONUtils {

  type ObjectMapperUtil = ObjectMapper with ScalaObjectMapper

  /**
    * This method will basically create an object mapper that we will be using to read event messages from Kafka
    * */
  def createObjectMapper(): ObjectMapperUtil = {
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper
  }

  def toJson(mapper: ObjectMapperUtil, value: Any): String = mapper.writeValueAsString(value)
  def fromJson[T](mapper: ObjectMapperUtil, json: String)(implicit m : Manifest[T]): T = mapper.readValue[T](json)
  def fromJsonToMap[V](mapper: ObjectMapperUtil, json: String)(implicit m: Manifest[V]) = fromJson[Map[String,V]](mapper, json)
}
