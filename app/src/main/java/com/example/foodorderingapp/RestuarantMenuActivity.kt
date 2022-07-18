package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodorderingapp.adapter.MenuListadapter
import com.example.foodorderingapp.models.Menus
import com.example.foodorderingapp.models.RestuarantModel
import kotlinx.android.synthetic.main.activity_restuarant_menu.*

class RestuarantMenuActivity : AppCompatActivity(), MenuListadapter.MenuListClickListener {

    private var itemInTheCartList: MutableList<Menus?>? = null
    private var totalItemInCartCount = 0
    private var menuList: List<Menus?>? = null
    private var menuListAdapter: MenuListadapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restuarant_menu)

        val restuarantModel = intent?.getParcelableExtra<RestuarantModel>("RestuarantModel")

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(restuarantModel?.name)
        actionBar?.setSubtitle(restuarantModel?.address)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        menuList = restuarantModel?.menus

        initRecyclerView(menuList)
        checkoutButton.setOnClickListener {
            if (itemInTheCartList != null && itemInTheCartList!!.size<= 0){
                Toast.makeText(this@RestuarantMenuActivity,"Please add some items in the cart",Toast.LENGTH_LONG).show()
            }else{
                restuarantModel?.menus = itemInTheCartList
                val intent = Intent(this@RestuarantMenuActivity, PlaceYourOrderActivity::class.java)
                intent.putExtra("RestuarantModel", restuarantModel)
                startActivityForResult(intent,1000)
            }
        }
    }
    private fun initRecyclerView(menus: List<Menus?>?){

        menuRecyclerView.layoutManager = GridLayoutManager(this,2)
        menuListAdapter = MenuListadapter(menus,this)
        menuRecyclerView.adapter = menuListAdapter
    }

    override fun addToCartClickListener(menu: Menus) {
        if(itemInTheCartList == null){
            itemInTheCartList = ArrayList()
        }
        itemInTheCartList?.add(menu)
        totalItemInCartCount = 0
        for(menu in itemInTheCartList!!){
            totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
        }
       checkoutButton.text = "Checkout (" + totalItemInCartCount + ") Items"

    }

    override fun updateCartClickListener(menu: Menus) {
        val index = itemInTheCartList!!.indexOf(menu)
        itemInTheCartList?.removeAt(index)
        itemInTheCartList?.add(menu)
        totalItemInCartCount = 0
        for(menu in itemInTheCartList!!){
            totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
        }
        checkoutButton.text = "Checkout (" + totalItemInCartCount + ") Items"
    }

    override fun removeFromCartClickListener(menu: Menus) {
       if(itemInTheCartList!!.contains(menu)){
           itemInTheCartList?.remove(menu)
           totalItemInCartCount = 0
           for(menu in itemInTheCartList!!){
               totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
           }
           checkoutButton.text = "Checkout (" + totalItemInCartCount + ") Items"
       }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1000 && resultCode == RESULT_OK){
            finish()
        }
    }

}