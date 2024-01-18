package com.mojang.minecraftpe;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import androidx.test.espresso.core.internal.deps.guava.collect.ImmutableList;
//
//import com.android.billingclient.api.AcknowledgePurchaseParams;
//import com.android.billingclient.api.BillingClient;
//import com.android.billingclient.api.BillingClientStateListener;
//import com.android.billingclient.api.BillingFlowParams;
//import com.android.billingclient.api.BillingResult;
//import com.android.billingclient.api.ConsumeParams;
//import com.android.billingclient.api.ConsumeResponseListener;
//import com.android.billingclient.api.ProductDetails;
//import com.android.billingclient.api.ProductDetailsResponseListener;
//import com.android.billingclient.api.Purchase;
//import com.android.billingclient.api.PurchasesUpdatedListener;
//import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinSdk;
import com.appsflyer.adrevenue.adnetworks.AppsFlyerAdNetworkEventType;
import com.appsflyer.adrevenue.adnetworks.generic.Scheme;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdValue;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MediaAspectRatio;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;

import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;
import com.appsflyer.adrevenue.AppsFlyerAdRevenue;
import com.appsflyer.adrevenue.adnetworks.generic.MediationNetwork;

public class Vietnq308Activity {
    private ConsentInformation consentInformation;
    // Use an atomic boolean to initialize the Google Mobile Ads SDK and load ads once.
    private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);

    private static final String TAG = "VIETNQ308";
    private static String ironsourceDevKey = "";
    private static String bannerAdmobId = "";
    private static String nativeAdmobId1 = "";
    private static String nativeAdmobId2 = "";
    private static String nativeAdmobId3 = "";
    private static String nativeAdmobId4 = "";
    private static String nativeAdmobId5 = "";
    private static String openAdsAdmobId = "";
    private static String interAoaAdsAdmobId = "";
    private static String interMediationAdsAdmobId = "";
    private static String interApplovinId = "";
    private static String interIronSourceId = "";
    private AppOpenAd openAdmobAd;
    private static InterstitialAd interAdmobAd;
    private static MaxInterstitialAd interApplovinAd;
    private static boolean isAoaType = false;  // AOA ads = true , inter = false
    private static int interFakeAoaType = 1;  // Admob =1 , Applovin =2, Ironsource =3
    private static boolean isAoaFinish = true; // prevent inter push when Aoa open *POLICY ADS
    private static boolean isInterFinish = true; // prevent inter push when Aoa open *POLICY ADS
    private static int interAdsType = 1;  // Admob =1 , Applovin =2, Ironsource =3
    private static boolean isOnBanner = true;
    private static boolean isOnCollapsible = true;
    private static boolean isOnNative = true;
    private static int nativeRefreshTimer = 100 * 1000;
    private static int nativeCounterTimer = 0;
    private static int nativeRetryTimer = 10000;
    private static boolean isDraggable = true;
    private static boolean isDismissible = true;
    private static int retryAttempt;
    private static int interAdsDelayTimer = 15 * 1000;
    private static int interAdsLoopTimer = 90 * 1000;
    private static double nativeRatioHeightScreen = 0.15;

    private static double nativeRatioWidthScreen = 0.3;

    private static double nativeRatioClick = 1.5;

    private static boolean isFirstOpenApp = true; // prevent inter push when Aoa open *POLICY ADS
    private static int remainingTimer = 30000;
    private static boolean isAppOnBackground = false; // prevent inter push when Aoa open *POLICY ADS
    private  static boolean isCalledOnResumeCreate = false;
    private static int numberRetryOpenAd = 0;
    private static int numberRetryNative = 0;
    private static boolean is_ads_removed = false;
    private MainActivity activity_context;
    private AdView adView;
    private static String jsonDefault = "{\"ironsourceDevKey\":\"X\",\"bannerAdmobId\":\"ca-app-pub-1839004489502882/1454660236\",\"nativeAdmobId1\":\"ca-app-pub-1839004489502882/6516300670\",\"nativeAdmobId2\":\"ca-app-pub-1839004489502882/5403204607\",\"nativeAdmobId3\":\"ca-app-pub-1839004489502882/5366870537\",\"nativeAdmobId4\":\"ca-app-pub-1839004489502882/1975825402\",\"nativeAdmobId5\":\"ca-app-pub-1839004489502882/6545625803\",\"openAdsAdmobId\":\"ca-app-pub-1839004489502882/1800035353\",\"interAoaAdsAdmobId\":\"ca-app-pub-1839004489502882/9913910235\",\"interMediationAdsAdmobId\":\"ca-app-pub-1839004489502882/4625299811\",\"interApplovinId\":\"X\",\"interIronSourceId\":\"X\",\"isAoaType\":false,\"interFakeAoaType\":1,\"isOnBanner\":false,\"isOnCollapsible\":false,\"isOnNative\":true,\"nativeRefreshTimer\":10,\"nativeWidth\":720,\"nativeHeight\":400,\"isDraggable\":true,\"isDismissible\":true,\"interAdsType\":1,\"interAdsDelayTimer\":12,\"interAdsLoopTimer\":180,\"nativeRatioHeightScreen\":0.15,\"nativeRatioWidthScreen\":0.3,\"nativeRatioClick\":1.1}";
    SharedPreferences sharedPreferences;

    private  Dialog adDialog;
    private NativeAd cacheNativeAd1;
    private NativeAd cacheNativeAd2;
    private int lastIndexNative = -1;

    private boolean isRated = false;

    //    private BillingClient billingClient;
//    private PurchasesUpdatedListener purchasesUpdatedListener;
    public Vietnq308Activity(MainActivity mainActivity) {
        this.activity_context = mainActivity;
        fetchingAPIconfig();
    }
    public void fetchingAPIconfig() {
        sharedPreferences = activity_context.getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setTagForUnderAgeOfConsent(false)
                .build();

        consentInformation = UserMessagingPlatform.getConsentInformation(this.activity_context);
        consentInformation.requestConsentInfoUpdate(
                this.activity_context,
                params,
                (ConsentInformation.OnConsentInfoUpdateSuccessListener) () -> {
                    UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                            this.activity_context,
                            (ConsentForm.OnConsentFormDismissedListener) loadAndShowError -> {
                                if (loadAndShowError != null) {
                                    // Consent gathering failed.
                                    Log.w(TAG, String.format("%s: %s",
                                            loadAndShowError.getErrorCode(),
                                            loadAndShowError.getMessage()));
                                }

                                // Consent has been gathered.
                                if (consentInformation.canRequestAds()) {
                                    initializeMobileAdsSdk();
                                }
                            }
                    );
                },
                (ConsentInformation.OnConsentInfoUpdateFailureListener) requestConsentError -> {
                    // Consent gathering failed.
                    Log.w(TAG, String.format("%s: %s",
                            requestConsentError.getErrorCode(),
                            requestConsentError.getMessage()));
                    initializeMobileAdsSdk();
                });

        // Check if you can initialize the Google Mobile Ads SDK in parallel
        // while checking for new consent information. Consent obtained in
        // the previous session can be used to request ads.
        if (consentInformation.canRequestAds()) {
            initializeMobileAdsSdk();
        }
    }

    private void initializeMobileAdsSdk() {
//        setupBilling();
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return;
        }
//        isRated = sharedPreferences.getBoolean("isRated", false);
//        if (!isRated) {
//            Observable.interval(1000,180000, TimeUnit.MILLISECONDS)
//                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new Observer<Long>() {
//                        @Override
//                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {}
//                        @Override
//                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {}
//                        @Override
//                        public void onComplete() {}
//                        @Override
//                        public void onNext(@io.reactivex.rxjava3.annotations.NonNull Long aLong) {
//                            if (!isRated) {
//                                Log.i("VIETNQ308", "RATING SHOW");
//                                showRatingDialog();
//                            }
//                        }
//                    });
//        }

//        AppsFlyerAdRevenue.Builder afRevenueBuilder = new AppsFlyerAdRevenue.Builder(activity_context.getApplication());
//        AppsFlyerAdRevenue.initialize(afRevenueBuilder.build());

        String apiUrl = "https://craft.gafugame.com/api/rmc_k.jsp?access-key=316def69-420c-42e6-aa6d-c8c47e1ad5ea&key=test_id";
        Volley.newRequestQueue(this.activity_context).add(new StringRequest(Request.Method.GET, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "===============API REQUEST SUCCESS=================\n" );
                        parseAPIResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "===============API REQUEST FAILED=================\n" );
                        Log.i(TAG, "===============SEVER DOWN ALERT=================\n" );
                        String retrievedJsonString = sharedPreferences.getString("json_default", jsonDefault);
                        if (retrievedJsonString != null) {
                            parseAPIResponse(retrievedJsonString);
                            Log.i(TAG, "PARSE OLD DATA " +  retrievedJsonString);
                        } else {
                            parseAPIResponse(jsonDefault);
                            Log.i(TAG, "PARSE DEFAULT DATA " +  jsonDefault);
                        }
                    }
                }));
    }

//    private void setupBilling() {
//        boolean isRemovedAds = sharedPreferences.getBoolean("is_ads_removed", false);
//        is_ads_removed = isRemovedAds;
//        try {
//            purchasesUpdatedListener = (billingResult, purchases) -> {
//                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
//                        && purchases != null) {
//                    for (Purchase purchase : purchases) {
//                        handlePurchase(purchase);
//                    }
//                } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
//                    Log.i(TAG, "BillingClient.BillingResponseCode.USER_CANCELED");
//                } else {
//                    // Handle any other error codes.
//                }
//            };
//
//            billingClient = BillingClient.newBuilder(activity_context)
//                    .setListener(purchasesUpdatedListener)
//                    .enablePendingPurchases()
//                    .build();
//
//            billingClient.startConnection(new BillingClientStateListener() {
//                @Override
//                public void onBillingSetupFinished(BillingResult billingResult) {
//                    if (billingResult.getResponseCode() ==  BillingClient.BillingResponseCode.OK) {
//                        checkSubscribed();
//                        Log.i(TAG, "BillingClient.BillingResponseCode.OK");
//                    }
//                }
//                @Override
//                public void onBillingServiceDisconnected() {
//                    Log.i(TAG, "onBillingServiceDisconnected========ERROR");
//                }
//            });
//        } catch (Exception exception) {
//            Log.i(TAG, String.valueOf(exception));
//        }
//    }
//    private void checkSubscribed() {
//        if (billingClient.isReady()) {
//            billingClient.queryPurchasesAsync(BillingClient.SkuType.SUBS, (billingResult, purchasesList) -> {
//                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
//                        && purchasesList != null) {
//                    for (Purchase purchase : purchasesList) {
//                        if ("one_month".equals(purchase.getSkus().get(0)) && purchase.isAutoRenewing()) {
//                            stopShowingAds();
//                        } else {
//                            if (!is_ads_removed) {
//                                SharedPreferences.Editor editor = activity_context.getSharedPreferences("sharedPreferences", MODE_PRIVATE).edit();
//                                editor.putBoolean("is_ads_removed", false);
//                                editor.apply();
//                                initializeAdsWithApi();
//                            }
//                        }
//                    }
//                    if (purchasesList.isEmpty()) {
//                        is_ads_removed = false;
//                        SharedPreferences.Editor editor = activity_context.getSharedPreferences("sharedPreferences", MODE_PRIVATE).edit();
//                        editor.putBoolean("is_ads_removed", false);
//                        editor.apply();
//                        initializeAdsWithApi();
//                    }
//                }
//            });
//        }
//    }
//    private void callSubscribe() {
//        QueryProductDetailsParams queryProductDetailsParams =
//                QueryProductDetailsParams.newBuilder()
//                        .setProductList(ImmutableList.of(
//                                        QueryProductDetailsParams.Product.newBuilder()
//                                                .setProductId("one_month")
//                                                .setProductType(BillingClient.ProductType.SUBS)
//                                                .build()))
//                        .build();
//
//        billingClient.queryProductDetailsAsync(
//                queryProductDetailsParams,
//                (billingResult, productDetailsList) -> {
//                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
//                            && !productDetailsList.isEmpty()) {
//                        ProductDetails productDetails = productDetailsList.get(0);
//
//                        try {
//                            BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
//                                    .setProductDetailsParamsList(ImmutableList.of(
//                                            BillingFlowParams.ProductDetailsParams.newBuilder()
//                                                    .setOfferToken(productDetails.getSubscriptionOfferDetails().get(0).getOfferToken())
//                                                    .setProductDetails(productDetails)
//                                                    .build()))
//                                    .build();
//                            billingClient.launchBillingFlow(activity_context, billingFlowParams);
//                        } catch (Exception exception) {
//                            Log.i(TAG, String.valueOf(exception));
//                        }
//
//                    } else {
//                        Log.e(TAG, "Product not found or error: " + billingResult.getDebugMessage());
//                    }
//                }
//        );
//    }
//    void handlePurchase(Purchase purchase) {
//        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
//            if ("one_month".equals(purchase.getSkus().get(0))) {
//                if (!purchase.isAcknowledged()) {
//                    AcknowledgePurchaseParams acknowledgePurchaseParams =
//                            AcknowledgePurchaseParams.newBuilder()
//                                    .setPurchaseToken(purchase.getPurchaseToken())
//                                    .build();
//
//                    billingClient.acknowledgePurchase(acknowledgePurchaseParams, billingResult -> {
//                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                            Log.i(TAG, "CONFIRM BOUGHT");
//                            SharedPreferences.Editor editor = activity_context.getSharedPreferences("sharedPreferences", MODE_PRIVATE).edit();
//                            editor.putBoolean("is_ads_removed", true);
//                            editor.apply();
//                            stopShowingAds();
//                        }
//                    });
//                }
//            }
//        }
//    }

//    void stopShowingAds() {
//        is_ads_removed = true;
//        adDialog.dismiss();
//    }
    private void parseAPIResponse(String response) {
        try {
            JSONObject jsonResult = new JSONObject(response);
            openAdsAdmobId = jsonResult.getString("openAdsAdmobId");
            bannerAdmobId = jsonResult.getString("bannerAdmobId");

            nativeAdmobId1  = jsonResult.getString("nativeAdmobId1");
            nativeAdmobId2  = jsonResult.getString("nativeAdmobId2");
            nativeAdmobId3  = jsonResult.getString("nativeAdmobId3");
            nativeAdmobId4  = jsonResult.getString("nativeAdmobId4");
            nativeAdmobId5  = jsonResult.getString("nativeAdmobId5");

            interAoaAdsAdmobId = jsonResult.getString("interAoaAdsAdmobId");
            interMediationAdsAdmobId = jsonResult.getString("interMediationAdsAdmobId");
            interApplovinId = jsonResult.getString("interApplovinId");
            interIronSourceId = jsonResult.getString("interIronSourceId");
            isAoaType = jsonResult.getBoolean("isAoaType");
            interFakeAoaType = jsonResult.getInt("interFakeAoaType");
            interAdsType = jsonResult.getInt("interAdsType");
            isOnBanner =  jsonResult.getBoolean("isOnBanner");
            isOnCollapsible = jsonResult.getBoolean("isOnCollapsible");
            isOnNative = jsonResult.getBoolean("isOnNative");
            isDraggable = jsonResult.getBoolean("isDraggable");
            isDismissible = jsonResult.getBoolean("isDismissible");
            nativeRefreshTimer  = jsonResult.getInt("nativeRefreshTimer") * 1000;
            interAdsDelayTimer = jsonResult.getInt("interAdsDelayTimer") * 1000;
            interAdsLoopTimer = jsonResult.getInt("interAdsLoopTimer") * 1000;
            nativeRatioHeightScreen = jsonResult.getDouble("nativeRatioHeightScreen");
            nativeRatioWidthScreen = jsonResult.getDouble("nativeRatioWidthScreen");
            nativeRatioClick = jsonResult.getDouble("nativeRatioClick");
            Log.i(TAG,"ADD DATA LOG : |||||||||||||||| " + response);
            initializeAdsWithApi();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("json_default", response);
            Log.i(TAG, "SAVE LOG : |||||||||||||||| " + response );
            editor.apply();
        } catch (Exception e) {
            String a = "{\"ironsourceDevKey\":\"X\",\"bannerAdmobId\":\"ca-app-pub-7045106961777391/8688589325\",\"nativeAdmobId1\":\"ca-app-pub-7045106961777391/8784846145\",\"nativeAdmobId2\":\"ca-app-pub-7045106961777391/8784846145\",\"nativeAdmobId3\":\"ca-app-pub-7045106961777391/8784846145\",\"nativeAdmobId4\":\"ca-app-pub-7045106961777391/8784846145\",\"nativeAdmobId5\":\"ca-app-pub-7045106961777391/8784846145\",\"openAdsAdmobId\":\"ca-app-pub-7045106961777391/3612410515\",\"interAoaAdsAdmobId\":\"ca-app-pub-7045106961777391/3754475772\",\"interMediationAdsAdmobId\":\"ca-app-pub-7045106961777391/7375507655\",\"interApplovinId\":\"X\",\"interIronSourceId\":\"X\",\"isAoaType\":false,\"interFakeAoaType\":1,\"isOnBanner\":false,\"isOnCollapsible\":false,\"isOnNative\":true,\"nativeRefreshTimer\":30,\"nativeWidth\":720,\"nativeHeight\":400,\"isDraggable\":true,\"isDismissible\":true,\"interAdsType\":1,\"interAdsDelayTimer\":12,\"interAdsLoopTimer\":189,\"nativeRatioHeightScreen\":0.15,\"nativeRatioWidthScreen\":0.4,\"nativeRatioClick\":1.4}";
            Log.i(TAG, "===============FAILED TO PARSE DATA=================\n" + e );
            String retrievedJsonString = sharedPreferences.getString("json_default", jsonDefault);
            if (retrievedJsonString != null) {
                parseAPIResponse(retrievedJsonString);
                Log.i(TAG, "PARSE OLD DATA " +  retrievedJsonString);
            } else {
                parseAPIResponse(jsonDefault);
                Log.i(TAG, "PARSE DEFAULT DATA " +  jsonDefault);
            }
        }
    }

    private void initializeAdsWithApi() {
        if (is_ads_removed) {
            return;
        }
        if (isAoaType == true || interAdsType == 1 || interFakeAoaType == 1) {
            initializeAdmobAds();
        }
        if (interAdsType == 2 || interFakeAoaType == 2) {
//            initializeApplovinAds();
        }
        if (interAdsType == 3 || interFakeAoaType == 3) {
//            init ironsource
        }
    }

    private void initializeAdmobAds() {
        MobileAds.initialize(this.activity_context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.i(TAG, "===============INIT ADMOB SUCCESS=================\n" );
            }
        });
        loadOpenAdmobAds();
        loadInterAdmobAds();

        if (isOnBanner) {
            createBannerAd();
        } else if (isOnNative) {
            showNativeAdDialog();
        }
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                showInterAdmobAd();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        showInterAdmobAd();
//                        handler.postDelayed(this, interAdsLoopTimer);
//                    }
//                }, interAdsLoopTimer);
//            }
//        }, interAdsDelayTimer);
        Observable.interval(5000,5000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {}
                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {}
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Long aLong) {
                        remainingTimer = remainingTimer- 5000;
                        Log.i(TAG, String.valueOf(remainingTimer));
                        if (remainingTimer < 0 && !isAppOnBackground  && isAoaFinish && isInterFinish && !is_ads_removed){
                            Completable.timer(1L, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.mojang.minecraftpe.ISC.6
                                @Override // io.reactivex.rxjava3.functions.Action
                                public final void run() {
                                    Log.i(TAG, "===============INTER INCOMING SHOW=================\n" );
                                    showInterAdmobAd();
                                }
                            });
                        }
                    }
                });
    }

    private void createBannerAd() {
        if (is_ads_removed) {
            return;
        }
        adDialog = new Dialog(activity_context);
        adDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        adDialog.setContentView(R.layout.dialog_ad_layout);
        Window dialogWindow = adDialog.getWindow();
        if (dialogWindow != null) {
            dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialogWindow.getAttributes());
            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
            layoutParams.dimAmount = 0;
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            dialogWindow.setAttributes(layoutParams);
        }
        adView = new AdView(activity_context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(bannerAdmobId);
        FrameLayout adContainerX = adDialog.findViewById(R.id.adContainerX);
        adContainerX.removeAllViews();
        adContainerX.addView(adView);
        Bundle extras = new Bundle();
        if (isOnCollapsible) {
            extras.putString("collapsible", "top");
        }
        AdRequest adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build();

        adView.loadAd(adRequest);
//        adView.setOnPaidEventListener(new OnPaidEventListener() {
//            @Override
//            public void onPaidEvent(@NonNull AdValue adValue) {
//                Map<String, String> customParams = new HashMap<>();
//                customParams.put(Scheme.AD_UNIT, bannerAdmobId);
//                customParams.put(Scheme.AD_TYPE, AppsFlyerAdNetworkEventType.BANNER.toString());
//                customParams.put("ad_platform", "Admob");
//                customParams.put("value", String.valueOf(adValue.getValueMicros()));
//
//                // Actually recording a single impression
//                AppsFlyerAdRevenue.logAdRevenue(
//                        "admob",
//                        MediationNetwork.googleadmob,
//                        Currency.getInstance(Locale.US),
//                        (double) adValue.getValueMicros(),
//                        customParams
//                );
//            }
//        });
        adDialog.setCancelable(false);
        adDialog.show();
//        noAdsButtonSetup();
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {}

            @Override
            public void onAdClosed() {}

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e("VIETNQ308", "================================= BANNER is FAILED"+ adError);
            }

            @Override
            public void onAdImpression() {}

            @Override
            public void onAdLoaded() {
                Log.e("VIETNQ308", "================================= BANNER is LOADED");
            }

            @Override
            public void onAdOpened() {
                Log.e("VIETNQ308", "================================= BANNER is OPENED");
            }
        });
    }

    private void showNativeAdDialog() {
        if (is_ads_removed) {
            return;
        }
        loadNativeAd1();
        loadNativeAd2();
        Observable.interval(1000,5000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {}
                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {}
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Long aLong) {
                        Log.i("VIETNQ308", "NATIVE COUNTER ==========   "+ String.valueOf(nativeCounterTimer) + " || native1 status: " + (cacheNativeAd1==null ? "null" : "available") + " || native2 status: " + (cacheNativeAd2==null ? "null" : "available"));
                        nativeCounterTimer = nativeCounterTimer - 5000;
                        if (nativeCounterTimer < 0 && !is_ads_removed) {
                            if (cacheNativeAd1 != null) {
                                Log.i("VIETNQ308", "SHOW NATIVE 1");
                                showNativeDialog(cacheNativeAd1);
                            } else if (cacheNativeAd2 != null) {
                                Log.i("VIETNQ308", "SHOW NATIVE 2");
                                showNativeDialog(cacheNativeAd2);
                            } else if (nativeCounterTimer == -310000) {
                                numberRetryNative = 2;
                                loadNativeAd1();
                                loadNativeAd2();
                            }
                        }
                    }
                });
    }

    public String pickRandomNative() {
        List<String> listNative = new ArrayList<>();
        listNative.add(nativeAdmobId1);
        listNative.add(nativeAdmobId2);
        listNative.add(nativeAdmobId3);
        listNative.add(nativeAdmobId4);
        listNative.add(nativeAdmobId5);

        if (lastIndexNative == -1) {
            Random random = new Random();
            lastIndexNative = random.nextInt(5);
        }
        String nativePicked = listNative.get(lastIndexNative);
        Log.i("VIETNQ308", "RANDOM ID : " + nativePicked + " || NO: " + lastIndexNative);

        if (lastIndexNative >= 4) {
            lastIndexNative = 0;
        } else {
            lastIndexNative = lastIndexNative +1 ;
        }
        return nativePicked;
    }
    private void loadNativeAd1() {
        if (cacheNativeAd1 == null && numberRetryNative <= 3) {
            String nativeId = pickRandomNative();
            AdLoader adLoader = new AdLoader.Builder(activity_context, nativeId)
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            nativeAd.setOnPaidEventListener(new OnPaidEventListener() {
                                @Override
                                public void onPaidEvent(@NonNull AdValue adValue) {
                                    Map<String, String> customParams = new HashMap<>();
                                    customParams.put(Scheme.AD_UNIT, nativeId);
                                    customParams.put(Scheme.AD_TYPE, AppsFlyerAdNetworkEventType.NATIVE.toString());
                                    customParams.put("ad_platform", "Admob");
                                    customParams.put("value", String.valueOf(adValue.getValueMicros()));

                                    // Actually recording a single impression
                                    AppsFlyerAdRevenue.logAdRevenue(
                                            "admob",
                                            MediationNetwork.googleadmob,
                                            Currency.getInstance(Locale.US),
                                            (double) adValue.getValueMicros(),
                                            customParams
                                    );
                                }
                            });

                            numberRetryNative = 0;
                            nativeRetryTimer = 10000;
                            cacheNativeAd1 = nativeAd;
                            Log.i("VIETNQ308", "LOADED NATIVE1 SUCCESSFULLY");
                        }
                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(LoadAdError adError) {
                            Log.e("VIETNQ308", "FAILED LOAD NATIVE1"+ String.valueOf(adError));
                            numberRetryNative = numberRetryNative +1;
                            if (numberRetryNative <= 3) {
                                nativeRetryTimer = 10000*numberRetryNative;
                                Log.e("VIETNQ308", "NATIVE1 LOAD AFTER: "+ String.valueOf(nativeRetryTimer) + "TIMES:" +String.valueOf(numberRetryNative));
                            } else {
                                nativeRetryTimer = 300000;
                                Log.e("VIETNQ308", "NATIVE1 LOAD AFTER: "+ String.valueOf(nativeRetryTimer) + "TIMES:" +String.valueOf(numberRetryNative));
                            }
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadNativeAd1();
                                }
                            }, nativeRetryTimer);
                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder().setMediaAspectRatio(MediaAspectRatio.LANDSCAPE).build()).build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }
    }

    private void loadNativeAd2() {
        if (cacheNativeAd2 == null && numberRetryNative <= 3) {
            String nativeId = pickRandomNative();
            AdLoader adLoader = new AdLoader.Builder(activity_context, nativeId)
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            nativeAd.setOnPaidEventListener(new OnPaidEventListener() {
                                @Override
                                public void onPaidEvent(@NonNull AdValue adValue) {
                                    Map<String, String> customParams = new HashMap<>();
                                    customParams.put(Scheme.AD_UNIT, nativeId);
                                    customParams.put(Scheme.AD_TYPE, AppsFlyerAdNetworkEventType.NATIVE.toString());
                                    customParams.put("ad_platform", "Admob");
                                    customParams.put("value", String.valueOf(adValue.getValueMicros()));

                                    // Actually recording a single impression
                                    AppsFlyerAdRevenue.logAdRevenue(
                                            "admob",
                                            MediationNetwork.googleadmob,
                                            Currency.getInstance(Locale.US),
                                            (double) adValue.getValueMicros(),
                                            customParams
                                    );
                                }
                            });

                            numberRetryNative = 0;
                            nativeRetryTimer = 10000;
                            cacheNativeAd2 = nativeAd;
                            Log.i("VIETNQ308", "LOADED NATIVE2 SUCCESSFULLY");
                        }
                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(LoadAdError adError) {
                            Log.e("VIETNQ308", "FAILED LOAD NATIVE2"+ String.valueOf(adError));
                            numberRetryNative = numberRetryNative +1;
                            if (numberRetryNative <= 3) {
                                nativeRetryTimer = 10000*numberRetryNative;
                                Log.e("VIETNQ308", "NATIVE2 LOAD AFTER: "+ String.valueOf(nativeRetryTimer)+ "TIMES:" +String.valueOf(numberRetryNative));
                            } else {
                                nativeRetryTimer = 300000;
                                Log.e("VIETNQ308", "NATIVE2 LOAD AFTER: "+ String.valueOf(nativeRetryTimer) + "TIMES:" +String.valueOf(numberRetryNative));
                            }
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadNativeAd2();
                                }
                            }, nativeRetryTimer);
                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder().setMediaAspectRatio(MediaAspectRatio.LANDSCAPE).build()).build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }
    }

    private void showNativeDialog(NativeAd nativeAd) {
        try {
            if (adDialog != null) {
                adDialog.dismiss();
            }
            Log.i("VIETNQ308",   "LOADED ! START LOADING NATIVE SETUP:" + nativeAd);
            adDialog = new Dialog(activity_context);
            adDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            adDialog.setContentView(R.layout.dialog_ad_layout);
            Window dialogWindow = adDialog.getWindow();

            if (dialogWindow != null) {
                dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialogWindow.getAttributes());
                layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                layoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                layoutParams.dimAmount = 0;
                layoutParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                layoutParams.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
                dialogWindow.setAttributes(layoutParams);
            }
            WindowManager windowManager = (WindowManager) activity_context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;

            View myButton = adDialog.findViewById(R.id.my_button);

            myButton.getLayoutParams().height = (int) (screenHeight*nativeRatioHeightScreen* nativeRatioClick);
            myButton.getLayoutParams().width = (int) (screenWidth*nativeRatioWidthScreen* nativeRatioClick);
            myButton.requestLayout();

            TemplateView template = adDialog.findViewById(R.id.my_template);
            ColorDrawable background = new ColorDrawable(0x00FFFFFF);
            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();
            template.setStyles(styles);
            template.setNativeAd(nativeAd);

            NativeAdView nativeAdView = template.getNativeAdView();
            nativeAdView.setImageView(template.findViewById(com.google.android.ads.nativetemplates.R.id.icon));
            Random random = new Random();
            if (random.nextInt(2) == 1) {
                nativeAdView.setAdvertiserView(myButton);
            } else {
                nativeAdView.setBodyView(myButton);
            }

            template.getLayoutParams().height = (int) (screenHeight*nativeRatioHeightScreen);
            template.getLayoutParams().width = (int) (screenWidth*nativeRatioWidthScreen);

            template.requestLayout();

//            noAdsButtonSetup();
            if (isAoaFinish) {
                adDialog.show();
            }
            nativeCounterTimer = nativeRefreshTimer;
            if (nativeAd == cacheNativeAd1) {
                cacheNativeAd1 = null;
                Log.i(TAG, "CLEAR NATIVE 1" );
                loadNativeAd1();
            } else {
                cacheNativeAd2 = null;
                Log.i(TAG, "CLEAR NATIVE 2" );
                loadNativeAd1();
            }
        } catch (Exception e) {
            Log.e("VIETNQ308", e + "========= EROROROORR");
        }
    }
//
//    public void showRatingDialog() {
//        ReviewManager manager = ReviewManagerFactory.create(activity_context);
//        Task<ReviewInfo> request = manager.requestReviewFlow();
//        request.addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                isRated = true;
//                isInterFinish = false;
//                Dialog dialog = new Dialog(activity_context);
//                dialog.setContentView(R.layout.dialog_rating);
//                dialog.setCancelable(false);
//                Window window = dialog.getWindow();
//                if (window != null) {
//                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    WindowManager.LayoutParams windowAttributes = window.getAttributes();
//                    windowAttributes.gravity = Gravity.BOTTOM;
//                    window.setAttributes(windowAttributes);
//                }
//
//                final RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
//
//                Button buttonSubmit = dialog.findViewById(R.id.buttonSubmit);
//                buttonSubmit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        float rating = ratingBar.getRating();
//                        if (rating > 3) {
//                            ReviewInfo reviewInfo = task.getResult();
//                            Task<Void> flow = manager.launchReviewFlow(activity_context, reviewInfo);
//                            flow.addOnCompleteListener(task2 -> {
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                editor.putBoolean("isRated", true);
//                                editor.apply();
//                                isInterFinish = true;
//                            });
//                            dialog.dismiss();
//                        } else {
//                            dialog.dismiss();
//                            isInterFinish = true;
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putBoolean("isRated", true);
//                            editor.apply();
//                        }
//                    }
//                });
//
//                Button buttonNotNow = dialog.findViewById(R.id.buttonNotNow);
//                buttonNotNow.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        isRated = true;
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//            } else {
//                @ReviewErrorCode int reviewErrorCode = ((ReviewException) task.getException()).getErrorCode();
//            }
//        });
//
//    }

//    public void noAdsButtonSetup() {
//        Button noAdsButton = adDialog.findViewById(R.id.btn_no_ads);
//        noAdsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callSubscribe();
//            }
//        });
//    }

    public void onResumeApp() {
        if (isCalledOnResumeCreate && isInterFinish && isAoaFinish && !is_ads_removed) {
            Log.i(TAG, "===============OPEN ADS INCOMING SHOW=================\n" );
            showOpenAdmobAdIfAvailable();
        }
        isCalledOnResumeCreate = true;
        isAppOnBackground = false;
    }


    public void onPauseApp() {
        loadOpenAdmobAds();
        isAppOnBackground = true;
    }

    private void showInterAdmobAd() {
        if (is_ads_removed) {
            return;
        }
        if (interAdmobAd != null && isAoaFinish && isInterFinish) {
            interAdmobAd.show(this.activity_context);
        } else {
            Log.i(TAG, "===============INTER ADS CANT SHOW=================\n" );
            Log.i(TAG, "interAdmobAd status :" + (interAdmobAd == null ? "not available" : "available") );
            Log.i(TAG, "AoaFinish" + isAoaFinish);
            Log.i(TAG, "InterFinish" + isInterFinish);
            loadInterAdmobAds();
        }
    }
    private void loadInterAdmobAds() {
        if (interAdmobAd != null) {
            return;
        }
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this.activity_context, (isFirstOpenApp && !isAoaType) ? interAoaAdsAdmobId : interMediationAdsAdmobId, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        Log.i(TAG, "===============INTER LOADED=================\n" );
                        interAdmobAd = interstitialAd;
                        interstitialAd.setOnPaidEventListener(
                            new OnPaidEventListener() {
                                @Override
                                public void onPaidEvent(@NonNull AdValue adValue) {
                                    Map<String, String> customParams = new HashMap<>();
                                    customParams.put(Scheme.AD_UNIT, interstitialAd.getAdUnitId());
                                    customParams.put(Scheme.AD_TYPE, AppsFlyerAdNetworkEventType.INTERSTITIAL.toString());
                                    customParams.put("ad_platform", "Admob");
                                    customParams.put("value", String.valueOf(adValue.getValueMicros()));

                                    // Actually recording a single impression
                                    AppsFlyerAdRevenue.logAdRevenue(
                                            "admob",
                                            MediationNetwork.googleadmob,
                                            Currency.getInstance(Locale.US),
                                            (double) adValue.getValueMicros(),
                                            customParams
                                    );
                                }
                            }
                        );
                        interAdmobAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        Log.i(TAG, "===============INTER FINISH=================\n" );
                                        if (isFirstOpenApp) {
                                            isFirstOpenApp = false;
                                            remainingTimer = interAdsDelayTimer;
                                        } else {
                                            remainingTimer = interAdsLoopTimer;
                                        }
                                        Log.d("LOG","SET DELAY :" + remainingTimer);
                                        interAdmobAd = null;
                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                isInterFinish = true;
                                            }
                                        }, 1000);
                                        loadInterAdmobAds();
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        Vietnq308Activity.interAdmobAd = null;
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        isInterFinish = false;
                                        Log.d("LOG","onAdShowedFullScreenContent: ");
                                    }
                                });
                        if (interFakeAoaType == 1 && !isAoaType && isFirstOpenApp) {
                            showInterAdmobAd();
                            Log.i(TAG, "===============INTER AOA SHOWING=================\n" );
                        }
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, "===============INTER LOAD FAILED=================\n" + loadAdError  );
                        interAdmobAd = null;
                    }
                });
    }

    private void loadOpenAdmobAds() {
        if (openAdmobAd != null) {
           return;
        }
        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(this.activity_context, openAdsAdmobId, request, AppOpenAd.APP_OPEN_AD_ORIENTATION_LANDSCAPE,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        Log.i(TAG, "===============OPEN ADS LOADED=================\n" );
                        // Called when an app open ad has loaded.
                        numberRetryOpenAd = 0;
                        openAdmobAd = ad;
                        openAdmobAd.setOnPaidEventListener(
                                new OnPaidEventListener() {
                                    @Override
                                    public void onPaidEvent(@NonNull AdValue adValue) {
                                        Map<String, String> customParams = new HashMap<>();
                                        customParams.put(Scheme.AD_UNIT, openAdmobAd.getAdUnitId());
                                        customParams.put(Scheme.AD_TYPE, AppsFlyerAdNetworkEventType.APP_OPEN.toString());
                                        customParams.put("ad_platform", "Admob");
                                        customParams.put("value", String.valueOf(adValue.getValueMicros()));

                                        // Actually recording a single impression
                                        AppsFlyerAdRevenue.logAdRevenue(
                                                "admob",
                                                MediationNetwork.googleadmob,
                                                Currency.getInstance(Locale.US),
                                                (double) adValue.getValueMicros(),
                                                customParams
                                        );
                                    }
                                }
                        );
                        openAdmobAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        Log.i(TAG, "===============OPEN ADS FINISHED=================\n" );
                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                isAoaFinish = true;
                                            }
                                        }, 1000);
                                        if (isFirstOpenApp) {
                                            isFirstOpenApp = false;
                                            remainingTimer = interAdsDelayTimer;
                                        }
                                        openAdmobAd = null;
                                        if (adDialog != null) {
                                            adDialog.show();
                                        }
                                        loadInterAdmobAds();
                                    }
                                    public void onAdFailedToShowFullScreenContent(@NonNull AdError var1) {
                                        isAoaFinish = true;
                                    }

                                    public void onAdImpression() {
                                        // firebase
                                    }

                                    public void onAdShowedFullScreenContent() {
                                    }
                                }
                        );
                        if (isFirstOpenApp && isAoaType) {
                            showOpenAdmobAdIfAvailable();
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        numberRetryOpenAd = numberRetryOpenAd +1;
                        if (numberRetryOpenAd <= 3) {
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i(TAG, "===============OPEN ADS LOAD FAILED and retry after "+ 5000*numberRetryOpenAd + "=================\n"  + loadAdError );
                                    loadOpenAdmobAds();
                                }
                            }, 5000*numberRetryOpenAd);
                        } else {
                            Log.i(TAG, "===============OPEN ADS LOAD FAILED and can not retry=================\n"  + loadAdError  );
                        }
                    }
                });
    }

    void showOpenAdmobAdIfAvailable() {
        if (is_ads_removed) {
            return;
        }
        if (openAdmobAd != null  && isAoaFinish && isInterFinish) {
            isAoaFinish = false;
            openAdmobAd.show(this.activity_context);
            if (adDialog != null && adDialog.isShowing()) {
                adDialog.hide();
            }
        } else {
            Log.i(TAG, "===============OPEN ADS CANT SHOW=================\n" );
            Log.i(TAG, "openAdmobAd status :" + (openAdmobAd == null ? "not available" : "available") );
            Log.i(TAG, "AoaFinish" + isAoaFinish);
            Log.i(TAG, "InterFinish" + isInterFinish);
            loadOpenAdmobAds();
            if (adDialog != null) {
                adDialog.show();
            }
        }
    }

    private void initializeApplovinAds() {
        AppLovinSdk.getInstance(this.activity_context).setMediationProvider("max");
        AppLovinSdk.getInstance(this.activity_context).initializeSdk(configuration -> {
            createInterApplovinAd();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    showApplovinAd();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            showApplovinAd();
//                            handler.postDelayed(this, interAdsLoopTimer);
//                        }
//                    }, interAdsLoopTimer);
//                }
//            }, interAdsDelayTimer);
            Observable.interval(5000,5000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new Observer<Long>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {}
                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {}
                        @Override
                        public void onComplete() {}
                        @Override
                        public void onNext(@io.reactivex.rxjava3.annotations.NonNull Long aLong) {
                            remainingTimer = remainingTimer- 5000;
                            Log.i(TAG, String.valueOf(remainingTimer));
                            if (remainingTimer < 0 && !isAppOnBackground && isAoaFinish && isInterFinish){
                                Completable.timer(1L, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.mojang.minecraftpe.ISC.6
                                    @Override // io.reactivex.rxjava3.functions.Action
                                    public final void run() {
                                        showApplovinAd();
                                    }
                                });
                                remainingTimer = interAdsLoopTimer;
                            }
                        }
                    });
            try {
                AppLovinSdk.getInstance( this.activity_context ).showMediationDebugger();
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }
        });
    }


    private void showApplovinAd() {
        if (interApplovinAd.isReady() && isAoaFinish) {
            interApplovinAd.showAd();
        } else {
            interApplovinAd.loadAd();
        }
    }

    private void createInterApplovinAd() {
        interApplovinAd = new MaxInterstitialAd(interApplovinId, (Activity) this.activity_context);
        interApplovinAd.setListener(new MaxAdListener() {
            @Override
            public void onAdLoaded(MaxAd maxAd) {
                retryAttempt = 0;
                if (interApplovinAd.isReady()) {
                    interApplovinAd.showAd();
                }
            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                retryAttempt++;
                long delayMillis = TimeUnit.SECONDS.toMillis((long) Math.pow(2, Math.min(6, retryAttempt)));

                new Handler().postDelayed(() -> interApplovinAd.loadAd(), delayMillis);
            }

            @Override
            public void onAdDisplayFailed(MaxAd maxAd, MaxError error) {
                interApplovinAd.loadAd();
            }

            @Override
            public void onAdDisplayed(MaxAd maxAd) {
            }

            @Override
            public void onAdClicked(MaxAd maxAd) {
            }

            @Override
            public void onAdHidden(MaxAd maxAd) {
                interApplovinAd.loadAd();
            }
        });

        // Load the first ad
        interApplovinAd.loadAd();
    }

    private static int convertDpToPixel(int dp, Context context) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}
