package com.android.akshayfaye.urbandictionary.ui

import android.os.Bundle
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
import com.android.akshayfaye.urbandictionary.DictionaryViewModel
import com.android.akshayfaye.urbandictionary.DictionaryViewModelFactory
import com.android.akshayfaye.urbandictionary.R
import com.android.akshayfaye.urbandictionary.data.Definitions
import com.android.akshayfaye.urbandictionary.utilities.InjectorUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_definition_list.view.*
import javax.inject.Inject

/**
 *  DefinitionListFragment displays list of definitions for searched words
 */
class DefinitionListFragment : Fragment(), AdapterView.OnItemSelectedListener{

    private val TAG : String = "DefinitionListFragment"
    lateinit var mListadapter : DefinitionRecyclerAdapter
    private lateinit var fragmentView : View
    var definitionList: MutableList<Definitions> = mutableListOf()

    companion object {

        fun newInstance(): DefinitionListFragment {
            return DefinitionListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fragmentView =  inflater.inflate(R.layout.fragment_definition_list, container, false)
        initializeSpinner()

        mListadapter = DefinitionRecyclerAdapter(requireContext())
        fragmentView.dictionary_recycler_view.adapter = mListadapter
        fragmentView.dictionary_recycler_view.layoutManager = LinearLayoutManager(requireContext())

        setRecyclerViewHeight();

        val factory = InjectorUtils.provideAcronymsViewModelFactory(requireContext())
        val viewModel : DictionaryViewModel = ViewModelProviders.of(requireActivity(), factory)
            .get(DictionaryViewModel::class.java)

        //fragmentView.definition_text_view.text = resources.getString(R.string.definition_for, it)

        //Observing for definitionList on service call
        viewModel.mData.observe(this, Observer {

            switchVisibility(false)

            if(it != null) {
                definitionList = it.definitionList
                updateViews()
            }else {
                Snackbar.make(requireView(), getString(R.string.no_data_for_definition),
                        Snackbar.LENGTH_LONG).show()
                requireActivity().progress_bar.visibility = View.GONE
            }
        })

        return fragmentView
    }

    /**
     * Method to update Views on service response
     */
    private fun updateViews(){
        requireActivity().progress_bar.visibility = View.GONE
        if(definitionList.isNotEmpty()){

            switchVisibility(true)
            mListadapter.setDefinition(definitionList)

        }else{
            Snackbar.make(requireView(), getString(R.string.no_data_for_definition),
                Snackbar.LENGTH_LONG).show()
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
            fragmentView.thump_sort_spinner.onItemSelectedListener = this
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(position != 0) {
            mListadapter.sortByThump(position)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    /**
     * Method to switch view's visibility according to condition
     */
    private fun switchVisibility(visibility: Boolean){

        if(visibility){
            fragmentView.sort_by_text_view.visibility = View.VISIBLE
            fragmentView.thump_sort_spinner.visibility = View.VISIBLE
            fragmentView.dictionary_recycler_view.visibility = View.VISIBLE
        }else{
            fragmentView.sort_by_text_view.visibility = View.GONE
            fragmentView.thump_sort_spinner.visibility = View.GONE
            fragmentView.dictionary_recycler_view.visibility = View.GONE
        }
    }

    /**
     * Set dynamic height for RecycleView
     */
    private fun setRecyclerViewHeight(){
        val params = fragmentView.dictionary_recycler_view.layoutParams
        params.height = 1600
        fragmentView.dictionary_recycler_view.layoutParams = params
    }

}