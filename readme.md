Table of Contents
=================

  * [About this library](#about)
  * [Integration](#Integration)
    * [Production](#prod)
    * [Staging](#staging)
  * [Target SDK Android 11 and above](#android11)
  * [Usage](#usage)
    * [SingpassWebViewClient](#webviewClient)
    * [Utility Functions](#utilityFunctions)
  * [License](#license)

# [About this library](#about)

`SingpassWebViewClient` is an extension of `androidx.webViewClientCompat` that handles opening of Singpass app upon clicking on a supported url.

# Integration

#### [Latest version](#prod)
[ ![version](https://img.shields.io/maven-central/v/io.github.singpass/singpass-webview-client?style=for-the-badge) ](https://repo1.maven.org/maven2/io/github/singpass/singpass-webview-client)

```
implementation "io.github.singpass:singpass-webview-client:<latest version>"
```
or

#### [Latest staging version](#staging)
[ ![version](https://img.shields.io/maven-central/v/io.github.singpass/singpass-webview-client-staging?style=for-the-badge) ](https://repo1.maven.org/maven2/io/github/singpass/singpass-webview-client-staging)
The staging library is currently not working properly if you are currently using the existing staging Singpass app with application id `sg.ndi.sp`.
This staging variant of the library is to prepare for when Singpass staging app can be installed along side the Production Singpass app on the same device.
**DO NOT USE THIS AT THIS POINT OF TIME**

```
implementation "io.github.singpass:singpass-webview-client-staging:<latest version>"
```
# [Targeting SDK 30 (Android 11) and above](#android11)
#### Supporting Android 11's package visiblity changes
Version 1.0.0 targets android SDK 29 (Android 10), if you are not ready to support android 11, please use this version.
Use version the latest version if your application is targeting SDK 30 (Android 11) and above, which has behavioral changes in privacy, more specifically [package visibility](https://developer.android.com/training/package-visibility).

TLDR; Applications targeting Android 11 onwards will require applications to declare specific application id/s that it might be querying the package manager for. Version 1.1.0 will include the `queries` Android manifest element which will be merged into your application's AndroidManifest during the manifest merger phase to enable your application to search for Singpass app to handle app-linking.

# [Usage](#usage)

This section describes what the library provides and how to use it.

### [SingpassWebViewClient](#webviewClient)
---

`SingpassWebViewClient` is extended from `androidx.webViewClientCompat` with the added functionality of detecting Singpass app supported urls and handling of opening up Singpass app or if Singpass app is not installed on the device, loading the fallback url. Below is a code snippet for sample usage.

```java
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
### [Utility Functions](#utilityFunctions)
#### UrlHandler
---
If you prefer to handle the url yourself or your WebViewClient is not able to inherit `SingpassWebViewClient`, you can use the utility functions in `UrlHandler` class instead.

#### UrlHandler.isSingpassQrCode

This utility function takes in either a `Uri` or a `String` and return a `boolean` value indicating if the input is a Singpass app url. Internally `SingpassWebViewClient` uses `isSingpassQrCode`.

#### UrlHandler.handleSingpassQrCode

This utility function takes in either `Uri` or `String`, `Context` and optionally a `Webview`. This will parse the uri or url string and either open up Singpass app, or load the fallback url if Singpass App is not installed (if webview argument is not null). Internally `SingpassWebViewClient` uses `handleSingpassQrCode`. this function should be called after checking if the url is a Singpass supported url by caling `UrlHandler.isSingpassQrCode` first.

#### UrlHandler.getFallbackUrl
This utility function takes in either a `Uri` or a `String` and returns a `String` value of the derived fall back url from the `uri` or `String` url.

### [License](#license)

MIT License

Copyright (c) [2021] [Kennet Leong]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.