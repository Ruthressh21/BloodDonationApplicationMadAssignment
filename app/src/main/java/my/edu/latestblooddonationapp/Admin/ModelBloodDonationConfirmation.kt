package my.edu.latestblooddonationapp.Admin

class ModelBloodDonationConfirmation {

    //variables, must match as in firebase

    var bloodDonationRequestReferenceID:String = ""
    var bloodDonationRequestName:String = ""
    var bloodDonationRequestBloodType:String = ""
    var bloodDonationRequestDescription:String = ""
    var uid:String = ""
    var name:String = ""
    var gender:String = ""
    var bloodType:String = ""
    var phoneNum:String = ""
    var id:String = ""


    //empty constructor, required by firebase
    constructor()

    //parameterized constructor
    constructor(
        bloodDonationRequestReferenceID: String,
        bloodDonationRequestName: String,
        bloodDonationRequestBloodType: String,
        bloodDonationRequestDescription: String,
        uid: String,
        name: String,
        gender: String,
        bloodType: String,
        phoneNum: String,
        id:String

    ) {
        this.bloodDonationRequestReferenceID =  bloodDonationRequestReferenceID
        this.bloodDonationRequestName = bloodDonationRequestName
        this.bloodDonationRequestBloodType = bloodDonationRequestBloodType
        this.bloodDonationRequestDescription = bloodDonationRequestDescription
        this.uid = uid
        this.name =  name
        this.gender = gender
        this.bloodType = bloodType
        this.phoneNum = phoneNum
        this.id = id
    }
}