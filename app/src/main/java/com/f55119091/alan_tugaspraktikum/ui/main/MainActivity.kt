package com.f55119091.alan_tugaspraktikum.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.f55119091.alan_tugaspraktikum.data.model.User
import com.f55119091.alan_tugaspraktikum.databinding.ActivityMainBinding
import com.f55119091.alan_tugaspraktikum.ui.detail.DetailActivity

private lateinit var binding : ActivityMainBinding
private lateinit var viewModel: MainViewModel
private lateinit var adapter: UserAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun OnItemClicked(data: User) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }

        })

        viewModel = ViewModelProvider( this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        binding.apply {
            userResult.layoutManager = LinearLayoutManager(this@MainActivity)
            userResult.setHasFixedSize(true)
            userResult.adapter = adapter

            buttonSearch.setOnClickListener {
                searchUser()
            }

            inputUsername.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
        viewModel.getSearchUsers().observe(this, {
            if (it!=null) {
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    private fun searchUser() {
        binding.apply { this@MainActivity
            val query = inputUsername.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.loading.visibility = View.VISIBLE
        }
        else {
            binding.loading.visibility = View.GONE
        }
    }

}