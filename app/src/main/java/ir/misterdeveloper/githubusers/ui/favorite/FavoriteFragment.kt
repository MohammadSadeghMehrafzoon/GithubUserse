package ir.misterdeveloper.githubusers.ui.favorite

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import ir.misterdeveloper.githubusers.R
import ir.misterdeveloper.githubusers.adapter.FavoriteAdapter

import ir.misterdeveloper.githubusers.database.AppDatabase
import ir.misterdeveloper.githubusers.database.Favorite
import ir.misterdeveloper.githubusers.databinding.FragmentFavoriteBinding
import ir.misterdeveloper.githubusers.model.Repository.GithubRepository

import ir.misterdeveloper.githubusers.model.model.GithubViewModel
import ir.misterdeveloper.githubusers.model.model.GithubViewModelFactory
import ir.misterdeveloper.githubusers.util.ApiServiceSingleton


class FavoriteFragment : Fragment() {

    lateinit var binding: FragmentFavoriteBinding
    private lateinit var githubViewModel: GithubViewModel
    private var appDatabase: AppDatabase? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentFavoriteBinding.inflate(layoutInflater)

        appDatabase = AppDatabase.getInstance(activity)

        githubViewModel = ViewModelProvider(
            this,
            GithubViewModelFactory(GithubRepository(ApiServiceSingleton.apiService!!))
        )[GithubViewModel::class.java]


        showFavoriteList()

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)


        binding.imageViewBack.setOnClickListener {

            findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)

        }


        return binding.root
    }


    private fun showFavoriteList() {


        val favoriteList: List<Favorite> = appDatabase!!.daoDatabase().favoriteUser

        if (favoriteList.isEmpty()) {

            binding.imageViewNoResult.visibility = View.VISIBLE
            binding.textViewNoResult.visibility = View.VISIBLE


        } else {

            binding.recyclerFavorite.adapter =
                FavoriteAdapter(
                    activity,
                    favoriteList,
                    appDatabase
                )
            binding.recyclerFavorite.layoutManager = LinearLayoutManager(
                requireContext().applicationContext,
                RecyclerView.VERTICAL,
                false
            )


        }


    }

}

