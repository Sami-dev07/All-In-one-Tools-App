package com.alltools.toolbox.utility.calculator.CommonToolsActivity.HiddenCamera.Helper;

import android.hardware.Camera;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CameraHelper {
    public static Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int screenWidth, int screenHeight) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) screenHeight / screenWidth;

        if (sizes == null)
            return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.height / size.width;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;

            int diff = Math.abs(size.height - screenHeight) + Math.abs(size.width - screenWidth);
            if (diff < minDiff) {
                optimalSize = size;
                minDiff = diff;
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                int diff = Math.abs(size.height - screenHeight) + Math.abs(size.width - screenWidth);
                if (diff < minDiff) {
                    optimalSize = size;
                    minDiff = diff;
                }
            }
        }

        return optimalSize;
    }


    public static Camera getDefaultCameraInstance() {
        return Camera.open();
    }



    public static Camera getDefaultCameraInstance(boolean useFrontCamera) {
        int cameraId = findCameraId(useFrontCamera);
        if (cameraId == -1) {
            return null; // No suitable camera found
        }

        return Camera.open(cameraId);
    }

    private static int findCameraId(boolean useFrontCamera) {
        int numberOfCameras = Camera.getNumberOfCameras();
        int desiredFacing = useFrontCamera ? Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;

        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == desiredFacing) {
                return i;
            }
        }
        return -1;
    }



    public static String getOutputMediaFileName() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        return "intruder" + timeStamp + ".mp4";
    }

}
