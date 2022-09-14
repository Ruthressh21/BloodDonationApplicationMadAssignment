package my.edu.latestblooddonationapp.Admin


class DonorRequestClass(var donarRequestId:String,
                        var patientName:String,
                        var bloodType:String,
                        var description:String
) {
    constructor() : this(
        "", "", "", ""
    )
}