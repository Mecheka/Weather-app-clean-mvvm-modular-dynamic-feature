package com.example.core.utils

import com.example.core.common.DataEntity
import com.example.core.common.ErrorEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <Q, R> flowOfWrapInnovation(service: Response<Q>, mapper: (Q?) -> R?): Flow<DataEntity<R>> {
    return flow {
        emit(DataEntity.LOADING)
        try {
            if (service.isSuccessful) {
                val body = service.body()
                emit(DataEntity.SUCCESS(mapper(body)))
            } else {
                emit(DataEntity.ERROR(ErrorEntity(service.message())))
            }
        } catch (e: Exception) {
            emit(DataEntity.ERROR(ErrorEntity(e.message)))
        }
    }
}