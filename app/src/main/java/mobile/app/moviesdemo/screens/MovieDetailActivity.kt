package mobile.app.moviesdemo.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import mobile.app.moviesdemo.viewmodel.MovieDetailState
import mobile.app.moviesdemo.viewmodel.MoviesDetailViewModel

@Composable
fun MovieDetailScreen(movieId: Long, viewModel: MoviesDetailViewModel = hiltViewModel()) {
    val movieState by viewModel.movieDetailState.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.getMoviesDetail(movieId)
    }

    when (movieState) {
        is MovieDetailState.DataState -> {
            val state = movieState as MovieDetailState.DataState
            val configuration = LocalConfiguration.current

            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ExoPlayerView(videoUrl = state.movie.streamSource, viewModel)
            } else {
                MovieDetailContent(state, viewModel)
            }
        }

        is MovieDetailState.InitialState -> {
            LoadingScreen()
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.Red)
    }
}

@Suppress("LongMethod")
@Composable
fun MovieDetailContent(state: MovieDetailState, viewModel: MoviesDetailViewModel) {
    if (state is MovieDetailState.DataState) {
        var isPlaying by remember { mutableStateOf(false) }
        val movie = state.movie
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                if (isPlaying) {
                    ExoPlayerView(videoUrl = movie.streamSource, viewModel)
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(movie.imagePath),
                        contentDescription = "Movie Poster",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.Gray, RoundedCornerShape(8.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movie.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "⭐ ${movie.voteAverage}", fontSize = 16.sp, color = Color.Black)
                Text(text = movie.dateRelease, fontSize = 16.sp, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movie.overView,
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { isPlaying = !isPlaying },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            ) {
                Text(
                    text = if (isPlaying) "Stop" else "Play",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}
