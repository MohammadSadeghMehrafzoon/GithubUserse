package ir.misterdeveloper.githubusers.ui.detail

import android.graphics.Color
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
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.misterdeveloper.githubusers.R
import ir.misterdeveloper.githubusers.adapter.FollowingAndFollowersAdapter
import ir.misterdeveloper.githubusers.databinding.FragmentDetailBinding
import ir.misterdeveloper.githubusers.model.Repository.GithubRepository
import ir.misterdeveloper.githubusers.model.Response.InformationUsers
import ir.misterdeveloper.githubusers.model.model.GithubViewModel
import ir.misterdeveloper.githubusers.model.model.GithubViewModelFactory
import ir.misterdeveloper.githubusers.util.ApiServiceSingleton
import ir.misterdeveloper.githubusers.util.toast


class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    private val shimmerFrameLayout: ShimmerFrameLayout? = null
    private lateinit var githubViewModel: GithubViewModel
    private val compositeDisposable = CompositeDisposable()
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)

        githubViewModel = ViewModelProvider(
            this,
            GithubViewModelFactory(GithubRepository(ApiServiceSingleton.apiService!!))
        )[GithubViewModel::class.java]


        setData()
        eventButtonFollowing()


        binding.imageViewBack.setOnClickListener {

            findNavController().navigate(R.id.action_detailFragment_to_homeFragment)

        }

        binding.buttonFollowing.setOnClickListener {

            visibleShimmer()
            eventButtonFollowing()

        }

        binding.buttonFollowers.setOnClickListener {


            visibleShimmer()
            eventButtonFollowers()
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()


        compositeDisposable.clear()
    }

    /**Determining the number of followers, followers and repositories*/
    private fun setData() {


        username = requireArguments().getString("userName")
        val avatarUrl = requireArguments().getString("avatarUrl")

        binding.textViewUserName.text = username
        Picasso.get().load(avatarUrl).placeholder(R.drawable.github)
            .into(binding.profileImage)


        githubViewModel.getFollowing(username!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<InformationUsers>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(informationUsersResponse: List<InformationUsers>) {

                    binding.textviewNumberFollowing.text = informationUsersResponse.size.toString()

                }

                override fun onError(e: Throwable) {

                    context!!.toast(requireContext(), activity!!.getString(R.string.network))

                }

            })

        githubViewModel.getFollowers(username!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<InformationUsers>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(informationUsersResponse: List<InformationUsers>) {

                    binding.textviewNumberFollowers.text = informationUsersResponse.size.toString()

                }

                override fun onError(e: Throwable) {


                    context!!.toast(requireContext(), activity!!.getString(R.string.network))

                }

            })



        githubViewModel.getRepos(username!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<InformationUsers>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(informationUsersResponse: List<InformationUsers>) {

                    binding.textviewNumberRepository.text = informationUsersResponse.size.toString()

                }

                override fun onError(e: Throwable) {

                    context!!.toast(requireContext(), activity!!.getString(R.string.network))

                }

            })

    }

    private fun eventButtonFollowers() {

        binding.buttonFollowers.setBackgroundColor(Color.WHITE)
        binding.buttonFollowers.setTextColor(Color.BLACK)
        binding.buttonFollowing.setBackgroundColor(requireActivity().resources.getColor(R.color.pink_900))
        binding.buttonFollowing.setTextColor(Color.WHITE)
        showFollowers()


    }

    private fun eventButtonFollowing() {


        binding.buttonFollowers.setBackgroundColor(requireActivity().resources.getColor(R.color.pink_900))
        binding.buttonFollowers.setTextColor(Color.WHITE)
        binding.buttonFollowing.setBackgroundColor(Color.WHITE)
        binding.buttonFollowing.setTextColor(Color.BLACK)
        showFollowing()

    }

    private fun visibleShimmer() {

        shimmerFrameLayout?.stopShimmer()
        binding.shimmerView.visibility = View.VISIBLE

    }

    private fun goneShimmer() {

        shimmerFrameLayout?.stopShimmer()
        binding.shimmerView.visibility = View.GONE

    }

    private fun showFollowers() {
        githubViewModel.getFollowers(username!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<InformationUsers>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(informationUsersResponse: List<InformationUsers>) {


                    binding.recyclerUser.adapter =
                        FollowingAndFollowersAdapter(
                            requireContext().applicationContext,
                            informationUsersResponse
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

    private fun showFollowing() {
        githubViewModel.getFollowing(username!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<InformationUsers>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(userResponse: List<InformationUsers>) {


                    binding.recyclerUser.adapter =
                        FollowingAndFollowersAdapter(
                            requireContext().applicationContext,
                            userResponse
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
}