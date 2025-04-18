package com.tencent.yolov8ncnn;

import android.content.res.AssetManager;
import android.view.Surface;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

// 这是你需要添加的 native 声明（你自己要用 jni 实现）
public class YOLOv8Ncnn {
    public native boolean loadModel(AssetManager mgr, int taskid, int modelid, int cpugpu);
    public native boolean loadModelFromBuffer(byte[] param, byte[] bin, int cpugpu);  // 你要自己加的 JNI 方法
    public native boolean openCamera(int facing);
    public native boolean closeCamera();
    public native boolean setOutputWindow(Surface surface);

    public boolean loadModelFromPath(String paramPath, String binPath, int cpugpu) {
        byte[] param = loadFile(paramPath);
        byte[] bin = loadFile(binPath);

        if (param == null || bin == null) {
            return false;
        }

        return loadModelFromBuffer(param, bin, cpugpu);
    }

    private byte[] loadFile(String path) {
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            fis.read(buffer);
            fis.close();
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static {
        System.loadLibrary("yolov8ncnn");
    }
}
