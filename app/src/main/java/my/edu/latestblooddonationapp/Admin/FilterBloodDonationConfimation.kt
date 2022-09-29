package my.edu.latestblooddonationapp.Admin

import android.widget.Filter

class FilterBloodDonationConfirmation: Filter {

    //arrayList in which we want to search
    private var filterList: ArrayList<ModelBloodDonationConfirmation>

    //adapter in which filter need to be implemented
    private var adapterBloodDonationConfirmation: AdapterBloodDonationConfirmation

    //constructor
    constructor(
        filterList: ArrayList<ModelBloodDonationConfirmation>,
        adapterBloodDonationConfirmation: AdapterBloodDonationConfirmation
    ) : super() {
        this.filterList = filterList
        this.adapterBloodDonationConfirmation = adapterBloodDonationConfirmation
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val results = FilterResults()

        //value should not be null and not empty
        if(constraint != null && constraint.isNotEmpty()) {

            //searched value is nor null not empty

            //change to upper case, or lower case to avoid case sensitivity
            constraint = constraint.toString().uppercase()
            val filteredModels: ArrayList<ModelBloodDonationConfirmation> = ArrayList()
            for (i in 0 until filterList.size) {
                //validate
                if (filterList[i].bloodDonationRequestReferenceID.uppercase().contains(constraint) || filterList[i].bloodDonationRequestName.uppercase().contains(constraint) || filterList[i].bloodDonationRequestBloodType.uppercase().contains(constraint) || filterList[i].bloodDonationRequestDescription.uppercase().contains(constraint) || filterList[i].uid.uppercase().contains(constraint)|| filterList[i].name.uppercase().contains(constraint)|| filterList[i].gender.uppercase().contains(constraint)|| filterList[i].bloodType.uppercase().contains(constraint)|| filterList[i].phoneNum.uppercase().contains(constraint)) {
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
        adapterBloodDonationConfirmation.categoryArrayList = results.values as ArrayList<ModelBloodDonationConfirmation>

        //notify changes
        adapterBloodDonationConfirmation.notifyDataSetChanged()
    }
}