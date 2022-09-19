package my.edu.latestblooddonationapp.Admin

class ModelDonorRequests {

    //variables, must match as in firebase

    var id:String = ""
    var patientName:String = ""
    var bloodType:String = ""
    var description:String = ""
    var timestamp:String = ""
    var uid:String = ""

    //empty constructor, required by firebase
    constructor()

    //parameterized constructor
    constructor(
        id: String,
        patientName: String,
        bloodType: String,
        description: String,
        timestamp: String,
        uid: String
    ) {
        this.id = id
        this.patientName = patientName
        this.bloodType = bloodType
        this.description = description
        this.timestamp = timestamp
        this.uid = uid
    }
}

