package study.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

import study.opengl.renderers.Texture3DRenderer;
/**
 * Created by wuminlai on 2018/1/9.
 */

public class Texture3DActivity extends Activity
    implements OnGestureListener {
    // 定义旋转角度
    static final float ROTATE_FACTOR = 60;
    // 定义手势检测器实例
    GestureDetector detector;
    Texture3DRenderer texture3DRenderer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 创建一个GLSurfaceView，用于显示OpenGL绘制的图形
        GLSurfaceView glView = new GLSurfaceView(this);
        // 创建GLSurfaceView的内容绘制器
        texture3DRenderer = new Texture3DRenderer(this);
        // 为GLSurfaceView设置绘制器
        glView.setRenderer(texture3DRenderer);
        setContentView(glView);
        // 创建手势检测器
        detector = new GestureDetector(this, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        // 将该Activity上的触碰事件交给GestureDetector处理
        return detector.onTouchEvent(me);
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        velocityX = velocityX > 2000 ? 2000 : velocityX;
        velocityX = velocityX < -2000 ? -2000 : velocityX;
        velocityY = velocityY > 2000 ? 2000 : velocityY;
        velocityY = velocityY < -2000 ? -2000 : velocityY;
        // 根据横向上的速度计算沿Y轴旋转的角度
        texture3DRenderer.angley += velocityX * ROTATE_FACTOR / 4000;
        // 根据纵向上的速度计算沿X轴旋转的角度
        texture3DRenderer.anglex += velocityY * ROTATE_FACTOR / 4000;
        return true;
    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent event) {
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2,
                            float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent event) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return false;
    }
}
