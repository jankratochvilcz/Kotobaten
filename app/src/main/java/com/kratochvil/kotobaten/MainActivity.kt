package com.kratochvil.kotobaten

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var isLoading = false
    private var hasContent = false
    private var results: List<SearchResult> = listOf()

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

        main_search_edit_text.setOnEditorActionListener({ v, actionId, event ->

            isLoading = true
            setVisibility()

            val text = v.text

            if(text == null)
                true

            val searchTask = SearchTask()
            searchTask.callback = {x -> displayResults(x)}
            val result = searchTask.execute(v.text.toString())

            val view = this.currentFocus;
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
            }

            true
        })

        main_search_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                hasContent = s?.any() ?: false
                setVisibility()
            }
        })

        main_clear_search_button.setOnClickListener(View.OnClickListener {
            main_search_edit_text.text = null
            results = listOf()
            setVisibility()
        })

//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun displayResults(results: List<SearchResult>) {
        this.results = results
        val searchResultsAdapter = SearchResultAdapter(this, results)
        search_results_list_view.adapter = searchResultsAdapter

        isLoading = false
        setVisibility()
    }

    private fun setVisibility() {
        main_loading_results_progress_bar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        main_clear_search_button.visibility = if (!isLoading && hasContent) View.VISIBLE else View.INVISIBLE

        search_results_list_view.visibility = if(results.any()) View.VISIBLE else View.INVISIBLE
        main_no_results_icon.visibility = if(!results.any()) View.VISIBLE else View.INVISIBLE
    }
}
