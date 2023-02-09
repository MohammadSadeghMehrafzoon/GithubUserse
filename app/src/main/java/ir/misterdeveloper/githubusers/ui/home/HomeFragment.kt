package ir.misterdeveloper.githubusers.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.misterdeveloper.githubusers.R
import ir.misterdeveloper.githubusers.adapter.UserAdapter
import ir.misterdeveloper.githubusers.database.AppDatabase
import ir.misterdeveloper.githubusers.databinding.FragmentHomeBinding
import ir.misterdeveloper.githubusers.model.Repository.GithubRepository
import ir.misterdeveloper.githubusers.model.Response.UserResponse
import ir.misterdeveloper.githubusers.model.model.GithubViewModel
import ir.misterdeveloper.githubusers.model.model.GithubViewModelFactory
import ir.misterdeveloper.githubusers.util.ApiServiceSingleton
import ir.misterdeveloper.githubusers.util.toast


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private lateinit var githubViewModel: GithubViewModel
    private val compositeDisposable = CompositeDisposable()
    private val shimmerFrameLayout: ShimmerFrameLayout? = null
    private lateinit var appDatabase: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        appDatabase = AppDatabase.getInstance(context)
        githubViewModel = ViewModelProvider(
            this,
            GithubViewModelFactory(GithubRepository(ApiServiceSingleton.apiService!!))
        )[GithubViewModel::class.java]

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    activity!!.finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)

        goneShimmer()
        binding.textViewNoResult.visibility = View.VISIBLE
        binding.imageViewNoResult.visibility = View.VISIBLE
        search()

        binding.imageViewFavorite.setOnClickListener({

            findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)

        })




        return binding.root


    }


    private fun searchResult(query: String) {

        visibleShimmer()
        val q = query.toLowerCase()
        if (q.equals("")) {
            goneShimmer()
            binding.recyclerUser.visibility = View.GONE
            binding.textViewNoResult.visibility = View.VISIBLE
            binding.imageViewNoResult.visibility = View.VISIBLE
        } else {
            binding.textViewNoResult.visibility = View.GONE
            binding.imageViewNoResult.visibility = View.GONE
            getUsersBySearch(q)
        }


    }


    private fun search() {

        binding.searchView.setQueryHint("search")
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchResult(newText!!)
                return true
            }

        })
    }


    private fun visibleShimmer() {

        shimmerFrameLayout?.stopShimmer()
        binding.shimmerView.visibility = View.VISIBLE

    }

    private fun goneShimmer() {

        shimmerFrameLayout?.stopShimmer()
        binding.shimmerView.visibility = View.GONE

    }


    private fun getUsersBySearch(query: String) {

        visibleShimmer()
        githubViewModel.getUsersBySearch(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<UserResponse> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(userResponse: UserResponse) {


                    binding.recyclerUser.adapter =
                        UserAdapter(
                            activity,
                            userResponse.informationUsers,
                            appDatabase
                        )
                    binding.recyclerUser.layoutManager = LinearLayoutManager(
                        requireContext().applicationContext,
                        RecyclerView.VERTICAL,
                        false
                    )


                    goneShimmer()


                }

                override fun onError(e: Throwable) {

                    context!!.toast(requireContext(), activity!!.getString(R.string.network))

                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()


        compositeDisposable.clear()
    }


}