import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:simple_file_picker/simple_file_picker.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String path = 'Unknown';

  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    // initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  /*  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await SimpleFilePicker.getPath();
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
 */
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body:  Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text("Path: $path"),
              RaisedButton(
                child: Text("Open File Picker"),
                onPressed: () => SimpleFilePicker.getPath().then((var p) {
                  setState(() => this.path = p);
                }),
              ),
            ],
        ),
      ),
    );
  }
}
