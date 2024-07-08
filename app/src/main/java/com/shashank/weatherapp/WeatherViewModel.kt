package com.shashank.weatherapp

import android.net.Network
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.weatherapp.api.NetworkResponse
import com.shashank.weatherapp.api.RetrofitInstance
import com.shashank.weatherapp.api.WeatherResponseModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
        private val weatherApi = RetrofitInstance.weatherApi
        private val _weatherData = MutableLiveData<NetworkResponse<WeatherResponseModel>>()
        val weatherData: MutableLiveData<NetworkResponse<WeatherResponseModel>> = _weatherData
     fun getData(city : String){
         _weatherData.value = NetworkResponse.Loading
        viewModelScope.launch {
           try {
               val response =  weatherApi.getWeather(Constant.apiKey,city)
               if(response.isSuccessful){
                   response.body()?.let {
                       _weatherData.value = NetworkResponse.Success(it)
                   }
               }else{
                   _weatherData.value = NetworkResponse.Failure("Something went wrong")
               }

           }catch (e : Exception){
               _weatherData.value = NetworkResponse.Failure("Something went wrong")
           }
        }


    }
}