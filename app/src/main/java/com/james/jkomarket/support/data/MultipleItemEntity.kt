package com.james.jkomarket.support.data

import java.lang.ref.ReferenceQueue
import java.lang.ref.SoftReference
import java.util.LinkedHashMap

class MultipleItemEntity internal constructor(fields: LinkedHashMap<Any, Any>?) {
        private val ITEM_QUEUE = ReferenceQueue<LinkedHashMap<Any, Any>>()
        private val MULTIPLE_FIELDS = LinkedHashMap<Any, Any>()
        private val FIELDS_REFERENCE = SoftReference(MULTIPLE_FIELDS, ITEM_QUEUE)

        val itemType: Int
            get() = FIELDS_REFERENCE.get()!![MultipleFields.VIEW_TYPE] as Int

        fun <T> getField(key: Any?): T? {
            return FIELDS_REFERENCE.get()!![key] as T?
        }

        class MultipleEntityBuilder {
            fun setItemType(itemType: Int): MultipleEntityBuilder {
                FIELDS[MultipleFields.VIEW_TYPE] = itemType
                return this
            }

            fun setField(key: Any, value: Any): MultipleEntityBuilder {
                FIELDS[key] = value
                return this
            }

            fun setFields(map: LinkedHashMap<*, *>?): MultipleEntityBuilder {
                FIELDS.putAll(map!!)
                return this
            }

            fun build(): MultipleItemEntity {
                return MultipleItemEntity(FIELDS)
            }

            companion object {
                private val FIELDS = LinkedHashMap<Any, Any>()
            }

            init { //先清除之前的数据
                FIELDS.clear()
            }
        }

        enum class MultipleFields {
            VIEW_TYPE, ITEM_TITLE, ITEM_CONTENT
        }

        companion object {
            fun builder(): MultipleEntityBuilder {
                return MultipleEntityBuilder()
            }
        }

        init {
            FIELDS_REFERENCE.get()?.putAll(fields!!)
        }
    }