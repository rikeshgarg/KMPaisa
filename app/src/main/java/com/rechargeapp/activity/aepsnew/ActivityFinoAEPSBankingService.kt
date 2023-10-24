package com.rechargeapp.activity.aepsnew

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.rechargeapp.activity.ActivityMain
import com.rechargeapp.activity.ActivitySplash
import com.codunite.rechargeapp.R
import com.rechargeapp.WebViewActivity
import com.commonutility.PreferenceConnector
import com.commonutility.retrofit.ApiInterface
import com.codunite.rechargeapp.databinding.ActivityAepsbankingBinding
import com.google.gson.JsonObject
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Document
import org.xml.sax.InputSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.StringReader
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class ActivityFinoAEPSBankingService : AppCompatActivity() {
    lateinit var mainBinding: ActivityAepsbankingBinding
    var TAG = "@@requestscreen"
    var pausingDialog: SweetAlertDialog? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var deviceType: Int = 0 //Default 0 for Morpho and 1 for Mantra
    var inputType: Int = 1
    lateinit var con: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_aepsbanking)
        con = this;
        mainBinding = ActivityAepsbankingBinding.inflate(layoutInflater)

        pausingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pausingDialog!!.titleText = "Please wait...."
        pausingDialog!!.setCancelable(false)

        mainBinding.imgCrossWeb.setOnClickListener(View.OnClickListener {
            mainBinding.layWeb.visibility = View.GONE
        })

        mainBinding.radioBankEnq.setOnClickListener(View.OnClickListener { EnableClick(1) })
        mainBinding.radioMiniStat.setOnClickListener(View.OnClickListener { EnableClick(2) })
        mainBinding.radioWithdrawal.setOnClickListener(View.OnClickListener { EnableClick(3) })
        mainBinding.radioAadharpay.setOnClickListener(View.OnClickListener { EnableClick(4) })

        mainBinding.inputType.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton = group.findViewById<View>(checkedId) as RadioButton
            val selectedText = checkedRadioButton.text
            if (selectedText.equals(mainBinding.radioOne.text)) {
                inputType = 1
            } else if (selectedText.equals(mainBinding.radioTwo.text)) {
                inputType = 2
            } else if (selectedText.equals(mainBinding.radioThree.text)) {
                inputType = 3
            } else {
                inputType = 4
            }
        })

        mainBinding.edtAmount.visibility = View.GONE

        var pageData: String = ""
        pageData = intent.getStringExtra("selecteditem").toString()

        if (pageData.equals("Aadhar Pay")) {
            mainBinding.radioAadharpay.isChecked = true
            radioSelected = mainBinding.radioAadharpay
            mainBinding.edtAmount.visibility = View.VISIBLE
        } else if (pageData.equals("Mini Statement")) {
            mainBinding.radioMiniStat.isChecked = true
            radioSelected = mainBinding.radioMiniStat
        } else if (pageData.equals("Withdrawal")) {
            mainBinding.radioWithdrawal.isChecked = true
            radioSelected = mainBinding.radioWithdrawal
            mainBinding.edtAmount.visibility = View.VISIBLE
        } else {
            mainBinding.radioBankEnq.isChecked = true
            radioSelected = mainBinding.radioBankEnq
        }

        mOnClick()

        setContentView(mainBinding.root)

//        imeiNumber = getDeviceIMEI()
        imeiNumber = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )

        OpenDemoLink()
    }

    lateinit var radioSelected: RadioButton
    fun EnableClick(clickedItem: Int) {
        mainBinding.radioBankEnq.isChecked = false
        mainBinding.radioAadharpay.isChecked = false
        mainBinding.radioMiniStat.isChecked = false
        mainBinding.radioWithdrawal.isChecked = false

        if (clickedItem == 1) {
            mainBinding.radioBankEnq.isChecked = true
            mainBinding.edtAmount.visibility = View.GONE
            radioSelected = mainBinding.radioBankEnq
        } else if (clickedItem == 2) {
            mainBinding.radioMiniStat.isChecked = true
            mainBinding.edtAmount.visibility = View.GONE
            radioSelected = mainBinding.radioMiniStat
        } else if (clickedItem == 3) {
            mainBinding.radioWithdrawal.isChecked = true
            mainBinding.edtAmount.visibility = View.VISIBLE
            radioSelected = mainBinding.radioWithdrawal
        } else {
            mainBinding.radioAadharpay.isChecked = true
            mainBinding.edtAmount.visibility = View.VISIBLE
            radioSelected = mainBinding.radioAadharpay
        }
    }

    fun mOnClick() {
        mainBinding.txtTobank.setOnClickListener {
            mainBinding.edtSearche.setText("")
            getBankList()
        }
        mainBinding.imgCross.setOnClickListener {
            mainBinding.rvMain.visibility = View.GONE
        }

        list_filter()

        if (PreferenceConnector.readBoolean(con, PreferenceConnector.ISMANTRA, false)) {
            mainBinding.radioMantra.isChecked = true
            mainBinding.radioMorpho.isChecked = false
            selecctMyntra()
        } else {
            mainBinding.radioMantra.isChecked = false
            mainBinding.radioMorpho.isChecked = true
            selecctMorpho()
        }

        mainBinding.deviceType.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton = group.findViewById<View>(checkedId) as RadioButton
            val selectedText = checkedRadioButton.text
            if (selectedText.equals(mainBinding.radioMantra.text)) {
                PreferenceConnector.writeBoolean(con, PreferenceConnector.ISMANTRA, true)
                selecctMyntra()
            } else if (selectedText.equals(mainBinding.radioMorpho.text)) {
                PreferenceConnector.writeBoolean(con, PreferenceConnector.ISMANTRA, false)
                selecctMorpho()
            }
        })

        mainBinding.btnProcess.setOnClickListener {
            mainBinding.edtMobNumber.setError(null)
            mainBinding.edtAadharNumber.setError(null)
            mainBinding.edtAmount.setError(null)

            if (mainBinding.edtMobNumber.text!!.isEmpty()) {
                Toast.makeText(
                    this@ActivityFinoAEPSBankingService,
                    "Please enter mobile number",
                    Toast.LENGTH_LONG
                ).show()
                mainBinding.edtMobNumber.setError("Please enter mobile number")
            } else if (mainBinding.edtAadharNumber.text!!.isEmpty()) {
                Toast.makeText(
                    this@ActivityFinoAEPSBankingService, "Please Enter aadhar number",
                    Toast.LENGTH_LONG
                ).show()
                mainBinding.edtAadharNumber.setError("Please Enter aadhar number")
            } else if (mainBinding.txtTobank.text.toString().isEmpty()) {
                Toast.makeText(
                    this@ActivityFinoAEPSBankingService, "Please select bank", Toast.LENGTH_LONG
                ).show()
                mainBinding.txtTobank.setError("Please select bank!")
            } else if (mainBinding.edtAadharNumber.text.toString().length != 12) {
                Toast.makeText(
                    this@ActivityFinoAEPSBankingService,
                    "Enter valid aadhar number",
                    Toast.LENGTH_LONG
                ).show()
                mainBinding.edtAadharNumber.setError("Enter valid aadhar number")
            } else if (mainBinding.edtMobNumber.text.toString().length != 10) {
                Toast.makeText(
                    this@ActivityFinoAEPSBankingService,
                    "Enter valid mobile number",
                    Toast.LENGTH_LONG
                ).show()
                mainBinding.edtMobNumber.setError("Enter valid mobile number")
            } else {

                mainBinding.edtMobNumber.setError(null)
                mainBinding.edtAadharNumber.setError(null)
                mainBinding.edtAmount.setError(null)

//                val genid: Int = mainBinding.bankDetails.getCheckedRadioButtonId()
//                val radioButton = findViewById<View>(genid) as RadioButton

                val type = radioSelected.text.toString()
                if (type.equals("Withdrawal")) {
                    if (mainBinding.edtAmount.text!!.isEmpty()) {
                        Toast.makeText(
                            this@ActivityFinoAEPSBankingService,
                            "Please Enter Amount",
                            Toast.LENGTH_LONG
                        ).show()
                        mainBinding.edtAmount.setError("Please Enter Amount")
                    }
                }
                GetDeviceInfo()
            }
        }
        loadToolBar()
        loadSubmit()
    }

    fun selecctMorpho() {
        deviceType = 0
        val intent = Intent("android.intent.action.SCL_RDSERVICE_OTP_RECIEVER")
        intent.setPackage("com.scl.rdservice")
        sendBroadcast(intent)
    }

    fun selecctMyntra() {
        deviceType = 1
    }

    fun list_filter() {
        mainBinding.edtSearche.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                filter(mainBinding.edtSearche.text.toString())
            }
        })
    }

    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filterdNames: ArrayList<FinoBankDataModel> = ArrayList()

        //looping through existing elements
        for (s in bankList) {
            //if the existing elements contains the search input
            if (s.itemName.lowercase().contains(text.lowercase())) {
                //adding the element to filtered list
                filterdNames.add(s)
            }
        }
        //calling a method of the adapter class and passing the filtered list
//        bankadapter?.filterList(filterdNames)
//        bankadapter?.notifyDataSetChanged()

        mainBinding.mainRecycler.adapter =
            ShowBankAdapter(filterdNames) { partItem: FinoBankDataModel ->
                partToItemClicked(partItem)
            }
    }


    var REQUEST_READ_PHONE_STATE: Int = 111
    var imeiNumber: String = ""
//    fun getDeviceIMEI(): String {
//        var permissionCheck =
//                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
//        var deviceUniqueIdentifier: String = ""
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(
//                    this, arrayOf(Manifest.permission.READ_PHONE_STATE),
//                    REQUEST_READ_PHONE_STATE
//            )
//        } else {
//            val tm = this.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
//            if (null != tm) {
//                deviceUniqueIdentifier = tm.deviceId
//            }
//            if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length) {
//                deviceUniqueIdentifier =
//                        Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
//            }
//        }
//        return deviceUniqueIdentifier
//    }

    var DEVICE_INFO: Int = 119
    val AUTHENTICATION_REQUEST: Int = 120
    fun GetDeviceInfo() {
        val intent = Intent("in.gov.uidai.rdservice.fp.INFO")
        if (deviceType == 0) {
            intent.setPackage("com.scl.rdservice")
        } else {
            intent.setPackage("com.mantra.rdservice")
        }
        startActivityForResult(intent, DEVICE_INFO)
    }

    fun SubmitAutheticateData() {
        var inputPidData: String?

        if (deviceType == 0) {
            inputPidData =
                "<PidOptions ver=\"1.0\"><Opts fCount=\"1\" fType=\"2\" iCount=\"0\" iType=\"0\" pCount=\"0\" pType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" env=\"P\" otp=\"\" wadh=\"\" posh=\"UNKNOWN\"/></PidOptions>"
        } else {
            inputPidData =
                "<PidOptions ver=\"1.0\"><Opts fCount=\"1\" fType=\"2\" iCount=\"0\" iType=\"0\" pCount=\"0\" pType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" env=\"P\" otp=\"\" wadh=\"\" posh=\"UNKNOWN\"/></PidOptions>"
        }

        try {
            val intentCapture = Intent()
            intentCapture.action = "in.gov.uidai.rdservice.fp.CAPTURE"
            if (deviceType == 0) {
                intentCapture.setPackage("com.scl.rdservice")
            } else {
                intentCapture.setPackage("com.mantra.rdservice")
            }
            intentCapture.putExtra("PID_OPTIONS", inputPidData)
            startActivityForResult(intentCapture, AUTHENTICATION_REQUEST)
        } catch (ex: Exception) {
            ex.printStackTrace()
            showInfoDialog(
                "RD Services App not Installed or Machine not Attached.",
                SweetAlertDialog.ERROR_TYPE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var display: String? = ""
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            DEVICE_INFO -> if (resultCode == RESULT_OK) {

                try {
                    if (data != null) {
                        val deviceInfo = data.getStringExtra("DEVICE_INFO")
                        val rdServiceInfo = data.getStringExtra("RD_SERVICE_INFO")
                        val dnc = data.getStringExtra("DNC")
                        val dnr = data.getStringExtra("DNR")

                        display =
                            "DEVICE_INFO: $deviceInfo\n\nRD_SERVICE_INFO: $rdServiceInfo\n\nDNC: $dnc\n\nDNR: $dnr"

                        if (!dnc.equals("null")) {
                            showInfoDialog("Device not connected", SweetAlertDialog.ERROR_TYPE)
                        } else if (!dnr.equals("null")) {
                            showInfoDialog("Device not registered", SweetAlertDialog.WARNING_TYPE)
                        } else {

                        }
                    }
                    mainBinding.tvOutput.text = display
                } catch (e: Exception) {
                    Log.e("Error", "Error while deserialze device info", e)
                }
            }
            AUTHENTICATION_REQUEST -> if (resultCode == RESULT_OK) {
                var biometricData: String = ""
                try {
                    if (data != null) {
                        val pidData =
                            data.getStringExtra("PID_DATA")// in this varaible you will get Pid data
                        val dnc =
                            data.getStringExtra("DNC")// you will get value in this variable when your finger print device not connected
                        val dnr =
                            data.getStringExtra("DNR")// you will get value in this variable when your finger print device not registered .

                        display = "PID_DATA: $pidData\n\nDNC: $dnc\n\nDNR: $dnr"
                        if (pidData != null) {
                            if (dnc == null || dnc == "null") {
                                if (deviceType == 0) {
                                    parseXmlDoc(pidData.split("\\?>".toRegex()).toTypedArray()[1])
                                    biometricData = createInputDocument()
                                    aadharAuthorise(biometricData)
                                    mainBinding.tvOutput.text = biometricData
                                } else {
                                    biometricData = "<?xml version=\"1.0\"?>" + pidData
                                    aadharAuthorise(biometricData)
                                    mainBinding.tvOutput.text = biometricData
                                }
                            } else if (dnc.contains("not")) {
                                showInfoDialog("Device not connected", SweetAlertDialog.ERROR_TYPE)
                            } else if (dnr != null) {
                                if (dnr.contains("not")) {
                                    showInfoDialog(
                                        "Device not registered",
                                        SweetAlertDialog.WARNING_TYPE
                                    )
                                }
                            }
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("Error", "Error while deserialze pid data", e)
                }
            }
        }
    }

    fun aadharAuthorise(bioMetricData: String) {
        var serviceType = ""
        var amountSend = "0"
//        val genid: Int = mainBinding.bankDetails.getCheckedRadioButtonId()
//        val radioButton = findViewById<View>(genid) as RadioButton
        val type = radioSelected.text.toString()
        if (type.equals(mainBinding.radioBankEnq.text.toString())) {
            serviceType = "balinfo"
            amountSend = "0"
        } else if (type.equals(mainBinding.radioMiniStat.text.toString())) {
            serviceType = "ministatement"
            amountSend
            amountSend = "0"
        } else if (type.equals(mainBinding.radioWithdrawal.text.toString())) {
            serviceType = "balwithdraw"
            amountSend = mainBinding.edtAmount.text.toString()
        } else {
            serviceType = "aadharpay"
            amountSend = mainBinding.edtAmount.text.toString()
        }

        if (imeiNumber.equals("")) {
            // imeiNumber = getDeviceIMEI()
            imeiNumber = Settings.Secure.getString(
                contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }

        pausingDialog!!.show()
        var apiInterface: ApiInterface =
            RetrofitManager(this).instance!!.create(ApiInterface::class.java)

        val obj = JSONObject()
        try {
            obj.put(
                "userID",
                PreferenceConnector.readString(this, PreferenceConnector.LOGINEDUSERID, "")
            )
            obj.put("ServiceType", serviceType)
            obj.put("deviceIMEI", imeiNumber)
            obj.put("AadharNumber", mainBinding.edtAadharNumber.text.toString())
            obj.put("mobileNumber", mainBinding.edtMobNumber.text.toString())
            obj.put("Amount", amountSend)
            obj.put("IIN", bankCode)
            obj.put("BiometricData", bioMetricData)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        apiInterface.newAepsApiAuth(obj.toString()).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                pausingDialog!!.dismiss()
                if (response.isSuccessful) {
                    var lstdata: ArrayList<MiniBalDataModel>
                    val mJsonObject = JSONObject((response.body().toString()).replace("\\", ""))

                    if (mJsonObject.getString("status").equals("1")) {
                        if (mJsonObject.getString("is_bal_info").equals("1")) {
                            PreferenceConnector.writeString(
                                con,
                                PreferenceConnector.WEBHEADING,
                                "Balance Account Invoice"
                            )
                            PreferenceConnector.writeString(
                                con,
                                PreferenceConnector.WEBURL,
                                (mJsonObject.getString("invoiceUrl")).replace("\\/", "/")
                            )

                            showAccountBalance(
                                "You Account balance is\n" + mJsonObject.getString("balanceAmount"),
                                SweetAlertDialog.SUCCESS_TYPE
                            )
                        } else {
                            if (serviceType.equals("ministatement")) {
                                lstdata = ArrayList()
                                mainBinding.layWeb.visibility = View.VISIBLE

                                var strArray = mJsonObject.getJSONArray("str")
                                for (i in 0 until strArray.length()) {
                                    var JsonObjectData = strArray.getJSONObject(i)
                                    lstdata.add(
                                        MiniBalDataModel(
                                            JsonObjectData.getString("date"),
                                            JsonObjectData.getString("txnType"),
                                            JsonObjectData.getString("amount"),
                                            JsonObjectData.getString("narration")
                                        )
                                    )
                                }

                                if (mJsonObject.has("balanceAmount")) {
                                    mainBinding.txtBalance.text =
                                        mJsonObject.getString("balanceAmount")
                                }

                                PreferenceConnector.writeString(
                                    con,
                                    PreferenceConnector.WEBHEADING,
                                    "Mini Statement Invoice"
                                )
                                PreferenceConnector.writeString(
                                    con,
                                    PreferenceConnector.WEBURL,
                                    (mJsonObject.getString("invoiceUrl")).replace("\\/", "/")
                                )

                                Loaddata(lstdata, true)

                                mainBinding.txtSharereceipt.setOnClickListener {
                                    val intent = Intent(con, InvoiceActivity::class.java)
                                    startActivity(intent)
                                }
                            } else if (serviceType.equals("balwithdraw") || serviceType.equals("aadharpay")) {
                                lstdata = ArrayList()
                                mainBinding.layWeb.visibility = View.VISIBLE

                                var JsonObjectData = mJsonObject.getJSONObject("str")
                                lstdata.add(
                                    MiniBalDataModel(
                                        JsonObjectData.getString("txnStatus"),
                                        JsonObjectData.getString("amount"),
                                        JsonObjectData.getString("balanceAmount"),
                                        JsonObjectData.getString("bankRRN")
                                    )
                                )

                                if (mJsonObject.has("balanceAmount")) {
                                    mainBinding.txtBalance.text =
                                        mJsonObject.getString("balanceAmount")
                                }

                                PreferenceConnector.writeString(
                                    con,
                                    PreferenceConnector.WEBHEADING,
                                    "Withdrwal/Aadhar Pay Invoice"
                                )
                                PreferenceConnector.writeString(
                                    con,
                                    PreferenceConnector.WEBURL,
                                    (mJsonObject.getString("invoiceUrl")).replace("\\/", "/")
                                )

                                mainBinding.txtSharereceipt.setOnClickListener {
                                    val intent = Intent(con, InvoiceActivity::class.java)
                                    startActivity(intent)
                                }

                                Loaddata(lstdata, false)
                            }
                        }
                    } else {
                        showInfoDialog(
                            mJsonObject.getString("message"),
                            SweetAlertDialog.WARNING_TYPE
                        )
                    }
                } else {
                    showInfoDialog("Something wrong", SweetAlertDialog.WARNING_TYPE)
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(
                    this@ActivityFinoAEPSBankingService,
                    " " + t.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
                pausingDialog!!.dismiss()
                showInfoDialog(t.message.toString(), SweetAlertDialog.WARNING_TYPE)
            }
        })
    }

    fun Loaddata(lstdata: ArrayList<MiniBalDataModel>, isMiniBal: Boolean) {
        var linearLayoutManager = LinearLayoutManager(
            this@ActivityFinoAEPSBankingService,
            LinearLayoutManager.VERTICAL,
            false
        )

        mainBinding.tvOutputWebview!!.layoutManager = linearLayoutManager
        mainBinding.tvOutputWebview!!.itemAnimator = DefaultItemAnimator()
        mainBinding.tvOutputWebview.adapter =
            ShowMiniBalAdapter(lstdata, isMiniBal) { partToItem: MiniBalDataModel ->
                partToItemClicked(partToItem)
            }
    }

    public fun getBankList() {
        bankList = ArrayList();
        pausingDialog?.show()
        var apiInterface: ApiInterface =
            RetrofitManager(this@ActivityFinoAEPSBankingService).instance!!.create(
                ApiInterface::class.java
            )
        apiInterface.getAllbankNamedetails().enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(
                    this@ActivityFinoAEPSBankingService,
                    t.message.toString() + " ",
                    Toast.LENGTH_LONG
                )
                    .show()
                pausingDialog?.dismiss()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    var mJsonObject = JSONObject(response.body().toString())

                    var mJsonArray = mJsonObject.getJSONArray("data")
                    if (mJsonObject.has("data") && !mJsonObject.isNull("data")) {
                        if (mJsonObject.getString("status").toLowerCase()
                                .equals("1") && mJsonArray != null
                        ) {
                            for (i in 0 until mJsonArray.length()) {
                                var JsonObjectData = mJsonArray.getJSONObject(i)
                                bankList.add(
                                    FinoBankDataModel(
                                        JsonObjectData.getString("iinno"),
                                        JsonObjectData.getString("bank_name")
                                    )
                                )
                            }
                        } else {
                            stop_progress()
                        }
                    } else {
                        stop_progress()
                    }

                    var linearLayoutManager = LinearLayoutManager(
                        this@ActivityFinoAEPSBankingService,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    mainBinding.mainRecycler!!.layoutManager = linearLayoutManager
                    mainBinding.mainRecycler!!.itemAnimator = DefaultItemAnimator()
                    mainBinding.mainRecycler.adapter =
                        ShowBankAdapter(bankList) { partToItem: FinoBankDataModel ->
                            partToItemClicked(
                                partToItem
                            )
                        }
                    mainBinding.rvMain.visibility = View.VISIBLE
                    mainBinding.edtAmount.clearFocus()
                    mainBinding.laySearchview.visibility = View.VISIBLE
                } else {
//                    mainBinding.imgEmptylist.visibility = View.VISIBLE
                }
                pausingDialog!!.dismiss()
            }
        })
    }

    private fun partToItemClicked(partItem: MiniBalDataModel) {

    }

    var bankCode: String = "";
    private fun partToItemClicked(partItem: FinoBankDataModel) {
        hideKeyboard();
        bankCode = partItem.id
        mainBinding.rvMain.visibility = View.GONE
        mainBinding.txtTobank.text = partItem.itemName
    }


    var bankList: ArrayList<FinoBankDataModel> = ArrayList()
    fun stop_progress() {
        if (pausingDialog?.isShowing!!) {
            pausingDialog?.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun showInfoDialog(title: String, type: Int) {
        SweetAlertDialog(this, type)
            .setTitleText(title)
            .setConfirmText("OK")
            .setConfirmClickListener { sDialog ->
                sDialog.dismiss()
            }
            .show()
    }


    fun showAccountBalance(title: String, type: Int) {
        SweetAlertDialog(this, type)
            .setTitleText(title)
            .setConfirmText("OK")
            .setConfirmClickListener { sDialog ->
                sDialog.dismiss()
                val intent = Intent(con, InvoiceActivity::class.java)
                startActivity(intent)
            }
            .show()
    }


    fun hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromInputMethod(view.windowToken, 0)
    }

    private fun loadSubmit() {
        mainBinding.btnSubmit.setOnClickListener {
            mainBinding.edtMobNumber.setError(null)
            mainBinding.edtAadharNumber.setError(null)
            mainBinding.edtAmount.setError(null)

            if (mainBinding.edtMobNumber.text!!.isEmpty()) {
                Toast.makeText(
                    this@ActivityFinoAEPSBankingService,
                    "Please enter mobile number",
                    Toast.LENGTH_LONG
                ).show()
                mainBinding.edtMobNumber.setError("Please enter mobile number")
            } else if (mainBinding.edtAadharNumber.text!!.isEmpty()) {
                Toast.makeText(
                    this@ActivityFinoAEPSBankingService, "Please Enter aadhar number",
                    Toast.LENGTH_LONG
                ).show()
                mainBinding.edtAadharNumber.setError("Please Enter aadhar number")
            } else if (mainBinding.txtTobank.text.toString().isEmpty()) {
                Toast.makeText(
                    this@ActivityFinoAEPSBankingService, "Please select bank", Toast.LENGTH_LONG
                ).show()
                mainBinding.txtTobank.setError("Please select bank!")
            } else if (mainBinding.edtAadharNumber.text.toString().length != 12) {
                Toast.makeText(
                    this@ActivityFinoAEPSBankingService,
                    "Enter valid aadhar number",
                    Toast.LENGTH_LONG
                ).show()
                mainBinding.edtAadharNumber.setError("Enter valid aadhar number")
            } else if (mainBinding.edtMobNumber.text.toString().length != 10) {
                Toast.makeText(
                    this@ActivityFinoAEPSBankingService,
                    "Enter valid mobile number",
                    Toast.LENGTH_LONG
                ).show()
                mainBinding.edtMobNumber.setError("Enter valid mobile number")
            } else {
                mainBinding.edtMobNumber.setError(null)
                mainBinding.edtAadharNumber.setError(null)
                mainBinding.edtAmount.setError(null)

//                val genid: Int = mainBinding.bankDetails.getCheckedRadioButtonId()
//                val radioButton = findViewById<View>(genid) as RadioButton
                val type = radioSelected.text.toString()
                if (type.equals(mainBinding.radioWithdrawal.text.toString()) ||
                    type.equals(mainBinding.radioAadharpay.text.toString())
                ) {
                    if (!mainBinding.edtAmount.text.isEmpty()) {
                        if ((mainBinding.edtAmount.text.toString()).toDouble() <= 100) {
                            Toast.makeText(
                                this@ActivityFinoAEPSBankingService,
                                "Please Enter Amount greater than 100",
                                Toast.LENGTH_LONG
                            ).show()
                            mainBinding.edtAmount.setError("Please Enter Amount greater than 100")
                        } else {
                            SubmitAutheticateData()
                        }
                    } else {

                    }
                } else {
                    SubmitAutheticateData()
                }
            }
        }
    }

    private fun loadToolBar() {
        mainBinding.imgBack.setOnClickListener { onBackPressed() }
        mainBinding.heading.text = getString(R.string.app_name)
        mainBinding.heading.text = "Fino AEPS"
        mainBinding.toolbarTxtWalletbal.text =
            ActivityMain.ShowBalance(this@ActivityFinoAEPSBankingService)
        mainBinding.toolbarTxtEwalletbal.text =
            ActivityMain.ShoweBalance(this@ActivityFinoAEPSBankingService)
        mainBinding.imgWallet.setOnClickListener { ActivitySplash.OpenWalletActivity(this@ActivityFinoAEPSBankingService) }
        mainBinding.imgEwallet.setOnClickListener { ActivitySplash.OpeneWalletActivity(this@ActivityFinoAEPSBankingService) }
    }

    var errCode = ""
    var errInfo = ""
    var fCount = ""
    var fType = ""
    var iCount = ""
    var nmPoints = ""
    var pCount = ""
    var pgCount = ""
    var qScore = ""
    var dc = ""
    var dpId = ""
    var mc = ""
    var mi = ""
    var rdsId = ""
    var rdsVer = ""
    var name = ""
    var value = ""
    var ci = ""
    var type = ""
    var pTimeout = ""
    var contOfSkeyElm = ""
    var contOfHmacElm = ""
    var contOfDataElm = ""
    fun createInputDocument(): String {
        val inputDoc = XMLUtil.createDocument("PidData")
        val pidDataElm = inputDoc.documentElement
        pidDataElm.textContent = ""
        val respElm = XMLUtil.createChildElement(pidDataElm, "Resp")
        respElm.setAttribute("errCode", errCode)
        respElm.setAttribute("errInfo", errInfo)
        respElm.setAttribute("fCount", fCount)
        respElm.setAttribute("fType", fType)
        respElm.setAttribute("iCount", iCount)
        respElm.setAttribute("nmPoints", nmPoints)
        respElm.setAttribute("pCount", pCount)
        respElm.setAttribute("pTimeout", pTimeout)
        respElm.setAttribute("pgCount", pgCount)
        respElm.setAttribute("qScore", qScore)
        val deviceInfoElm = XMLUtil.createChildElement(pidDataElm, "DeviceInfo")
        deviceInfoElm.setAttribute("dc", dc)
        deviceInfoElm.setAttribute("dpId", dpId)
        deviceInfoElm.setAttribute("mc", mc)
        deviceInfoElm.setAttribute("mi", mi)
        deviceInfoElm.setAttribute("rdsId", rdsId)
        deviceInfoElm.setAttribute("rdsVer", rdsVer)
        val additional_infoElm = XMLUtil.createChildElement(deviceInfoElm, "additional_info")
        val paramElm = XMLUtil.createChildElement(additional_infoElm, "Param")
        paramElm.setAttribute("name", name)
        paramElm.setAttribute("value", value)
        val skeyElm = XMLUtil.createChildElement(pidDataElm, "Skey")
        skeyElm.setAttribute("ci", ci)
        skeyElm.textContent = contOfSkeyElm
        val hmacElm = XMLUtil.createChildElement(pidDataElm, "Hmac")
        hmacElm.textContent = contOfHmacElm
        val dataElm = XMLUtil.createChildElement(pidDataElm, "Data")
        dataElm.setAttribute("type", type)
        dataElm.textContent = contOfDataElm
        val domSource = DOMSource(inputDoc)
        val writer = StringWriter()
        val result = StreamResult(writer)
        val tf: TransformerFactory = TransformerFactory.newInstance()
        val transformer: Transformer = tf.newTransformer()
        transformer.transform(domSource, result)

        return writer.toString()
    }


    private fun parseXmlDoc(xml: String) {
        val doc: Document? = convertStringToXMLDocument(xml)
        val pidDataElm = doc?.documentElement
//        val contOfPidDataElm = pidDataElm?.textContent
        val respElm = XMLUtil.getChildElement(pidDataElm, "Resp")
        errCode = respElm.getAttribute("errCode")
        errInfo = respElm.getAttribute("errInfo")
        fCount = respElm.getAttribute("fCount")
        fType = respElm.getAttribute("fType")
        iCount = respElm.getAttribute("iCount")
        nmPoints = respElm.getAttribute("nmPoints")
        pCount = respElm.getAttribute("pCount")
        pTimeout = respElm.getAttribute("pTimeout")
        pgCount = respElm.getAttribute("pgCount")
        qScore = respElm.getAttribute("qScore")
        val deviceInfoElm = XMLUtil.getChildElement(pidDataElm, "DeviceInfo")
        dc = deviceInfoElm.getAttribute("dc")
        dpId = deviceInfoElm.getAttribute("dpId")
        mc = deviceInfoElm.getAttribute("mc")
        mi = deviceInfoElm.getAttribute("mi")
        rdsId = deviceInfoElm.getAttribute("rdsId")
        rdsVer = deviceInfoElm.getAttribute("rdsVer")
        val additional_infoElm = XMLUtil.getChildElement(deviceInfoElm, "additional_info")
        val paramElm = XMLUtil.getChildElement(additional_infoElm, "Param")
        name = paramElm.getAttribute("name")
        value = paramElm.getAttribute("value")
        val skeyElm = XMLUtil.getChildElement(pidDataElm, "Skey")
        ci = skeyElm.getAttribute("ci")
        contOfSkeyElm = skeyElm.textContent
        val hmacElm = XMLUtil.getChildElement(pidDataElm, "Hmac")
        contOfHmacElm = hmacElm.textContent
        val dataElm = XMLUtil.getChildElement(pidDataElm, "Data")
        type = dataElm.getAttribute("type")
        contOfDataElm = dataElm.textContent
    }

    private fun convertStringToXMLDocument(xmlString: String): Document? {
        //Parser that produces DOM object trees from XML content
        val factory = DocumentBuilderFactory.newInstance()
        //API to obtain DOM Document instance
        var builder: DocumentBuilder? = null
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder()
            //Parse the content to Document object
            return builder.parse(InputSource(StringReader(xmlString)))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }

    private var strDemoServiceName: String? = ""
    private var dtrDemoServiceUrl: String? = ""
    private fun OpenDemoLink() {
        LoadDemoLink()
        (findViewById<View>(R.id.lay_demo_url)).setOnClickListener { v: View? ->
            PreferenceConnector.writeString(
                this,
                PreferenceConnector.WEBHEADING,
                strDemoServiceName
            )
            PreferenceConnector.writeString(
                this,
                PreferenceConnector.WEBURL,
                dtrDemoServiceUrl
            )
            val svIntent = Intent(this, WebViewActivity::class.java)
            this.startActivity(svIntent)
        }
    }

    fun LoadDemoLink() {
        val obj = JSONObject()
        try {
            obj.put("service_id", "3")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        var apiInterface: ApiInterface =
            RetrofitManager(this@ActivityFinoAEPSBankingService).instance!!.create(
                ApiInterface::class.java
            )
        apiInterface.AddAepsRequest(obj.toString()).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                pausingDialog!!.dismiss()
                if (response.isSuccessful) {
                    var lstdata: java.util.ArrayList<MiniBalDataModel> = java.util.ArrayList()
                    val mJsonObject = JSONObject((response.body().toString()).replace("\\", ""))

                    if (mJsonObject.getString("status").equals("1")) {
                        strDemoServiceName = mJsonObject.getString("service")
                        dtrDemoServiceUrl = mJsonObject.getString("demo_link")
                    } else {
//                        showInfoDialog(
//                            mJsonObject.getString("message"),
//                            SweetAlertDialog.WARNING_TYPE
//                        )
                    }
                } else {
                    showInfoDialog("Something wrong", SweetAlertDialog.WARNING_TYPE)
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(
                    this@ActivityFinoAEPSBankingService,
                    " " + t.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
                pausingDialog!!.dismiss()
                showInfoDialog(t.message.toString(), SweetAlertDialog.WARNING_TYPE)
            }
        })
    }


}