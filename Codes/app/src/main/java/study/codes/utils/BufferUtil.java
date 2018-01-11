package study.codes.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by wuminlai on 2018/1/8.
 */
public class BufferUtil {
    public static FloatBuffer floatBufferUtil(float[] a) {
        //先初始化buffer，数组的长度*4，因为一个float占4个字节
        ByteBuffer mbb = ByteBuffer.allocateDirect(a.length * 4);
        //数组排序用nativeOrder,根据本地的排列顺序，指定存储方式，是1． Little endian（小头）：将低序字节存储在起始地址
        // 2． Big endian（大头）：将高序字节存储在起始地址
        mbb.order(ByteOrder.nativeOrder());
        FloatBuffer mBuffer = mbb.asFloatBuffer();
        mBuffer.put(a);
        mBuffer.position(0);
        return mBuffer;
    }
    // 将数组a转化为intbuffer
    public static IntBuffer intBufferUtil(int[] a) {
        //先初始化buffer，数组的长度*4，因为一个float占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(a.length * 4);
        //数组排序用nativeOrder
        qbb.order(ByteOrder.nativeOrder());
        IntBuffer mBuffer = qbb.asIntBuffer();
        mBuffer.put(a);
        mBuffer.position(0);
        return mBuffer;
    }
    //创建一个长度为length的Floatbuffer,存储方式为opengl的存储方式，在每次调用put加入点后position都会加1，因此加入点后在绘图时候将position重置为0
    public static FloatBuffer getFloatBuffer(int length)
    {
        ByteBuffer mbb = ByteBuffer.allocateDirect(length * 4);
        mbb.order(ByteOrder.nativeOrder());
        FloatBuffer   mBuffer = mbb.asFloatBuffer();
        mBuffer.position(0);
        return mBuffer;
    }

    public static IntBuffer getIntBuffer(int length)
    {
        ByteBuffer mbb = ByteBuffer.allocateDirect(length * 4);
        mbb.order(ByteOrder.nativeOrder());
        IntBuffer   mBuffer = mbb.asIntBuffer();
        mBuffer.position(0);
        return mBuffer;
    }
}
