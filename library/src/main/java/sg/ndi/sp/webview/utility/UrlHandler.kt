/**
 * @author Kenneth Leong
 */

package sg.ndi.sp.webview.utility

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import java.net.URISyntaxException
import sg.ndi.sp.webview.BuildConfig.SPM_APP_ID
import sg.ndi.sp.webview.utility.Constants.CHROME_INTENT_FALLBACK_URL
import sg.ndi.sp.webview.utility.Constants.DEFAULT_FALLBACK_URL
import sg.ndi.sp.webview.utility.Constants.HTTPS_SCHEME
import sg.ndi.sp.webview.utility.Constants.INTENT_SCHEME
import sg.ndi.sp.webview.utility.Constants.LEGACY_TRIGGER_URL
import sg.ndi.sp.webview.utility.Constants.LEGACY_WWW_TRIGGER_URL
import sg.ndi.sp.webview.utility.Constants.REQUIRED_PATH_PARAM
import sg.ndi.sp.webview.utility.Constants.TRIGGER_URL

object UrlHandler {

    /**
     * Function that determines if a uri is a Singpass uri
     *
     * @param uri The uri to be tested
     * @return Boolean value true if uri argument is a Singpass uri
     */
    fun isSingpassQrCode(uri: Uri): Boolean {

        val scheme = uri.scheme
        val pathSegments = uri.pathSegments
        val host = uri.host

        if (scheme.isNullOrBlank() || pathSegments.isNullOrEmpty() || host.isNullOrBlank())
            return false

        if (pathSegments.size > 1)
            return false

        val path = pathSegments[0]

        /** if scheme is https:// or intent:// */
        val schemeValid = scheme.equals(INTENT_SCHEME, ignoreCase = true) || scheme.equals(HTTPS_SCHEME, ignoreCase = true)
       
        /** if host is [LEGACY_TRIGGER_URL] or [LEGACY_WWW_TRIGGER_URL] or [TRIGGER_URL] */
        val hostValid = host.equals(LEGACY_TRIGGER_URL, ignoreCase = true) || 
            host.equals(LEGACY_WWW_TRIGGER_URL, ignoreCase = true) || 
            host.equals(TRIGGER_URL, ignoreCase = true)
        
        /** if path is [REQUIRED_PATH_PARAM] */
        val pathValid = path.equals(REQUIRED_PATH_PARAM, ignoreCase = true)
        
        return schemeValid && hostValid && pathValid
    }

    /**
     * Function that determines if a url String is a Singpass url
     *
     * @param url The url string to be tested
     * @return Boolean value true if uri argument is a Singpass url
     */
    fun isSingpassQrCode(url: String): Boolean {
        val uri = Uri.parse(url)
        return isSingpassQrCode(uri)
    }

    /**
     *
     * this function should only be called after checking the uri via [isSingpassQrCode]
     *
     * @param uri the uri to be checked
     * @param context the current activity context
     * @param view the webView which will be loaded witth he fallback url, this argument is nullable
     */
    fun handleSingpassQrCode(uri: Uri, context: Context, view: WebView?) {

        val packageManager = context.packageManager

//            Singpass Mobile Chrome intent scheme
        if (uri.scheme.equals(INTENT_SCHEME, ignoreCase = true)) {

//                Try to parse Singpass Mobile chrome intent URL to get an intent
            try {
                val intent = Intent.parseUri(uri.toString(), Intent.URI_INTENT_SCHEME)

//                    Try to find activity that can handle Singpass Mobile chrome intent
                val info = packageManager.resolveActivity(intent, 0)

//                    Singpass Mobile activity found, launch Singpass Mobile
                if (info != null) {
                    context.startActivity(intent)
                } else {

                    view?.run {
                        val fallbackUrl = intent.getStringExtra(CHROME_INTENT_FALLBACK_URL)
                        if (fallbackUrl.isNullOrBlank())
                            loadUrl(DEFAULT_FALLBACK_URL)
                        else
                            loadUrl(fallbackUrl)
                    }
                }
            } catch (e: URISyntaxException) {
//                Uri parse exception, try to load Singpass app fallback landing page in webview
                view?.loadUrl(DEFAULT_FALLBACK_URL)
            }
        } else {
            val intent = Intent(Intent.ACTION_VIEW, uri)
//                Check if there are activities that can handle
            if (packageManager.resolveActivity(intent, 0) != null) {
                val list = packageManager.queryIntentActivities(intent, 0)
                var spmInstalled = false

//                    Iterate handler activities and filter out SingPass Mobile
                for (info in list) {
                    if (info.activityInfo.packageName.equals(SPM_APP_ID, ignoreCase = true) && info.activityInfo.enabled) {
                        spmInstalled = true
                        break
                    }
                }

//                    If Singpass app found, launch it
                if (spmInstalled) {
                    intent.setPackage(SPM_APP_ID)
                    context.startActivity(intent)
                } else {
                    view?.loadUrl(DEFAULT_FALLBACK_URL)
                }
            } else {
                view?.loadUrl(DEFAULT_FALLBACK_URL)
            }
        }
    }

    /**
     *
     * this function should only be called after checking the uri via [isSingpassQrCode]
     *
     * @param url the url String to be checked
     * @param context the current activity context
     * @param view the webView which will be loaded witth he fallback url, this argument is nullable
     */
    fun handleSingpassQrCode(url: String, context: Context, view: WebView?) {
        val uri = Uri.parse(url)
        handleSingpassQrCode(uri, context, view)
    }

    fun handleableBySingpassApp(uri: Uri, view: WebView): Boolean {

        val isSingpassQrCode = isSingpassQrCode(uri)

        if (!isSingpassQrCode)
            return false

        handleSingpassQrCode(uri, view.context, view)

//            Return true if this function handled the URL
        return true
    }
}
