package study.opengl;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import study.opengl.renderers.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GLSurfaceView glView = new GLSurfaceView(this);

//        PolygonRenderer polygonRenderer = new PolygonRenderer();
//        glView.setRenderer(polygonRenderer);

//        RotatePolygonRenderer rotatePolygonRenderer = new RotatePolygonRenderer();
//        glView.setRenderer(rotatePolygonRenderer);

        Simple3DRenderer simple3DRenderer = new Simple3DRenderer();
        glView.setRenderer(simple3DRenderer);

        setContentView(glView);
    }
}
