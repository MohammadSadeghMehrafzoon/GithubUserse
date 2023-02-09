package ir.misterdeveloper.githubusers.model.Repository

import io.reactivex.Single
import ir.misterdeveloper.githubusers.model.Response.InformationUsers
import ir.misterdeveloper.githubusers.model.Response.UserResponse
import ir.misterdeveloper.githubusers.model.api.ApiService

class GithubRepository(private var apiService: ApiService) {


    fun getUsersBySearch(query: String): Single<UserResponse> {
        return apiService.getUsersBySearch(query)
    }

    fun getFollowers(username: String): Single<List<InformationUsers>> {
        return apiService.getFollowers(username)
    }

    fun getFollowing(username: String): Single<List<InformationUsers>> {
        return apiService.getFollowing(username)
    }


    fun getRepos(username: String): Single<List<InformationUsers>> {
        return apiService.getRepos(username)
    }











}