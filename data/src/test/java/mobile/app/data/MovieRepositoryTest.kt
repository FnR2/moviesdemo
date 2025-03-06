import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import mobile.app.data.DefaultMovieRepository
import mobile.app.data.DiscoverResponse
import mobile.app.data.MoviesWithGroup
import mobile.app.data.RestResult
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*


@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    private val movieRepository: DefaultMovieRepository = mock(DefaultMovieRepository::class.java)

    @Test
    fun `getMovies return success`() = runTest {
        // Given
        val mockResponse = MoviesWithGroup(
            data = DiscoverResponse(
                page = 1,
                totalPages = 10,
                results = listOf(),
                totalResults = 100
            ),
            title = "Popular Movies",
            key = "popularity.desc"
        )

        `when`(movieRepository.getMovies("popularity.desc", "Popular Movies", 1))
            .thenReturn(RestResult.Success(mockResponse))

        // When
        val result = movieRepository.getMovies("popularity.desc", "Popular Movies", 1)

        // Then
        assertTrue(result is RestResult.Success)
        assertEquals(mockResponse, (result as RestResult.Success).data)
    }

    @Test
    fun `getMovies should return error `() = runTest {
        // Given
        val exception = Exception("Network error")
        `when`(movieRepository.getMovies("popularity.desc", "Popular Movies", 1))
            .thenReturn(RestResult.Failure(exception))

        // When
        val result = movieRepository.getMovies("popularity.desc", "Popular Movies", 1)

        // Then
        assertTrue(result is RestResult.Failure)
        assertEquals("Network error", (result as RestResult.Failure).exception.message)
    }
}
