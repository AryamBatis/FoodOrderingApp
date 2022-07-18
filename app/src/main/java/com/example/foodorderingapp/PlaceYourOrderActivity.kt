package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.adapter.PlaceYourOrdedAdapter
import com.example.foodorderingapp.models.RestuarantModel
import kotlinx.android.synthetic.main.activity_place_your_order.*

class PlaceYourOrderActivity : AppCompatActivity() {

    var placeYourOrdedAdapter: PlaceYourOrdedAdapter? = null
    var isDeliveryOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_your_order)

        val restuarantModel:RestuarantModel? = intent.getParcelableExtra("RestuarantModel")
        val actionbar: ActionBar? = supportActionBar
        actionbar?.setTitle(restuarantModel?.name)
        actionbar?.setSubtitle(restuarantModel?.address)
        actionbar?.setDisplayHomeAsUpEnabled(true)

        buttonPlaceYourOrder.setOnClickListener {
            onPlaceOrdedButtonClick(restuarantModel)
        }

        switchDelivery?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                inputAddress.visibility = View.VISIBLE
                inputCity.visibility = View.VISIBLE
                inputState.visibility = View.VISIBLE
                inputZip.visibility = View.VISIBLE
                tvDeliveryCharge.visibility = View.VISIBLE
                tvDeliveryChargeAmount.visibility = View.VISIBLE
                isDeliveryOn = true
                calculateTotalAmount(restuarantModel)
            }else{
                inputAddress.visibility = View.GONE
                inputCity.visibility = View.GONE
                inputState.visibility = View.GONE
                inputZip.visibility = View.GONE
                tvDeliveryCharge.visibility = View.GONE
                tvDeliveryChargeAmount.visibility = View.GONE
                isDeliveryOn = false
                calculateTotalAmount(restuarantModel)
            }
        }
        initRecyclerView(restuarantModel)
        calculateTotalAmount(restuarantModel)
    }
    private fun initRecyclerView(restuarantModel: RestuarantModel?){
        cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        placeYourOrdedAdapter = PlaceYourOrdedAdapter(restuarantModel?.menus)
        cartItemsRecyclerView.adapter = placeYourOrdedAdapter
    }

    private fun calculateTotalAmount(restuarantModel: RestuarantModel?){
        var subTotalAmount = 0f
        for(menu in restuarantModel?.menus!!){
            subTotalAmount += menu?.price!! * menu?.totalInCart!!
        }
        tvSubtotalAmount.text = "$" + String.format("%.2f", subTotalAmount)
        if(isDeliveryOn){
            tvDeliveryChargeAmount.text = "$" + String.format("%.2f", restuarantModel.delivery_charge?.toFloat())
            subTotalAmount += restuarantModel?.delivery_charge?.toFloat()!!
        }
        tvTotalAmount.text = "$" + String.format("%.2f", subTotalAmount)
    }

    private fun onPlaceOrdedButtonClick(restuarantModel: RestuarantModel?){
        if(TextUtils.isEmpty(inputName.text.toString())){
            inputName.error = "Enter your Name"
            return
        }else if(isDeliveryOn && TextUtils.isEmpty(inputAddress.text.toString())) {
            inputAddress.error = "Enter your Address"
            return
        } else if(isDeliveryOn && TextUtils.isEmpty(inputCity.text.toString())) {
            inputCity.error = "Enter your City Name"
            return
        } else if(isDeliveryOn && TextUtils.isEmpty(inputZip.text.toString())) {
            inputZip.error = "Enter your Zip code"
            return
        } else if(TextUtils.isEmpty(inputCardNumber.text.toString())) {
            inputCardNumber.error = "Enter your credit card name"
            return
        } else if(TextUtils.isEmpty(inputCardExpiry.text.toString())) {
            inputCardExpiry.error = "Enter your credit card expiry"
            return
        } else if(TextUtils.isEmpty(inputCardPin.text.toString())) {
            inputCardPin.error = "Enter your credit card pin/ccv"
            return
        }
        val intent = Intent(this@PlaceYourOrderActivity, SuccessOrderActivity::class.java)
        intent.putExtra("RestuarantModel", restuarantModel)
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1000){
            setResult(RESULT_OK)
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }
}