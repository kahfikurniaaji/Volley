package com.example.volley

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private var requestQueue: RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "LibraryVolleyApp"
        textView = findViewById(R.id.textViewResult)
        val button: Button = findViewById(R.id.btnParse)
        requestQueue = Volley.newRequestQueue(this)
        button.setOnClickListener {
            jsonParse()
        }
    }
    private fun jsonParse() {
        val url = "https://randomuser.me/api/"
        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
            response ->try {
            val jsonArray = response.getJSONArray("results")
            for (i in 0 until jsonArray.length()) {
                val results = jsonArray.getJSONObject(i)
                val firstName = results.getJSONObject("name").getString("first")
                val lastName = results.getJSONObject("name").getString("last")
                val gender = results.getString("gender")
                val age = results.getJSONObject("dob").getString("age")
                val phone = results.getString("phone")
                val email = results.getString("email")
//                textView.append("$name\n$email\n\n")
                textView.setText("Name: $firstName $lastName\nGender: $gender\nAge: $age\nPhone: $phone\nEmail: $email")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }
}