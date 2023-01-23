package id.fakhri_khairi.core.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import id.fakhri_khairi.core.base.BaseFragment
import id.fakhri_khairi.core.databinding.FragmentSearchMovieBinding
import id.fakhri_khairi.core.presentation.adapter.MovieAdapter
import id.fakhri_khairi.domain.Movie

class SearchMovieFragment : BaseFragment<FragmentSearchMovieBinding>() {

    private val movieList : MutableList<Movie> = mutableListOf()
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

        }

        svMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {  }
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
        }
    }

    private fun renderMovie(data: List<Movie>) {
        movieList.clear()
        movieList.addAll(data)
        movieAdapter.setItems(movieList)
    }

    override suspend fun FragmentSearchMovieBinding.setupState() {}

    override suspend fun FragmentSearchMovieBinding.setupEvent() {}
}