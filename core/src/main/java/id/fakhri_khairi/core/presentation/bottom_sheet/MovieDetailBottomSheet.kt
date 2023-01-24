package id.fakhri_khairi.core.presentation.bottom_sheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import id.fakhri_khairi.core.R
import id.fakhri_khairi.core.base.BaseDialogFragment
import id.fakhri_khairi.core.databinding.BottomSheetMovieDetailBinding
import id.fakhri_khairi.core.misc.convertToImageTmdbUrl
import id.fakhri_khairi.core.presentation.adapter.MovieAdapter
import id.fakhri_khairi.domain.Movie
import id.fakhri_khairi.domain.Video
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MovieDetailBottomSheet(
    private val onShow: () -> Unit,
    private val onDismiss: () -> Unit,
    private val movieDetail: Movie
) : BaseDialogFragment<BottomSheetMovieDetailBinding>() {
    private val viewModel by viewModels<MovieDetailViewModel>()
    private var latestMovieAdapter = MovieAdapter("horizontal")
    private var videoSelected : Video? = null
    private var onVideoClick : (Video) -> Unit = {_ ->}

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): BottomSheetMovieDetailBinding {
        return BottomSheetMovieDetailBinding.inflate(inflater, container, false)
    }

    override fun BottomSheetMovieDetailBinding.initBinding() {

        initAdapter()
        initListener()
        renderMovie(movieDetail)
        viewModel.getMovieVideo(movieDetail.id)
        onShow.invoke()
    }

    private fun BottomSheetMovieDetailBinding.initListener() {
        ivClose.setOnClickListener {
            dismiss()
        }

        ivPlay.setOnClickListener {
            videoSelected?.let {
                onVideoClick(it)
            }
        }
    }

    private fun BottomSheetMovieDetailBinding.initAdapter() {
        rvLatestMovie.apply {
            adapter = latestMovieAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun BottomSheetMovieDetailBinding.renderMovie(movie: Movie) {
        val imgUrl = movie.backdropPath.convertToImageTmdbUrl(500)

        ivPoserMovie.load(imgUrl) {
            error(R.drawable.ic_baseline_broken_image_24)
            placeholder(R.drawable.ic_baseline_broken_image_24)
        }
        tvTitleMovie.text = movie.title
        tvDescMovie.text = HtmlCompat.fromHtml(
            movie.overview,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    fun setOnVideoClicked(OnVideClick : (Video) -> Unit) {
        onVideoClick = OnVideClick
    }

    override fun onDestroyView() {
        onDismiss.invoke()
        super.onDestroyView()
    }

    override suspend fun BottomSheetMovieDetailBinding.setupState() {}

    override suspend fun BottomSheetMovieDetailBinding.setupEvent() {
        viewModel.event.collect {
            when(it) {
                MovieDetailEvent.Empty -> {
                    ivPlay.isVisible = false
                }
                is MovieDetailEvent.Error -> ivPlay.isVisible = false
                is MovieDetailEvent.Success -> {
                    ivPlay.isVisible = true
                    videoSelected = it.data
                    viewModel.getMovieRecommendation(movieDetail.id)
                }
                is MovieDetailEvent.SuccessGetMovieRecommendation -> {
                    latestMovieAdapter.setItems(it.data)
                }
            }
        }
    }
}