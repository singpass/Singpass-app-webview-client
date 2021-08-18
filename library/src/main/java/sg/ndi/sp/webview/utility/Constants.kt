/**
 * @author Kenneth Leong
 */

package sg.ndi.sp.webview.utility

internal object Constants {
    const val INTENT_SCHEME = "intent"
    const val HTTPS_SCHEME = "https"
    const val LEGACY_TRIGGER_URL = "singpassmobile.sg"
    const val LEGACY_WWW_TRIGGER_URL = "www.singpassmobile.sg"
    const val TRIGGER_URL = "app.singpass.gov.sg"
    val REQUIRED_PATH_PARAMS = arrayOf("qrlogin", "sgverify", "docsign", "txnsign")
    const val CHROME_INTENT_FALLBACK_URL = "browser_fallback_url"
    const val DEFAULT_FALLBACK_URL = "https://app.singpass.gov.sg/qrlogin"
    const val DEFAULT_FALLBACK_BASE_URL = "https://app.singpass.gov.sg/%s"
}

