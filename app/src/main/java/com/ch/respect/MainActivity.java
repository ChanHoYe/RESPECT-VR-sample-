package com.ch.respect;

import android.graphics.Color;
import android.os.Bundle;

import org.gearvrf.GVRActivity;
import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.scene_objects.GVRTextViewSceneObject;

public class MainActivity extends GVRActivity {
    private Main main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Set Main Scene
         * It will be displayed when app starts
         */
        main = new Main();
        setMain(main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        main.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        main.onPause();
    }

    private final class Main extends GVRMain {
        private GVRVideoPlayerObject mPlayerObj = null;

        @Override
        public void onInit(GVRContext gvrContext) throws Throwable {
            /*//Load texture
            GVRTexture texture = gvrContext.getAssetLoader().loadTexture(new GVRAndroidResource(gvrContext, R.drawable.__default_splash_screen__));

            //Create a rectangle with the texture we just loaded
            GVRSceneObject quad = new GVRSceneObject(gvrContext, 4, 2, texture);
            quad.getTransform().setPosition(0, 0, -3);

            //Add rectangle to the scene
            gvrContext.getMainScene().addSceneObject(quad);*/

            GVRSceneObject headTracker = new GVRSceneObject(gvrContext,
                    gvrContext.createQuad(0.1f, 0.1f),
                    gvrContext.getAssetLoader().loadTexture(new GVRAndroidResource(gvrContext, R.drawable.cursor)));
            headTracker.getTransform().setPosition(0.0f, 0.0f, -1.0f);
            headTracker.getRenderData().setDepthTest(false);
            headTracker.getRenderData().setRenderingOrder(100000);
            gvrContext.getMainScene().getMainCameraRig().addChildObject(headTracker);

            mPlayerObj = new GVRVideoPlayerObject(gvrContext);
            mPlayerObj.loadVideo("sdcard/DCIM/Gear 360/SAM_100_0528.mp4");
            mPlayerObj.setLooping(true);
            mPlayerObj.play();

            gvrContext.getMainScene().addSceneObject(mPlayerObj);

            GVRTextViewSceneObject text = new GVRTextViewSceneObject(gvrContext, "TEST");
            text.getTransform().setPosition(0.0f, 0.0f, -5.0f);
            text.getRenderData().setDepthTest(false);
            text.getRenderData().setRenderingOrder(100000);
            text.setTextColor(Color.WHITE);
            text.setTextSize(20);
            text.setBackgroundColor(Color.TRANSPARENT);
            gvrContext.getMainScene().getMainCameraRig().addChildObject(text);
        }

        @Override
        public SplashMode getSplashMode() {
            return SplashMode.NONE;
        }

        @Override
        public void onStep() {
            //Add update logic here
        }

        public void onResume() {
            if(mPlayerObj != null) {
                mPlayerObj.onResume();
            }
        }

        public void onPause() {
            if(mPlayerObj != null) {
                mPlayerObj.onPause();
            }
        }
    }
}
