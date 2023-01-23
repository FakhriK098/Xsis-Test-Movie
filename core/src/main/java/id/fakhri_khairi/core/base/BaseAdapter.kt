package id.fakhri_khairi.core.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.fakhri_khairi.core.databinding.ViewLoadingItemBinding

abstract class BaseAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var _items: MutableList<T?> = mutableListOf()
    protected val items: MutableList<T?>
        get() = _items

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<T>) {
        _items.clear()
        _items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<T>) {
        // Hide loading indicator
        if (items.isEmpty()) {
            _items.removeAt(_items.lastIndex)
            notifyItemRemoved(_items.lastIndex)
            return
        }

        val firstInsertedIndex = _items.lastIndex + 1
        _items.addAll(items)
        notifyItemRangeChanged(firstInsertedIndex, _items.lastIndex)
    }

    override fun getItemViewType(position: Int): Int {
        if (_items[position] == null) return LOADING_VIEW_TYPE
        return getViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == LOADING_VIEW_TYPE) return LoadingViewHolder(
            ViewLoadingItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

        return getViewHolder(parent, viewType)
    }

    private class LoadingViewHolder(
        binding: ViewLoadingItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    fun showLoading() {
        _items.add(null)
        notifyItemInserted(_items.lastIndex)
    }

    override fun getItemCount(): Int = _items.size

    abstract fun getViewType(position: Int): Int

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    companion object {
        private const val LOADING_VIEW_TYPE = Int.MAX_VALUE
    }
}