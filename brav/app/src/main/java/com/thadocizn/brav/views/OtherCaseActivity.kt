package com.thadocizn.brav.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseAuth
import com.thadocizn.brav.R
import com.thadocizn.brav.models.Case
import com.thadocizn.brav.services.RetroInstance
import com.thadocizn.brav.utils.SharedPreference
import kotlinx.android.synthetic.main.activity_mediator.*
import kotlinx.android.synthetic.main.activity_other_case.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtherCaseActivity : AppCompatActivity() {
    private lateinit var idToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_case)

        val sharedPreference = SharedPreference(this)

        idToken = sharedPreference.getToken("token").toString()

        setupSpinner()

        btnSend.setOnClickListener {
            val case: Case = case()
            createCase(case)
            startActivity<CaseActivity>()
        }
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.specialization,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spDisputeCategory.adapter = adapter
        }
    }

    private fun case(): Case {
        var caseId:Int? = null
        val caseAcceptedAt = ""
        val caseCompletedAt = ""
        val caseDeclinedAt = ""
        val notes = etCaseNotes.text.toString()
        val courtCase = false
        val courtFilingDate = ""
        val courtJurisdiction = ""
        val courtNumber = ""
        val description = etDescription.text.toString()
        val disputeAmount = etDisputeAmount.text.toString()
        val disputeCategory = spDisputeCategory.selectedItem.toString()
        val partiesContactInfo = etPartiesContactInfo.text.toString()
        val partiesInvolved = etPartiesInvolved.text.toString()

        val case: Case = Case(
            caseId,
            caseCompletedAt,
            description,
            disputeCategory,
            disputeAmount,
            partiesInvolved,
            partiesContactInfo,
            courtCase,
            courtJurisdiction,
            courtNumber,
            courtFilingDate,
            notes,
            caseAcceptedAt,
            caseDeclinedAt
        )
        return case
    }

    private fun createCase(case: Case) {
        val service = RetroInstance().service(idToken)
        val call = service.postCase(case)

        call.enqueue(object : Callback<Case> {
            override fun onFailure(call: Call<Case>, t: Throwable) {
                println(t.message)
            }

            override fun onResponse(call: Call<Case>, response: Response<Case>) {
                println(response.body().toString())
            }
        })
    }
}
