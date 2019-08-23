import 'package:flutter/material.dart';
import 'package:simple_file_picker/simple_file_picker.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String path = "No file selected";

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text("Path: $path"),
              RaisedButton(
                child: Text("Open File Picker"),
                onPressed: () => SimpleFilePicker.open().then((var p) {
                  setState(() => this.path = p);
                }),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
