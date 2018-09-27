package com.ch.respect;

import android.os.Bundle;
import android.os.Environment;

import org.gearvrf.GVRActivity;
import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRTexture;

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

            mPlayerObj = new GVRVideoPlayerObject(gvrContext);
            mPlayerObj.loadVideo("file://"+Environment.getExternalStorageDirectory().getPath()+"/DCIM/Gear 360/SAM_100_0528.mp4");
            mPlayerObj.setLooping(true);
            mPlayerObj.play();

            //Load texture
            GVRTexture texture = gvrContext.getAssetLoader().loadTexture(new GVRAndroidResource(gvrContext, R.drawable.__default_splash_screen__));

            //Create a rectangle with the texture we just loaded
            GVRSceneObject quad = new GVRSceneObject(gvrContext, 4, 2, texture);
            quad.getTransform().setPosition(0, 0, -3);

            //Add rectangle to the scene
            gvrContext.getMainScene().addSceneObject(quad);
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
