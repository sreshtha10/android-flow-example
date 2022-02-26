package com.sreshtha.android_flow_example.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sreshtha.android_flow_example.databinding.ActivityFlowBinding
import com.sreshtha.android_flow_example.model.User
import com.sreshtha.android_flow_example.viewmodels.FlowViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FlowActivity : AppCompatActivity() {

    private lateinit var flowBinding: ActivityFlowBinding
    private val mViewModel: FlowViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flowBinding = ActivityFlowBinding.inflate(layoutInflater)
        setContentView(flowBinding.root)

        flowBinding.apply {

            btnLogin.setOnClickListener {
                val email = editText.text.toString()
                val password = editText2.text.toString()
                if(email.isNullOrEmpty() || password.isNullOrEmpty()){
                    return@setOnClickListener
                }
                mViewModel.doLoginUserFlow(User(email,password))
            }




        }

       lifecycleScope.launch{
           lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
               launch {
                   mViewModel.loginUserFlow.collect {
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


    }



}