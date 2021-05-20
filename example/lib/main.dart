/*
 * @Author: your name
 * @Date: 2020-12-28 10:36:49
 * @LastEditTime: 2020-12-29 11:30:11
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /example/lib/main.dart
 */
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:lbwWake/lbwWake.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  LbwWake.initSDK(
      appid: "23174501",
      appkey: "HQg507YYm55jQ7o7bFjh3sEf",
      secretkey: "8dPMtSjHPqI5nwbDuIbxb8PhTPOL8XYa");
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
    LbwWake.addListen((String method, String data) {
      print(method + ":" + data);
      LbwWake.startspeak();
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await LbwWake.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on: $_platformVersion\n'),
        ),
      ),
    );
  }
}
