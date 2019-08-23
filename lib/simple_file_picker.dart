import 'dart:async';

import 'package:flutter/services.dart';

class SimpleFilePicker {
  static const MethodChannel _channel =
      const MethodChannel('simple_file_picker');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
