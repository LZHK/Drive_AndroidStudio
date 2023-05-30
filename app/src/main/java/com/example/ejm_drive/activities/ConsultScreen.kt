package com.example.ejm_drive.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.ejm_drive.R
import com.example.ejm_drive.adapters.PeopleAdapter
import com.example.ejm_drive.models.IGoogleSheets
import com.example.ejm_drive.models.People
import com.example.ejm_drive.utils.Common
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ConsultScreen : AppCompatActivity() {
    private lateinit var iGoogleSheets: IGoogleSheets
    private lateinit var peopleList: MutableList<People>
    private lateinit var recyclerPeople: RecyclerView
    private lateinit var progressDialog: ProgressDialog
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consult_screen)

        recyclerPeople = findViewById(R.id.recycler_people)
        fab = findViewById(R.id.fab_register)

        peopleList = ArrayList()

        iGoogleSheets = Common.iGSGetMethodClient(Common.BASE_URL)
        loadDataFromGoogleSheets()
    }

    private fun loadDataFromGoogleSheets() {
        progressDialog = ProgressDialog.show(
            this,
            "Cargando resultados",
            "Espere por favor",
            true,
            false
        )

        val pathUrl = "exec?spreadsheetId=${Common.GOOGLE_SHEET_ID}&sheet=${Common.SHEET_NAME}"
        iGoogleSheets.getPeople(pathUrl).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                try {
                    response.body()?.let { responseBody ->
                        val responseObject = JSONObject(responseBody)
                        val peopleArray = responseObject.getJSONArray("personas")

                        for (i in 0 until peopleArray.length()) {
                            val object = peopleArray.getJSONObject(i)
                            val id = object.getString("id")
                            val name = object.getString("nombre")
                            val surname = object.getString("apellido")
                            val age = object.getString("edad")

                            val people = People(id, name, surname, age)
                            peopleList.add(people)
                        }

                        setPeopleAdapter(peopleList)
                        progressDialog.dismiss()

                        val size = peopleList.size
                        goToRegisterScreen(size)
                    }
                } catch (je: JSONException) {
                    je.printStackTrace()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // Manejar el fallo de la solicitud aquí
            }
        })
    }

    private fun setPeopleAdapter(peopleList: List<People>) {
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL

        val peopleAdapter = PeopleAdapter(this, peopleList)
        recyclerPeople.layoutManager = manager
        recyclerPeople.adapter = peopleAdapter
    }

    private fun goToRegisterScreen(size: Int) {
        fab.setOnClickListener {
            startActivity(
                Intent(this@ConsultScreen, RegisterScreen::class.java)
                    .putExtra("count", size)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }
    }

}