package com.example.lbwWake;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import com.baidu.speech.asr.SpeechConstant;
import com.example.lbwWake.mini.MiniRecog;
import com.example.lbwWake.wakeup.IWakeupListener;
import com.example.lbwWake.wakeup.Mywakeup;
import com.example.lbwWake.wakeup.SimpleWakeupListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** LbwWakePlugin */
public class LbwWakePlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  protected BaiduConfig config;
  private Context context;
  protected Application application;

  protected Mywakeup myWakeup;
  protected MiniRecog recog;


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "lbwWake");
    channel.setMethodCallHandler(this);
    application = ((Application) flutterPluginBinding.getApplicationContext());
    context = application.getApplicationContext();
    config = BaiduConfig.getInstance();
  }
  //此处是旧的插件加载注册方式
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "lbwWake");
    channel.setMethodCallHandler(new LbwWakePlugin().initPlugin(channel, registrar));
  }
  public LbwWakePlugin initPlugin(MethodChannel methodChannel, Registrar registrar) {
    channel = methodChannel;
    context = registrar.context();
    application = ((Application)context.getApplicationContext());
    config = BaiduConfig.getInstance();
    return this;
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("initsdk")) {
      Map<String, String> params = call.arguments();
      initSDK(params);
    }else if (call.method.equals("startspeak")) {
      startspeak();
    }else if (call.method.equals("stopspeak")) {
      stopspeak();
    }else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  void initSDK(Map<String, String> params){
    config = BaiduConfig.getInstance();
    config.SetAppId(params.get("appid"));
    config.SetAppKey(params.get("appkey"));
    config.SetSecretKey(params.get("secretkey"));
    //唤醒OK
    SimpleWakeupListener listener = new SimpleWakeupListener(channel);
    myWakeup = new Mywakeup(context, listener);
    Map<String, Object> params1 = new HashMap<String, Object>();
    params1.put(SpeechConstant.WP_WORDS_FILE, "assets:///WakeUp.bin");
    params1.put(SpeechConstant.APP_KEY, config.getAppKey());
    params1.put(SpeechConstant.APP_ID, config.getAppId());
    params1.put(SpeechConstant.SECRET, config.getSecretKey());
    myWakeup.start(params1);
    recog = new MiniRecog(context, channel, config);
  }

  void startspeak(){
    recog.start();
  }
  void stopspeak(){
    recog.stop();
  }
}
