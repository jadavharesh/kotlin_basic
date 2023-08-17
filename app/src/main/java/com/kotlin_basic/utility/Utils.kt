
import android.view.View

import android.widget.TextView

import android.app.Activity

import android.app.ActivityManager

import android.os.Build

import android.annotation.SuppressLint
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.Dialog
import android.content.Context

import android.provider.ContactsContract

import android.database.Cursor
import android.graphics.*

import android.net.Uri

import java.io.IOException

import android.location.Geocoder

import java.net.InetAddress

import java.net.NetworkInterface

import android.util.Log

import android.net.ConnectivityManager

import com.google.android.material.snackbar.Snackbar

import java.io.FileOutputStream

import java.io.File

import android.os.Environment

import android.util.DisplayMetrics

import android.view.inputmethod.InputMethodManager

import android.text.TextUtils

import android.media.MediaMetadataRetriever


import androidx.core.content.FileProvider

import android.location.Address
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.idi.BuildConfig
import com.idi.R
import com.idi.utility.Idimain

import java.io.InputStream

import java.net.HttpURLConnection

import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale



class Utils {

    companion object{

        private var customDialog: Dialog? = null
        private var progressDialog: Dialog? = null


        // validatation message on snackbar
        fun showMessage(activity: Activity, message: String?) {
            val snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message!!, Snackbar.LENGTH_LONG)
            val textView = snackbar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            //textView.setTextColor(if (isSuccess) Color.GREEN else Color.WHITE)
            textView.maxLines = 3
            snackbar.show()
        }

        fun showToastMsg(msg: String?, length: Int = Toast.LENGTH_SHORT) {
            Toast.makeText(Idimain.getContext(), msg, length).show()
        }

        fun showProgressDialog(context:Context)
        {
            try {
                hideProgressDialog()
                val dialog = Dialog(context)
                progressDialog=dialog
                dialog.setCancelable(false)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.progress_dialog_circle)
                if (dialog.window != null) {
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
                dialog.show()
            }catch (e:Exception)
            {

            }
        }

        fun hideProgressDialog()
        {
            try {
                if(progressDialog!=null)
                {
                    if(progressDialog!!.isShowing)
                    {
                        progressDialog!!.dismiss()
                    }
                }

            }catch (e:Exception)
            { }
            progressDialog=null
        }



        fun showCustomDialog(context:Context,message:String)
        {
            try {
                hideCustomDialog()
                val dialog = Dialog(context)
                customDialog=dialog
                dialog.setCancelable(false)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.progress_dialog)
                if (dialog.window != null) {
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
                val txtMessage=dialog.findViewById<AppCompatTextView>(R.id.txtMessage)
                txtMessage.setText(message)
                dialog.show()
            }catch (e:Exception)
            {
            }
        }

        fun hideCustomDialog()
        {
            try {
                if(customDialog!=null)
                {
                    if(customDialog!!.isShowing)
                    {
                        customDialog!!.dismiss()
                    }
                }

            }catch (e:Exception)
            { }
            customDialog=null
        }


        // check internet connectivity
        @SuppressLint("MissingPermission")
        fun isInternetAvailable(): Boolean {
            return try {
                val cm = Idimain.getContext()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val netInfo = cm.activeNetworkInfo
                netInfo != null && netInfo.isConnected && netInfo.isAvailable
            } catch (e: Exception) {
                false
            }
        }

        // no internet message
        fun showNoInternetAvailable(activity: Activity) {
            val snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "No internet available", Snackbar.LENGTH_LONG)
            val textView = snackbar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.RED)
            snackbar.show()
        }


        fun fileExt(url: String): String? {
            var url = url
            if (url.contains("?")) {
                url = url.substring(0, url.indexOf("?"))
            }
            return if (url.lastIndexOf(".") == -1) {
                null
            } else {
                var ext = url.substring(url.lastIndexOf(".") + 1)
                if (ext.contains("%")) {
                    ext = ext.substring(0, ext.indexOf("%"))
                }
                if (ext.contains("/")) {
                    ext = ext.substring(0, ext.indexOf("/"))
                }
                ext.toLowerCase()
            }
        }

        fun getUriFromFile(photoFile: File?, context: Context?): Uri? {
            var photoURI: Uri? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (photoFile != null) {
                    photoURI = FileProvider.getUriForFile(context!!, BuildConfig.APPLICATION_ID + ".provider", photoFile)
                }
            } else {
                photoURI = Uri.fromFile(photoFile)
            }
            return photoURI
        }

        fun parseDateToddMMyyyy(time: String?): String? {
            val inputPattern = "yyyy-MM-dd'T'HH:mm:ss+00:00"
            val outputPattern = "yyyy-MM-dd"
            val inputFormat = SimpleDateFormat(inputPattern)
            val outputFormat = SimpleDateFormat(outputPattern)
            var date: Date? = null
            var str: String? = null
            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str
        }

        fun getMillSecond(mContext: Context?, audioLocalPath: String?): Int {

            val uri = Uri.parse(audioLocalPath)
            var durationStr = ""
            try {
                val mmr = MediaMetadataRetriever()
                mmr.setDataSource(mContext, uri)
                durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return if (TextUtils.isEmpty(durationStr)) 0 else durationStr.toInt()
        }

        /**
         * Function to convert milliseconds time to
         * Timer Format
         * Hours:Minutes:Seconds
         */
        fun formateMilliSeccond(milliseconds: Long): String? {
            var finalTimerString = ""
            var secondsString = ""

            // Convert total duration into time
            val hours = (milliseconds / (1000 * 60 * 60)).toInt()
            val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
            val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()

            // Add hours if there
            if (hours > 0) {
                finalTimerString = "$hours:"
            }

            // Prepending 0 to seconds if it is one digit
            secondsString = if (seconds < 10) {
                "0$seconds"
            } else {
                "" + seconds
            }
            finalTimerString = "$finalTimerString$minutes:$secondsString"
            //      return  String.format("%02d Min, %02d Sec",
            //                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
            //                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
            //                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));

            // return timer string
            return finalTimerString
        }

        fun parseDateAlertList(Date: String?): String? {
            val CurrentPattern = "yyyy-MM-dd HH:mm:ss"
            val OutputPattern = "dd MMM hh:mm a"
            val sdf = SimpleDateFormat(CurrentPattern, Locale.getDefault())
            return try {
                val startDate: Date = sdf.parse(Date)
                sdf.applyPattern(OutputPattern)
                sdf.format(startDate)
            } catch (e: Exception) {
                ""
            }
        }

        fun capitalize(str: String): String {
            if (TextUtils.isEmpty(str)) {
                return str
            }
            val arr = str.toCharArray()
            var capitalizeNext = true
            var phrase = ""
            for (c in arr) {
                if (capitalizeNext && Character.isLetter(c)) {
                    phrase += Character.toUpperCase(c)
                    capitalizeNext = false
                    continue
                } else if (Character.isWhitespace(c)) {
                    capitalizeNext = true
                }
                phrase += c
            }
            return phrase
        }

        fun getDeviceName(): String? {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else capitalize(manufacturer) + " " + model
        }

        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun filesizecheck(filepath: String?): Long {
            return try {
                val file = File(filepath)
                val fileSizeInBytes = file.length()
                // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
                val fileSizeInKB = fileSizeInBytes / 1024
                // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
                fileSizeInKB / 1024
            } catch (e: Exception) {
                println("File not found : " + e.message + e)
                0
            }
        }


        fun parseDateAlertListNotification(Date: String?): String? {
            val CurrentPattern = "yyyy-MM-dd'T'HH:mm:ss+05:30"
            val OutputPattern = "dd MMM hh:mm a"
            val sdf = SimpleDateFormat(CurrentPattern, Locale.getDefault())
            return try {
                val startDate: Date = sdf.parse(Date)
                sdf.applyPattern(OutputPattern)
                getDateDifference(sdf.format(startDate))
            } catch (e: Exception) {
                ""
            }
        }


        fun getDateDifference(dt: String?): String? {
            val pattern = "dd MMM hh:mm a"
            return try {
                val sdf = SimpleDateFormat(pattern, Locale.getDefault())
                val lastSeenDate: Date = sdf.parse(dt)
                val currentDate: Date = sdf.parse(getCurrentDateTime(pattern))
                var different: Long = currentDate.getTime() - lastSeenDate.getTime()
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24
                val elapsedDays = different / daysInMilli
                different = different % daysInMilli
                val elapsedHours = different / hoursInMilli
                different = different % hoursInMilli
                val elapsedMinutes = different / minutesInMilli
                different = different % minutesInMilli
                val elapsedSeconds = different / secondsInMilli
                if (elapsedDays > 0) if (elapsedDays == 1L) "Yesterday " + parseDate(
                    dt,
                    pattern,
                    "hh:mm a"
                ) else dt else if (elapsedHours > 0) "$elapsedHours Hours ago" else if (elapsedMinutes > 0) "$elapsedMinutes Minutes ago" else if (elapsedSeconds > 0) "Just Now" else dt
            } catch (e: Exception) {
                ""
            }
        }


        fun parseDateAlertListReminder(Date: String?): String? {
            val CurrentPattern = "yyyy-MM-dd'T'HH:mm:ss+05:30"
            val OutputPattern = "dd MMM hh:mm a"
            val sdf = SimpleDateFormat(CurrentPattern, Locale.getDefault())
            return try {
                val startDate: Date = sdf.parse(Date)
                sdf.applyPattern(OutputPattern)
                getDateDifferenceFuture(sdf.format(startDate))
            } catch (e: Exception) {
                ""
            }
        }

        fun getDateDifferenceFuture(dt: String?): String? {
            val pattern = "dd MMM hh:mm a"
            return try {
                val sdf = SimpleDateFormat(pattern, Locale.getDefault())
                val lastSeenDate: Date = sdf.parse(dt)
                val currentDate: Date = sdf.parse(getCurrentDateTime(pattern))

                //            long different = currentDate.getTime() - lastSeenDate.getTime();
                var different: Long = lastSeenDate.getTime() - currentDate.getTime()
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24
                val elapsedDays = different / daysInMilli
                different = different % daysInMilli
                val elapsedHours = different / hoursInMilli
                different = different % hoursInMilli
                val elapsedMinutes = different / minutesInMilli
                different = different % minutesInMilli
                val elapsedSeconds = different / secondsInMilli
                if (elapsedDays > 0) if (elapsedDays == 1L) "tomorrow " + parseDate(
                    dt,
                    pattern,
                    "hh:mm a"
                ) else dt else if (elapsedHours > 0) "After $elapsedHours Hours " else if (elapsedMinutes > 0) "After $elapsedMinutes Minutes" else if (elapsedSeconds > 0) "Just Now" else dt
            } catch (e: Exception) {
                ""
            }
        }

        fun getCurrentDateTime(pattern: String?): String? {
            return SimpleDateFormat(pattern, Locale.getDefault()).format(Date())
        }

        fun parseDate(Date: String?, CurrentPattern: String?, OutputPattern: String?): String {
            val sdf = SimpleDateFormat(CurrentPattern, Locale.getDefault())
            return try {
                val startDate: Date = sdf.parse(Date)
                sdf.applyPattern(OutputPattern)
                sdf.format(startDate)
            } catch (e: Exception) {
                ""
            }
        }


        fun resizeImage(filepath: String?, context: Context, filedir: String?): String? {
            return try {
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val image = BitmapFactory.decodeFile(filepath, options)
                val scaleToUse = 100 // this will be our percentage
                val displayMetrics = DisplayMetrics()
                (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
                val width = displayMetrics.widthPixels
                val sizeY = width * scaleToUse / 100
                val sizeX = image.width * sizeY / image.height
                val resizeImage = getResizedBitmap(image, sizeX, sizeY)
                SaveImage(resizeImage)
            } catch (e: Exception) {
                println(e)
                ""
            }
        }

        fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
            val width = bm.width
            val height = bm.height
            val scaleWidth = newWidth.toFloat() / width
            val scaleHeight = newHeight.toFloat() / height
            // CREATE A MATRIX FOR THE MANIPULATION
            val matrix = Matrix()
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight)

            // "RECREATE" THE NEW BITMAP
            val resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false
            )
            bm.recycle()
            return resizedBitmap
        }


        private fun SaveImage(finalBitmap: Bitmap): String {
            val root = Environment.getExternalStorageDirectory().toString()
            val myDir = File("$root/.nomedia/ServiceCrewUpload")
            if (!myDir.exists()) myDir.mkdirs()
            val generator = Random()
            var n = 10
            n = generator.nextInt(n)
            val fname = "ServiceCrewImage-$n.jpg"
            val file = File(myDir, fname)
            if (file.exists()) file.delete()
            return try {
                val out = FileOutputStream(file)
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 60, out)
                out.flush()
                out.close()
                file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

        @SuppressLint("NewApi")
        fun getCurrentDateandtime():String
        {
            val current = LocalDateTime.now()

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            return current.format(formatter)
        }

        fun showMessageAlert(activity: Activity, isSuccess: Boolean, message: String?) {
            val snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                message!!, Snackbar.LENGTH_LONG
            )
            val textView =
                snackbar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(if (isSuccess) Color.GREEN else Color.RED)
            textView.maxLines = 3
            snackbar.show()
        }

        //getIpAddress
        fun getIpAddress(): String? {
            var IPaddress: String? = ""
            var IPValue: Boolean
            var WIFI = false
            var MOBILE = false
            val CM = Idimain.getContext()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = CM.allNetworkInfo
            for (netInfo in networkInfo) {
                if (netInfo.typeName.equals(
                        "WIFI",
                        ignoreCase = true
                    )
                ) if (netInfo.isConnected) WIFI = true
                if (netInfo.typeName.equals(
                        "MOBILE",
                        ignoreCase = true
                    )
                ) if (netInfo.isConnected) MOBILE = true
            }
           /* if (WIFI == true) {
                IPaddress = GetDeviceipWiFiData()
                Log.e("Ip--------------->", IPaddress!!)
            }*/
            if (MOBILE == true) {
                IPaddress = GetDeviceipMobileData()
                Log.e("Ip--------------->", IPaddress!!)
            }
            return IPaddress
        }

        fun GetDeviceipMobileData(): String? {
            try {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val networkinterface = en.nextElement()
                    val enumIpAddr = networkinterface.inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress) {
                            return inetAddress.hostAddress.toString()
                        }
                    }
                }
            } catch (ex: Exception) {
                Log.e("Current IP", ex.toString())
            }
            return null
        }


        //getIp Address User Mobile
        fun getIPAddress(useIPv4: Boolean): String? {
            try {
                val interfaces: List<NetworkInterface> =
                    Collections.list(NetworkInterface.getNetworkInterfaces())
                for (intf in interfaces) {
                    val addrs: List<InetAddress> = Collections.list(intf.inetAddresses)
                    for (addr in addrs) {
                        if (!addr.isLoopbackAddress) {
                            val sAddr = addr.hostAddress
                            //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                            val isIPv4 = sAddr.indexOf(':') < 0
                            if (useIPv4) {
                                if (isIPv4) return sAddr
                            } else {
                                if (!isIPv4) {
                                    val delim = sAddr.indexOf('%') // drop ip6 zone suffix
                                    return if (delim < 0) sAddr.toUpperCase() else sAddr.substring(
                                        0,
                                        delim
                                    ).toUpperCase()
                                }
                            }
                        }
                    }
                }
            } catch (ex: Exception) {
            } // for now eat exceptions
            return ""
        }


        fun getLatLngDetails(latStr: Double?, lngStr: Double?, context: Context?): Array<String?>? {
            val addressDetails = arrayOfNulls<String>(3)
            if (latStr == null || lngStr == null) {
                return addressDetails
            }
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                val addressList: List<Address>? = geocoder.getFromLocation(latStr, lngStr, 1)
                if (addressList != null && addressList.size > 0) {
                    val address: Address = addressList[0]
                    try {
                        if (address.locality != null) addressDetails[0] =
                            address.locality else addressDetails[0] = address.subAdminArea
                    } catch (e: Exception) {
                        addressDetails[0] = ""
                    }
                    try {
                        addressDetails[1] = address.adminArea
                    } catch (e: Exception) {
                        addressDetails[1] = ""
                    }
                    try {
                        addressDetails[2] = address.postalCode
                    } catch (e: Exception) {
                        addressDetails[2] = ""
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                return addressDetails
            }
            return addressDetails
        }

        fun getDayDifference(fromdate: String?, toDate: String?, pattern: String?): Long {
            return try {
                val sdf = SimpleDateFormat(pattern, Locale.getDefault())
                val lastSeenDate: Date = sdf.parse(getCurrentDateTime(pattern))
                val currentDate: Date = sdf.parse(toDate)
                val different: Long = currentDate.getTime() - lastSeenDate.getTime()
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24
                val elapsedDays = different / daysInMilli
                if (elapsedDays > 0) elapsedDays else if (elapsedDays == 0L) 0 else -1
            } catch (e: Exception) {
                -1
            }
        }

        fun priceWithoutDecimal(price: Double): String? {
            val formatter = DecimalFormat("##,##,##,###")
            return formatter.format(price)
        }


        fun getAddress(context: Context, lat: Double, lng: Double): String? {
            val geocoder = Geocoder(context.getApplicationContext(), Locale.getDefault())
            return try {
                val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
                val obj: Address = addresses[0]
                var add: String = obj.getAddressLine(0)
                add = """
                 $add
                 ${obj.getCountryName()}
                 """.trimIndent()
                add = """
                 $add
                 ${obj.getCountryCode()}
                 """.trimIndent()
                add = """
                 $add
                 ${obj.getAdminArea()}
                 """.trimIndent()
                add = """
                 $add
                 ${obj.getPostalCode()}
                 """.trimIndent()
                add = """
                 $add
                 ${obj.getSubAdminArea()}
                 """.trimIndent()
                add = """
                 $add
                 ${obj.getLocality()}
                 """.trimIndent()
                add = """
                 $add
                 ${obj.getSubThoroughfare()}
                 """.trimIndent()
                var place = ""
                var Area = ""
                var City = ""
                place = if (obj.getThoroughfare() != null) obj.getThoroughfare()
                    .toString() + "," else ""
                Area =
                    if (obj.getSubLocality() != null) obj.getSubLocality().toString() + "," else ""
                City = if (obj.getLocality() != null) obj.getLocality() else ""
                place + Area + City
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                ""
            }
        }

        fun getPumpAddress(context: Context, lat: Double, lng: Double): String? {
            val geocoder = Geocoder(context.getApplicationContext(), Locale.getDefault())
            return try {
                val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
                val obj: Address = addresses[0]
                val add: String = obj.getAddressLine(0)
                //            add = add + "\n" + obj.getCountryName();
                //            add = add + "\n" + obj.getCountryCode();
                //            add = add + "\n" + obj.getAdminArea();
                //            add = add + "\n" + obj.getPostalCode();
                //            add = add + "\n" + obj.getSubAdminArea();
                //            add = add + "\n" + obj.getLocality();
                //            add = add + "\n" + obj.getSubThoroughfare();
                var place = ""
                var Area = ""
                var City = ""
                place = if (obj.getThoroughfare() != null) obj.getThoroughfare()
                    .toString() + "," else ""
                Area =
                    if (obj.getSubLocality() != null) obj.getSubLocality().toString() + "," else ""
                City = if (obj.getLocality() != null) obj.getLocality() else ""
                "$add, $place$Area$City."
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                ""
            }
        }

        /**
         * @param phoneNumber
         * @return
         */
        @SuppressLint("Range")
        fun getContactName(phoneNumber: String?): String? {
            var contactName = ""
            try {
                val uri = Uri.withAppendedPath(
                    ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                    Uri.encode(phoneNumber)
                )
                val projection = arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME)
                val cursor: Cursor? = Idimain.getContext()?.getContentResolver()?.query(uri, projection, null, null, null)
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        contactName =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME))
                    }
                    cursor.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return contactName
        }



        @SuppressLint("NewApi")
        fun getDeviceWidth(activity: Activity): Int {
            val wm = activity.windowManager
            val point = Point()
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                wm.defaultDisplay.getSize(point)
                point.x
            } else {
                wm.defaultDisplay.width
            }
        }

        @SuppressLint("NewApi")
        fun getDeviceHeight(activity: Activity): Int {
            val wm = activity.windowManager
            val point = Point()
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                wm.defaultDisplay.getSize(point)
                point.y
            } else {
                wm.defaultDisplay.height
            }
        }

        fun hasGingerbread(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
        }

        fun hasHoneycomb(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
        }

        fun hasFroyo(): Boolean {
            // Can use static final constants like FROYO, declared in later versions
            // of the OS since they are inlined at compile time. This is guaranteed behavior.
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO
        }

        fun hasKitKat(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        }


        fun hasHoneycombMR1(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1
        }

        fun isMyServiceRunning(mContext: Context, serviceClass: Class<*>): Boolean {
            val manager = mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            if (manager != null) {
                for (service in manager.getRunningServices(Int.MAX_VALUE)) {
                    if (serviceClass.name == service.service.className) {
                        return true
                    }
                }
            }
            return false
        }


        fun isAppRunning(context: Context): Boolean {
            var isInBackground = true
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                val runningProcesses = am.runningAppProcesses
                for (processInfo in runningProcesses) {
                    if (processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        for (activeProcess in processInfo.pkgList) {
                            if (activeProcess == context.getPackageName()) {
                                isInBackground = false
                            }
                        }
                    }
                }
            } else {
                val taskInfo = am.getRunningTasks(1)
                val componentInfo = taskInfo[0].topActivity
                if (componentInfo!!.packageName == context.getPackageName()) {
                    isInBackground = false
                }
            }
            return isInBackground
        }

        fun formatValue(value: Double): String? {
            var value = value
            val power: Int
            val suffix = " kmbt"
            var formattedNumber = ""
            val formatter: NumberFormat = DecimalFormat("#,###.#")
            power = StrictMath.log10(value).toInt()
            value = value / Math.pow(10.0, (power / 3 * 3).toDouble())
            formattedNumber = formatter.format(value)
            formattedNumber = formattedNumber + suffix[power / 3]
            return if (formattedNumber.length > 4) formattedNumber.replace(
                "\\.[0-9]+".toRegex(),
                ""
            ) else formattedNumber
        }

        fun getBitmapfromUrl(imageUrl: String?): Bitmap? {
            return try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input = connection.inputStream
                BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                null
            }
        }

        fun getDateDifferences(dt: String?): String? {
            val pattern = "dd MMM hh:mm a"
            return try {
                val sdf = SimpleDateFormat(pattern, Locale.getDefault())
                val lastSeenDate = sdf.parse(dt)
                val currentDate = sdf.parse(getCurrentDateTime(pattern))
                var different = currentDate.time - lastSeenDate.time
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24
                val elapsedDays = different / daysInMilli
                different = different % daysInMilli
                val elapsedHours = different / hoursInMilli
                different = different % hoursInMilli
                val elapsedMinutes = different / minutesInMilli
                different = different % minutesInMilli
                val elapsedSeconds = different / secondsInMilli
                if (elapsedDays > 0) if (elapsedDays == 1L) "Yesterday " + parseDate(
                    dt,
                    pattern,
                    "hh:mm a"
                ) else dt else if (elapsedHours > 0) "$elapsedHours Hours ago" else if (elapsedMinutes > 0) "$elapsedMinutes Minutes ago" else if (elapsedSeconds > 0) "Just Now" else dt
            } catch (e: Exception) {
                ""
            }
        }


        /*fun showDialog(activity: Activity?, msg: String?) {
            val dialog = Dialog(activity!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog)
            val text = dialog.findViewById<View>(R.id.text_dialog) as TextView
            text.text = msg
            val dialogButton = dialog.findViewById<View>(R.id.btn_dialog) as Button
            dialogButton.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }*/
    }
}