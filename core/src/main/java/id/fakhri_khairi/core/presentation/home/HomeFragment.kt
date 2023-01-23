package id.fakhri_khairi.core.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import id.fakhri_khairi.core.R
import id.fakhri_khairi.core.base.BaseFragment
import id.fakhri_khairi.core.databinding.ChipGenreBinding
import id.fakhri_khairi.core.databinding.FragmentHomeBinding
import id.fakhri_khairi.core.presentation.adapter.MovieAdapter
import id.fakhri_khairi.core.presentation.bottom_sheet.MovieDetailBottomSheet
import id.fakhri_khairi.domain.Genre
import id.fakhri_khairi.domain.Movie

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private var isBottomSheetShow = false
    private var genreSelected : Genre? = null
    private val bannerMovieList : MutableList<Movie> = mutableListOf()
    private val latestMovieList : MutableList<Movie> = mutableListOf()
    private val movieByGenreList : MutableList<Movie> = mutableListOf()
    private val genreList : MutableList<Genre> = mutableListOf()
    private lateinit var indicators: ArrayList<TextView>
    private var latestMovieAdapter = MovieAdapter("horizontal")
    private var movieByGenreAdapter = MovieAdapter()
    private var bannerAdapter = MovieAdapter()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun FragmentHomeBinding.initBinding() {
        initAdapter()
        initListener()
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
        }

        rvMovieByGenre.apply {
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            itemAnimator = DefaultItemAnimator()
            adapter = movieByGenreAdapter
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

    private fun FragmentHomeBinding.renderBannerMovie(data: List<Movie>) {
        bannerMovieList.clear()
        bannerMovieList.addAll(data)
        bannerAdapter.setItems(bannerMovieList)
        initIndicator()
    }

    private fun FragmentHomeBinding.renderLatestMovie(data: List<Movie>) {
        latestMovieList.clear()
        latestMovieList.addAll(data)
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
                    }
                }
                cgGenreMovie.addView(chip)
            }

            genreSelected = genreList.first()
            val chipSelected = cgGenreMovie.getChildAt(0) as Chip
            chipSelected.isCheckable = true
        }

        genreSelected?.let {  }
    }

    private fun showMovieDetailBottomSheet(movie: Movie) {
        val movieDetailBottomSheet = MovieDetailBottomSheet(
            movieDetail = movie,
            movieList = latestMovieList,
            onShow = { isBottomSheetShow = false },
            onDismiss = { isBottomSheetShow = true }
        )

        if (!isBottomSheetShow)
            movieDetailBottomSheet.show(childFragmentManager, movieDetailBottomSheet.tag)
    }

    override suspend fun FragmentHomeBinding.setupState() {}

    override suspend fun FragmentHomeBinding.setupEvent() {}
}