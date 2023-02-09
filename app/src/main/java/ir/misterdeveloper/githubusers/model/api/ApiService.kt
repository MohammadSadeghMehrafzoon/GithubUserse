package ir.misterdeveloper.githubusers.model.api

import io.reactivex.Single
import ir.misterdeveloper.githubusers.model.Response.InformationUsers
import ir.misterdeveloper.githubusers.model.Response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    //Searching GitHub users
    @GET("search/users")
    fun getUsersBySearch(@Query("q") query: String): Single<UserResponse>


    //Getting a list of the user's followers
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Single<List<InformationUsers>>

    //Getting a list of the user's following
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Single<List<InformationUsers>>

    //Getting a list of the user's repos
    @GET("users/{username}/repos")
    fun getRepos(@Path("username") username: String): Single<List<InformationUsers>>


}