package com.pascal.androidphpmysql

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputName:EditText=findViewById(R.id.inputName)
        val inputEmail:EditText=findViewById(R.id.inputEmail)
        val inputPhone:EditText=findViewById(R.id.inputPhone)
        val inputAddress:EditText=findViewById(R.id.inputAddress)
        val inputCity:EditText=findViewById(R.id.inputCity)
        val inputCountry:EditText=findViewById(R.id.inputCountry)

        val buttonSave:Button=findViewById(R.id.buttonSave)
        val buttonFetch:Button=findViewById(R.id.buttonFetch)

        val queue= Volley.newRequestQueue(this)
        val url= "https://android.emobilis.ac.ke/insert.php"

        buttonSave.setOnClickListener {

            val name= inputName.text.toString().trim()
            val email= inputEmail.text.toString().trim()
            val phone= inputPhone.text.toString().trim()
            val address= inputAddress.text.toString().trim()
            val country= inputCountry.text.toString().trim()
            val city= inputCity.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty() && country.isNotEmpty() && city.isNotEmpty()){
                //save
                val progressDialog= ProgressDialog(this)
                progressDialog.setTitle("Saving...")
                progressDialog.setMessage("Processing")
                progressDialog.show()
                val request= object : StringRequest(Method.POST,url,{
                    //TODO use a bottomsheet dialog
                    progressDialog.dismiss()
                    Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
                    inputName.text.clear()
                    inputEmail.text.clear()
                    inputPhone.text.clear()
                    inputAddress.text.clear()
                },
                    {
                        progressDialog.dismiss()
                        Log.e("SAVING","onCreate: ",it)
                        Toast.makeText(this, "Error occurred while saving user", Toast.LENGTH_SHORT).show()
                    }) {
                    override fun getParams(): MutableMap<String, String>? {
                        val map= HashMap<String, String>()
                        map["name"]=name
                        map["email"]=email
                        map["phone"]=phone
                        map["address"]=address
                        map["country"]=country
                        map["city"]=city

                        return map
                    }
                }
                queue.add(request)
            }else
            {
                Toast.makeText(this, "Fill in all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        buttonFetch.setOnClickListener {
            val intent= Intent(this,UsersActivity::class.java)
            startActivity(intent)
        }






/*        val url= "https://android.emobilis.ac.ke/fetch.php"

        val request= JsonObjectRequest(Request.Method.GET, url, null,
            { responseJson ->
               Log.d("FETCHING","OnCreate:$responseJson")
                val jsonArray= responseJson.getJSONArray("users")
                for (i in 0 until jsonArray.length())
                {
                    val userJsonObject= jsonArray.getJSONObject(i)
                    val name= userJsonObject.get("name")
                    val phone= userJsonObject.get("phone")
                    Log.d("USER","OnCreate: $name:$phone")
                }

        },
            {error ->
                Log.e("FETCHING","OnCreate:Error while fetching",error)

            })

        queue.add(request)*/
       /* val weatherUrl="http://api.weatherapi.com/v1/current.json?key=f269d6ac5ca5477896375924220208&q=London"
        val weatherRequest= JsonObjectRequest(Request.Method.GET, weatherUrl, null,
            {
            mainJsonObject ->
                val locationObject= mainJsonObject.getJSONObject("location")
                val city= locationObject.get("name")
                val country= locationObject.get("country")
                Log.d("WEATHER","OnCreate:$city in $country")
                //parse json
                val currentObject= mainJsonObject.getJSONObject("current")
                val temp= currentObject.get("temp_c")
                val windSpeed= currentObject.get("wind_kph")
                val visibility= currentObject.get("vis_km")
                Log.d("WEATHER","OnCreate:Temperature is $temp C, Wind Speed is $windSpeed/kmh, Visibility is is $visibility km")

                val condition=mainJsonObject.getJSONObject("Current").getJSONObject("condition").get("text")
                Log.d("WEATHER","OnCreate:Condition is $condition")

            },
            {
                error ->
                Log.e("WEATHER","OnCreate:Error while fetching weather data",error)
            })
        queue.add(weatherRequest)*/


    }
}