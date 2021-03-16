package com.czy.lib_webview;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.czy.lib_base.utils.file.FileUtils;
import com.czy.lib_webview.jsApi.JSGetVersion;
import com.czy.lib_webview.jsBridge.InvokeFunction;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebSettingsExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;
import java.util.Map;

/**
 * @author CuiZhen
 * @date 2019/10/20
 * GitHub: https://github.com/goweii
 */
public class WebHolder {
    private OnPageTitleCallback mOnPageTitleCallback = null;
    private OnPageLoadCallback mOnPageLoadCallback = null;
    private OnPageProgressCallback mOnPageProgressCallback = null;
    private OnHistoryUpdateCallback mOnHistoryUpdateCallback = null;
    private OverrideUrlInterceptor mOverrideUrlInterceptor = null;
    private InterceptUrlInterceptor mInterceptUrlInterceptor = null;

    private final Activity mActivity;
    private final WebContainer mWebContainer;
    private final WebView mWebView;
    private final String mUserAgentString;


    private boolean isProgressShown = false;

    ProgressBar mProgressBar;
    private boolean handleCreateFunctionFinished;//js桥是否注入完成

    public static WebHolder with(Activity activity, WebContainer container, ProgressBar progressBar) {
        return new WebHolder(activity, container, progressBar);
    }

    public static WebHolder with(Activity activity, WebContainer container) {
        return new WebHolder(activity, container, null);
    }

    private static void syncCookiesForWanAndroid(String url) {
//        if (TextUtils.isEmpty(url)) {
//            return;
//        }
//        String host = Uri.parse(url).getHost();
//        if (!TextUtils.equals(host, "www.wanandroid.com")) {
//            return;
//        }
//        List<Cookie> cookies = WanApp.getCookieJar().loadForRequest(HttpUrl.get(url));
//        if (cookies == null || cookies.isEmpty()) {
//            return;
//        }
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            cookieManager.removeSessionCookie();
//            cookieManager.removeExpiredCookie();
//        } else {
//            cookieManager.removeSessionCookies(null);
//        }
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            for (Cookie cookie : cookies) {
//                cookieManager.setCookie(url, cookie.name() + "=" + cookie.value());
//            }
//            CookieSyncManager.createInstance(WanApp.getAppContext());
//            CookieSyncManager.getInstance().sync();
//        } else {
//            for (Cookie cookie : cookies) {
//                cookieManager.setCookie(url, cookie.name() + "=" + cookie.value());
//            }
//            cookieManager.flush();
//        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private WebHolder(Activity activity, WebContainer container, ProgressBar progressBar) {
        activity.getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mActivity = activity;
        mWebContainer = container;
        mWebContainer.setBackgroundResource(R.color.white);
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            QbSdk.forceSysWebView();
        } else {
            QbSdk.unForceSysWebView();
        }
        mWebView = new WebView(activity);
        mWebView.setBackgroundResource(R.color.transparent);
        mWebView.setBackgroundColor(0);
        mWebView.getBackground().setAlpha(0);
        container.addView(mWebView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        mProgressBar = progressBar;
        mProgressBar.setMax(100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
        }
        mWebView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        mWebView.getView().setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWebView.setWebChromeClient(new WanWebChromeClient());
        mWebView.setWebViewClient(new WanWebViewClient());
        WebSettings webSetting = mWebView.getSettings();
        mUserAgentString = webSetting.getUserAgentString();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(false);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSetting.setAppCachePath(activity.getFilesDir().getAbsolutePath()+ File.separator+"h5_cache");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        IX5WebSettingsExtension ext = mWebView.getSettingsExtension();
        if (ext != null) {
            ext.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        }
        nightMode(activity);

        initJs();

    }

    private void initJs() {
        InvokeFunction invokeFunction  = new InvokeFunction(mWebView);
        invokeFunction.addJSApi(new JSGetVersion());
        mWebView.addJavascriptInterface(invokeFunction, "JSBridge");

    }

    private void nightMode(Activity activity) {
        boolean isAppDarkMode = isNightMode(activity);
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            View v = mWebView.getView();
            if (v instanceof android.webkit.WebView) {
                android.webkit.WebView wv = (android.webkit.WebView) v;
                if (isAppDarkMode) {
                    WebSettingsCompat.setForceDark(wv.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
                } else {
                    WebSettingsCompat.setForceDark(wv.getSettings(), WebSettingsCompat.FORCE_DARK_OFF);
                }
            }
        } else {
            if (isAppDarkMode) {
                mWebView.setDayOrNight(false);
            } else {
                mWebView.setDayOrNight(true);
            }
        }
    }
    public static boolean isNightMode(Configuration config) {
        int uiMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return uiMode == Configuration.UI_MODE_NIGHT_YES;
    }

    public static boolean isNightMode(Context context) {
        return isNightMode(context.getResources().getConfiguration());
    }

    public WebHolder loadUrl(String url) {
        mWebView.loadUrl(url);
        return this;
    }

    @NonNull
    public String getUrl() {
        String url = mWebView.getUrl();
        return url == null ? "" : url;
    }

    @NonNull
    public String getTitle() {
        String title = mWebView.getTitle();
        return title == null ? "" : title;
    }

    @NonNull
    public String getUserAgent() {
        return mUserAgentString;
    }

    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    public boolean canGoForward() {
        return mWebView.canGoForward();
    }

    public boolean canGoBackOrForward(int steps) {
        return mWebView.canGoBackOrForward(steps);
    }

    public void goBack() {
        mWebView.goBack();
    }

    public void goForward() {
        mWebView.goForward();
    }

    public void goBackOrForward(int steps) {
        mWebView.goBackOrForward(steps);
    }

    public void reload() {
        mWebView.reload();
    }

    public void stopLoading() {
        mWebView.stopLoading();
    }

    public void onPause() {
        mWebView.onPause();
    }

    public void onResume() {
        mWebView.onResume();
    }

    public void onDestroy() {
        mProgressBar.clearAnimation();
        ViewParent parent = mWebView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(mWebView);
        }
        try {
            mWebView.removeAllViews();
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.destroy();
        } catch (Exception ignore) {
        }
    }

    public boolean handleKeyEvent(int keyCode, KeyEvent keyEvent) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return false;
    }

    public WebHolder setOnLongClickHitTestResult(OnLongClickHitTestResult onLongClickHitTestResult) {
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult hitTestResult = mWebView.getHitTestResult();
                HitResult result = new HitResult(hitTestResult);
                if (onLongClickHitTestResult != null) {
                    return onLongClickHitTestResult.onHitTestResult(result);
                }
                return false;
            }
        });
        return this;
    }

    public WebHolder setOnPageTitleCallback(OnPageTitleCallback onPageTitleCallback) {
        mOnPageTitleCallback = onPageTitleCallback;
        return this;
    }

    public WebHolder setOnPageLoadCallback(OnPageLoadCallback onPageLoadCallback) {
        mOnPageLoadCallback = onPageLoadCallback;
        return this;
    }

    public WebHolder setOnPageProgressCallback(OnPageProgressCallback onPageProgressCallback) {
        mOnPageProgressCallback = onPageProgressCallback;
        return this;
    }

    public WebHolder setOnHistoryUpdateCallback(OnHistoryUpdateCallback onHistoryUpdateCallback) {
        mOnHistoryUpdateCallback = onHistoryUpdateCallback;
        return this;
    }

    public WebHolder setOverrideUrlInterceptor(OverrideUrlInterceptor overrideUrlInterceptor) {
        mOverrideUrlInterceptor = overrideUrlInterceptor;
        return this;
    }

    public WebHolder setInterceptUrlInterceptor(InterceptUrlInterceptor interceptUrlInterceptor) {
        mInterceptUrlInterceptor = interceptUrlInterceptor;
        return this;
    }

    public class WanWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mOnPageTitleCallback != null) {
                mOnPageTitleCallback.onReceivedTitle(title);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress < 95) {
                if (!isProgressShown) {
                    isProgressShown = true;
                    onShowProgress();
                }
                onProgressChanged(newProgress);
            } else {
                onProgressChanged(newProgress);
                if (isProgressShown) {
                    isProgressShown = false;
                    onHideProgress();
                }
            }
        }

        private void onShowProgress() {
            showProgress();
            if (mOnPageProgressCallback != null) {
                mOnPageProgressCallback.onShowProgress();
            }
        }

        private void onProgressChanged(int progress) {
            setProgress(progress);
            if (mOnPageProgressCallback != null) {
                mOnPageProgressCallback.onProgressChanged(progress);
            }
        }

        private void onHideProgress() {
            hideProgress();
            if (mOnPageProgressCallback != null) {
                mOnPageProgressCallback.onHideProgress();
            }
        }

        private void setProgress(int progress) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mProgressBar.setProgress(progress, true);
            } else {
                mProgressBar.setProgress(progress);
            }
        }

        private void showProgress() {
            mProgressBar.animate()
                    .alpha(1F)
                    .setDuration(200)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            mProgressBar.setVisibility(View.VISIBLE);
                            setProgress(0);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).start();
        }

        private void hideProgress() {
            mProgressBar.animate()
                    .alpha(0F)
                    .setDuration(200)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            setProgress(100);
                            mProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    }).start();
        }
    }

    public class WanWebViewClient extends WebViewClient {
        private WebResourceResponse shouldInterceptRequest(@NonNull Uri reqUri,
                                                           @Nullable Map<String, String> reqHeaders,
                                                           @Nullable String reqMethod) {
            syncCookiesForWanAndroid(reqUri.toString());
            if (mInterceptUrlInterceptor == null) {
                return null;
            }
            return mInterceptUrlInterceptor.onInterceptUrl(reqUri, reqHeaders, reqMethod);
        }

        private boolean shouldOverrideUrlLoading(Uri uri) {
            if (mOverrideUrlInterceptor == null) {
                return false;
            } else {
                return mOverrideUrlInterceptor.onOverrideUrl(uri.toString());
            }
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            Uri reqUri = Uri.parse(url);
            WebResourceResponse response = shouldInterceptRequest(reqUri, null, null);
            if (response != null) {
                return response;
            } else {
                return super.shouldInterceptRequest(view, url);
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Uri reqUri = request.getUrl();
            if (reqUri == null) {
                return super.shouldInterceptRequest(view, request);
            }
            Map<String, String> reqHeaders = request.getRequestHeaders();
            String reqMethod = request.getMethod();
            WebResourceResponse response = shouldInterceptRequest(reqUri, reqHeaders, reqMethod);
            if (response != null) {
                return response;
            } else {
                return super.shouldInterceptRequest(view, request);
            }
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request, Bundle bundle) {
            Uri reqUri = request.getUrl();
            if (reqUri == null) {
                return super.shouldInterceptRequest(view, request, bundle);
            }
            Map<String, String> reqHeaders = request.getRequestHeaders();
            String reqMethod = request.getMethod();
            WebResourceResponse response = shouldInterceptRequest(reqUri, reqHeaders, reqMethod);
            if (response != null) {
                return response;
            } else {
                return super.shouldInterceptRequest(view, request, bundle);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return shouldOverrideUrlLoading(Uri.parse(url));
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return shouldOverrideUrlLoading(request.getUrl());
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if(!handleCreateFunctionFinished){
                handleBridgeCallback(view);
            }
            if (mOnPageTitleCallback != null) {
                mOnPageTitleCallback.onReceivedTitle(getUrl());
            }
            if (mOnPageLoadCallback != null) {
                mOnPageLoadCallback.onPageStarted();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (mOnPageTitleCallback != null) {
                mOnPageTitleCallback.onReceivedTitle(getTitle());
            }
            if (mOnPageLoadCallback != null) {
                mOnPageLoadCallback.onPageFinished();
            }
            handleBridgeCallback(view);
        }

        private void handleBridgeCallback(final WebView webView) {
            webView.evaluateJavascript("javascript:window.BROSJSBridge", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.d("WWWWWW", "WebView -- BROSJSBridge -- onReceiveValue  "+s);
                    if ("null".equals(s)) {
                        webView.evaluateJavascript("javascript:" + FileUtils.getAssetsStr(webView.getContext(), "BROSJSBridge.js"), new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                webView.evaluateJavascript("javascript:handleCreateFunction()", new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String s) {
                                        Log.d("WWWWWW", "WebView -- BROSJSBridge -- 注入完成");
                                        handleCreateFunctionFinished = true;
                                    }
                                });
                            }
                        });
                    }
                }
            });
        }


        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
            if (mOnHistoryUpdateCallback != null) {
                mOnHistoryUpdateCallback.onHistoryUpdate(isReload);
            }
        }
    }

    public interface OnLongClickHitTestResult {
        boolean onHitTestResult(@NonNull HitResult result);
    }

    public interface OnPageTitleCallback {
        void onReceivedTitle(@NonNull String title);
    }

    public interface OnPageLoadCallback {
        void onPageStarted();

        void onPageFinished();
    }

    public interface OnPageProgressCallback {
        void onShowProgress();

        void onProgressChanged(int progress);

        void onHideProgress();
    }

    public interface OnHistoryUpdateCallback {
        void onHistoryUpdate(boolean isReload);
    }

    public interface OverrideUrlInterceptor {
        boolean onOverrideUrl(String url);
    }

    public interface InterceptUrlInterceptor {
        @Nullable
        WebResourceResponse onInterceptUrl(@NonNull Uri reqUri,
                                           @Nullable Map<String, String> reqHeaders,
                                           @Nullable String reqMethod);
    }



    public void addJavaScript(){
    }

}