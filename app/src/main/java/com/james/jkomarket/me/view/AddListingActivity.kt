package com.james.jkomarket.me.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.james.jkomarket.R
import com.james.jkomarket.me.viewmodel.AddListingViewModel

class AddListingActivity : AppCompatActivity() {

    private lateinit var viewModel: AddListingViewModel
    private var categorySelected: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_listing)

        val title: EditText = findViewById(R.id.listingTitle)
        val description: EditText = findViewById(R.id.listingDescription)
        val price: EditText = findViewById(R.id.listingPrice)
        val category: Spinner = findViewById(R.id.listingCategory)
        val confirm: Button = findViewById(R.id.confirm)

        viewModel = ViewModelProvider(this).get(AddListingViewModel::class.java)
        var adapter = ArrayAdapter<Any>(this@AddListingActivity, android.R.layout.simple_spinner_item)
        viewModel.allCategory.observe(this, Observer {
            it.forEach{
                adapter.add(it.name)
            }
        })
        category.adapter = adapter
        category.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long){
                categorySelected = pos + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        confirm.setOnClickListener {
            val _title = title.text.toString()
            val _description = description.text.toString()
            val _price = price.text.toString()
            val _category = categorySelected

            if (TextUtils.isEmpty(_title)) {
                showWarning("title can not be null")
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(_description)) {
                showWarning("description can not be null")
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(_price)) {
                showWarning("price can not be null")
                return@setOnClickListener
            }


            val replayIntent = Intent()
            replayIntent.putExtra(EXTRA_TITLE, _title)
            replayIntent.putExtra(EXTRA_DESCRIPTION, _description)
            replayIntent.putExtra(EXTRA_PRICE, _price.toDouble())
            replayIntent.putExtra(EXTRA_CATEGORY, _category)


            setResult(Activity.RESULT_OK, replayIntent)
            finish()
        }
    }

    private fun showWarning(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_DESCRIPTION = "description"
        const val EXTRA_PRICE = "price"
        const val EXTRA_CATEGORY = "category"
    }
}
