package com.ephrim.simple_file_picker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.File;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * SimpleFilePickerPlugin
 */
public class SimpleFilePickerPlugin implements MethodCallHandler, PluginRegistry.ActivityResultListener, PluginRegistry.RequestPermissionsResultListener {
    private String contentType = "*/*";
    private Registrar mRegistrar;
    private static MethodChannel.Result channelResult;
    private static final int READ_REQUEST_CODE = 1;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 2;

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "simple_file_picker");
        SimpleFilePickerPlugin instance = new SimpleFilePickerPlugin(registrar);
        channel.setMethodCallHandler(instance);
        registrar.addActivityResultListener(instance);
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("filePicker")) {
            channelResult = result;
            contentType = String.valueOf(call.argument("contentType"));
            requestPermission();
        } else {
            result.notImplemented();
        }
    }

    private SimpleFilePickerPlugin(Registrar registrar) {
        this.mRegistrar = registrar;
    }

    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mRegistrar.activeContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(mRegistrar.activity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            initiateFilePicker();
        }

    }

    private void initiateFilePicker() {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        }

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        intent.setType(contentType);

        mRegistrar.activity().startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent resultData) {
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri;
            if (resultData != null) {
                uri = resultData.getData();
                File file = FileUtil.getFile(mRegistrar.activity().getApplicationContext(), uri);

                channelResult.success(file != null ? file.getAbsolutePath() : null);
            } else {
                channelResult.error("ERROR", "The file path was empty.", null);
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode,
                                              String[] permissions, int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initiateFilePicker();
            }
            return true;
        }
        return false;
    }

}
