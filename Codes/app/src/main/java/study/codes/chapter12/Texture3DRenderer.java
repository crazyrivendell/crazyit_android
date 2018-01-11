package study.codes.chapter12;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import study.codes.R;
import study.codes.utils.BufferUtil;

/**
 * Created by wuminlai on 2018/1/8.
 * Draw a cube with texture
 */
public class Texture3DRenderer implements Renderer {
    // 立方体的顶点坐标（一共是36个顶点，组成12个三角形）
    private float[] cubeVertices = { -0.6f, -0.6f, -0.6f, -0.6f, 0.6f,
        -0.6f, 0.6f, 0.6f, -0.6f, 0.6f, 0.6f, -0.6f, 0.6f, -0.6f, -0.6f,
        -0.6f, -0.6f, -0.6f, -0.6f, -0.6f, 0.6f, 0.6f, -0.6f, 0.6f, 0.6f,
        0.6f, 0.6f, 0.6f, 0.6f, 0.6f, -0.6f, 0.6f, 0.6f, -0.6f, -0.6f,
        0.6f, -0.6f, -0.6f, -0.6f, 0.6f, -0.6f, -0.6f, 0.6f, -0.6f, 0.6f,
        0.6f, -0.6f, 0.6f, -0.6f, -0.6f, 0.6f, -0.6f, -0.6f, -0.6f, 0.6f,
        -0.6f, -0.6f, 0.6f, 0.6f, -0.6f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f,
        0.6f, 0.6f, -0.6f, 0.6f, 0.6f, -0.6f, -0.6f, 0.6f, 0.6f, -0.6f,
        -0.6f, 0.6f, -0.6f, -0.6f, 0.6f, 0.6f, -0.6f, 0.6f, 0.6f, 0.6f,
        0.6f, 0.6f, 0.6f, 0.6f, -0.6f, -0.6f, 0.6f, -0.6f, -0.6f, -0.6f,
        -0.6f, -0.6f, -0.6f, 0.6f, -0.6f, -0.6f, 0.6f, -0.6f, 0.6f, 0.6f,
        -0.6f, 0.6f, -0.6f, };
    // 定义立方体所需要的6个面（一共是12个三角形所需的顶点）
    private byte[] cubeFacets = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
        13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
        30, 31, 32, 33, 34, 35, };
    // 定义纹理贴图的坐标数据
    private float[] cubeTextures = { 1.0000f, 1.0000f, 1.0000f, 0.0000f,
        0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 1.0000f, 1.0000f,
        1.0000f, 0.0000f, 1.0000f, 1.0000f, 1.0000f, 1.0000f, 0.0000f,
        1.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 1.0000f, 0.0000f,
        1.0000f, 1.0000f, 1.0000f, 1.0000f, 0.0000f, 1.0000f, 0.0000f,
        0.0000f, 0.0000f, 0.0000f, 1.0000f, 0.0000f, 1.0000f, 1.0000f,
        1.0000f, 1.0000f, 0.0000f, 1.0000f, 0.0000f, 0.0000f, 0.0000f,
        0.0000f, 1.0000f, 0.0000f, 1.0000f, 1.0000f, 1.0000f, 1.0000f,
        0.0000f, 1.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 1.0000f,
        0.0000f, 1.0000f, 1.0000f, 1.0000f, 1.0000f, 0.0000f, 1.0000f,
        0.0000f, 0.0000f, 0.0000f, 0.0000f, 1.0000f };
    private Context context;
    private FloatBuffer cubeVerticesBuffer;
    private ByteBuffer cubeFacetsBuffer;
    private FloatBuffer cubeTexturesBuffer;

    private int texture;

    public float anglex = 0f;
    public float angley = 0f;

    public Texture3DRenderer(Context main)
    {
        this.context = main;
        cubeVerticesBuffer = BufferUtil.floatBufferUtil(cubeVertices);
        cubeFacetsBuffer = ByteBuffer.wrap(cubeFacets);
        cubeTexturesBuffer = BufferUtil.floatBufferUtil(cubeTextures);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(0, 0, 0, 0);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // 启用2D纹理贴图
        gl.glEnable(GL10.GL_TEXTURE_2D);
        // 装载纹理
        loadTexture(gl);
    }
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        float ratio = (float) width / height;
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
    }
    public void onDrawFrame(GL10 gl)
    {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(0f, 0.0f, -2.0f);
        gl.glRotatef(angley, 0, 1, 0);
        gl.glRotatef(anglex, 1, 0, 0);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeVerticesBuffer);
        // 设置贴图的坐标数据
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, cubeTexturesBuffer);
        // 执行纹理贴图
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);

        gl.glDrawElements(GL10.GL_TRIANGLES, cubeFacetsBuffer.remaining(),
            GL10.GL_UNSIGNED_BYTE, cubeFacetsBuffer);

        gl.glFinish();

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }
    private void loadTexture(GL10 gl)
    {
        Bitmap bitmap = null;
        try
        {
            // 加载位图
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.sand);
            int[] textures = new int[1];
            // 指定生成N个纹理（第一个参数指定生成一个纹理）
            // textures数组将负责存储所有纹理的代号
            gl.glGenTextures(1, textures, 0);
            // 获取textures纹理数组中的第一个纹理
            texture = textures[0];
            // 通知OpenGL将texture纹理绑定到GL10.GL_TEXTURE_2D目标中
            gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
            // 设置纹理被缩小（距离视点很远时被缩小）时的滤波方式
            gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
            // 设置纹理被放大（距离视点很近时被方法）时的滤波方式
            gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
            // 设置在横向、纵向上都是平铺纹理
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
                GL10.GL_REPEAT);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
                GL10.GL_REPEAT);
            // 加载位图生成纹理
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        }
        finally
        {
            // 生成纹理之后，回收位图
            if (bitmap != null)
                bitmap.recycle();
        }
    }
}
