package com.example.ejm_drive.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.ejm_drive.R
import com.example.ejm_drive.models.IGoogleSheets
import com.example.ejm_drive.utils.Common
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RegisterScreen : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etSurname: EditText
    private lateinit var etAge: EditText
    private lateinit var btnRegister: AppCompatButton

    private var lastId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)

        etName = findViewById(R.id.et_name)
        etSurname = findViewById(R.id.et_surname)
        etAge = findViewById(R.id.et_age)
        btnRegister = findViewById(R.id.btn_register)

        lastId = intent.getIntExtra("count", 0)

        btnRegister.setOnClickListener { registerPerson() }
    }

    private fun registerPerson() {
        val progressDialog = ProgressDialog.show(
            this,
            "Registrando nueva persona",
            "Espere por favor",
            true,
            false
        )

        val name = etName.text.toString()
        val surname = etSurname.text.toString()
        val age = etAge.text.toString()

        AsyncTask.execute {
            try {
                val retrofit = Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://script.google.com/macros/s/AKfycbyJfkyxt5hyccVQigB2ybYvmpBaTJhI2gt22VUY4JOEmGRw9ddzTvaBzIxhGGIlMIcZ/")
                    .build()

                val iGoogleSheets = retrofit.create(IGoogleSheets::class.java)

                val id = lastId + 1

                val jsonRequest = """
                    {
                        "spreadsheet_id": "${Common.GOOGLE_SHEET_ID}",
                        "sheet": "${Common.SHEET_NAME}",
                        "rows": [
                            [
                                "$id",
                                "$name",
                                "$surname",
                                "$age"
                            ]
                        ]
                    }
                """.trimIndent()

                val call: Call<String> = iGoogleSheets.getStringRequestBody(jsonRequest)

                val response: Response<String> = call.execute()
                val code = response.code()

                progressDialog.dismiss()
                if (code == 200) {
                    startActivity(
                        Intent(this@RegisterScreen, ConsultScreen::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
