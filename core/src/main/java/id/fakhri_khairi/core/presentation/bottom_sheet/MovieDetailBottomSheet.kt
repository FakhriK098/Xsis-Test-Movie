package id.fakhri_khairi.core.presentation.bottom_sheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import coil.load
import id.fakhri_khairi.core.R
import id.fakhri_khairi.core.base.BaseDialogFragment
import id.fakhri_khairi.core.databinding.BottomSheetMovieDetailBinding
import id.fakhri_khairi.core.misc.convertToImageTmdbUrl
import id.fakhri_khairi.core.presentation.adapter.MovieAdapter
import id.fakhri_khairi.domain.Movie

class MovieDetailBottomSheet(
    private val onShow: () -> Unit,
    private val onDismiss: () -> Unit,
    private val movieList: List<Movie>,
    private val movieDetail: Movie
) : BaseDialogFragment<BottomSheetMovieDetailBinding>() {

    private var latestMovieAdapter = MovieAdapter("horizontal")

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): BottomSheetMovieDetailBinding {
        return BottomSheetMovieDetailBinding.inflate(inflater, container, false)
    }

    override fun BottomSheetMovieDetailBinding.initBinding() {

        initAdapter()
        initListener()
        renderMovie(movieDetail, movieList)
        onShow.invoke()
    }

    private fun BottomSheetMovieDetailBinding.initListener() {
        ivClose.setOnClickListener {
            dismiss()
        }

        ivPlay.setOnClickListener {  }
    }

    private fun BottomSheetMovieDetailBinding.initAdapter() {
        rvLatestMovie.apply {
            adapter = latestMovieAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun BottomSheetMovieDetailBinding.renderMovie(movie: Movie, movieList: List<Movie>) {
        val imgUrl = movie.backdropPath.convertToImageTmdbUrl(700)

        ivPoserMovie.load(imgUrl) {
            error(R.drawable.ic_baseline_broken_image_24)
            placeholder(R.drawable.ic_baseline_broken_image_24)
        }
        tvTitleMovie.text = movie.title
        tvDescMovie.text = HtmlCompat.fromHtml(
            movie.overview,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        latestMovieAdapter.setItems(movieList)
    }

    override fun onDestroyView() {
        onDismiss.invoke()
        super.onDestroyView()
    }

    override suspend fun BottomSheetMovieDetailBinding.setupState() {}

    override suspend fun BottomSheetMovieDetailBinding.setupEvent() {}
}