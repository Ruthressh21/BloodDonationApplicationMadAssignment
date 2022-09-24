package my.edu.latestblooddonationapp.User

import android.widget.Filter

class FilterDonateBlood: Filter {

    //arrayList in which we want to search
    private var filterList: ArrayList<ModelDonateBlood>

    //adapter in which filter need to be implemented
    private var adapterDonateBlood: AdapterDonateBlood

    //constructor
    constructor(
        filterList: ArrayList<ModelDonateBlood>,
        adapterDonateBlood: AdapterDonateBlood
    ) : super() {
        this.filterList = filterList
        this.adapterDonateBlood = adapterDonateBlood
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val results = FilterResults()

        //value should not be null and not empty
        if(constraint != null && constraint.isNotEmpty()) {

            //searched value is nor null not empty

            //change to upper case, or lower case to avoid case sensitivity
            constraint = constraint.toString().uppercase()
            val filteredModels: ArrayList<ModelDonateBlood> = ArrayList()
            for (i in 0 until filterList.size) {
                //validate
                if (filterList[i].bloodType.uppercase().contains(constraint)) {
                    //add to filtered list
                    filteredModels.add(filterList[i])
                }
            }
            results.count = filteredModels.size
            results.values = filteredModels
        }
        else{
            //search value is either null or empty
            results.count = filterList.size
            results.values = filterList
        }
        return results //don't miss it
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults) {
        //apply filter changes
        adapterDonateBlood.categoryArrayList = results.values as ArrayList<ModelDonateBlood>

        //notify changes
        adapterDonateBlood.notifyDataSetChanged()
    }


}