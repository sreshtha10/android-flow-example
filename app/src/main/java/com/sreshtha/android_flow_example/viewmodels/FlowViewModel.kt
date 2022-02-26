package com.sreshtha.android_flow_example.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sreshtha.android_flow_example.api.ApiInterface
import com.sreshtha.android_flow_example.api.response.LoginResponse
import com.sreshtha.android_flow_example.model.User
import com.sreshtha.android_flow_example.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlowViewModel @Inject constructor(private val api: ApiInterface) : ViewModel() {


    private val mLoginUserFlow = Channel<Resource<LoginResponse>>(Channel.BUFFERED)

    val loginUserFlow = mLoginUserFlow.receiveAsFlow()

    fun doLoginUserFlow(user:User) {
        viewModelScope.launch {
            flow {
                val result = api.login(user)
                emit(result)
            }.flowOn(Dispatchers.IO)
                .catch {
                    mLoginUserFlow.send(Resource.error(data = null, msg = "Error"))
                }
                .collect {
                    if(it.isSuccessful){
                        mLoginUserFlow.send(Resource.success(it.body()))
                    }
                    else{
                        mLoginUserFlow.send(Resource.error(it.message().toString(),null))
                    }
                }
        }
    }



}