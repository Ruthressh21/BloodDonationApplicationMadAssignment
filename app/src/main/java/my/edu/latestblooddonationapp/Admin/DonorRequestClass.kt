package my.edu.latestblooddonationapp.Admin


class DonorRequestClass(var donorRequestId:String,
                        var patientName:String,
                        var bloodType:String,
                        var description:String
) {
    constructor() : this(
        "", "", "",""
    )
}