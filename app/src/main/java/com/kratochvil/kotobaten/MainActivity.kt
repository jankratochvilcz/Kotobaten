package com.kratochvil.kotobaten

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val searchTask = SearchTask()
    /*private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchTask.callback = {x -> displayResults(x)}

        main_search_edit_text.setOnEditorActionListener({ v, actionId, event ->
            val text = v.text

            if(text == null)
                true

            val result = searchTask.execute(v.text.toString())

            true
        })

//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun displayResults(results: List<SearchResult>) {
        val searchResultsAdapter = SearchResultAdapter(this, results)
        search_results_list_view.adapter = searchResultsAdapter
    }
}
