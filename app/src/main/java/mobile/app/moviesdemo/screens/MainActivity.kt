package mobile.app.moviesdemo.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import dagger.hilt.android.AndroidEntryPoint
import mobile.app.moviesdemo.ui.theme.MoviesDemoTheme
import mobile.app.moviesdemo.viewmodel.INTENT_PARAM
import mobile.app.moviesdemo.viewmodel.MovieDetailUIModel
import mobile.app.moviesdemo.viewmodel.MoviesViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MoviesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesDemoTheme {
                Scaffold(
                    topBar = { MovieToolbar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MovieScreen(viewModel, innerPadding)

                }
            }
        }
    }
}


@Composable
fun MovieScreen(viewModel: MoviesViewModel, paddingValues: PaddingValues) {
    val moviesState by viewModel.moviesState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(paddingValues)
    ) {

        items(moviesState.list) { movie ->
            SectionTitle(movie.title)
            MovieRow(movie.key,movie.movieList,viewModel)
        }

    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .background(Color.Red)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

@Composable
fun MovieRow(key:String,movieUrls: List<MovieDetailUIModel>,viewModel: MoviesViewModel) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movieUrls.size) { index ->
            MovieCard(movieUrls[index])
            viewModel.paginateMovies(key,index,movieUrls.size)
        }
    }
}

@Composable
fun MovieCard(movie: MovieDetailUIModel) {
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(120.dp)
            .height(180.dp)
            .clickable {
                val intent = Intent(context, MovieDetailActivity::class.java).apply {
                    putExtra(INTENT_PARAM, movie.id)
                }
                context.startActivity(intent)
            }
    ) {
        if (movie.imagePath != null) {
            Image(
                painter = rememberAsyncImagePainter(model = movie.imagePath, onSuccess = {
                    Log.d("MovieCard", "Image loaded successfully: $movie.imagePath")
                }, onError = {
                    Log.e("MovieCard", "Failed to load image: $movie.imagePath")
                }),
                contentDescription = "Movie Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),

                )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "NO PHOTO AVAILABLE", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieToolbar() {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Movies Demo",
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Black
            )
        )

        Divider(color = Color.Red, thickness = 2.dp)
    }
}