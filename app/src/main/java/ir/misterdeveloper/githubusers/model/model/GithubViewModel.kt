package ir.misterdeveloper.githubusers.model.model

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import ir.misterdeveloper.githubusers.model.Repository.GithubRepository
import ir.misterdeveloper.githubusers.model.Response.InformationUsers
import ir.misterdeveloper.githubusers.model.Response.UserResponse

class GithubViewModel(private val githubRepository: GithubRepository) : ViewModel()  {

    fun getUsersBySearch(query: String): Single<UserResponse> {
        return githubRepository.getUsersBySearch(query)
    }

    fun getFollowers(username: String): Single<List<InformationUsers>> {
        return githubRepository.getFollowers(username)
    }



    fun getFollowing(username: String): Single<List<InformationUsers>> {
        return githubRepository.getFollowing(username)
    }


    fun getRepos(username: String): Single<List<InformationUsers>> {
        return githubRepository.getRepos(username)
    }









}