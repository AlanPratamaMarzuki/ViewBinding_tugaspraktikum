package com.f55119091.alan_tugaspraktikum.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.f55119091.alan_tugaspraktikum.api.RetrofitClient
import com.f55119091.alan_tugaspraktikum.data.model.User
import com.f55119091.alan_tugaspraktikum.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// atau UserViewModel

class MainViewModel : ViewModel(){
    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(query: String) {
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }
}