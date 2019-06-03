package com.android.akshayfaye.urbandictionary.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.akshayfaye.urbandictionary.R
import com.android.akshayfaye.urbandictionary.utilities.InjectorUtils
import com.android.akshayfaye.urbandictionary.utilities.setSafeOnClickListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

/**
 * SearchFragment provides search view for searching word definition
 */
class SearchFragment : Fragment() {

    private val TAG : String = "SearchFragment"

    companion object{

        fun newInstance(): SearchFragment{
            return SearchFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val fragmentView = inflater.inflate(R.layout.fragment_search, container, false)

        //SafeOnClickListener for maintaining the interval between two button clicks
        fragmentView.find_definition_button.setSafeOnClickListener{
            val searchString : String = dictionary_search_edit_text.text.toString().trim()

            dictionary_search_edit_text.hideKeyboard();

            if(validateString(searchString)){
                val factory = InjectorUtils.provideAcronymsViewModelFactory(requireContext())
                val viewModel : DictionaryViewModel = ViewModelProviders.of(requireActivity(), factory)
                    .get(DictionaryViewModel::class.java)

                //sets search string for observing it in DefinitionListFragment fragment
                viewModel.setSearchString(searchString)

            }else{
                Snackbar.make(requireView(), getString(R.string.not_valid_word), Snackbar.LENGTH_LONG).show()
            }
        }

        return fragmentView;
    }

    /**
     * Method to validate user entered search string
     * @param searchString
     * @return boolean flag
     */
    private fun validateString(searchString : String) : Boolean{

        if(searchString.isBlank())
            return false

        if(searchString.contains(" "))
            return false

        return true
    }

    /**
     * Method to hide Keyboard on button click
     */
    private fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

}