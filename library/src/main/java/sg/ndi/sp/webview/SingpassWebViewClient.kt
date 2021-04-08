/**
 * @author Kenneth Leong
 */

package sg.ndi.sp.webview

import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Message
import android.view.KeyEvent
import android.webkit.ClientCertRequest
import android.webkit.HttpAuthHandler
import android.webkit.RenderProcessGoneDetail
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.webkit.SafeBrowsingResponseCompat
import androidx.webkit.WebResourceErrorCompat
import androidx.webkit.WebViewClientCompat
import sg.ndi.sp.webview.utility.UrlHandler

/**
 * Webview client which will automatically handle Singpass links and launch
 * Singpass app. Extend this class and run the super method in `shouldOverrideUrlLoading`.
 *
 * Example usage below
 * ```java
 * // create your WebViewClient by inheriting from SingpassWebViewClient
 * class CustomWebViewClient extends SingpassWebViewClient {
 *
 *  // For devices api 21 and above
 *  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
 *  @Override
 *  public boolean shouldOverrideUrlLoading(@NonNull WebView view, @NonNull WebResourceRequest request) {
 *      boolean handled = super.shouldOverrideUrlLoading(view, request);
 *
 *      // if handled == true, means that url can be opened by Singpass app
 *      if (handled) {
 *          // return true to signal to webview that url is being handled already
 *          return true;
 *      }
 *      // if handled == false, means that url is NOT a Singpass app url
 *      else {
 *          // do your own handling if needed
 *          ... your own handling code for any other urls ...
 *          // return true if you have handled and urls yourself
 *          // or false if webview should proceed to handle it by default
 *          return false;
 *      }
 * }
 * 
 *  // override this function only if you need to handle devices api 19 to 20
 *  // this will ONLY be called on devices api 20 and below
 *  @Override
 *  public boolean shouldOverrideUrlLoading(@Nullable WebView view, @Nullable String url) {
 *      boolean handled = super.shouldOverrideUrlLoading(view, request);
 * 
 *      // if handled == true, means that url can be opened by Singpass app
 *      if (handled) {
 *          // return true to signal to webview that url is being handled already
 *          return true;
 *      }
 *      // if handled == false, means that url is NOT a Singpass app url
 *      else {
 *          // do your own handling if needed
 *          ... your own handling code for any other urls ...
 *          // return true if you have handled and urls yourself
 *          // or false if webview should proceed to handle it by default
 *          return false;
 *          }
 *      }
 * }
 * 
 *  // in your activity or fragment
 *  WebView webView = findViewById(R.id.wvWebview);
 *  webView.setWebViewClient(new CustomWebViewClient());
 * 
 * ```
 */
open class SingpassWebViewClient : WebViewClientCompat() {

    /*
        Override this function and call `super.shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest)` to detect and handle Singpass Urls
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val uri = request.url
        return UrlHandler.handleableBySingpassApp(uri, view)
    }

    /*
       Override this function and call `shouldOverrideUrlLoading(view: WebView?, url: String?)` to detect and handle Singpass Urls
    */
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return if (view == null || url.isNullOrBlank()) false
        else UrlHandler.handleableBySingpassApp(Uri.parse(url), view)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        super.onLoadResource(view, url)
    }

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        return super.shouldInterceptRequest(view, request)
    }

    override fun onFormResubmission(view: WebView?, dontResend: Message?, resend: Message?) {
        super.onFormResubmission(view, dontResend, resend)
    }

    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
        super.doUpdateVisitedHistory(view, url, isReload)
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
    }

    override fun onReceivedClientCertRequest(view: WebView?, request: ClientCertRequest?) {
        super.onReceivedClientCertRequest(view, request)
    }

    override fun onReceivedHttpAuthRequest(
        view: WebView?,
        handler: HttpAuthHandler?,
        host: String?,
        realm: String?
    ) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm)
    }

    override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
        return super.shouldOverrideKeyEvent(view, event)
    }

    override fun onUnhandledKeyEvent(view: WebView?, event: KeyEvent?) {
        super.onUnhandledKeyEvent(view, event)
    }

    override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
        super.onScaleChanged(view, oldScale, newScale)
    }

    override fun onReceivedLoginRequest(
        view: WebView?,
        realm: String?,
        account: String?,
        args: String?
    ) {
        super.onReceivedLoginRequest(view, realm, account, args)
    }

    override fun onRenderProcessGone(view: WebView?, detail: RenderProcessGoneDetail?): Boolean {
        return super.onRenderProcessGone(view, detail)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onReceivedError(
        view: WebView,
        request: WebResourceRequest,
        error: WebResourceErrorCompat
    ) {
        super.onReceivedError(view, request, error)
    }

    override fun onPageCommitVisible(view: WebView, url: String) {
        super.onPageCommitVisible(view, url)
    }

    override fun onReceivedHttpError(
        view: WebView,
        request: WebResourceRequest,
        errorResponse: WebResourceResponse
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
    }

    override fun onSafeBrowsingHit(
        view: WebView,
        request: WebResourceRequest,
        threatType: Int,
        callback: SafeBrowsingResponseCompat
    ) {
        super.onSafeBrowsingHit(view, request, threatType, callback)
    }
}
