package com.picpay.desafio.android

import com.nhaarman.mockitokotlin2.mock
import com.picpay.desafio.android.data.service.PicPayService
import org.junit.Test

class ExampleServiceTest {

    private val api = mock<PicPayService>()

    private val service = ExampleService(api)

    @Test
    fun exampleTest() {

    }
}