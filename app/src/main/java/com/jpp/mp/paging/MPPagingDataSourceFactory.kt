package com.jpp.mp.paging

import androidx.paging.DataSource

/**
 * A [DataSource.Factory] implementation, part of the paging library. It only takes care
 * of creating the proper [DataSource] when needed.
 */
/*
 * TODO JPP delete this class in favor of com.jpp.mp.common.paging.MPPagingDataSourceFactory
 */
@Deprecated("Use MPPagingDataSourceFactory in common instead")
class MPPagingDataSourceFactory<T>(private val fetchItems: (Int, (List<T>, Int) -> Unit) -> Unit)
    : DataSource.Factory<Int, T>() {

    private lateinit var dsInstance: MPPagingDataSource<T>

    /**
     * From [DataSource.Factory#create()]
     */
    override fun create(): DataSource<Int, T> {
        dsInstance = MPPagingDataSource(fetchItems)
        return dsInstance
    }


    fun retryLast() {
        dsInstance.retryLastCall()
    }
}