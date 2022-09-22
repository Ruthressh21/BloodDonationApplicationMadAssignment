//package my.edu.latestblooddonationapp.User

////import androidx.appcompat.app.AppCompatActivity
////import android.os.Bundle
//import android.widget.Button
//import android.widget.TextView
//import android.widget.Toast
//import com.google.firebase.database.*
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
//import my.edu.latestblooddonationapp.R


//class DonorVerifyRequest : AppCompatActivity() {
    //override fun onCreate(savedInstanceState: Bundle?) {
       // super.onCreate(savedInstanceState)


//        val buttonAccept: Button = findViewById(R.id.btnAccept)
//        val buttonReject: Button = findViewById(R.id.btnReject)
//
//        val patientName: TextView = findViewById(R.id.outputPatientName)
//        val bloodType: TextView = findViewById(R.id.outputBloodType)
//        val description: TextView = findViewById(R.id.outputDescription)
//
//
//            val databaseuser =
//                FirebaseDatabase.getInstance("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                    .getReference("DonorRequests")
//
//
//
//            databaseuser.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    for (i in snapshot.children) {
//                        val nameFb = i.child("patientName").getValue().toString()
//                        val bloodFb = i.child("bloodType").getValue().toString()
//                        val descriptionFb = i.child("description").getValue().toString()
//
//
//                        patientName.setText(nameFb)
//                        bloodType.setText(bloodFb)
//                        description.setText(descriptionFb)
//
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//
//                }
//
//            })
//        }
        package my.edu.test

        import androidx.appcompat.app.AppCompatActivity
                import android.os.Bundle
                import android.widget.Toast
                import com.google.firebase.database.DatabaseReference
                import com.google.firebase.database.FirebaseDatabase
                import my.edu.test.databinding.ActivityReadDataBinding

        class ReadData : AppCompatActivity() {

            private lateinit var binding :ActivityReadDataBinding
            private lateinit var database : DatabaseReference



            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)

                binding = ActivityReadDataBinding.inflate(layoutInflater)
                setContentView(binding.root)

                binding.readdataBtn.setOnClickListener{
                    val ID :String = binding.etusername.text.toString()
                    if(ID.isNotEmpty()){
                        readData(ID)
                    }else{
                        Toast.makeText(this,"Please enter the UserID", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            private fun readData(ID: String){
                database = FirebaseDatabase.getInstance().getReference("Employees")
                database.child(ID).get().addOnSuccessListener {
                    if (it.exists()){
                        val empAge = it.child("empAge").value
                        val empId = it.child("empId").value
                        val empName = it.child("empName").value
                        val empSalary = it.child("empSalary").value

                        Toast.makeText(this,"Successful", Toast.LENGTH_SHORT).show()
                        binding.etusername.text.clear()
                        binding.toAge.text=empAge.toString()
                        binding.toID.text=empId.toString()
                        binding.toName.text=empName.toString()
                        binding.toSalary.text=empSalary.toString()

                    }else{
                        Toast.makeText(this,"User Doesn't Exist ", Toast.LENGTH_SHORT).show()
                    }

                }.addOnFailureListener{
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }}