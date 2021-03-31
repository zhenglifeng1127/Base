# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#实体类
-keep class com.nxhz.base.bean.**{*;}

#第三方-----------
# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}


# okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.**{*; }
#RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent

# dagger2
-dontwarn dagger.**
-keep class dagger.** { *; }

# EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# loadsir
-dontwarn com.kingja.loadsir.**
-keep class com.kingja.loadsir.** {*;}

#zxing
-keep class com.google.zxing.{ *;}
-dontwarn com.google.zxing.**
#xpopup
 -dontwarn com.lxj.xpopup.widget.**
 -keep class com.lxj.xpopup.widget.**{*;}
#日历
 -keepclasseswithmembers class * {
     public <init>(android.content.Context);
 }
 -keep class your project path.MonthView {
     public <init>(android.content.Context);
 }
 -keep class your project path.WeekBar {
     public <init>(android.content.Context);
 }
 -keep class your project path.WeekView {
     public <init>(android.content.Context);
 }
 -keep class your project path.YearView {
     public <init>(android.content.Context);
 }
#微信
-dontwarn com.tencent.mm.**
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}
#citypicker
-keep class com.lljjcoder.**{
	*;
}
#3D 地图 V5.0.0之前：
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.amap.mapcore.*{*;}
-keep   class com.amap.api.trace.**{*;}


-keep class com.amap.api.**{*;}
#3D 地图 V5.0.0之后
-keep class com.amap.api.maps.**{*;}
-keep class com.autonavi.**{*;}
-keep class com.amap.api.trace.**{*;}
#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.loc.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}

#搜索
-keep class com.amap.api.services.**{*;}
#2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}
#导航

#推送
 -keepattributes Signature
 -keep class sun.misc.Unsafe { *; }
 -keep class com.taobao.** {*;}
 -keep class com.alibaba.** {*;}
 -keep class com.alipay.** {*;}
 -keep class com.ut.** {*;}
 -keep class com.ta.** {*;}
 -keep class anet.**{*;}
 -keep class anetwork.**{*;}
 -keep class org.android.spdy.**{*;}
 -keep class org.android.agoo.**{*;}
 -keep class android.os.**{*;}
 -keep class org.json.**{*;}
 -dontwarn com.taobao.**
 -dontwarn com.alibaba.**
 -dontwarn com.alipay.**
 -dontwarn anet.**
 -dontwarn org.android.spdy.**
 -dontwarn org.android.agoo.**
 -dontwarn anetwork.**
 -dontwarn com.ut.**
 -dontwarn com.ta.**
-keep class com.amap.api.navi.**{*;}
# 小米通道
-keep class com.xiaomi.** {*;}
-dontwarn com.xiaomi.**
# 华为通道
-keep class com.huawei.** {*;}
-dontwarn com.huawei.**
# GCM/FCM通道
-keep class com.google.firebase.**{*;}
-dontwarn com.google.firebase.**
# OPPO通道
-keep public class * extends android.app.Service
# VIVO通道
-keep class com.vivo.** {*;}
-dontwarn com.vivo.**
# 魅族通道
-keep class com.meizu.cloud.** {*;}
-dontwarn com.meizu.cloud.**

-keep class com.alibaba.sdk.android.oss.** {*; }

-dontwarn okio.**

-dontwarn org.apache.commons.codec.binary.**

