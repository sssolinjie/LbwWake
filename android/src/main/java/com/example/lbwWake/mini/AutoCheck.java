package com.example.lbwWake.mini;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.baidu.speech.asr.SpeechConstant;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.net.ssl.HttpsURLConnection;
public class AutoCheck {
    private LinkedHashMap<String, Check> checks;

    private abstract static class Check {
        protected String errorMessage = null;

        protected String fixMessage = null;

        protected String infoMessage = null;

        protected StringBuilder logMessage;

        public Check() {
            logMessage = new StringBuilder();
        }

        public abstract void check();

        public boolean hasError() {
            return errorMessage != null;
        }

        public boolean hasFix() {
            return fixMessage != null;
        }

        public boolean hasInfo() {
            return infoMessage != null;
        }

        public boolean hasLog() {
            return !logMessage.toString().isEmpty();
        }

        public void appendLogMessage(String message) {
            logMessage.append(message + "\n");
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public String getFixMessage() {
            return fixMessage;
        }

        public String getInfoMessage() {
            return infoMessage;
        }

        public String getLogMessage() {
            return logMessage.toString();
        }
    }
}
