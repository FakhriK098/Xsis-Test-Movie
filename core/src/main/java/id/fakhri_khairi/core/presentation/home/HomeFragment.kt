package id.fakhri_khairi.core.presentation.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import id.fakhri_khairi.core.R
import id.fakhri_khairi.core.base.BaseFragment
import id.fakhri_khairi.core.databinding.ChipGenreBinding
import id.fakhri_khairi.core.databinding.FragmentHomeBinding
import id.fakhri_khairi.core.presentation.adapter.BannerAdapter
import id.fakhri_khairi.core.presentation.adapter.MovieAdapter
import id.fakhri_khairi.core.presentation.bottom_sheet.MovieDetailBottomSheet
import id.fakhri_khairi.core.presentation.youtube.YouTubePlayerActivity
import id.fakhri_khairi.data.misc.Constants
import id.fakhri_khairi.domain.Genre
import id.fakhri_khairi.domain.Movie
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    private var isBottomSheetShow = false
    private var genreSelected : Genre? = null
    private val bannerMovieList : MutableList<Movie> = mutableListOf()
    private val latestMovieList : MutableList<Movie> = mutableListOf()
    private val movieByGenreList : MutableList<Movie> = mutableListOf()
    private val genreList : MutableList<Genre> = mutableListOf()
    private val indicators: ArrayList<TextView> = arrayListOf()
    private var latestMovieAdapter = MovieAdapter("horizontal")
    private var movieByGenreAdapter = MovieAdapter()
    private var bannerAdapter = BannerAdapter()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun FragmentHomeBinding.initBinding() {
        initAdapter()
        initListener()

        fetchData()
    }

    private fun fetchData() {
        viewModel.getPopularMovie()
        viewModel.getGenreMovie()
    }

    private fun FragmentHomeBinding.initListener() {

        toolbarMovie.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionSearch -> {
                    findNavController().navigate(R.id.action_homeFragment_to_searchMovieFragment)
                }
            }

            true
        }

        bannerAdapter.setOnMovieClicked { movie ->
            showMovieDetailBottomSheet(movie)
        }

        latestMovieAdapter.setOnMovieClicked { movie ->
            showMovieDetailBottomSheet(movie)
        }

        movieByGenreAdapter.setOnMovieClicked { movie ->
            showMovieDetailBottomSheet(movie)
        }
    }

    private fun FragmentHomeBinding.initAdapter() {
        rvLatestMovie.apply {
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            itemAnimator = DefaultItemAnimator()
            adapter = latestMovieAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        rvMovieByGenre.apply {
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            itemAnimator = DefaultItemAnimator()
            adapter = movieByGenreAdapter
            layoutManager = GridLayoutManager(context, 2,)
        }

        vgBannerMovie.apply {
            adapter = bannerAdapter
            isNestedScrollingEnabled = false

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback()  {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    selectedIndicator(position)
                }
            })
        }
    }

    private fun selectedIndicator(position: Int) {
        for (i in 0 until bannerMovieList.size) {
            indicators[i].setTextColor(
                requireActivity().getColor(
                    if (i != position)
                        R.color.grey_D9D9D9
                    else
                        R.color.red_E92823
                )
            )
        }
    }

    private fun FragmentHomeBinding.initIndicator() {
        llIndicator.removeAllViews()
        indicators.clear()
        llIndicator.removeAllViews()
        for (i in 0 until bannerMovieList.size) {
            if (indicators.size == bannerMovieList.size) {
                indicators[i] = TextView(requireContext())
            } else {
                indicators.add(TextView(requireContext()))
            }
            indicators[i].text =
                HtmlCompat.fromHtml("&#9679", HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            indicators[i].textSize = 18f
            llIndicator.addView(indicators[i])
        }
        if (indicators.isNotEmpty()) selectedIndicator(0)
    }

    private fun FragmentHomeBinding.renderBannerMovie() {
        bannerAdapter.setItems(bannerMovieList)
        if (bannerMovieList.isNotEmpty()) {
            initIndicator()
        }
    }

    private fun FragmentHomeBinding.renderLatestMovie() {
        latestMovieAdapter.setItems(latestMovieList)
    }

    private fun FragmentHomeBinding.renderMovieByGenre(data: List<Movie>) {
        movieByGenreList.clear()
        movieByGenreList.addAll(data)
        movieByGenreAdapter.setItems(movieByGenreList)
    }

    private fun FragmentHomeBinding.renderGenreMovie() {
        if (genreList.isNotEmpty()) {

            cgGenreMovie.removeAllViews()
            for (genre in genreList) {
                val chip = ChipGenreBinding.inflate(
                    layoutInflater,
                    cgGenreMovie,
                    false
                ).root

                chip.apply {
                    id = genre.id
                    text = genre.name
                    isCheckable = true
                    isCheckedIconVisible = false
                    setOnClickListener {
                        genreSelected = genre
                        genreSelected?.let {
                            viewModel.getMovieByGenre(it.id)
                        }
                    }
                }
                cgGenreMovie.addView(chip)
            }

            genreSelected = genreList.first()
            val chipSelected = cgGenreMovie.getChildAt(0) as Chip
            chipSelected.isChecked = true
        }

        genreSelected?.let {
            viewModel.getMovieByGenre(it.id)
        }
    }

    private fun showMovieDetailBottomSheet(movie: Movie) {
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

    override suspend fun FragmentHomeBinding.setupState() {
        viewModel.state.collect {
            when(it) {
                HomeState.Idle -> {}
                is HomeState.SuccessGetMovieGenre -> {
                    genreList.clear()
                    genreList.addAll(it.data)
                    renderGenreMovie()
                }
                is HomeState.SuccessGetNowPlaying -> {
                    latestMovieList.clear()
                    latestMovieList.addAll(it.data)
                    renderLatestMovie()
                }
                is HomeState.SuccessGetPopularMovie -> {
                    bannerMovieList.clear()
                    bannerMovieList.addAll(it.data)
                    renderBannerMovie()
                    viewModel.getNowPlaying()
                }
            }
        }
    }

    override suspend fun FragmentHomeBinding.setupEvent() {
        viewModel.event.collect {
            when(it) {
                is HomeEvent.Error -> {}
                is HomeEvent.SuccessGetMovieByGenre -> {
                    renderMovieByGenre(it.data)
                }
            }
        }
    }
}