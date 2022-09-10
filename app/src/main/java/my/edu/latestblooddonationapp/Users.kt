package my.edu.latestblooddonationapp

class Users(var id:String,
            var name:String,
            var email:String,
            var passwd:String,
            var dateOfBirth:String,
            var bloodGroup:String,
            var gender:String,
            var address:String,
            var phoneNumber:String
) {
    constructor() : this(
        "", "", "", "", "", "", "", "",""
    )
}