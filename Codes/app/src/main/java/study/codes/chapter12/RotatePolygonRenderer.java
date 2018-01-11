package study.codes.chapter12;

import android.opengl.GLSurfaceView.Renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import study.codes.utils.BufferUtil;


/**
 * Created by wuminlai on 2018/1/8.
 * Rotate Polygons
 */
public class RotatePolygonRenderer implements Renderer {
    float[] triangleData = new float[]{
        0.1f, 0.6f, 0.0f, //top point
        -0.3f, 0.0f, 0.0f, //left point
        0.3f, 0.1f, 0.0f //right point
    };
    int[] triangleColor = new int[]{
        65535, 0, 0, 0,  //top red
        0, 65535, 0, 0, //left green
        0, 0, 65535, 0  //right yellow
    };
    float[] rectData = new float[]{
        0.4f, 0.4f, 0.0f,  // right-top point
        0.4f, -0, 4f, 0.0f, // right-bottom point
        -0.4f, 0.4f, 0.0f, // left-top point
        -0.4f, -0.4f, 0.0f // left-bottom point
    };
    int[] rectColor = new int[]{
        0, 65535, 0, 0,
        0, 0, 65535, 0,
        65535, 0, 0, 0,
        65535, 65535, 0, 0
    };
    float[] rectData2 = new float[]{
        -0.4f, 0.4f, 0.0f, // left-top point
        0.4f, 0.4f, 0.0f,  // right-top point
        0.4f, -0, 4f, 0.0f, // right-bottom point
        -0.4f, -0.4f, 0.0f // left-bottom point
    };
    float[] pentacle = new float[]{
        0.4f, 0.4f, 0.0f,
        -0.2f, 0.3f, 0.0f,
        0.5f, 0.0f, 0f,
        -0.4f, 0.0f, 0f,
        -0.1f, -0.3f, 0f
    };

    FloatBuffer triangleDataBuffer;
    IntBuffer triangleColorBuffer;
    FloatBuffer rectDataBuffer;
    IntBuffer rectColorBuffer;
    FloatBuffer rect2DataBuffer;
    FloatBuffer pentacleBuffer;

    private float rotate;

    public RotatePolygonRenderer() {
        triangleDataBuffer = BufferUtil.floatBufferUtil(triangleData);
        triangleColorBuffer = BufferUtil.intBufferUtil(triangleColor);
        rectDataBuffer = BufferUtil.floatBufferUtil(rectData);
        rectColorBuffer = BufferUtil.intBufferUtil(rectColor);
        rect2DataBuffer = BufferUtil.floatBufferUtil(rectData2);
        pentacleBuffer = BufferUtil.floatBufferUtil(pentacle);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config){
        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(0, 0, 0, 0);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height){
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        float ratio = (float) width / height;
        gl.glFrustumf(-ratio,ratio, -1, 1, 1, 10);
    }

    @Override
    public void onDrawFrame(GL10 gl){
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glMatrixMode(GL10.GL_MODELVIEW);

        gl.glLoadIdentity();
        gl.glTranslatef(-0.32f, 0.6f, -1.2f);
        gl.glRotatef(rotate, 0.1f, 0.0f, 0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleDataBuffer);
        gl.glColorPointer(4, GL10.GL_FIXED, 0, triangleColorBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        gl.glLoadIdentity();
        gl.glTranslatef(0.1f, 0.6f, -1.4f);
        gl.glRotatef(rotate, 0f, 0f, 0.1f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectDataBuffer);
        gl.glColorPointer(4, GL10.GL_FIXED, 0, rectColorBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        gl.glLoadIdentity();
        gl.glTranslatef(-0.2f, -0.5f, -1.5f);
        gl.glRotatef(rotate, 0f, 0.1f, 0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rect2DataBuffer);
//        gl.glColorPointer(4, GL10.GL_FIXED, 0, rectColorBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        gl.glLoadIdentity();
        gl.glTranslatef(0.3f, -1.2f, -1.5f);
        gl.glRotatef(rotate, 0.0f, 0.0f, 0.0f);
        gl.glColor4f(1.0f, 0.2f, 0.2f, 0.0f);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, pentacleBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 5);

        gl.glFinish();
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        rotate += 1;
    }
}
