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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxAppOpenAd;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinSdk;
import com.appsflyer.adrevenue.AppsFlyerAdRevenue;
import com.appsflyer.adrevenue.adnetworks.AppsFlyerAdNetworkEventType;
import com.appsflyer.adrevenue.adnetworks.generic.MediationNetwork;
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
import com.google.android.gms.ads.RequestConfiguration;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Vietnq308Activity {
    private ConsentInformation consentInformation;
    // Use an atomic boolean to initialize the Google Mobile Ads SDK and load ads once.
    private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);

    private static final String TAG = "VIETNQ308";
    private static String ironsourceDevKey = "";
    private static String bannerAdmobId = "";
    private static String mrecAdmobId = "";
    private static String nativeAdmobId = "";
    //    private static String nativeAdmobId2 = "";
//    private static String nativeAdmobId3 = "";
//    private static String nativeAdmobId4 = "";
//    private static String nativeAdmobId5 = "";
    private static String openAdsAdmobId = "";
    private static String interAoaAdsAdmobId = "";
    private static String interMediationAdsAdmobId = "";
    private static String interApplovinId = "";
    private static String bannerApplovinId = "";
    private static String openApplovinId = "";

    private static String interIronSourceId = "";
    private AppOpenAd openAdmobAd;
    private static InterstitialAd interAdmobAd;
    private static MaxInterstitialAd interApplovinAd;
    private static boolean policyMode = false;
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

    private AdView adBannerView;
    private static String jsonDefault = "";
    SharedPreferences sharedPreferences;

    private  Dialog adDialog;
    private  Dialog  alertDialog;
    private NativeAd cacheNativeAd;
//    private NativeAd cacheAlertNativeAd;

    private MaxAppOpenAd maxAppOpenAd;
    private MaxAdView maxAdView;

    private int timePlayed = 0;
    private boolean isTooLateForAds = false;
    private int timerOpenAdsCanShow = 0;
    //    private boolean isRated = false;
    //    private BillingClient billingClient;
    //    private PurchasesUpdatedListener purchasesUpdatedListener;
    private boolean isFailMrecBanner = false;
    private boolean isLimitStartTime = true;
    private int openAdDelayTimer = 30 *1000;

    private boolean isNativeLoading = false;
    private boolean isAoaLoading = false;

    private boolean isInterLoading = false;

    public Vietnq308Activity(MainActivity mainActivity) {
        this.activity_context = mainActivity;
        fetchingAPIconfig();
    }
    public void fetchingAPIconfig() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLimitStartTime) {
                    isTooLateForAds = true;
                }
            }
        }, 10000);
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
//            Observable.interval(180000,180000, TimeUnit.MILLISECONDS)
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

        AppsFlyerAdRevenue.Builder afRevenueBuilder = new AppsFlyerAdRevenue.Builder(activity_context.getApplication());
        AppsFlyerAdRevenue.initialize(afRevenueBuilder.build());

//        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setMinimumFetchIntervalInSeconds(5)
//                .build();
//        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//        mFirebaseRemoteConfig.fetchAndActivate()
//                .addOnCompleteListener(activity_context, new OnCompleteListener<Boolean>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Boolean> task) {
//                        jsonDefault = mFirebaseRemoteConfig.getString("config_ver5");
//                    }
//                });

        String apiUrl = "https://craft.gafugame.com/api/rmc_k.jsp?access-key=316def69-420c-42e6-aa6d-c8c47e1ad5ea&key=test_id";
        Volley.newRequestQueue(this.activity_context).add(new StringRequest(Request.Method.GET, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "===============Lấy API thành cmn công=================\n" );
                        parseAPIResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "===============Lấy API thất bại (mẹ của thành công)=================\n" );
                        Log.i(TAG, "===============Khả năng là sever down đấy=================\n" );
                        String retrievedJsonString = sharedPreferences.getString("json_default", jsonDefault);
                        if (retrievedJsonString != null) {
                            parseAPIResponse(retrievedJsonString);
                            Log.i(TAG, "Lấy tạm fuwx liệu cũ : " +  retrievedJsonString);
                        } else {
                            parseAPIResponse(jsonDefault);
                            Log.i(TAG, "Lấy dữ liệu mặc định vậy : " +  jsonDefault);
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
            policyMode = jsonResult.getBoolean("policyMode");
            openAdsAdmobId = jsonResult.getString("openAdsAdmobId");
            bannerAdmobId = jsonResult.getString("bannerAdmobId");
            mrecAdmobId = jsonResult.getString("mrecAdmobId");
            nativeAdmobId  = jsonResult.getString("nativeAdmobId");
            interAoaAdsAdmobId = jsonResult.getString("interAoaAdsAdmobId");
            interMediationAdsAdmobId = jsonResult.getString("interMediationAdsAdmobId");

            interApplovinId = jsonResult.getString("interApplovinId");
            bannerApplovinId = jsonResult.getString("bannerApplovinId");
            openApplovinId = jsonResult.getString("openApplovinId");

            interIronSourceId = jsonResult.getString("interIronSourceId");
            isAoaType = jsonResult.getBoolean("isAoaType");
            interFakeAoaType = jsonResult.getInt("interFakeAoaType");
            interAdsType = jsonResult.getInt("interAdsType");
            isOnBanner =  jsonResult.getBoolean("isOnBanner");
            isOnCollapsible = jsonResult.getBoolean("isOnCollapsible");
            isOnNative = jsonResult.getBoolean("isOnNative");
            nativeRefreshTimer  = jsonResult.getInt("nativeRefreshTimer") * 1000;
            interAdsDelayTimer = jsonResult.getInt("interAdsDelayTimer") * 1000;
            interAdsLoopTimer = jsonResult.getInt("interAdsLoopTimer") * 1000;
            nativeRatioHeightScreen = jsonResult.getDouble("nativeRatioHeightScreen");
            nativeRatioWidthScreen = jsonResult.getDouble("nativeRatioWidthScreen");
            nativeRatioClick = jsonResult.getDouble("nativeRatioClick");
            isLimitStartTime = jsonResult.getBoolean("isLimitStartTime");
            openAdDelayTimer = jsonResult.getInt("openAdDelayTimer") * 1000;

            Log.i(TAG,"Thêm data vào : |||||||||||||||| " + response);
            initializeAdsWithApi();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("json_default", response);
            Log.i(TAG, "Lưu lại dữ liệu : |||||||||||||||| " + response );
            editor.apply();
        } catch (Exception e) {
            String a = "{\"policyMode\":true,\"ironsourceDevKey\":\"X\",\"bannerAdmobId\":\"ca-app-pub-1839004489502882/7208738692\",\"mrecAdmobId\":\"ca-app-pub-1839004489502882/2085806899\",\"nativeAdmobId\":\"ca-app-pub-1839004489502882/4638967989\",\"openAdsAdmobId\":\"ca-app-pub-1839004489502882/4921486702\",\"interAoaAdsAdmobId\":\"ca-app-pub-1839004489502882/7040383586\",\"interMediationAdsAdmobId\":\"ca-app-pub-1839004489502882/7547650041\",\"interApplovinId\":\"x\",\"bannerApplovinId\":\"x\",\"openApplovinId\":\"x\",\"interIronSourceId\":\"X\",\"isAoaType\":false,\"interFakeAoaType\":1,\"isOnBanner\":false,\"isOnCollapsible\":false,\"isOnNative\":true,\"nativeRefreshTimer\":30,\"interAdsType\":1,\"interAdsDelayTimer\":12,\"interAdsLoopTimer\":189,\"nativeRatioHeightScreen\":0.15,\"nativeRatioWidthScreen\":0.4,\"nativeRatioClick\":1.67,\"isLimitStartTime\": true,\"openAdDelayTimer\": 30}";
            Log.i(TAG, "===============FAILED TO PARSE DATA=================\n" + e );
            String retrievedJsonString = sharedPreferences.getString("json_default", jsonDefault);
            if (retrievedJsonString != null) {
                parseAPIResponse(retrievedJsonString);
                Log.i(TAG, "Lấy dữ liệu cũ " +  retrievedJsonString);
            } else {
                parseAPIResponse(jsonDefault);
                Log.i(TAG, "Lấy dữ liệu mặc định " +  jsonDefault);
            }
        }
    }

    private void initializeAdsWithApi() {
        if (is_ads_removed) {
            return;
        }
        if (interAdsType == 1) {
            initializeAdmobAds();
        }
        if (interAdsType == 2) {
            initializeApplovinAds();
        }
        if (interAdsType == 3 || interFakeAoaType == 3) {
//            init ironsource
        }
    }

    private void initializeAdmobAds() {
        MobileAds.initialize(this.activity_context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.i(TAG, "===============Khởi tạo ADMOB thành công=================\n" );
            }
        });
        List<String> testDeviceIds = Arrays.asList("8B4E3A1BD78D05CFF2967770656D5EF8");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);
        loadOpenAdmobAds();
        loadInterAdmobAds();

        if (isOnBanner) {
            createBannerAd();
        }

        if (policyMode) {
            // BANNER SETUP
//            initMrecBanner();
//            loadAlertNativeAd();
        }

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
                                                                                                              timePlayed = timePlayed + 5000;

                                                                                                              remainingTimer = remainingTimer- 5000;
                                                                                                              timerOpenAdsCanShow = timerOpenAdsCanShow- 5000;
                                                                                                              Log.i(TAG, "INTER đếm giây : " + String.valueOf(remainingTimer) + " AOA đếm giây: " + String.valueOf(timerOpenAdsCanShow));
                                                                                                              if (remainingTimer < 0 && !isAppOnBackground  && isAoaFinish && isInterFinish && !is_ads_removed){
                                                                                                                  Completable.timer(1L, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.mojang.minecraftpe.ISC.6
                                                                                                                      @Override // io.reactivex.rxjava3.functions.Action
                                                                                                                      public final void run() {
                                                                                                                          Log.i(TAG, "===============INTER chuẩn bị hiện lên=================\n" );
                                                                                                                          if (policyMode) {
                                                                                                                              createDialogInterAdmob();
                                                                                                                          } else {
                                                                                                                              showInterAdmobAd();
                                                                                                                          }
                                                                                                                      }
                                                                                                                  });
                                                                                                              }
                                                                                                              boolean isShowAlertDialog = false;
                                                                                                              if (alertDialog != null) {
                                                                                                                  isShowAlertDialog = alertDialog.isShowing();
                                                                                                              }
                                                                                                              if (isOnNative && !isShowAlertDialog) {
                                                                                                                  showNativeAdDialog();
                                                                                                              }
                                                                                                          }
                                                                                                      }
                );
    }

    private void initMrecBanner() {
        if (adBannerView != null) {
            adBannerView = null;
        }
        adBannerView = new AdView(activity_context);
//            WindowManager windowManager = (WindowManager) activity_context.getSystemService(Context.WINDOW_SERVICE);
//            DisplayMetrics displayMetrics = new DisplayMetrics();
//            windowManager.getDefaultDisplay().getMetrics(displayMetrics);

//        adBannerView.setAdSize(AdSize.MEDIUM_RECTANGLE);


        if (isFailMrecBanner) {
            adBannerView.setAdSize(AdSize.LARGE_BANNER);
            Log.i("VIETNQ308", "================================= đặt kích thước banner là  320x100");
        } else {
            adBannerView.setAdSize(AdSize.MEDIUM_RECTANGLE);
            Log.i("VIETNQ308", "================================= đặt kích thước banner là  300x250");
        }

        adBannerView.setAdUnitId(mrecAdmobId);

        Bundle extras = new Bundle();
        AdRequest adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build();

        adBannerView.loadAd(adRequest);
        adBannerView.setOnPaidEventListener(new OnPaidEventListener() {
            @Override
            public void onPaidEvent(@NonNull AdValue adValue) {
                Map<String, String> customParams = new HashMap<>();
                customParams.put(Scheme.AD_UNIT, mrecAdmobId);
                customParams.put(Scheme.AD_TYPE, AppsFlyerAdNetworkEventType.BANNER.toString());
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
        adBannerView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {}

            @Override
            public void onAdClosed() {}

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e("VIETNQ308", "================================= Tải banner thất bại"+ adError);
                isFailMrecBanner = true;
                Log.e("VIETNQ308", "================================= Đặt size banner thành 320x100 cho lần sau");
            }

            @Override
            public void onAdImpression() {}

            @Override
            public void onAdLoaded() {
                Log.e("VIETNQ308", "================================= BANNER load thành công rồi ");
                Log.e("VIETNQ308", "================================= BANNER này đang là dạng " + (isFailMrecBanner ? "320x100" : "300x250"));
                Log.e("VIETNQ308", "================================= Chỉnh lại thành loại 300x250 cho lần sau");
                isFailMrecBanner = false;
            }

            @Override
            public void onAdOpened() {
                Log.e("VIETNQ308", "================================= Banner được mở");
            }
        });
    }

//    private void loadAlertNativeAd() {
//        if (cacheAlertNativeAd == null) {
//            String nativeId = nativeAdmobId;
//            AdLoader adLoader = new AdLoader.Builder(activity_context, nativeId)
//                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
//                        @Override
//                        public void onNativeAdLoaded(NativeAd nativeAd) {
//                            nativeAd.setOnPaidEventListener(new OnPaidEventListener() {
//                                @Override
//                                public void onPaidEvent(@NonNull AdValue adValue) {
//                                    Map<String, String> customParams = new HashMap<>();
//                                    customParams.put(Scheme.AD_UNIT, nativeId);
//                                    customParams.put(Scheme.AD_TYPE, AppsFlyerAdNetworkEventType.NATIVE.toString());
//                                    customParams.put("ad_platform", "Admob");
//                                    customParams.put("value", String.valueOf(adValue.getValueMicros()));
//
//                                    // Actually recording a single impression
//                                    AppsFlyerAdRevenue.logAdRevenue(
//                                            "Admob",
//                                            MediationNetwork.googleadmob,
//                                            Currency.getInstance(Locale.US),
//                                            (double) adValue.getValueMicros(),
//                                            customParams
//                                    );
//                                }
//                            });
//
//                            numberRetryNative = 0;
//                            cacheAlertNativeAd = nativeAd;
////                            if (cacheNativeAd2 == null) {
////                                loadNativeAd2();
////                            }
//                            Log.i("VIETNQ308", "LOADED NATIVE1 SUCCESSFULLY");
//                        }
//                    })
//                    .withAdListener(new AdListener() {
//                        @Override
//                        public void onAdFailedToLoad(LoadAdError adError) {
//                            Log.e("VIETNQ308", "FAILED LOAD NATIVE in alert"+ String.valueOf(adError));
//                            numberRetryNative = numberRetryNative +1;
//                            if (nativeCounterTimer < 0 ) {
//                                nativeCounterTimer = 10000*numberRetryNative*numberRetryNative;
//                                Log.e("VIETNQ308", "NATIVE in alert LOAD AFTER: "+ String.valueOf(nativeCounterTimer) + "TIMES:" +String.valueOf(numberRetryNative));
//                            }
//                        }
//                    })
//                    .withNativeAdOptions(new NativeAdOptions.Builder().setMediaAspectRatio(MediaAspectRatio.LANDSCAPE).build()).build();
//            adLoader.loadAd(new AdRequest.Builder().build());
//        }
//    }

    private void createDialogInterAdmob() {
        try {
            if (is_ads_removed) {
                return;
            }
            if (interAdmobAd != null && isAoaFinish && isInterFinish) {
                initMrecBanner();
//                if (cacheAlertNativeAd == null) {
//                    loadAlertNativeAd();
//                }

                remainingTimer = 999999000;
                alertDialog = new Dialog(activity_context);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.setContentView(R.layout.play_dialog);
                Window dialogWindow = alertDialog.getWindow();
                if (dialogWindow != null) {
                    dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(dialogWindow.getAttributes());
                    layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    layoutParams.gravity = Gravity.CENTER;
                    dialogWindow.setAttributes(layoutParams);
                }
                alertDialog.setCancelable(false);

                TextView titleAlert = alertDialog.findViewById(R.id.titleAlert);
                if (((int) (timePlayed/60000)) >= 1) {
                    titleAlert.setText("You have been playing for "+ ((int) (timePlayed/60000)) +" minutes");
                } else {
                    titleAlert.setText("You have been playing for "+ ((int) (timePlayed/1000)) +" seconds");
                }
                titleAlert.setTextColor(Color.parseColor("#000000"));

                ImageView playButton = alertDialog.findViewById(R.id.playButton);
//            GradientDrawable shape =  new GradientDrawable();
//            shape.setCornerRadius( 8 );
//            shape.setColor(Color.parseColor("#B20000"));
//            playButton.setBackground(shape);

                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        alertDialog = null;
                        if (adDialog != null ) {
                            adDialog.show();
                        }
                        showInterAdmobAd();
//                        loadAlertNativeAd();
                    }
                });
                ImageButton closeButton = alertDialog.findViewById(R.id.closeButton);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        alertDialog = null;
                        if (adDialog != null ) {
                            adDialog.show();
                        }
                        showInterAdmobAd();
//                        loadAlertNativeAd();
                    }
                });

                //BANNER ADD TO VIEW
                FrameLayout adBanner = alertDialog.findViewById(R.id.adBanner);
                if (adBannerView.getParent() != null) {
                    ((ViewGroup) adBannerView.getParent()).removeView(adBannerView);
                }
                adBanner.removeAllViews();
                adBanner.addView(adBannerView);
                //####################
//                if (cacheAlertNativeAd != null) {
//                    TemplateView templateNative = alertDialog.findViewById(R.id.adViewNative);
//                    ColorDrawable background = new ColorDrawable(0x00FFFFFF);
//                    NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();
//                    templateNative.setStyles(styles);
//                    templateNative.setNativeAd(cacheAlertNativeAd);
//
//                    NativeAdView nativeAdView = templateNative.getNativeAdView();
//                    if (cacheAlertNativeAd.getIcon() != null) {
//                        nativeAdView.setIconView(templateNative.findViewById(com.google.android.ads.nativetemplates.R.id.icon));
//                    }
//                    ConstraintLayout content = templateNative.findViewById(com.google.android.ads.nativetemplates.R.id.content);
//                    try {
//                        nativeAdView.setBodyView(content);
//                    } catch (Error error) {
//                        Log.e("VIETNQ308", String.valueOf(error));
//                    }
//                    templateNative.getLayoutParams().height = 300;
//                    templateNative.getLayoutParams().width = 300;
//                    templateNative.requestLayout();
//                }

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog.show();
                        if (adDialog != null) {
                            adDialog.hide();
                        }
                    }
                }, 4000);

            } else {
                Log.i(TAG, "===============INTER Không thể hiện ra, xem lý do dưới đây : =================\n" );
                Log.i(TAG, "---- tình trạng thằng inter :" + (interAdmobAd == null ? "Không có sẵn" : "có sẵn") );
                Log.i(TAG, "---- Thằng OpenAds đã hoàn thành chưa (không bị che): " + (isAoaFinish ? "Đúng" : "Sai" ));
                Log.i(TAG, "---- Thằng Inter đã hoàn thành chưa (không bị che): " + (isInterFinish ? "Đúng" : "Sai"));
                loadInterAdmobAds();
                return;
            }
        } catch (Exception e) {
            Log.e("VIETNQ308", e + "========= EROROROORR");
        }
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
        adView.setOnPaidEventListener(new OnPaidEventListener() {
            @Override
            public void onPaidEvent(@NonNull AdValue adValue) {
                Map<String, String> customParams = new HashMap<>();
                customParams.put(Scheme.AD_UNIT, bannerAdmobId);
                customParams.put(Scheme.AD_TYPE, AppsFlyerAdNetworkEventType.BANNER.toString());
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
        adDialog.setCancelable(false);
        adDialog.show();
        if (!isFirstOpenApp) adDialog.hide();
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
        Log.i("VIETNQ308", "NATIVE bộ đếm giây : "+ String.valueOf(nativeCounterTimer) + " || Lưu trữ Native tình trạng: " + (cacheNativeAd==null ? "Không có sẵn" : "có sẵn"));
        nativeCounterTimer = nativeCounterTimer - 5000;
        if (nativeCounterTimer < 0 && !is_ads_removed) {
            if (cacheNativeAd != null ) {
                Log.i("VIETNQ308", "Hiện lên NATIVE (làm mới)");
                showNativeDialog(cacheNativeAd);
            } else {
                loadNativeAd1();
            }
        }
    }

//    public String pickRandomNative() {
//        List<String> listNative = new ArrayList<>();
//        listNative.add(nativeAdmobId1);
//        listNative.add(nativeAdmobId2);
//        listNative.add(nativeAdmobId3);
//        listNative.add(nativeAdmobId4);
//        listNative.add(nativeAdmobId5);
//
//        if (lastIndexNative == -1) {
//            Random random = new Random();
//            lastIndexNative = random.nextInt(5);
//        }
//        String nativePicked = listNative.get(lastIndexNative);
//        Log.i("VIETNQ308", "RANDOM ID : " + nativePicked + " || NO: " + lastIndexNative);
//
//        if (lastIndexNative >= 4) {
//            lastIndexNative = 0;
//        } else {
//            lastIndexNative = lastIndexNative +1 ;
//        }
//        return nativePicked;
//    }
    private void loadNativeAd1() {
        if (cacheNativeAd == null && !isNativeLoading) {
            isNativeLoading = true;
            String nativeId = nativeAdmobId;
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
                                            "Admob",
                                            MediationNetwork.googleadmob,
                                            Currency.getInstance(Locale.US),
                                            (double) adValue.getValueMicros(),
                                            customParams
                                    );
                                }
                            });

                            numberRetryNative = 0;
                            cacheNativeAd = nativeAd;
                            isNativeLoading = false;
//                            if (cacheNativeAd2 == null) {
//                                loadNativeAd2();
//                            }
                            Log.i("VIETNQ308", "Native đã tải thành công về");
                        }
                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(LoadAdError adError) {
                            isNativeLoading = false;
                            Log.e("VIETNQ308", "Thằng Native tải về thất bại rồi ông giáo ạ"+ String.valueOf(adError));
                            numberRetryNative = numberRetryNative +1;
                            if (nativeCounterTimer < 0 ) {
                                nativeCounterTimer = 10000*numberRetryNative*numberRetryNative;
                                Log.e("VIETNQ308", "Tải lại thằng native sau số giây: "+ String.valueOf(nativeCounterTimer) + " lần thứ :" +String.valueOf(numberRetryNative));
                            }
                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder().setMediaAspectRatio(MediaAspectRatio.LANDSCAPE).build()).build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }
    }

//    private void loadNativeAd2() {
//        if (cacheNativeAd2 == null) {
//            String nativeId = nativeAdmobId;
//            AdLoader adLoader = new AdLoader.Builder(activity_context, nativeId)
//                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
//                        @Override
//                        public void onNativeAdLoaded(NativeAd nativeAd) {
//                            nativeAd.setOnPaidEventListener(new OnPaidEventListener() {
//                                @Override
//                                public void onPaidEvent(@NonNull AdValue adValue) {
//                                    Map<String, String> customParams = new HashMap<>();
//                                    customParams.put(Scheme.AD_UNIT, nativeId);
//                                    customParams.put(Scheme.AD_TYPE, AppsFlyerAdNetworkEventType.NATIVE.toString());
//                                    customParams.put("ad_platform", "Admob");
//                                    customParams.put("value", String.valueOf(adValue.getValueMicros()));
//
//                                    // Actually recording a single impression
//                                    AppsFlyerAdRevenue.logAdRevenue(
//                                            "admob",
//                                            MediationNetwork.googleadmob,
//                                            Currency.getInstance(Locale.US),
//                                            (double) adValue.getValueMicros(),
//                                            customParams
//                                    );
//                                }
//                            });
//
//                            numberRetryNative = 0;
//                            cacheNativeAd2 = nativeAd;
//                            Log.i("VIETNQ308", "LOADED NATIVE2 SUCCESSFULLY");
//                        }
//                    })
//                    .withAdListener(new AdListener() {
//                        @Override
//                        public void onAdFailedToLoad(LoadAdError adError) {
//                            Log.e("VIETNQ308", "FAILED LOAD NATIVE2"+ String.valueOf(adError));
//                            numberRetryNative = numberRetryNative +1;
//                            if (nativeCounterTimer < 0 ) {
//                                nativeCounterTimer = 10000*numberRetryNative*numberRetryNative;
//                                Log.e("VIETNQ308", "NATIVE2 LOAD AFTER: "+ String.valueOf(nativeCounterTimer)+ "TIMES:" +String.valueOf(numberRetryNative));
//                            }
//                        }
//                    })
//                    .withNativeAdOptions(new NativeAdOptions.Builder().setMediaAspectRatio(MediaAspectRatio.LANDSCAPE).build()).build();
//            adLoader.loadAd(new AdRequest.Builder().build());
//        }
//    }

    private void showNativeDialog(NativeAd nativeAd) {
        try {
            if (adDialog != null) {
                adDialog.dismiss();
            }
            Log.i("VIETNQ308",   "Đang trong quá trình cài đặt native lên dialog" + nativeAd);
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


            TemplateView template = adDialog.findViewById(R.id.my_template);
            ColorDrawable background = new ColorDrawable(0x00FFFFFF);
            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();
            template.setStyles(styles);
            template.setNativeAd(nativeAd);
            template.getLayoutParams().height = dpToPx(activity_context, 400);
            template.getLayoutParams().width = dpToPx(activity_context, 180);//(int) (screenWidth*nativeRatioWidthScreen);

            NativeAdView template_2 = adDialog.findViewById(R.id.my_template_2);
            TemplateView newCloneTemplateView = template;
            if (newCloneTemplateView.getParent() != null) {
                ((ViewGroup) newCloneTemplateView.getParent()).removeView(newCloneTemplateView);
            }
            template_2.addView(newCloneTemplateView);
            template_2.getLayoutParams().height = (int) (screenHeight*nativeRatioHeightScreen);
            template_2.getLayoutParams().width = (int) (screenWidth*nativeRatioWidthScreen);

            if (nativeAd.getIcon() != null) {
                ImageView iconNative = (ImageView) adDialog.findViewById(R.id.icon);
                iconNative.setImageDrawable(nativeAd.getIcon().getDrawable());
            }
            if (nativeAd.getHeadline() != null ) {
                TextView primaryText = (TextView) adDialog.findViewById(R.id.primary);
                primaryText.setText(nativeAd.getHeadline());
            }

            if (nativeAd.getAdvertiser() != null ) {
                TextView secondaryText = (TextView) adDialog.findViewById(R.id.secondary);
                secondaryText.setText(nativeAd.getAdvertiser());
            } else {
                if (nativeAd.getBody() != null ){
                    TextView body = (TextView) adDialog.findViewById(R.id.secondary);
                    body.setText(nativeAd.getBody());
                } else {
                    if (nativeAd.getStore() != null ){
                        TextView getStore = (TextView) adDialog.findViewById(R.id.secondary);
                        getStore.setText(nativeAd.getStore());
                    } else {
                        if (nativeAd.getPrice() != null ){
                            TextView getPrice = (TextView) adDialog.findViewById(R.id.secondary);
                            getPrice.setText(nativeAd.getPrice());
                        }
                    }
                }
            }

            if (nativeAd.getCallToAction() != null ) {
                AppCompatButton cta = (AppCompatButton) adDialog.findViewById(R.id.cta);
                cta.setText(nativeAd.getCallToAction());
            }

//            if (nativeAd.getAdChoicesInfo() != null){
//                ImageView adChoicesIcon = (ImageView) adDialog.findViewById(R.id.adChoicesIcon);
//                AdChoicesView choicesView = newCloneTemplateView.getNativeAdView().getAdChoicesView();
//                adChoicesIcon.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        newCloneTemplateView.getNativeAdView().getAdChoicesView().performClick();
//                    }
//                });
//            }

            template_2.requestLayout();

            View myButton = adDialog.findViewById(R.id.my_button);
            myButton.getLayoutParams().height = (int) (screenHeight*nativeRatioHeightScreen* nativeRatioClick);
            myButton.getLayoutParams().width = (int) (screenWidth*nativeRatioWidthScreen* nativeRatioClick);
            NativeAdView nativeAdView1 = template.getNativeAdView();
            nativeAdView1.setCallToActionView(myButton);
            myButton.requestLayout();
            template.requestLayout();


//
//            NativeAdView nativeAdView = template.getNativeAdView();
//            if (nativeAd.getIcon() != null) {
//                nativeAdView.setIconView(template.findViewById(com.google.android.ads.nativetemplates.R.id.icon));
//            }
//            ConstraintLayout content = template.findViewById(com.google.android.ads.nativetemplates.R.id.content);
//            try {
//                nativeAdView.setBodyView(content);
//            } catch (Error error) {
//                Log.e("VIETNQ308", String.valueOf(error));
//            }

            //#########################################
//            View myButton = adDialog.findViewById(R.id.my_button);
//            myButton.getLayoutParams().height = (int) (screenHeight*nativeRatioHeightScreen* nativeRatioClick);
//            myButton.getLayoutParams().width = (int) (screenWidth*nativeRatioWidthScreen* nativeRatioClick);
//            myButton.requestLayout();
            //#########################################





            // clone new native =======================================================
//            TemplateView myButton = adDialog.findViewById(R.id.my_button);
//            NativeAd nativeAdClone = nativeAd;
//            myButton.setStyles(styles);
//            myButton.setNativeAd(nativeAd);
//            NativeAdView nativeAdViewClone = myButton.getNativeAdView();
//            if (nativeAdClone.getIcon() != null) {
//                nativeAdViewClone.setIconView(myButton.findViewById(com.google.android.ads.nativetemplates.R.id.icon));
//            }
//            ConstraintLayout contentBackground = myButton.findViewById(com.google.android.ads.nativetemplates.R.id.content);
//            try {
//                nativeAdViewClone.setBodyView(contentBackground);
//            } catch (Error error) {
//                Log.e("VIETNQ308", String.valueOf(error));
//            }
//            myButton.getLayoutParams().height = (int) (screenHeight*nativeRatioHeightScreen* nativeRatioClick);
//            myButton.getLayoutParams().width = (int) (screenWidth*nativeRatioWidthScreen* nativeRatioClick);
//            myButton.requestLayout();
            // clone new native =======================================================

            //#########################################
//            if (new Random().nextBoolean()) {
//                myButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        nativeAdView.findViewById(com.google.android.ads.nativetemplates.R.id.cta).performClick();
//                    }
//                });
//            } else {
//                if (nativeAd.getBody() != null) {
//                    nativeAdView.setBodyView(myButton);
//                }
//            }
//            if (nativeAd.getBody() != null) {
//                nativeAdView.setBodyView(myButton);
//            }
            //#########################################

//            noAdsButtonSetup();
            if (isAoaFinish) {
                adDialog.show();
            } else {
                adDialog.hide();
            }
            nativeCounterTimer = nativeRefreshTimer;
            cacheNativeAd = null;
            Log.i(TAG, "Xoá lưu trữ Native cũ" );
//            loadNativeAd1();
//            if (nativeAd == cacheNativeAd1) {
//                cacheNativeAd1 = null;
//                Log.i(TAG, "CLEAR NATIVE 1" );
//                numberMarkNativeShowed = 1;
//                loadNativeAd1();
//            } else {
//                cacheNativeAd2 = null;
//                Log.i(TAG, "CLEAR NATIVE 2" );
//                numberMarkNativeShowed = 2;
//                loadNativeAd2();
//            }
        } catch (Exception e) {
            Log.e("VIETNQ308", e + "========= EROROROORR");
        }
    }


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
//                        isInterFinish = true;
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
            if (interAdsType == 1) {
                Log.i(TAG, "===============OPEN ADS chuẩn bị được gọi hiện lên này=================\n" );
                showOpenAdmobAdIfAvailable();
            }
            if (interAdsType == 2) {
                Log.i(TAG, "===============OPEN APPLOVIN ADS INCOMING SHOW=================\n" );
                showAdIfReady();
            }
        }
        isCalledOnResumeCreate = true;
        isAppOnBackground = false;
    }


    public void onPauseApp() {
        if (interAdsType == 1) {
            loadOpenAdmobAds();
        }
        if (interAdsType == 2 && maxAppOpenAd == null) {
            maxAppOpenAd.loadAd();
        }
        isAppOnBackground = true;
    }

    private void showInterAdmobAd() {
        if (is_ads_removed) {
            return;
        }
        if (interAdmobAd != null && isAoaFinish && isInterFinish) {
            interAdmobAd.show(this.activity_context);
        } else {
            Log.i(TAG, "===============INTER không hiện được lên, xem lý do bên dưới =================\n" );
            Log.i(TAG, "Tình trạng Inter :" + (interAdmobAd == null ? "Không có sẵn" : "có sẵn") );
            Log.i(TAG, "---- Thằng OpenAds đã hoàn thành chưa (không bị che): " + (isAoaFinish ? "Đúng" : "Sai" ));
            Log.i(TAG, "---- Thằng Inter đã hoàn thành chưa (không bị che): " + (isInterFinish ? "Đúng" : "Sai"));
            if (isAoaFinish && isInterFinish) {
                loadInterAdmobAds();
                Log.i(TAG, "===============Load lại thằng Inter vì nó không có sẵn=================\n" );
            }
        }
    }
    private void loadInterAdmobAds() {
        if (interAdmobAd == null && !isInterLoading) {
            isInterLoading = true;
            Log.i(TAG, (isFirstOpenApp && !isAoaType) ? "====Đang load thằng inter AOA nhé =====\n" : "==== Đang load thằng inter Mediation nhé=====\n" );
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(this.activity_context, (isFirstOpenApp && !isAoaType) ? interAoaAdsAdmobId : interMediationAdsAdmobId, adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            isInterLoading = false;
                            Log.i(TAG, "===============Inter load thành công rồi ông giáo =================\n" );
                            interAdmobAd = interstitialAd;
                            interAdmobAd.setOnPaidEventListener(
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
                                            Log.i(TAG, (isFirstOpenApp && !isAoaType) ? "==== Kết thúc 1 inter AOA =====\n" : "==== Kết thúc 1 inter Mediation =====\n" );
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
                                        }

                                        @Override
                                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                                            interAdmobAd = null;
                                            Log.d("LOG","Inter này hiện lên bị lỗi, lỗi hiếm lắm đấy ");
                                        }

                                        @Override
                                        public void onAdShowedFullScreenContent() {
                                            isInterFinish = false;
                                            Log.d("LOG","Inter này hiện lên cả màn hình rồi này ");
                                        }
                                    });
                            if (interFakeAoaType == 1 && !isAoaType && isFirstOpenApp && !isTooLateForAds) {
                                showInterAdmobAd();
                                Log.i(TAG, "===============Hiện lên thằng inter AOA ngay lập tức nè=================\n" );
                            }
                        }
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            isInterLoading = false;
                            // Handle the error
                            Log.i(TAG, "===============Inter tải về thất bại rồi ông giáo ạ =================\n" + loadAdError  );
                            interAdmobAd = null;
                        }
                    });
        }
    }

    private void loadOpenAdmobAds() {
        if (openAdmobAd == null && !isAoaLoading) {
            isAoaLoading = true;
            Log.i(TAG, "===============Open Ads đang tải về đợi tý =================\n" );
            AdRequest request = new AdRequest.Builder().build();
            AppOpenAd.load(this.activity_context, openAdsAdmobId, request, AppOpenAd.APP_OPEN_AD_ORIENTATION_LANDSCAPE,
                    new AppOpenAd.AppOpenAdLoadCallback() {
                        @Override
                        public void onAdLoaded(AppOpenAd ad) {
                            isAoaLoading = false;
                            Log.i(TAG, "===============Open Ads tải về thành công rồi nhé ông giáo =================\n" );
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
                                            Log.i(TAG, "=============== Open Ads cút khỏi màn hình rồi ông giáo  =================\n" );
                                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    isAoaFinish = true;
                                                    timerOpenAdsCanShow = openAdDelayTimer;
                                                }
                                            }, 1000);
                                            if (isFirstOpenApp) {
                                                isFirstOpenApp = false;
                                                remainingTimer = interAdsDelayTimer;
                                            }
                                            openAdmobAd = null;
                                            loadOpenAdmobAds();
                                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (policyMode) {
                                                        if (alertDialog != null) {
                                                            alertDialog.show();
                                                        } else if (adDialog != null) {
                                                            adDialog.show();
                                                        }
                                                    } else {
                                                        if (adDialog != null) {
                                                            adDialog.show();
                                                        }
                                                    }
                                                }
                                            }, 1000);
                                        }
                                        public void onAdFailedToShowFullScreenContent(@NonNull AdError var1) {
                                            isAoaFinish = true;
                                        }

                                        public void onAdImpression() {
                                            // firebase
                                        }

                                        public void onAdShowedFullScreenContent() {
                                            isAoaFinish = false;
                                            if (adDialog != null) {
                                                adDialog.hide();
                                                if (adDialog.isShowing()) {
                                                    adDialog.hide();
                                                }
                                            }
                                            if ( alertDialog != null ) {
                                                alertDialog.hide();
                                                if (alertDialog.isShowing()) {
                                                    alertDialog.hide();
                                                }
                                            }
                                        }
                                    }
                            );
                            if (isFirstOpenApp && isAoaType && !isTooLateForAds) {
                                showOpenAdmobAdIfAvailable();
                            }
                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            isAoaLoading = false;
                            numberRetryOpenAd = numberRetryOpenAd +1;
                            if (numberRetryOpenAd <= 3) {
                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.i(TAG, "===============OPEN ADS tải thất bại rồi, tại lại sau số giây : "+ 5000*numberRetryOpenAd + "=================\n"  + loadAdError );
                                        loadOpenAdmobAds();
                                    }
                                }, 5000*numberRetryOpenAd);
                            } else {
                                Log.i(TAG, "===============OPEN ADS tải thất bại quá nhiều lần, không load lại nữa đâu================\n"  + loadAdError  );
                            }
                        }
                    });
        }
    }

    void showOpenAdmobAdIfAvailable() {
        if (is_ads_removed) {
            return;
        }
        if (openAdmobAd != null  && isAoaFinish && isInterFinish && (timerOpenAdsCanShow<=0)) {
            isAoaFinish = false;
            if (adDialog != null) {
                adDialog.hide();
                if (adDialog.isShowing()) {
                    adDialog.hide();
                }
            }
            if ( alertDialog != null ) {
                alertDialog.hide();
                if (alertDialog.isShowing()) {
                    alertDialog.hide();
                }
            }
            openAdmobAd.show(this.activity_context);
        } else {
            Log.i(TAG, "===============OPEN ADS không hiện lên được, xem lý do phía dưới : =================\n" );
            Log.i(TAG, "Tình trạng của thằng OPEN ADS :" + (openAdmobAd == null ? "Không có sẵn" : "có sẵn") );
            Log.i(TAG, "---- Thằng OpenAds đã hoàn thành chưa (không bị che): " + (isAoaFinish ? "Đúng" : "Sai" ));
            Log.i(TAG, "---- Thằng Inter đã hoàn thành chưa (không bị che): " + (isInterFinish ? "Đúng" : "Sai"));
            if (isAoaFinish && isInterFinish) {
                loadOpenAdmobAds();
            }
//            if (alertDialog != null) {
//                alertDialog.show();
//            } else if (adDialog != null) {
//                adDialog.show();
//            }
        }
    }

    private void  showAOAInterAdmob() {
        MobileAds.initialize(this.activity_context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                AdRequest adRequest = new AdRequest.Builder().build();
                InterstitialAd.load(activity_context, interAoaAdsAdmobId, adRequest,
                        new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                retryAttempt = 0;
                                Log.i(TAG, "===============INTER AOA LOADED=================\n" );
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
                                                Log.i(TAG, "===============INTER FINISH=================\n" );new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        isInterFinish = true;
                                                    }
                                                }, 1000);
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
                                showInterAdmobAd();
                                Log.i(TAG, "===============INTER AOA SHOWING=================\n" );
                            }
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                retryAttempt++;
                                // Handle the error
                                Log.i(TAG, "===============INTER LOAD FAILED=================\n" + loadAdError  );
                                interAdmobAd = null;
                                if (retryAttempt <2 ) {
                                    showAOAInterAdmob();
                                }
                            }
                        });
            }
        });
    }

    private void initializeApplovinAds() {
        AppLovinSdk.getInstance(this.activity_context).setMediationProvider("max");
        AppLovinSdk.getInstance(this.activity_context).initializeSdk(configuration -> {
//            showAOAInterAdmob();
            remainingTimer = interAdsLoopTimer;
            createInterApplovinAd();
            createOpenAdsApplovinAd();
            createApplovinBannerAd();
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
                                        Log.i(TAG, "CALL SHOW INTER APPLOVIN");
                                        showApplovinAd();
                                    }
                                });
                            }
                        }
                    });
//            try {
//                AppLovinSdk.getInstance( this.activity_context ).showMediationDebugger();
//            } catch (Exception e) {
//                System.out.println("Error " + e.getMessage());
//            }
        });
    }


    private void showApplovinAd() {
        if (interApplovinAd.isReady() && isAoaFinish && isInterFinish) {
            interApplovinAd.showAd();
            Log.i(TAG, "SHOWING INTER APPLOVIN");
        } else {
            interApplovinAd.loadAd();
            Log.i(TAG, "INTER NOT AVAILABLE - > LOAD INTER APPLOVIN");
        }
    }

    private void createInterApplovinAd() {
        interApplovinAd = new MaxInterstitialAd(interApplovinId, (Activity) this.activity_context);
        interApplovinAd.setListener(new MaxAdListener() {
            @Override
            public void onAdLoaded(MaxAd maxAd) {
                retryAttempt = 0;
            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                retryAttempt++;
                long delayMillis = TimeUnit.SECONDS.toMillis((long) Math.pow(2, Math.min(6, retryAttempt)));
                new Handler().postDelayed(() -> interApplovinAd.loadAd(), delayMillis);
                Log.i(TAG, "INTER APPLOVIN LOAD FAILED - RETRY TIMES" + retryAttempt);
            }

            @Override
            public void onAdDisplayFailed(MaxAd maxAd, MaxError error) {
                interApplovinAd.loadAd();
            }

            @Override
            public void onAdDisplayed(MaxAd maxAd) {
                isInterFinish = false;

            }

            @Override
            public void onAdClicked(MaxAd maxAd) {
            }

            @Override
            public void onAdHidden(MaxAd maxAd) {
                interApplovinAd.loadAd();
                isInterFinish = true;
                remainingTimer = interAdsLoopTimer;
            }
        });

        // Load the first ad
        interApplovinAd.loadAd();
    }

    private void createOpenAdsApplovinAd() {
        maxAppOpenAd = new MaxAppOpenAd( openApplovinId, activity_context);
        maxAppOpenAd.setListener( new MaxAdListener() {
            @Override
            public void onAdLoaded(@NonNull MaxAd maxAd) {
                Log.i(TAG, "APPLOVIN OPEN LOAD SUCCESS");
            }

            @Override
            public void onAdDisplayed(@NonNull MaxAd maxAd) {

            }

            @Override
            public void onAdHidden(@NonNull MaxAd maxAd) {
                maxAppOpenAd.loadAd();
            }

            @Override
            public void onAdClicked(@NonNull MaxAd maxAd) {

            }

            @Override
            public void onAdLoadFailed(@NonNull String s, @NonNull MaxError maxError) {
                Log.e(TAG, "APPLOVIN OPEN LOAD FAILED");
                Log.e(TAG, s + String.valueOf(maxError));
            }

            @Override
            public void onAdDisplayFailed(@NonNull MaxAd maxAd, @NonNull MaxError maxError) {
                maxAppOpenAd.loadAd();
            }
        });
    }

    private void showAdIfReady()
    {
        if ( maxAppOpenAd == null || !AppLovinSdk.getInstance( activity_context ).isInitialized() )  {
            return;
        }
        if ( maxAppOpenAd.isReady() )
        {
            maxAppOpenAd.showAd( openApplovinId );
        }
        else
        {
            maxAppOpenAd.loadAd();
        }
    }

    private void createApplovinBannerAd() {
        maxAdView = new MaxAdView( bannerApplovinId, activity_context );
        maxAdView.setListener( new MaxAdViewAdListener() {
            @Override
            public void onAdLoaded(@NonNull MaxAd maxAd) {

            }

            @Override
            public void onAdDisplayed(@NonNull MaxAd maxAd) {

            }

            @Override
            public void onAdHidden(@NonNull MaxAd maxAd) {

            }

            @Override
            public void onAdClicked(@NonNull MaxAd maxAd) {

            }

            @Override
            public void onAdLoadFailed(@NonNull String s, @NonNull MaxError maxError) {

            }

            @Override
            public void onAdDisplayFailed(@NonNull MaxAd maxAd, @NonNull MaxError maxError) {

            }

            @Override
            public void onAdExpanded(@NonNull MaxAd maxAd) {

            }

            @Override
            public void onAdCollapsed(@NonNull MaxAd maxAd) {

            }
        } );
        WindowManager windowManager = (WindowManager) activity_context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels * nativeRatioWidthScreen);;
        // Get the adaptive banner height.
//        int heightDp = MaxAdFormat.BANNER.getAdaptiveSize( activity_context ).getHeight();
        int heightPx = (int) (displayMetrics.heightPixels * nativeRatioHeightScreen);
//        int heightPx = AppLovinSdkUtils.dpToPx( activity_context, screenHeight );

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, heightPx);
        layoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        maxAdView.setLayoutParams(layoutParams);
        maxAdView.setExtraParameter( "adaptive_banner", "true" );
        maxAdView.setLocalExtraParameter( "adaptive_banner_height", heightPx );
        maxAdView.getAdFormat().getAdaptiveSize( heightPx, activity_context ).getHeight(); // Set your ad height to this value

        maxAdView.setBackgroundColor(Color.WHITE);
        ViewGroup rootView = activity_context.findViewById( android.R.id.content );
        rootView.addView( maxAdView );

        maxAdView.loadAd();
    }

    public static int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}

