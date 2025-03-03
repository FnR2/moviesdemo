package mobile.app.moviesdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import dagger.hilt.android.AndroidEntryPoint
import mobile.app.moviesdemo.INTENT_PARAM
import mobile.app.moviesdemo.MoviesDetailViewModel
import mobile.app.moviesdemo.MoviesViewModel

@AndroidEntryPoint
class MovieDetailActivity : ComponentActivity() {
    private val viewModel: MoviesDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieId = intent.extras!!.get("movieId") as Long

        setContent {
            LaunchedEffect(movieId) {
                viewModel.getMoviesDetail(movieId)
            }
            val movieState by viewModel.movieDetailState.collectAsState()
            MovieDetailScreen(movieState)
        }
    }
}

@Composable
fun MovieDetailScreen(state: MovieDetailState) {
    if (state is MovieDetailState.DataState) {
        val movie = state.movie
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(movie.imagePath),
                contentDescription = "Movie Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray, RoundedCornerShape(8.dp))
            )

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
        }

    }
}
