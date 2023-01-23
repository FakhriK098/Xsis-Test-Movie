package id.fakhri_khairi.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.fakhri_khairi.core.R
import id.fakhri_khairi.core.base.BaseAdapter
import id.fakhri_khairi.core.databinding.ItemBannerMovieBinding
import id.fakhri_khairi.core.databinding.ItemMovieBinding
import id.fakhri_khairi.core.misc.convertToImageTmdbUrl
import id.fakhri_khairi.data.misc.Constants
import id.fakhri_khairi.domain.Movie

class MovieAdapter(
    private val orientation: String = "vertical"
) : BaseAdapter<Movie>() {

    private var onMovieClick : (Movie) -> Unit = {_ ->}

    private class MovieViewHolder(
        val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root)

    private class BannerViewHolder(
        val binding: ItemBannerMovieBinding
    ) : RecyclerView.ViewHolder(binding.root)

    private fun ItemMovieBinding.bind(movie: Movie) {
        val posterParams = ivMovePoster.layoutParams as ConstraintLayout.LayoutParams
        posterParams.height = if (orientation == "vertical") 244 else 150

        val imgUrl = movie.posterPath.convertToImageTmdbUrl(300)

        ivMovePoster.load(imgUrl) {
            error(R.drawable.ic_baseline_broken_image_24)
            placeholder(R.drawable.ic_baseline_broken_image_24)
        }

        root.setOnClickListener { onMovieClick(movie) }
    }

    private fun ItemBannerMovieBinding.bind(movie: Movie) {
        val imgUrl = movie.posterPath.convertToImageTmdbUrl(500)
        ivMovePoster.load(imgUrl){
            error(R.drawable.ic_baseline_broken_image_24)
            placeholder(R.drawable.ic_baseline_broken_image_24)
        }

        tvTitleMovie.text = movie.title
        tvDescMovie.text = HtmlCompat.fromHtml(
            movie.overview,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        root.setOnClickListener { onMovieClick(movie) }
    }

    fun setOnMovieClicked(OnMovieClick: (Movie) -> Unit) {
        onMovieClick = OnMovieClick
    }

    override fun getViewType(position: Int): Int = items[position]?.viewType!!

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constants.BASE_VIEW_TYPE) {
            MovieViewHolder(
                ItemMovieBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        } else {
            BannerViewHolder(
                ItemBannerMovieBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            val movie = items[position]
            movie?.let { holder.binding.bind(it) }
        } else if (holder is BannerViewHolder) {
            val movie = items[position]
            movie?.let { holder.binding.bind(it) }
        }
    }
}