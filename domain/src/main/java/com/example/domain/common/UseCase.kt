package com.example.domain.common

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow

@FlowPreview
abstract class UseCase<Q, R> {

    abstract fun validateRequest(request: Q): Q

    protected abstract suspend fun executeRepos(request: Q): Flow<DataEntity<R>>

    fun execute(request: Q): Flow<DataEntity<R>> {
        return flow {
            emit(validateRequest(request))
        }.flatMapConcat { executeRepos(request) }
    }
}