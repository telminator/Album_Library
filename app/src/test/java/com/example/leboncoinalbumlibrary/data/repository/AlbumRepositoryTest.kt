package com.example.leboncoinalbumlibrary.data.repository

import com.example.leboncoinalbumlibrary.data.local.dao.AlbumDao
import com.example.leboncoinalbumlibrary.data.local.entity.AlbumEntity
import com.example.leboncoinalbumlibrary.data.remote.api.AlbumApiService
import com.example.leboncoinalbumlibrary.data.remote.dto.AlbumDto
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class AlbumRepositoryTest {
    // mocks
    private lateinit var mockApiService: AlbumApiService
    private lateinit var mockDao: AlbumDao

    // repository
    private lateinit var repository: AlbumRepository

    //sample data
    private val albumEntity1 = AlbumEntity(
        id = 1,
        albumId = 1,
        title = "accusamus beatae ad facilis cum similique qui sunt",
        url = "https://placehold.co/600x600/92c952/white/png",
        thumbnailUrl = "https://placehold.co/150x150/92c952/white/png"
    )
    private val albumEntity2 = AlbumEntity(
        id = 2,
        albumId = 1,
        title = "eprehenderit est deserunt velit ipsam",
        url = "https://placehold.co/600x600/771796/white/png",
        thumbnailUrl = "https://placehold.co/150x150/771796/white/png"
    )

    private val albumDto1 = AlbumDto(
        id = 1,
        albumId = 1,
        title = "accusamus beatae ad facilis cum similique qui sunt",
        url = "https://placehold.co/600x600/92c952/white/png",
        thumbnailUrl = "https://placehold.co/150x150/92c952/white/png"
    )
    private val albumDto2 = AlbumDto(
        id = 2,
        albumId = 1,
        title = "eprehenderit est deserunt velit ipsam",
        url = "https://placehold.co/600x600/771796/white/png",
        thumbnailUrl = "https://placehold.co/150x150/771796/white/png"
    )

    @Before
    fun setup() {
        mockApiService = mock(AlbumApiService::class.java)
        mockDao = mock(AlbumDao::class.java)

        repository = AlbumRepository(mockApiService, mockDao)
    }

    @Test
    fun getAlbumsReturnsDataFromDao() = runTest {
        val albumEntities = listOf(albumEntity1, albumEntity2)

        // Configure the mock
        `when`(mockDao.getAllAlbums()).thenReturn(flowOf(albumEntities))

        // Call the method
        val result = repository.getAlbums().first()

        // Check the result
        assertEquals(2, result.size)
        assertEquals("accusamus beatae ad facilis cum similique qui sunt", result[0].title)
        assertEquals("eprehenderit est deserunt velit ipsam", result[1].title)
    }

    @Test
    fun refreshAlbumsGetsDataFromApiAndSavesToDao() = runTest {
        val albumDtos = listOf(albumDto1, albumDto2)

        `when`(mockApiService.getAlbums()).thenReturn(albumDtos)

        // Call the method
        repository.refreshAlbums()

        // Verify that the  API was called
        verify(mockApiService).getAlbums()

        // Verify DAO was called with some data
        verify(mockDao).insertAlbums(org.mockito.Mockito.anyList())
    }
}