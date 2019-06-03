package com.android.akshayfaye.urbandictionary.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.akshayfaye.urbandictionary.DefinitionRecyclerAdapter
import com.android.akshayfaye.urbandictionary.R
import com.android.akshayfaye.urbandictionary.data.Definitions
import com.android.akshayfaye.urbandictionary.utilities.InjectorUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_definition_list.view.*
import java.util.*
import kotlin.Comparator

/**
 *  DefinitionListFragment displays list of definitions for searched words
 */
class DefinitionListFragment : Fragment(), AdapterView.OnItemSelectedListener{

    private val TAG : String = "DefinitionListFragment"
    private lateinit var adapter : DefinitionRecyclerAdapter
    private lateinit var fragmentView : View
    lateinit var definitionList: List<Definitions>

    companion object {

        fun newInstance(): DefinitionListFragment {
            return DefinitionListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fragmentView =  inflater.inflate(R.layout.fragment_definition_list, container, false)

        adapter = DefinitionRecyclerAdapter(requireContext())
        fragmentView.dictionary_recycler_view.adapter = adapter
        fragmentView.dictionary_recycler_view.layoutManager = LinearLayoutManager(requireContext())

        initializeSpinner()

        val factory = InjectorUtils.provideAcronymsViewModelFactory(requireContext())
        val viewModel : DictionaryViewModel = ViewModelProviders.of(requireActivity(), factory)
            .get(DictionaryViewModel::class.java)

        //Observing search string share from SearchFragment
        viewModel.searchStringData.observe(this, Observer<String>{

            fragmentView.progress_bar.visibility = View.VISIBLE

            switchVisibility(false)

            val searchString : String = it

            //Observing for definitionList on service call
            viewModel.getDefinition(searchString)
                .observe(this, Observer {

                    if(it != null) {
                        definitionList = it.definitionList
                        updateViews(searchString)
                    }else
                        Snackbar.make(requireView(), getString(R.string.no_data_for_definition),
                            Snackbar.LENGTH_LONG).show()
                })

        })

        return fragmentView
    }

    /**
     * Method to update Views on service response
     */
    private fun updateViews(searchString : String){

        fragmentView.progress_bar.visibility = View.GONE

        if(definitionList.isNotEmpty()){

            switchVisibility(true)
            fragmentView.definition_text_view.text = resources.getString(R.string.definition_for, searchString)
            adapter.setDefinition(definitionList)
            adapter.notifyDataSetChanged()

        }else{
            Snackbar.make(requireView(), getString(R.string.no_data_for_definition), Snackbar.LENGTH_LONG).show()
        }
    }

    /**
     * Method to initialize spinner
     */
    private fun initializeSpinner(){

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.thump_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            fragmentView.thump_sort_spinner.adapter = adapter
            fragmentView.thump_sort_spinner.onItemSelectedListener = newInstance()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if(position != 0) {
            Log.e(TAG, "definitionList "+definitionList)
            sortByThump(position)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    /**
     * Method for sorting the definition list elements according to thumpsUp & thumpsDown
     */
    private fun sortByThump(position: Int){

        Collections.sort(definitionList, object : Comparator<Definitions> {
            override fun compare(def1: Definitions, def2: Definitions): Int {

                if(position != 1)
                    return Integer.valueOf(def1.thumbsUp).compareTo(def2.thumbsUp)

                else if(position != 2)
                    return Integer.valueOf(def2.thumbsUp).compareTo(def1.thumbsUp)

                return Integer.valueOf(def1.thumbsUp).compareTo(def2.thumbsUp)
            }
        })

        adapter.setDefinition(definitionList)
        adapter.notifyDataSetChanged()
    }

    /**
     * Method to switch view's visibility according to condition
     */
    private fun switchVisibility(visibility: Boolean){

        if(visibility){
            fragmentView.definition_text_view.visibility = View.VISIBLE
            fragmentView.sort_by_text_view.visibility = View.VISIBLE
            fragmentView.thump_sort_spinner.visibility = View.VISIBLE
            fragmentView.dictionary_recycler_view.visibility = View.VISIBLE
        }else{
            fragmentView.definition_text_view.visibility = View.GONE
            fragmentView.sort_by_text_view.visibility = View.GONE
            fragmentView.thump_sort_spinner.visibility = View.GONE
            fragmentView.dictionary_recycler_view.visibility = View.GONE
        }

    }

}