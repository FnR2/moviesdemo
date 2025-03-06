package mobile.app.moviesdemo.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import mobile.app.moviesdemo.NavigateEvent
import mobile.app.moviesdemo.ShowSnackbarEvent
import mobile.app.moviesdemo.ui.theme.MoviesDemoTheme
import mobile.app.moviesdemo.viewmodel.MovieDetailUIModel
import mobile.app.moviesdemo.viewmodel.MoviesViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesDemoTheme {
                val navController = rememberNavController()

                Scaffold(
                    topBar = { MovieToolbar() },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "movie_list",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("movie_list") {
                            val viewModel: MoviesViewModel = hiltViewModel()
                            MovieScreen(viewModel, navController)
                        }
                        composable("movie_detail/{movieId}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movieId")?.toLongOrNull()
                            movieId?.let {
                                MovieDetailScreen(movieId)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieScreen(viewModel: MoviesViewModel, navController: NavController) {
    val moviesState by viewModel.moviesState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is NavigateEvent -> {
                    navController.navigate("movie_detail/${event.id}")
                }

                is ShowSnackbarEvent -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(innerPadding)
        ) {
            items(moviesState.list) { movie ->
                SectionTitle(movie.title)
                MovieRow(movie.key, movie.movieList, viewModel)
            }
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
fun MovieRow(key: String, movieUrls: List<MovieDetailUIModel>, viewModel: MoviesViewModel) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movieUrls.size) { index ->
            MovieCard(movieUrls[index])
            viewModel.paginateMovies(key, index, movieUrls.size)
        }
    }
}

@Composable
fun MovieCard(movie: MovieDetailUIModel, viewModel: MoviesViewModel = hiltViewModel()) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(120.dp)
            .height(180.dp)
            .clickable {
                viewModel.navigateDetail(movie.id)
            }
    ) {
        if (movie.imagePath != null) {
            Image(
                painter = rememberAsyncImagePainter(model = movie.imagePath),
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
