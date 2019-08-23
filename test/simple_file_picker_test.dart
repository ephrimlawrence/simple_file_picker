import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:simple_file_picker/simple_file_picker.dart';

void main() {
  const MethodChannel channel = MethodChannel('simple_file_picker');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await SimpleFilePicker.platformVersion, '42');
  });
}
