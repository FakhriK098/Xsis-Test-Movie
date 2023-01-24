package id.fakhri_khairi.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.fakhri_khairi.core.R
import id.fakhri_khairi.core.base.BaseAdapter
import id.fakhri_khairi.core.databinding.ItemLatestMovieBinding
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

    private class LatestViewHolder(
        val binding: ItemLatestMovieBinding
    ) : RecyclerView.ViewHolder(binding.root)

    private fun ItemLatestMovieBinding.bind(movie: Movie) {
        val imgUrl = movie.posterPath.convertToImageTmdbUrl(300)

        ivMovePoster.load(imgUrl) {
            error(R.drawable.ic_baseline_broken_image_24)
            placeholder(R.drawable.ic_baseline_broken_image_24)
        }

        root.setOnClickListener { onMovieClick(movie) }
    }

    private fun ItemMovieBinding.bind(movie: Movie) {
        val imgUrl = movie.posterPath.convertToImageTmdbUrl(300)

        ivMovePoster.load(imgUrl) {
            error(R.drawable.ic_baseline_broken_image_24)
            placeholder(R.drawable.ic_baseline_broken_image_24)
        }

        root.setOnClickListener { onMovieClick(movie) }
    }

    fun setOnMovieClicked(OnMovieClick: (Movie) -> Unit) {
        onMovieClick = OnMovieClick
    }

    override fun getViewType(position: Int): Int {
        return if (orientation == "vertical") {
            Constants.BASE_VIEW_TYPE
        } else {
            Constants.LATEST_VIEW_TYPE
        }
    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
         return if (viewType == Constants.BASE_VIEW_TYPE) {
             MovieViewHolder(
                 ItemMovieBinding.inflate(
                     LayoutInflater.from(parent.context), parent, false
                 )
             )
         } else {
             LatestViewHolder(
                 ItemLatestMovieBinding.inflate(
                     LayoutInflater.from(parent.context), parent, false
                 )
             )
         }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            val movie = items[position]
            movie?.let { holder.binding.bind(it) }
        } else if (holder is LatestViewHolder) {
            val movie = items[position]
            movie?.let { holder.binding.bind(it) }
        }
    }
}