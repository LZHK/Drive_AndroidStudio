package com.example.ejm_drive.utils

import com.example.ejm_drive.models.GoogleSheetsResponse
import com.example.ejm_drive.models.IGoogleSheets


class Common {
    const val BASE_URL = "https://script.google.com/macros/s/AKfycbznE9gLwf3TJkr86Cef3FBq3X-lT_QCS972LvWVxV6jsDGB8T07GJZCz33ih_fAH9Zlqw/"
    const val GOOGLE_SHEET_ID = "1CS-i02Jans4zLm3VIitwjKmbROOw09u2IoGLLYCO3Ao"
    const val SHEET_NAME = "personas"

    fun iGSGetMethodClient(baseUrl: String): IGoogleSheets {
        return GoogleSheetsResponse.getClientGetMethod(baseUrl)
            .create(IGoogleSheets::class.java)
    }

}