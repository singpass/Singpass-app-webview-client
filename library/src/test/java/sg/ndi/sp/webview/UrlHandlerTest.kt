package sg.ndi.sp.webview

import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import sg.ndi.sp.webview.utility.UrlHandler

@RunWith(AndroidJUnit4::class)
class UrlHandlerTest {

    companion object {
        const val wwwHost = "www."
        const val legacyDomain = "singpassmobile.sg"
        const val currentDomain = "app.singpass.gov.sg"

        const val httpsScheme = "https://"
        const val intentScheme = "intent://"

        const val sgverify = "/sgverify"
        const val qrlogin = "/qrlogin"
        const val docsign = "/docsign"
        const val txnsign = "/txnsign"

        const val httpsSgVerifyV1Postfix = "?client_id=SOME_CLIENT_ID&callback=https://someCallbackUrl.net/webhook&state=27398127"

        const val httpsSgVerifyV2Postfix = "?callback=https://test.api.myinfo.gov.sg/sgverify/v2/callbacksample&client_id=STG2-SGVERIFY-SELF-TEST&nonce=585134253719973&qr_type=static&signature_method=RS256&state=kenn_kiosk001&timestamp_expiry=1762917123000&timestamp_start=1570702210000&v=2&signature=EF02ulbaGUuvgxY05cpYPOzRDPKT4JgQsxccHB2CkB9dXzMshHB3RSDFO7jiMmorVCCyh2Aig92flcjsXu6HmoEFKIoj6+3aBvLGO0Ti8A+mxE1bi7a6cLZTDp9Lv4sLaXGYl1LeV+yuaJqjZLmfFNZLFMmOXk7ZUjx1+qplDk8kPh+sKNpcKEKNNjkgiHpc/xtoRXHfH444rn0LatUoKK0B9NTd4ZYKe9GnvS1EHlMMFRO+y5a4xKPjX3+wmQQXNVsT1i3Nw3tJ66wXiOskZf4Gh9nGEPyVjyX5wflVwJ6JnKAnfLsJgN23D0E5hI8Cm+SPVI0TS3unBCPeJt4fcg=="

        const val intentSgVerifyV1Postfix = "?client_id=SOME_CLIENT_ID&callback=https://someCallbackUrl.net/webhook&state=dmrvUkdytEa5qMsvV0vxXN92TBz1#Intent;scheme=https;package=sg.ndi.sp;S.browser_fallback_url=%s;end"

        const val intentSgVerifyV2Postfix = "?callback=https://test.api.myinfo.gov.sg/sgverify/v2/callbacksample&client_id=STG2-SGVERIFY-SELF-TEST&nonce=585134253719973&qr_type=static&signature_method=RS256&state=kenn_kiosk001&timestamp_expiry=1762917123000&timestamp_start=1570702210000&v=2&signature=EF02ulbaGUuvgxY05cpYPOzRDPKT4JgQsxccHB2CkB9dXzMshHB3RSDFO7jiMmorVCCyh2Aig92flcjsXu6HmoEFKIoj6+3aBvLGO0Ti8A+mxE1bi7a6cLZTDp9Lv4sLaXGYl1LeV+yuaJqjZLmfFNZLFMmOXk7ZUjx1+qplDk8kPh+sKNpcKEKNNjkgiHpc/xtoRXHfH444rn0LatUoKK0B9NTd4ZYKe9GnvS1EHlMMFRO+y5a4xKPjX3+wmQQXNVsT1i3Nw3tJ66wXiOskZf4Gh9nGEPyVjyX5wflVwJ6JnKAnfLsJgN23D0E5hI8Cm+SPVI0TS3unBCPeJt4fcg==#Intent;scheme=https;package=sg.ndi.sp;S.browser_fallback_url=%s;end"

        const val httpsQrloginPostfix = "?&auth_ref=dd6eeef3-1acb-4f4e-9df4-abbee5f90e88"

        const val intentQrloginPostfix = "?&auth_ref=dd6eeef3-1acb-4f4e-9df4-abbee5f90e88#Intent;scheme=https;package=sg.ndi.sp;S.browser_fallback_url=https://app.singpass.gov.sg/qrlogin;end"

        const val httpsDocTxnSignPostFix = "?sign_ref=cb13b714-663b-4c98-a64a-af081a46aac0"

        const val intentTxnSignPostFix = "?sign_ref=cb13b714-663b-4c98-a64a-af081a46aac0#Intent;scheme=https;package=sg.ndi.sp;S.browser_fallback_url=https://app.singpass.gov.sg/txnsign;end"

        const val intentDocSignPostFix = "?sign_ref=cb13b714-663b-4c98-a64a-af081a46aac0#Intent;scheme=https;package=sg.ndi.sp;S.browser_fallback_url=https://app.singpass.gov.sg/docsign;end"

    }

    private fun getFallbackUrl(domain: String, feature: String): String {
        return "$httpsScheme$domain$feature"
    }

    private fun formUrl(scheme: String, host: String = "", domain: String, feature: String, postFix: String): String {
        return "$scheme$host$domain$feature${postFix.format(getFallbackUrl(domain, feature))}"
    }

//    region legacy domain sgverify
    // https scheme
    @Test
    fun root_legacy_sgverify_v1_https_uri_isSingpassQrCode_test() {

        val rootLegacySgverifyV1Url = formUrl(
            scheme = httpsScheme,
            domain = legacyDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV1Postfix
        )

        val uri = Uri.parse(rootLegacySgverifyV1Url)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_legacy_sgverify_v1_https_url_isSingpassQrCode_test() {

        val rootLegacySgverifyV1Url = formUrl(
            scheme = httpsScheme,
            domain = legacyDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV1Postfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(rootLegacySgverifyV1Url))
    }

    @Test
    fun www_legacy_sgverify_v1_https_uri_isSingpassQrCode_test() {

        val wwwLegacySgverifyV1Url = formUrl(
            scheme = httpsScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV1Postfix
        )

        val uri = Uri.parse(wwwLegacySgverifyV1Url)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_legacy_sgverify_v1_https_url_isSingpassQrCode_test() {

        val wwwLegacySgverifyV1Url = formUrl(
            scheme = httpsScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV1Postfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(wwwLegacySgverifyV1Url))
    }

    // intent scheme
    @Test
    fun root_legacy_sgverify_v1_intent_uri_isSingpassQrCode_test() {

        val legacyRootSgVerifyV1IntentUrl = formUrl(
            scheme = intentScheme,
            domain = legacyDomain,
            feature = sgverify,
            postFix = intentSgVerifyV1Postfix
        )
        println(legacyRootSgVerifyV1IntentUrl)
        val uri = Uri.parse(legacyRootSgVerifyV1IntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_legacy_sgverify_v1_intent_url_isSingpassQrCode_test() {

        val legacyRootSgVerifyV1IntentUrl = formUrl(
            scheme = intentScheme,
            domain = legacyDomain,
            feature = sgverify,
            postFix = intentSgVerifyV1Postfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(legacyRootSgVerifyV1IntentUrl))
    }

    @Test
    fun www_legacy_sgverify_v1_intent_uri_isSingpassQrCode_test() {

        val legacyWwwSgVerifyV1IntentUrl = formUrl(
            scheme = intentScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = sgverify,
            postFix = intentSgVerifyV1Postfix
        )

        val uri = Uri.parse(legacyWwwSgVerifyV1IntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_legacy_sgverify_v1_intent_url_isSingpassQrCode_test() {

        val legacyWwwSgVerifyV1IntentUrl = formUrl(
            scheme = intentScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = sgverify,
            postFix = intentSgVerifyV1Postfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(legacyWwwSgVerifyV1IntentUrl))
    }
// endregion

//    region legacy domain sgverify v2
    // https scheme
    @Test
    fun root_legacy_sgverify_v2_https_uri_isSingpassQrCode_test() {

        val rootLegacySgverifyV2Url = formUrl(
            scheme = httpsScheme,
            domain = legacyDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV2Postfix
        )

        val uri = Uri.parse(rootLegacySgverifyV2Url)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_legacy_sgverify_v2_https_url_isSingpassQrCode_test() {

        val rootLegacySgverifyV2Url = formUrl(
            scheme = httpsScheme,
            domain = legacyDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV2Postfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(rootLegacySgverifyV2Url))
    }

    @Test
    fun www_legacy_sgverify_v2_https_uri_isSingpassQrCode_test() {

        val wwwLegacySgverifyV2Url = formUrl(
            scheme = httpsScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV2Postfix
        )

        val uri = Uri.parse(wwwLegacySgverifyV2Url)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_legacy_sgverify_v2_https_url_isSingpassQrCode_test() {

        val wwwLegacySgverifyV2Url = formUrl(
            scheme = httpsScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV2Postfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(wwwLegacySgverifyV2Url))
    }

    // intent scheme
    @Test
    fun root_legacy_sgverify_v2_intent_uri_isSingpassQrCode_test() {

        val legacyRootSgVerifyV2IntentUrl = formUrl(
            scheme = intentScheme,
            domain = legacyDomain,
            feature = sgverify,
            postFix = intentSgVerifyV2Postfix
        )
        println(legacyRootSgVerifyV2IntentUrl)
        val uri = Uri.parse(legacyRootSgVerifyV2IntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_legacy_sgverify_v2_intent_url_isSingpassQrCode_test() {

        val legacyRootSgVerifyV2IntentUrl = formUrl(
            scheme = intentScheme,
            domain = legacyDomain,
            feature = sgverify,
            postFix = intentSgVerifyV2Postfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(legacyRootSgVerifyV2IntentUrl))
    }

    @Test
    fun www_legacy_sgverify_v2_intent_uri_isSingpassQrCode_test() {

        val legacyWwwSgVerifyV2IntentUrl = formUrl(
            scheme = intentScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = sgverify,
            postFix = intentSgVerifyV2Postfix
        )

        val uri = Uri.parse(legacyWwwSgVerifyV2IntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_legacy_sgverify_v2_intent_url_isSingpassQrCode_test() {

        val legacyWwwSgVerifyV2IntentUrl = formUrl(
            scheme = intentScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = sgverify,
            postFix = intentSgVerifyV2Postfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(legacyWwwSgVerifyV2IntentUrl))
    }
// endregion
    
//    region legacy domain qrlogin
    // https scheme
    @Test
    fun root_legacy_qrlogin_https_uri_isSingpassQrCode_test() {

        val rootLegacyQrloginUrl = formUrl(
            scheme = httpsScheme,
            domain = legacyDomain,
            feature = qrlogin,
            postFix = httpsQrloginPostfix
        )

        val uri = Uri.parse(rootLegacyQrloginUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_legacy_qrlogin_https_url_isSingpassQrCode_test() {

        val rootLegacyQrloginUrl = formUrl(
            scheme = httpsScheme,
            domain = legacyDomain,
            feature = qrlogin,
            postFix = httpsQrloginPostfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(rootLegacyQrloginUrl))
    }

    @Test
    fun www_legacy_qrlogin_https_uri_isSingpassQrCode_test() {

        val wwwLegacyQrloginUrl = formUrl(
            scheme = httpsScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = qrlogin,
            postFix = httpsQrloginPostfix
        )

        val uri = Uri.parse(wwwLegacyQrloginUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_legacy_qrlogin_https_url_isSingpassQrCode_test() {

        val wwwLegacyQrloginUrl = formUrl(
            scheme = httpsScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = qrlogin,
            postFix = httpsQrloginPostfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(wwwLegacyQrloginUrl))
    }

    // intent scheme
    @Test
    fun root_legacy_qrlogin_intent_uri_isSingpassQrCode_test() {

        val legacyRootQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = legacyDomain,
            feature = qrlogin,
            postFix = intentQrloginPostfix
        )
        println(legacyRootQrloginIntentUrl)
        val uri = Uri.parse(legacyRootQrloginIntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_legacy_qrlogin_intent_url_isSingpassQrCode_test() {

        val legacyRootQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = legacyDomain,
            feature = qrlogin,
            postFix = intentQrloginPostfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(legacyRootQrloginIntentUrl))
    }

    @Test
    fun www_legacy_qrlogin_intent_uri_isSingpassQrCode_test() {

        val legacyWwwQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = qrlogin,
            postFix = intentQrloginPostfix
        )

        val uri = Uri.parse(legacyWwwQrloginIntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_legacy_qrlogin_intent_url_isSingpassQrCode_test() {

        val legacyWwwQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = qrlogin,
            postFix = intentQrloginPostfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(legacyWwwQrloginIntentUrl))
    }
// endregion

//    region legacy domain docsign
    // https scheme
    @Test
    fun root_legacy_docsign_https_uri_isSingpassQrCode_test() {

        val rootLegacyDocsignUrl = formUrl(
            scheme = httpsScheme,
            domain = legacyDomain,
            feature = docsign,
            postFix = httpsDocTxnSignPostFix
        )

        val uri = Uri.parse(rootLegacyDocsignUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_legacy_docsign_https_url_isSingpassQrCode_test() {

        val rootLegacyDocsignUrl = formUrl(
            scheme = httpsScheme,
            domain = legacyDomain,
            feature = docsign,
            postFix = httpsDocTxnSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(rootLegacyDocsignUrl))
    }

    @Test
    fun www_legacy_docsign_https_uri_isSingpassQrCode_test() {

        val wwwLegacyDocsignUrl = formUrl(
            scheme = httpsScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = docsign,
            postFix = httpsDocTxnSignPostFix
        )

        val uri = Uri.parse(wwwLegacyDocsignUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_legacy_docsign_https_url_isSingpassQrCode_test() {

        val wwwLegacyDocsignUrl = formUrl(
            scheme = httpsScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = docsign,
            postFix = httpsDocTxnSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(wwwLegacyDocsignUrl))
    }

    // intent scheme
    @Test
    fun root_legacy_docsign_intent_uri_isSingpassQrCode_test() {

        val legacyRootQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = legacyDomain,
            feature = docsign,
            postFix = intentDocSignPostFix
        )
        println(legacyRootQrloginIntentUrl)
        val uri = Uri.parse(legacyRootQrloginIntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_legacy_docsign_intent_url_isSingpassQrCode_test() {

        val legacyRootQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = legacyDomain,
            feature = docsign,
            postFix = intentDocSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(legacyRootQrloginIntentUrl))
    }

    @Test
    fun www_legacy_docsign_intent_uri_isSingpassQrCode_test() {

        val legacyWwwQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = docsign,
            postFix = intentDocSignPostFix
        )

        val uri = Uri.parse(legacyWwwQrloginIntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_legacy_docsign_intent_url_isSingpassQrCode_test() {

        val legacyWwwQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = docsign,
            postFix = intentDocSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(legacyWwwQrloginIntentUrl))
    }
// endregion
    
//    region legacy domain txnsign
    // https scheme
    @Test
    fun root_legacy_txnsign_https_uri_isSingpassQrCode_test() {

        val rootLegacyTxnsignUrl = formUrl(
            scheme = httpsScheme,
            domain = legacyDomain,
            feature = txnsign,
            postFix = httpsDocTxnSignPostFix
        )

        val uri = Uri.parse(rootLegacyTxnsignUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_legacy_txnsign_https_url_isSingpassQrCode_test() {

        val rootLegacyTxnsignUrl = formUrl(
            scheme = httpsScheme,
            domain = legacyDomain,
            feature = txnsign,
            postFix = httpsDocTxnSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(rootLegacyTxnsignUrl))
    }

    @Test
    fun www_legacy_txnsign_https_uri_isSingpassQrCode_test() {

        val wwwLegacyTxnsignUrl = formUrl(
            scheme = httpsScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = txnsign,
            postFix = httpsDocTxnSignPostFix
        )

        val uri = Uri.parse(wwwLegacyTxnsignUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_legacy_txnsign_https_url_isSingpassQrCode_test() {

        val wwwLegacyTxnsignUrl = formUrl(
            scheme = httpsScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = txnsign,
            postFix = httpsDocTxnSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(wwwLegacyTxnsignUrl))
    }

    // intent scheme
    @Test
    fun root_legacy_txnsign_intent_uri_isSingpassQrCode_test() {

        val legacyRootQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = legacyDomain,
            feature = txnsign,
            postFix = httpsDocTxnSignPostFix
        )
        println(legacyRootQrloginIntentUrl)
        val uri = Uri.parse(legacyRootQrloginIntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_legacy_txnsign_intent_url_isSingpassQrCode_test() {

        val legacyRootQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = legacyDomain,
            feature = txnsign,
            postFix = intentTxnSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(legacyRootQrloginIntentUrl))
    }

    @Test
    fun www_legacy_txnsign_intent_uri_isSingpassQrCode_test() {

        val legacyWwwQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = txnsign,
            postFix = intentTxnSignPostFix
        )

        val uri = Uri.parse(legacyWwwQrloginIntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_legacy_txnsign_intent_url_isSingpassQrCode_test() {

        val legacyWwwQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = txnsign,
            postFix = intentTxnSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(legacyWwwQrloginIntentUrl))
    }
// endregion

//    region sgverify
    // https scheme
    @Test
    fun root_current_sgverify_v1_https_uri_isSingpassQrCode_test() {

        val rootCurrentSgverifyV1Url = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV1Postfix
        )

        val uri = Uri.parse(rootCurrentSgverifyV1Url)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_current_sgverify_v1_https_url_isSingpassQrCode_test() {

        val rootCurrentSgverifyV1Url = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV1Postfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(rootCurrentSgverifyV1Url))
    }

    @Test
    fun www_current_sgverify_v1_https_uri_isSingpassQrCode_test() {

        val wwwCurrentSgverifyV1Url = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV1Postfix
        )

        val uri = Uri.parse(wwwCurrentSgverifyV1Url)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_current_sgverify_v1_https_url_isSingpassQrCode_test() {

        val wwwCurrentSgverifyV1Url = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV1Postfix
        )

        println(wwwCurrentSgverifyV1Url)

        assertTrue(UrlHandler.isSingpassQrCode(wwwCurrentSgverifyV1Url))
    }

    // intent scheme
    @Test
    fun root_current_sgverify_v1_intent_uri_isSingpassQrCode_test() {

        val currentRootSgVerifyV1IntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = intentSgVerifyV1Postfix
        )
        println(currentRootSgVerifyV1IntentUrl)
        val uri = Uri.parse(currentRootSgVerifyV1IntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_current_sgverify_v1_intent_url_isSingpassQrCode_test() {

        val currentRootSgVerifyV1IntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = intentSgVerifyV1Postfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(currentRootSgVerifyV1IntentUrl))
    }

    @Test
    fun www_current_sgverify_v1_intent_uri_isSingpassQrCode_test() {

        val currentWwwSgVerifyV1IntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = intentSgVerifyV1Postfix
        )

        val uri = Uri.parse(currentWwwSgVerifyV1IntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_current_sgverify_v1_intent_url_isSingpassQrCode_test() {

        val currentWwwSgVerifyV1IntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = intentSgVerifyV1Postfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(currentWwwSgVerifyV1IntentUrl))
    }
// endregion

//    region sgverify v2
    // https scheme
    @Test
    fun root_current_sgverify_v2_https_uri_isSingpassQrCode_test() {

        val rootCurrentSgverifyV2Url = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV2Postfix
        )

        val uri = Uri.parse(rootCurrentSgverifyV2Url)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_current_sgverify_v2_https_url_isSingpassQrCode_test() {

        val rootCurrentSgverifyV2Url = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV2Postfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(rootCurrentSgverifyV2Url))
    }

    @Test
    fun www_current_sgverify_v2_https_uri_isSingpassQrCode_test() {

        val wwwCurrentSgverifyV2Url = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV2Postfix
        )

        val uri = Uri.parse(wwwCurrentSgverifyV2Url)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_current_sgverify_v2_https_url_isSingpassQrCode_test() {

        val wwwCurrentSgverifyV2Url = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV2Postfix
        )

        println(wwwCurrentSgverifyV2Url)

        assertTrue(UrlHandler.isSingpassQrCode(wwwCurrentSgverifyV2Url))
    }

    // intent scheme
    @Test
    fun root_current_sgverify_v2_intent_uri_isSingpassQrCode_test() {

        val currentRootSgVerifyV2IntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = intentSgVerifyV2Postfix
        )
        println(currentRootSgVerifyV2IntentUrl)
        val uri = Uri.parse(currentRootSgVerifyV2IntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_current_sgverify_v2_intent_url_isSingpassQrCode_test() {

        val currentRootSgVerifyV2IntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = intentSgVerifyV2Postfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(currentRootSgVerifyV2IntentUrl))
    }

    @Test
    fun www_current_sgverify_v2_intent_uri_isSingpassQrCode_test() {

        val currentWwwSgVerifyV2IntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = intentSgVerifyV2Postfix
        )

        val uri = Uri.parse(currentWwwSgVerifyV2IntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_current_sgverify_v2_intent_url_isSingpassQrCode_test() {

        val currentWwwSgVerifyV2IntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = sgverify,
            postFix = intentSgVerifyV2Postfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(currentWwwSgVerifyV2IntentUrl))
    }
// endregion

//    region qrlogin
    // https scheme
    @Test
    fun root_current_qrlogin_https_uri_isSingpassQrCode_test() {

        val rootCurrentQrloginUrl = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = qrlogin,
            postFix = httpsQrloginPostfix
        )

        val uri = Uri.parse(rootCurrentQrloginUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_current_qrlogin_https_url_isSingpassQrCode_test() {

        val rootCurrentQrloginUrl = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = qrlogin,
            postFix = httpsQrloginPostfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(rootCurrentQrloginUrl))
    }

    @Test
    fun www_current_qrlogin_https_uri_isSingpassQrCode_test() {

        val wwwCurrentQrloginUrl = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = qrlogin,
            postFix = httpsQrloginPostfix
        )

        val uri = Uri.parse(wwwCurrentQrloginUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_current_qrlogin_https_url_isSingpassQrCode_test() {

        val wwwCurrentQrloginUrl = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = qrlogin,
            postFix = httpsQrloginPostfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(wwwCurrentQrloginUrl))
    }

    // intent scheme
    @Test
    fun root_current_qrlogin_intent_uri_isSingpassQrCode_test() {

        val currentRootQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = qrlogin,
            postFix = intentQrloginPostfix
        )
        println(currentRootQrloginIntentUrl)
        val uri = Uri.parse(currentRootQrloginIntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_current_qrlogin_intent_url_isSingpassQrCode_test() {

        val currentRootQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = qrlogin,
            postFix = intentQrloginPostfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(currentRootQrloginIntentUrl))
    }

    @Test
    fun www_current_qrlogin_intent_uri_isSingpassQrCode_test() {

        val currentWwwQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = qrlogin,
            postFix = intentQrloginPostfix
        )

        val uri = Uri.parse(currentWwwQrloginIntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_current_qrlogin_intent_url_isSingpassQrCode_test() {

        val currentWwwQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = qrlogin,
            postFix = intentQrloginPostfix
        )

        assertTrue(UrlHandler.isSingpassQrCode(currentWwwQrloginIntentUrl))
    }
// endregion

//    region docsign
    // https scheme
    @Test
    fun root_current_docsign_https_uri_isSingpassQrCode_test() {

        val rootCurrentDocsignUrl = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = docsign,
            postFix = httpsDocTxnSignPostFix
        )

        val uri = Uri.parse(rootCurrentDocsignUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_current_docsign_https_url_isSingpassQrCode_test() {

        val rootCurrentDocsignUrl = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = docsign,
            postFix = httpsDocTxnSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(rootCurrentDocsignUrl))
    }

    @Test
    fun www_current_docsign_https_uri_isSingpassQrCode_test() {

        val wwwCurrentDocsignUrl = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = docsign,
            postFix = httpsDocTxnSignPostFix
        )

        val uri = Uri.parse(wwwCurrentDocsignUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_current_docsign_https_url_isSingpassQrCode_test() {

        val wwwCurrentDocsignUrl = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = docsign,
            postFix = httpsDocTxnSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(wwwCurrentDocsignUrl))
    }

    // intent scheme
    @Test
    fun root_current_docsign_intent_uri_isSingpassQrCode_test() {

        val currentRootQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = docsign,
            postFix = intentDocSignPostFix
        )
        println(currentRootQrloginIntentUrl)
        val uri = Uri.parse(currentRootQrloginIntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_current_docsign_intent_url_isSingpassQrCode_test() {

        val currentRootQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = docsign,
            postFix = intentDocSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(currentRootQrloginIntentUrl))
    }

    @Test
    fun www_current_docsign_intent_uri_isSingpassQrCode_test() {

        val currentWwwQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = docsign,
            postFix = intentDocSignPostFix
        )

        val uri = Uri.parse(currentWwwQrloginIntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_current_docsign_intent_url_isSingpassQrCode_test() {

        val currentWwwQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = docsign,
            postFix = intentDocSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(currentWwwQrloginIntentUrl))
    }
// endregion

//    region txnsign
    // https scheme
    @Test
    fun root_current_txnsign_https_uri_isSingpassQrCode_test() {

        val rootCurrentTxnsignUrl = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = txnsign,
            postFix = httpsDocTxnSignPostFix
        )

        val uri = Uri.parse(rootCurrentTxnsignUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_current_txnsign_https_url_isSingpassQrCode_test() {

        val rootCurrentTxnsignUrl = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = txnsign,
            postFix = httpsDocTxnSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(rootCurrentTxnsignUrl))
    }

    @Test
    fun www_current_txnsign_https_uri_isSingpassQrCode_test() {

        val wwwCurrentTxnsignUrl = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = txnsign,
            postFix = httpsDocTxnSignPostFix
        )

        val uri = Uri.parse(wwwCurrentTxnsignUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_current_txnsign_https_url_isSingpassQrCode_test() {

        val wwwCurrentTxnsignUrl = formUrl(
            scheme = httpsScheme,
            domain = currentDomain,
            feature = txnsign,
            postFix = httpsDocTxnSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(wwwCurrentTxnsignUrl))
    }

    // intent scheme
    @Test
    fun root_current_txnsign_intent_uri_isSingpassQrCode_test() {

        val currentRootQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = txnsign,
            postFix = httpsDocTxnSignPostFix
        )
        println(currentRootQrloginIntentUrl)
        val uri = Uri.parse(currentRootQrloginIntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun root_current_txnsign_intent_url_isSingpassQrCode_test() {

        val currentRootQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = txnsign,
            postFix = intentTxnSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(currentRootQrloginIntentUrl))
    }

    @Test
    fun www_current_txnsign_intent_uri_isSingpassQrCode_test() {

        val currentWwwQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = txnsign,
            postFix = intentTxnSignPostFix
        )

        val uri = Uri.parse(currentWwwQrloginIntentUrl)
        assertTrue(UrlHandler.isSingpassQrCode(uri))
    }

    @Test
    fun www_current_txnsign_intent_url_isSingpassQrCode_test() {

        val currentWwwQrloginIntentUrl = formUrl(
            scheme = intentScheme,
            domain = currentDomain,
            feature = txnsign,
            postFix = intentTxnSignPostFix
        )

        assertTrue(UrlHandler.isSingpassQrCode(currentWwwQrloginIntentUrl))
    }
// endregion

//    region negative test for [UrlHandler.isSingpassQrCode]
    @Test
    fun https_url_isSingpassQrCode_negative_test() {

        val currentWwwQrloginIntentUrl = formUrl(
            scheme = httpsScheme,
            host = wwwHost,
            domain = "google.com",
            feature = txnsign,
            postFix = httpsDocTxnSignPostFix
        )

        assertFalse(UrlHandler.isSingpassQrCode(currentWwwQrloginIntentUrl))
    }

    @Test fun getFallbackUrl() {

        val expectedFallbackUrl = getFallbackUrl(currentDomain, sgverify)

        val legacyWwwSgVerifyV1IntentUrl = formUrl(
            scheme = intentScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = sgverify,
            postFix = intentSgVerifyV1Postfix
        )
        println(legacyWwwSgVerifyV1IntentUrl)
        assertEquals(expectedFallbackUrl, UrlHandler.getFallbackUrl(legacyWwwSgVerifyV1IntentUrl))
        val legacyWwwSgVerifyV1IntentUrl_uri = Uri.parse(legacyWwwSgVerifyV1IntentUrl)
        assertEquals(expectedFallbackUrl, UrlHandler.getFallbackUrl(legacyWwwSgVerifyV1IntentUrl_uri))

        val legacyRootSgVerifyV1IntentUrl = formUrl(
            scheme = intentScheme,
            domain = legacyDomain,
            feature = sgverify,
            postFix = intentSgVerifyV1Postfix
        )
        println(legacyRootSgVerifyV1IntentUrl)
        assertEquals(expectedFallbackUrl, UrlHandler.getFallbackUrl(legacyRootSgVerifyV1IntentUrl))
        val legacyRootSgVerifyV1IntentUrl_uri = Uri.parse(legacyRootSgVerifyV1IntentUrl)
        assertEquals(expectedFallbackUrl, UrlHandler.getFallbackUrl(legacyRootSgVerifyV1IntentUrl_uri))

        val legacyWwwSgVerifyV1httpsUrl = formUrl(
            scheme = httpsScheme,
            host = wwwHost,
            domain = legacyDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV1Postfix
        )
        println(legacyWwwSgVerifyV1httpsUrl)
        assertEquals(expectedFallbackUrl, UrlHandler.getFallbackUrl(legacyWwwSgVerifyV1httpsUrl))
        val legacyWwwSgVerifyV1httpsUrl_uri = Uri.parse(legacyWwwSgVerifyV1httpsUrl)
        assertEquals(expectedFallbackUrl, UrlHandler.getFallbackUrl(legacyWwwSgVerifyV1httpsUrl_uri))

        val legacyRootSgVerifyV1HttpsUrl = formUrl(
            scheme = httpsScheme,
            domain = legacyDomain,
            feature = sgverify,
            postFix = httpsSgVerifyV1Postfix
        )
        println(legacyRootSgVerifyV1HttpsUrl)
        assertEquals(expectedFallbackUrl, UrlHandler.getFallbackUrl(legacyRootSgVerifyV1HttpsUrl))
        val legacyRootSgVerifyV1HttpsUrl_uri = Uri.parse(legacyRootSgVerifyV1HttpsUrl)
        assertEquals(expectedFallbackUrl, UrlHandler.getFallbackUrl(legacyRootSgVerifyV1HttpsUrl_uri))
    }
}
