package com.example.foodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.foodorderingapp.models.RestuarantModel
import kotlinx.android.synthetic.main.activity_success_order.*

class SuccessOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_order)

        val restuarantModel: RestuarantModel? = intent.getParcelableExtra("RestuarantModel")
        val actionbar: ActionBar? = supportActionBar
        actionbar?.setTitle(restuarantModel?.name)
        actionbar?.setSubtitle(restuarantModel?.address)
        actionbar?.setDisplayHomeAsUpEnabled(false)

        buttonDone.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }
}