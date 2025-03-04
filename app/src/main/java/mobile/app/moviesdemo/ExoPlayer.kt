import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaItem.DrmConfiguration
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.DefaultHttpDataSource.Factory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.drm.FrameworkMediaDrm
import androidx.media3.ui.PlayerView
import java.util.UUID

@Composable
fun ExoPlayerView(videoUrl: String) {
    val context = LocalContext.current
    val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
    var playWhenReady by remember { mutableStateOf(true) }
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

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    playWhenReady = exoPlayer.playWhenReady
                    exoPlayer.playWhenReady = false
                }

                Lifecycle.Event.ON_RESUME -> {
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
