package app.sco.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import app.sco.myapplication.R
import app.sco.myapplication.acivity.MainActivity
import app.sco.myapplication.databinding.FragmentCoinDetailsBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class CoinDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCoinDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedCoinName = arguments?.getString("coin_name", null)
        GlobalScope.launch {
            (activity as? MainActivity)?.coinViewModel?.getCoinByName(
                selectedCoinName ?: return@launch
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinDetailsBinding.inflate(LayoutInflater.from(context))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
    }

    private fun observeData() {
        GlobalScope.launch {
            (activity as? MainActivity)?.coinViewModel?.selectedCoin?.collect {
                binding.coin = it ?: return@collect

                (activity as? MainActivity)?.runOnUiThread {
                    with(binding) {
                        tvName.text = it.name.lowercase(Locale.getDefault())
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                        tvPrice.text = "Price: \n${it.current_price}"
                        tvMarketcap.text = "Market cap:\n${it.market_cap}"
                        tvHighestvalue.text = "high for 24h:\n${it.high_24h}"
                        tvPricechange.text =
                            "Percentage price change:\n{${it.price_change_percentage_24h.toString()}}"
                        tvMarketcapchange.text =
                            "Percentage market cap (24):\n${it.market_cap_change_percentage_24h}"
                        tvLowestvalue.text = "Lowest value for last 24 h:\n${it.low_24h}"

                        if (it.market_cap_change_percentage_24h < 0)
                            tvMarketcapchange.setTextColor(
                                ContextCompat.getColor(
                                    context ?: return@runOnUiThread, R.color.red
                                )
                            )
                        if (it.price_change_percentage_24h < 0)
                            tvPricechange.setTextColor(
                                ContextCompat.getColor(
                                    context ?: return@runOnUiThread, R.color.red
                                )
                            )
                        if (it.low_24h < 0)
                            tvLowestvalue.setTextColor(
                                ContextCompat.getColor(
                                    context ?: return@runOnUiThread, R.color.red
                                )
                            )
                    }
                    setDataBinding()
                    Glide
                        .with(context ?: return@runOnUiThread)
                        .load(it.image)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding.ivLogo)
                }
            }
        }
    }

    private fun setDataBinding() {
        binding.coin ?: return
        if (binding.coin?.favourite == true) {
            binding.btnLike.setImageResource(android.R.drawable.star_big_on)
        } else {
            binding.btnLike.setImageResource(android.R.drawable.star_big_off)
        }

        binding.btnLike.setOnClickListener {
            val country = binding.coin
            country?.favourite = country?.favourite != true

            if (country?.favourite == true) {
                binding.btnLike.setImageResource(android.R.drawable.star_big_on)
            } else {
                binding.btnLike.setImageResource(android.R.drawable.star_big_off)
            }

            GlobalScope.launch {
                (activity as? MainActivity)?.coinViewModel?.updateFavourites(
                    country ?: return@launch
                )
            }
        }
    }
}