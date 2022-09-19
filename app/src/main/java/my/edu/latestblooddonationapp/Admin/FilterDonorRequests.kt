package my.edu.latestblooddonationapp.Admin

import android.widget.Filter

class FilterDonorRequests: Filter {

    //arrayList in which we want to search
    private var filterList: ArrayList<ModelDonorRequests>

    //adapter in which filter need to be implemented
    private var adapterDonorRequests: AdapterDonorRequests

    //constructor
    constructor(
        filterList: ArrayList<ModelDonorRequests>,
        adapterDonorRequests: AdapterDonorRequests
    ) : super() {
        this.filterList = filterList
        this.adapterDonorRequests = adapterDonorRequests
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
       var constraint = constraint
        val results = FilterResults()

        //value should not be null and not empty
        if(constraint != null && constraint.isNotEmpty()) {

            //searched value is nor null not empty

            //change to upper case, or lower case to avoid case sensitivity
            constraint = constraint.toString().uppercase()
            val filteredModels: ArrayList<ModelDonorRequests> = ArrayList()
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
        adapterDonorRequests.categoryArrayList = results.values as ArrayList<ModelDonorRequests>

        //notify changes
        adapterDonorRequests.notifyDataSetChanged()
    }


}