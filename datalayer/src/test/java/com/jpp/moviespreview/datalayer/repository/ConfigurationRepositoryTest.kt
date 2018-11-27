package com.jpp.moviespreview.datalayer.repository

import com.jpp.moviespreview.datalayer.AppConfiguration
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ConfigurationRepositoryTest {

    @RelaxedMockK
    private lateinit var dbRepository: ConfigurationRepository
    @MockK
    private lateinit var serverRepository: ConfigurationRepository

    private lateinit var subject: ConfigurationRepository


    @BeforeEach
    fun setUp() {
        subject = ConfigurationRepositoryImpl(dbRepository, serverRepository)
    }


    @Test
    @DisplayName("Should never use server data when data is stored locally")
    fun getConfiguration_whenDataIsInDB() {
        val expected = mockk<AppConfiguration>()

        every { dbRepository.getConfiguration() } returns expected

        val actual = subject.getConfiguration()

        assertEquals(expected, actual)

        verify(exactly = 0) { serverRepository.getConfiguration() }
    }


    @Test
    @DisplayName("Should retrieve data from server and update the local DB when data is not stored")
    fun getConfiguration_whenDataIsNotInDB() {
        val expected = mockk<AppConfiguration>()

        every { dbRepository.getConfiguration() } returns null
        every { serverRepository.getConfiguration() } returns expected

        val actual = subject.getConfiguration()

        assertEquals(expected, actual)
        assertNotNull(actual)

        verify { dbRepository.updateAppConfiguration(expected) }
    }

    @Test
    @DisplayName("Should retrieve null when data is not stored and server fails")
    fun getConfiguration_whenDataIsNotInDB_andServerFails() {
        every { dbRepository.getConfiguration() } returns null
        every { serverRepository.getConfiguration() } returns null

        val actual = subject.getConfiguration()

        assertNull(actual)

        verify(exactly = 0) { dbRepository.updateAppConfiguration(any()) }
    }

}