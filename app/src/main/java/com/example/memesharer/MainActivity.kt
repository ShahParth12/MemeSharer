package com.example.memesharer

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var memeUrl : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }

    private fun loadmeme(){
        progressBar.visibility= View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://dog.ceo/api/breeds/image/random"
        //https://meme-api.herokuapp.com/gimme
        //https://dog.ceo/api/breeds/image/random
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null ,
            Response.Listener { response ->
                memeUrl=response.getString("message") //url
                Glide.with(this).load(memeUrl).listener( object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }


                }).into(memeImage)
            },
            Response.ErrorListener {

            })
        queue.add(jsonObjectRequest)
    }

    fun sharememe(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT , "Checkout this funny meme I got through MemeSharer $memeUrl ")
        val chooser = Intent.createChooser(intent , " Choose the app where you want to share the meme")
        startActivity(chooser)
    }
    fun nextmeme(view: View) {
        loadmeme()

    }
}