package study.codes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import study.codes.chapter12.Texture3DActivity;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private String[] chapterIndexs = new String[]{
        "Chapter1", "Chapter2", "Chapter3", "Chapter4", "Chapter5", "Chapter6", "Chapter7",
        "Chapter8", "Chapter9", "Chapter10",  "Chapter11", "Chapter12", "Chapter13","Chapter14",
        "Chapter15", "Chapter16", "Chapter17", "Chapter18", "Chapter19"
    };
    private String[] chapterDescription = new String[]{
        "Android应用和开发环境", "Android应用的界面编程", "Android的事件处理",
        "深入理解Activity和Fragment", "使用Intent和IntentFilter进行通信",
        "Android应用的资源", "图形和图像处理", "Android数据存储和IO",
        "使用ContentProvider实现数据共享", "Service和BroadcastReceiver", "多媒体应用开发",
        "OpenGL和3D开发", "Android网络应用", "管理Android手机桌面","传感器应用开发", "GPS应用开发",
        "整合高德Map服务", "合金弹头", "电子拍卖系统"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 创建一个List集合，List集合的元素是Map
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < chapterIndexs.length; i++) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("personName", chapterIndexs[i]);
            listItem.put("desc", chapterDescription[i]);
            listItems.add(listItem);
        }
        // 创建一个SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
            R.layout.simple_item,
            new String[] { "personName" , "desc"},
            new int[] { R.id.index , R.id.description });
        ListView list = (ListView) findViewById(R.id.chapterlist);
        // 为ListView设置Adapter
        list.setAdapter(simpleAdapter);

        // 为ListView的列表项的单击事件绑定事件监听器
        list.setOnItemClickListener(
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Log.d(TAG, position + " " + chapterIndexs[position] + " was clicked");
                    switch (position){
                        case 11:
                            Intent intent = new Intent(MainActivity.this, Texture3DActivity.class);
                            Log.d(TAG, chapterIndexs[position] + " was started");
                            startActivity(intent);
                            break;
                        default:
                            break;
                    }
                }
            }
        );

        list.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    Log.d(TAG, chapterIndexs[position] + " was selected");
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {
                }
            }
        );

    }
}
