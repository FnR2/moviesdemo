import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaItem.DrmConfiguration
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import mobile.app.moviesdemo.MoviesDetailViewModel
import java.util.UUID

@Composable
fun ExoPlayerView(videoUrl: String, viewModel: MoviesDetailViewModel) {
    val context = LocalContext.current
    val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
    var playWhenReady by rememberSaveable { mutableStateOf(true) }
    val currentPosition = viewModel.currentPosition.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val drmConfiguration =
                DrmConfiguration.Builder(UUID.fromString("EDEF8BA9-79D6-4ACE-A3C8-27DCD51D21ED"))
                    .setLicenseUri(
                        "https://proxy.uat.widevine.com/proxy?video_id=2015_tears&provider=widevin\n" +
                                "e_test"
                    )
                    .build()
            val mediaItem = MediaItem.Builder()
                .setUri(videoUrl)
                .setMimeType("application/dash+xml")
                .setDrmConfiguration(
                    drmConfiguration
                )
                .build()

            setMediaItem(mediaItem)
            prepare()
            seekTo(currentPosition.value)
            playWhenReady = true
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (isActive) {
                viewModel.updatePosition(exoPlayer.currentPosition)
                delay(1000)
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> { viewModel.updatePosition(exoPlayer.currentPosition)
                    playWhenReady = exoPlayer.playWhenReady
                    exoPlayer.playWhenReady = false
                }

                Lifecycle.Event.ON_RESUME -> {
                    exoPlayer.seekTo(viewModel.currentPosition.value)
                    exoPlayer.playWhenReady = playWhenReady
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
        }

    }

}
