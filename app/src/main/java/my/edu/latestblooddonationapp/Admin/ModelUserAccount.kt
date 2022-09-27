package my.edu.latestblooddonationapp.Admin

class ModelUserAccount{

    //variables, must match as in firebase

    var address:String = ""
    var bloodType:String = ""
    var dateBirth:String = ""
    var email:String = ""
    var gender:String = ""
    var name:String = ""
    var password:String = ""
    var phoneNum:String = ""
    var timestamp:String = ""
    var uid:String = ""
    var userType:String = ""

    //empty constructor, required by firebase
    constructor()

    //parameterized constructor
    constructor(
        address: String,
        bloodType: String,
        dateBirth: String,
        email: String,
        gender: String,
        name: String,
        password: String,
        phoneNum: String,
        timestamp: String,
        uid: String,
        userType: String,
    ) {
        this.address = address
        this.bloodType = bloodType
        this.dateBirth = dateBirth
        this.email = email
        this.gender = gender
        this.name = name
        this.password = password
        this.phoneNum = phoneNum
        this.timestamp = timestamp
        this.uid = uid
        this.userType = userType
    }
}

