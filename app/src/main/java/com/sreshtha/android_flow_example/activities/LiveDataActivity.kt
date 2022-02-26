package com.sreshtha.android_flow_example.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sreshtha.android_flow_example.databinding.ActivityLiveDataBinding
import com.sreshtha.android_flow_example.model.User
import com.sreshtha.android_flow_example.viewmodels.LiveDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LiveDataActivity : AppCompatActivity() {

    private lateinit var liveDataBinding:ActivityLiveDataBinding
    private val _viewModel : LiveDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        liveDataBinding = ActivityLiveDataBinding.inflate(layoutInflater)
        setContentView(liveDataBinding.root)

        liveDataBinding.apply {
            btnLogin.setOnClickListener {
                val email = editText.text.toString()
                val password = editText2.text.toString()
                if(email.isNullOrEmpty() || password.isNullOrEmpty()){
                    return@setOnClickListener
                }
                _viewModel.doLogin(User(email = email, password = password))
            }


            // set up obs

            _viewModel.login.observe(this@LiveDataActivity){
               if(it.data == null){
                   Log.d("Error",it.message.toString())
               }
                else{
                    Log.d("Success",it.data.toString())
               }
            }
        }
    }
}