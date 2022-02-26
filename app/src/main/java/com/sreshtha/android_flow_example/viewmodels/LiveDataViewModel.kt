package com.sreshtha.android_flow_example.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sreshtha.android_flow_example.api.ApiInterface
import com.sreshtha.android_flow_example.api.response.LoginResponse
import com.sreshtha.android_flow_example.model.User
import com.sreshtha.android_flow_example.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveDataViewModel @Inject constructor(private val api:ApiInterface): ViewModel(){

    //cached
    private val _login = MutableLiveData<Resource<LoginResponse>>()

    //public
    val login : LiveData<Resource<LoginResponse>> get() =  _login


    //do login
    fun doLogin(user: User) =
        viewModelScope.launch {
            try {
                val res =api.login(user)
                if(res.isSuccessful){
                    _login.value = Resource.success(res.body())
                }
                else{
                    _login.value = Resource.error("Error",null)
                }
            }
            catch (exception: Exception){
               _login.value = Resource.error("API call failed!",null)
            }
        }

}