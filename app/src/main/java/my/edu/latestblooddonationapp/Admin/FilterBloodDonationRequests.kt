package my.edu.latestblooddonationapp.Admin

import android.widget.Filter

class FilterBloodDonationRequests: Filter {

    //arrayList in which we want to search
    private var filterList: ArrayList<ModelBloodDonationRequests>

    //adapter in which filter need to be implemented
    private var adapterBloodDonationRequests: AdapterBloodDonationRequests

    //constructor
    constructor(
        filterList: ArrayList<ModelBloodDonationRequests>,
        adapterBloodDonationRequests: AdapterBloodDonationRequests
    ) : super() {
        this.filterList = filterList
        this.adapterBloodDonationRequests = adapterBloodDonationRequests
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
       var constraint = constraint
        val results = FilterResults()

        //value should not be null and not empty
        if(constraint != null && constraint.isNotEmpty()) {

            //searched value is nor null not empty

            //change to upper case, or lower case to avoid case sensitivity
            constraint = constraint.toString().uppercase()
            val filteredModels: ArrayList<ModelBloodDonationRequests> = ArrayList()
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
        adapterBloodDonationRequests.categoryArrayList = results.values as ArrayList<ModelBloodDonationRequests>

        //notify changes
        adapterBloodDonationRequests.notifyDataSetChanged()
    }


}