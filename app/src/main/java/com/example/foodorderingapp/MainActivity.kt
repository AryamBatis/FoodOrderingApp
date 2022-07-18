package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.adapter.RestuarantListAdapter
import com.example.foodorderingapp.models.RestuarantModel
import com.google.gson.Gson
import java.io.*

class MainActivity : AppCompatActivity(), RestuarantListAdapter.RestuarantListClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle("Restaurant List")

        val restuarantModel = getRestuarantData()
        initRecyclerView(restuarantModel)
    }

    private fun initRecyclerView(restuarantList: List<RestuarantModel?>?){
        val recyclerViewRestuarant = findViewById<RecyclerView>(R.id.rvRestuarant)
        recyclerViewRestuarant.layoutManager = LinearLayoutManager(this)
        val adapter = RestuarantListAdapter(restuarantList, this)
        recyclerViewRestuarant.adapter = adapter
    }

    private fun getRestuarantData(): List<RestuarantModel?>? {
        val inputStream: InputStream = resources.openRawResource(R.raw.retuarant)
        val writer:Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader:Reader = BufferedReader(InputStreamReader(inputStream,"UTF-8"))
            var n :Int
            while (reader.read(buffer).also { n = it } !=-1){
                writer.write(buffer,0, n)

            }
        }catch(e:Exception){}
        val jsonStr: String = writer.toString()
        val gson = Gson()
        val restuarantModel = gson.fromJson<Array<RestuarantModel>>(jsonStr, Array<RestuarantModel>::class.java).toList()

        return restuarantModel
    }

    override fun onItemClick(restuarantModel: RestuarantModel) {
        val intent = Intent(this@MainActivity, RestuarantMenuActivity::class.java)
        intent.putExtra("RestuarantModel", restuarantModel)
        startActivity(intent)
    }
}