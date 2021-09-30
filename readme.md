Table of Contents
=================

* [About this library](#About-this-library)
* [Integration](#Integration)
    * [Production](#Latest-version)
    * [Staging](#Latest-staging-version)
* [Target SDK Android 11 and above](#Targeting-Sdk-30-Android-11-and-above)
* [Usage](#Usage)
    * [SingpassWebViewClient](#SingpassWebViewClient)
    * [Utility Functions](#Utility-Functions)
* [License](#license)

# About this library

`SingpassWebViewClient` is an extension of `androidx.webViewClientCompat` that handles opening of Singpass app upon clicking on a supported url. (i.e singpassmobile.sg and app.singpass.gov.sg [current supported combinations](https://github.com/singpass/Singpass-app-webview-client/blob/main/library/src/main/java/sg/ndi/sp/webview/utility/Constants.kt) ) To facilitate the transition to the new domain, mobile applications implementing Singpass logins are highly recommended to adopt this library.

# Integration

#### Latest version
[ ![version](https://img.shields.io/maven-central/v/io.github.singpass/singpass-webview-client?style=for-the-badge) ](https://repo1.maven.org/maven2/io/github/singpass/singpass-webview-client)

```
implementation "io.github.singpass:singpass-webview-client:<latest version>"
```
or

#### Latest staging version
[ ![version](https://img.shields.io/maven-central/v/io.github.singpass/singpass-webview-client-staging?style=for-the-badge) ](https://repo1.maven.org/maven2/io/github/singpass/singpass-webview-client-staging)

The staging library is currently not working properly if you are currently using the existing staging Singpass app with application id `sg.ndi.sp`.
This staging variant of the library is to prepare for when Singpass staging app can be installed along side the Production Singpass app on the same device.
**DO NOT USE THIS AT THIS POINT OF TIME**

```
implementation "io.github.singpass:singpass-webview-client-staging:<latest version>"
```
# Targeting SDK 30 (Android 11) and above
#### Supporting Android 11's package visiblity changes
Version 1.0.0 targets android SDK 29 (Android 10), if you are not ready to support android 11, please use this version.
Use version the latest version if your application is targeting SDK 30 (Android 11) and above, which has behavioral changes in privacy, more specifically [package visibility](https://developer.android.com/training/package-visibility).

TLDR; Applications targeting Android 11 onwards will require applications to declare specific application id/s that it might be querying the package manager for. Version 1.1.0 will include the `queries` Android manifest element which will be merged into your application's AndroidManifest during the manifest merger phase to enable your application to search for Singpass app to handle app-linking.

# Usage

This section describes what the library provides and how to use it.

## SingpassWebViewClient

`SingpassWebViewClient` is extended from `androidx.webViewClientCompat` with the added functionality of detecting Singpass app supported urls and handling of opening up Singpass app or if Singpass app is not installed on the device, loading the fallback url. Below is a code snippet for sample usage.

``` java
// create your WebViewClient by inheriting from SingpassWebViewClient
class CustomWebViewClient extends SingpassWebViewClient {

        // For devices api 21 and above
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(@NonNull WebView view, @NonNull WebResourceRequest request) {
            boolean handled = super.shouldOverrideUrlLoading(view, request);

			// if handled == true, means that url can be opened by Singpass app
            if (handled) {
				// return true to signal to webview that url is being handled already
                return true;
            }
			// if handled == false, means that url is NOT a Singpass app url
            else {
                // do your own handling if needed
				... your own handling code for any other urls ...
				// return true if you have handled and urls yourself
				// or false if webview should proceed to handle it by default
                return false;
            }
        }

        // override this function only if you need to handle devices api 19 to 20
		// this will ONLY be called on devices api 20 and below
        @Override
        public boolean shouldOverrideUrlLoading(@Nullable WebView view, @Nullable String url) {
            boolean handled = super.shouldOverrideUrlLoading(view, request);

			// if handled == true, means that url can be opened by Singpass app
            if (handled) {
				// return true to signal to webview that url is being handled already
                return true;
            }
			// if handled == false, means that url is NOT a Singpass app url
            else {
                // do your own handling if needed
				... your own handling code for any other urls ...
				// return true if you have handled and urls yourself
				// or false if webview should proceed to handle it by default
                return false;
            }
        }
    }

// in your activity or fragment
WebView webView = findViewById(R.id.wvWebview);
webView.setWebViewClient(new CustomWebViewClient());

```
## Utility Functions
### UrlHandler
If you prefer to handle the url yourself or your WebViewClient is not able to inherit `SingpassWebViewClient`, you can use the utility functions in `UrlHandler` class instead.

| Function | Arguments | Return value | Description |
|---|---|---|---|
| UrlHandler.handleableBySingpassApp | Uri/String, WebView | Boolean | This utility function takes in either a `Uri` or a `String` and returns a `boolean` value. `true` when Uri/String is a Singpass app QR and is handled or `false` when Uri/String is not a Singpass app QR. Internally `SingpassWebViewClient`uses `Urlhandler.handleableBySingpassApp`. |
| UrlHandler.isSingpassQrCode | Uri/String | Boolean | This utility function takes in either a `Uri` or a `String` and returns a `Boolean` value indicating if the input is a Singpass app QR url or not. This function is called internally by `UrlHandler.handleableBySingpassApp`. |
| UrlHandler.handleSingpassQrCode | Uri/String, Context, Webview | Void | This utility function takes in either a `Uri` or `String`, `Context` and optionally a `Webview`. This will parse the uri or url string and either open up Singpass app, or load the fallback url if Singpass App is not installed (if `Webview` is not null). This function should be called after checking if the url is a Singpass supported url by calling `UrlHandler.isSingpassQrCode` first. This function is called internally by `UrlHandler.handleableBySingpassApp`. |
| UrlHandler.getFallbackUrl | Uri/String | String | This utility function takes in either a `Uri` or a `String` and returns a `String` value of the derived fall back url from input `uri` or `String` url. This function is called internally by `handleableBySingpassApp`. |

## License
[![#LICENSE.txt](https://img.shields.io/github/license/singpass/Singpass-app-webview-client)](https://github.com/singpass/Singpass-app-webview-client/blob/main/LICENSE.txt)