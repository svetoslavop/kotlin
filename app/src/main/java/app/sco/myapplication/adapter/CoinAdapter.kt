package app.sco.myapplication.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import app.sco.myapplication.R
import app.sco.myapplication.acivity.MainActivity
import app.sco.myapplication.databinding.CoinListItemBinding
import app.sco.myapplication.db.entity.CoinsDetails
import app.sco.myapplication.fragments.CoinDetailsFragment
import com.bumptech.glide.Glide
import java.util.*

class CoinAdapter(private val coins: List<CoinsDetails>) :
    RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {

    class CoinViewHolder(val binding: CoinListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CoinListItemBinding.inflate(layoutInflater, parent, false)

        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val currentCoin = coins[position]
        holder.binding.apply {
            coin = currentCoin.name.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            symbol = currentCoin.symbol
            price = "Price: ${currentCoin.current_price} USD"
            ivLiked.visibility = if (currentCoin.favourite) View.VISIBLE else View.GONE

            Glide
                // context to use for requesting the image
                .with(root.context)
                // URL to load the asset from
                .load(currentCoin.image)
                .centerCrop()
                // image to be shown until online asset is downloaded
                .placeholder(R.drawable.ic_launcher_foreground)
                // ImageView to load the online asset into when ready
                .into(ivLogo)

            holder.binding.root.setOnClickListener {
                (it.context as MainActivity).supportFragmentManager.commit {
                    val bundle = Bundle()
                    bundle.putString("coin_name", currentCoin.name)
                    replace(R.id.container_view, CoinDetailsFragment::class.java, bundle)
                    addToBackStack("coins_to_details")
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return coins.size
    }
}