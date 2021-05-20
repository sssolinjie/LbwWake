package com.example.lbwWake.wakeup;
import com.example.lbwWake.wakeup.MyLogger;
import com.example.lbwWake.wakeup.WakeUpResult;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
public class SimpleWakeupListener implements IWakeupListener{

    private static final String TAG = "SimpleWakeupListener";
    private final MethodChannel channel;

    public SimpleWakeupListener(MethodChannel listener) {
        this.channel = listener;
    }

    @Override
    public void onSuccess(String word, WakeUpResult result) {
        channel.invokeMethod("wake", word);
        MyLogger.info(TAG, "唤醒成功，唤醒词：" + word);
    }

    @Override
    public void onStop() {
        MyLogger.info(TAG, "唤醒词识别结束：");
    }

    @Override
    public void onError(int errorCode, String errorMessge, WakeUpResult result) {
        MyLogger.info(TAG, "唤醒错误：" + errorCode + ";错误消息：" + errorMessge + "; 原始返回" + result.getOrigalJson());
    }

    @Override
    public void onASrAudio(byte[] data, int offset, int length) {
       // MyLogger.error(TAG, "audio data： " + data.length);
    }
}
