package app.sco.myapplication.acivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.RoomDatabase
import app.sco.myapplication.R
import app.sco.myapplication.adapter.CoinAdapter
import app.sco.myapplication.databinding.ActivityMainBinding
import app.sco.myapplication.db.AppDatabase
import app.sco.myapplication.factory.CoinViewModelFactory
import app.sco.myapplication.repository.CoinRepository
import app.sco.myapplication.services.CoinService
import app.sco.myapplication.util.NetworkUtil
import app.sco.myapplication.viewmodel.CoinViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var coinService: CoinService

    private lateinit var coinRepository: CoinRepository

    lateinit var coinViewModel: CoinViewModel

    lateinit var db: RoomDatabase

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.coingecko.com/api/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        init()
        observeData()

        if (!NetworkUtil.isConnected(this)) {
            Snackbar.make(
                binding.root,
                "No internet connection, information could be outdated",
                Snackbar.LENGTH_LONG
            ).show()
        }

        GlobalScope.launch {
            coinViewModel.getCoins()
        }
    }
    private fun init() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "countries_database"
        ).build()

        val coinDao = (db as AppDatabase).CoinsDao()
        coinService = retrofit.create(CoinService::class.java)
        coinRepository = CoinRepository(this, coinService, coinDao)
        val coinViewModelFactory = CoinViewModelFactory(coinRepository)
        coinViewModel =
            ViewModelProvider(this, coinViewModelFactory)[CoinViewModel::class.java]
    }

    private fun observeData() {
        GlobalScope.launch {
            coinViewModel.coinsList.collect {
                runOnUiThread {
                    val coins = it
                    val sortedCoins = coins.sortedByDescending { it.favourite }
                    val adapter = CoinAdapter(sortedCoins)
                    binding.coinsList.adapter = adapter
                }
            }
        }
    }
}
