package com.example.core.utils

import com.example.core.common.DataEntity
import com.example.core.common.ErrorEntity

fun Throwable.toDataEntityError(): DataEntity.ERROR {
    return DataEntity.ERROR(ErrorEntity(this.message))
}