package id.fakhri_khairi.core.presentation.youtube

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import id.fakhri_khairi.core.BuildConfig
import id.fakhri_khairi.core.R
import id.fakhri_khairi.data.misc.Constants

class YouTubePlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private var youtubeId : String? = null

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)

        val layout = layoutInflater.inflate(R.layout.activity_youtube_player, null) as ConstraintLayout
        setContentView(layout)

        youtubeId = intent?.extras?.getString(Constants.YOUTUBE_ID)

        val playerView = YouTubePlayerView(this)
        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )

        layout.addView(playerView)

        playerView.initialize(BuildConfig.YOUTUBE_TOKEN, this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youtubePlayer: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        if (!wasRestored)
            youtubeId?.let {
                youtubePlayer?.cueVideo(it)
            }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {
        if (youTubeInitializationResult?.isUserRecoverableError == true) {
            youTubeInitializationResult.getErrorDialog(this, 0).show()
        } else {
            val errorMessage = "There was an error initializing the YoutubePlayer ($youTubeInitializationResult)"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}