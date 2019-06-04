package com.android.akshayfaye.urbandictionary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.akshayfaye.urbandictionary.data.Definitions
import kotlinx.android.synthetic.main.definition_details.view.*
import java.util.*
import kotlin.Comparator

/**
 * DefinitionRecyclerAdapter provide a binding from an app-specific data set to views
 * @param context
 */
class DefinitionRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<DefinitionRecyclerAdapter.ViewHolder>(){

    private var definitionList : List<Definitions> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(
            R.layout.definition_details,
            parent,
            false)

        return ViewHolder(view);
    }

    /**
     * sets definition list
     */
    fun setDefinition(definitionList: List<Definitions>) {
        this.definitionList = definitionList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return definitionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(definitionList.get(position));
    }

    /**
     * Method for sorting the definition list elements according to thumpsUp & thumpsDown
     */
     fun sortByThump(position: Int){

        Collections.sort(definitionList, object : Comparator<Definitions> {
            override fun compare(def1: Definitions, def2: Definitions): Int {

                if(position != 1)
                    return Integer.valueOf(def1.thumbsUp).compareTo(def2.thumbsUp)
                else if(position != 2)
                    return Integer.valueOf(def2.thumbsUp).compareTo(def1.thumbsUp)

                return Integer.valueOf(def1.thumbsUp).compareTo(def2.thumbsUp)
            }
        })
        notifyDataSetChanged()
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val definitionTextView = view.definition_text_view
        val thumpsUpTextView = view.thumps_up_text_view
        val thumpsDownTextView = view.thumps_down_text_view

        fun setData(definition : Definitions){
            definitionTextView?.text = definition.definition
            thumpsUpTextView?.text = definition.thumbsUp.toString()
            thumpsDownTextView?.text = definition.thumbsDown.toString()
        }
    }
}