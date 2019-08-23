import 'package:flutter/services.dart';

class SimpleFilePicker {
  static const MethodChannel _channel =
      const MethodChannel('simple_file_picker');

  static Future<String> open({String contentType: "*/*"}) async {
    String path =
        await _channel.invokeMethod("filePicker", {"contentType": contentType});
    return path;
  }
}
