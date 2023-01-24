package id.fakhri_khairi.core.presentation.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.fakhri_khairi.core.base.BaseFragment
import id.fakhri_khairi.core.databinding.FragmentSearchMovieBinding
import id.fakhri_khairi.core.presentation.adapter.MovieAdapter
import id.fakhri_khairi.core.presentation.bottom_sheet.MovieDetailBottomSheet
import id.fakhri_khairi.core.presentation.youtube.YouTubePlayerActivity
import id.fakhri_khairi.data.misc.Constants
import id.fakhri_khairi.domain.Movie
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchMovieFragment : BaseFragment<FragmentSearchMovieBinding>() {
    private val viewModel by viewModels<SearchMovieViewModel>()
    private val movieList : MutableList<Movie> = mutableListOf()
    private var isBottomSheetShow = false
    private var movieAdapter = MovieAdapter()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchMovieBinding {
        return FragmentSearchMovieBinding.inflate(inflater, container, false)
    }

    override fun FragmentSearchMovieBinding.initBinding() {
        initAdapter()
        initListener()
    }

    private fun FragmentSearchMovieBinding.initListener() {
        movieAdapter.setOnMovieClicked { movie ->
            val movieDetailBottomSheet = MovieDetailBottomSheet(
                movieDetail = movie,
                onShow = { isBottomSheetShow = true },
                onDismiss = { isBottomSheetShow = false }
            )

            if (!isBottomSheetShow)
                movieDetailBottomSheet.show(childFragmentManager, movieDetailBottomSheet.tag)

            movieDetailBottomSheet.setOnVideoClicked { video ->
                val intent = Intent(requireActivity(), YouTubePlayerActivity::class.java)
                intent.putExtra(Constants.YOUTUBE_ID, video.key)
                startActivity(intent)
            }
        }

        svMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchMovie(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun FragmentSearchMovieBinding.initAdapter() {
        rvMovie.apply {
            adapter = movieAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(context, 2,)
        }
    }

    private fun renderMovie(data: List<Movie>) {
        movieList.clear()
        movieList.addAll(data)
        movieAdapter.setItems(movieList)
    }

    override suspend fun FragmentSearchMovieBinding.setupState() {}

    override suspend fun FragmentSearchMovieBinding.setupEvent() {
        viewModel.event.collect {
            when(it) {
                SearchMovieEvent.Empty -> {
                    renderMovie(listOf())
                }
                is SearchMovieEvent.Error -> renderMovie(listOf())
                is SearchMovieEvent.Success -> renderMovie(it.data)
            }
        }
    }
}