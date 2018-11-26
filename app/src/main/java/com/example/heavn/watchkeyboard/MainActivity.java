package com.example.heavn.watchkeyboard;

import android.Manifest;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextPaint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener,View.OnLongClickListener{
    private EditText text;
    private Button button1,button2,button3,button4,button5,button6,button7,button8,button9;
    private Button direction,hide,back,change,space,enter;
    private Button buttons[] = new Button[9];
    private double x,y,x1,y1,x2,y2;
    private boolean flag = true;//初始化为小写英文界面
    private boolean dir = true;//初始化为小写英文界面
    private PopupWindow popupWindow,capital_popupWindow;
    private LinearLayout watch;
    private TextView c1,c2,c3,c4;
    private ImageView help;
    private Vibrator vibrator;//震动

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                new String[] {
                        Manifest.permission.VIBRATE

                },
                10000
        );

        text = findViewById(R.id.text);
        text.setOnClickListener(this);
        watch = findViewById(R.id.watch);
        help = findViewById(R.id.help);
        help.setOnClickListener(this);
        vibrator  = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        disableShowInput();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        popupWindow.dismiss();
    }

    @Override
    public void onClick(View v) {
        //计算字符长度，便于光标的移动
        int index = text.getSelectionStart();
        Editable editable = text.getText();
        TextPaint mTextPaint = text.getPaint();
        float textWidth = mTextPaint.measureText(text.getText().toString());
        Log.e("content",""+text.getText());
        Log.e("width",""+textWidth);
        int n = (int)(text.getText().length()/(textWidth/(watch.getWidth())));
        Log.e("width",""+n);
        switch (v.getId()){
            case R.id.text:
                if (popupWindow == null || !popupWindow.isShowing()) {
                    initPopupWindowView();
                    popupWindow.showAsDropDown(v,0,0);
                }
                break;
            case R.id.direction:
                move();
                break;
            case R.id.hide:
                popupWindow.dismiss();
                break;
            case R.id.back:
                int keyCode = KeyEvent.KEYCODE_DEL;
                KeyEvent keyEventDown = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
                KeyEvent keyEventUp = new KeyEvent(KeyEvent.ACTION_UP, keyCode);
                text.onKeyDown(keyCode, keyEventDown);
                text.onKeyUp(keyCode, keyEventUp);
                break;
            case R.id.change:
                change();
                break;
            case R.id.space:
                editable.insert(index, "\u0020");
                break;
            case R.id.enter:
                editable.insert(index, "\n");
                break;
            //光标向上
            case R.id.button2:
                if(text.getSelectionStart() - n > 0  && button2.getText().equals("move_up")){
                    text.setSelection(text.getSelectionStart() - n);
                }
                break;
            //光标向左
            case R.id.button4:
                if (text.getSelectionStart() != 0 && button4.getText().equals("move_left")){
                    text.setSelection(text.getSelectionStart()-1);
                }
                break;
            //光标向右
            case R.id.button6:
                if (text.getSelectionStart() != text.getText().length() && button6.getText().equals("move_right")){
                    text.setSelection(text.getSelectionStart()+1);
                }
                break;
            //光标向下
            case R.id.button8:
                if(text.getSelectionStart() + n < text.getText().length() && button8.getText().equals("move_down")){
                    text.setSelection(text.getSelectionStart() + n);
                }
                break;
            case R.id.help:
                Intent intent = new Intent(MainActivity.this,Help.class);
                startActivity(intent);
                break;
            default:
                break;

        }

    }

    @Override
    public boolean onLongClick(View v) {

        //正则表达式判断是小写英文字母长按才有效
        String s = "^[a-z]+$";
        Pattern pattern = Pattern.compile(s);
        //获取点击button上的文字内容
        Button button = (Button)v;
        String content = ""+button.getText();
        //震动
        if(pattern.matches(s,content)){
            vibrator.vibrate(40);
        }
        switch (v.getId()){
            case R.id.back:
                vibrator.vibrate(40);
                text.getText().clear();
                back.setBackgroundResource(R.drawable.clear);
                break;
            case R.id.button2:
                if (pattern.matches(s,button2.getText())){
                    button2.setText(button2.getText().toString().toUpperCase());
                    button2.setBackgroundResource(R.drawable.c_abc);
                }
                break;
            case R.id.button3:
                if (pattern.matches(s,button3.getText())){
                    button3.setText(button3.getText().toString().toUpperCase());
                    button3.setBackgroundResource(R.drawable.c_def);
                }
                break;
            case R.id.button4:
                if (pattern.matches(s,button4.getText())){
                    button4.setText(button4.getText().toString().toUpperCase());
                    button4.setBackgroundResource(R.drawable.c_ghi);
                }
                break;
            case R.id.button5:
                if (pattern.matches(s,button5.getText())){
                    button5.setText(button5.getText().toString().toUpperCase());
                    button5.setBackgroundResource(R.drawable.c_jkl);
                }
                break;
            case R.id.button6:
                if (pattern.matches(s,button6.getText())){
                    button6.setText(button6.getText().toString().toUpperCase());
                    button6.setBackgroundResource(R.drawable.c_mno);
                }
                break;
            case R.id.button7:
                if (pattern.matches(s,button7.getText())){
                    button7.setText(button7.getText().toString().toUpperCase());
                    button7.setBackgroundResource(R.drawable.c_pqrs);
                }
                break;
            case R.id.button8:
                if (pattern.matches(s,button8.getText())){
                    button8.setText(button8.getText().toString().toUpperCase());
                    button8.setBackgroundResource(R.drawable.c_tuv);
                }
                break;
            case R.id.button9:
                if (pattern.matches(s,button2.getText())){
                    button9.setText(button9.getText().toString().toUpperCase());
                    button9.setBackgroundResource(R.drawable.c_wxyz);
                }
                break;
            default:
                break;

        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //获取点击button上的文字内容
        Button button = (Button)v;
        String content = ""+button.getText();
        //button的中点坐标
//        x1 = (v.getX()+v.getWidth())/b2;
//        y1 = (v.getY()+v.getHeight())/b2;

        //取出文字当中的每个符号存到数组里面
        char[] marks = new char[content.length()];
        for(int i=0;i<content.length();i++){
            marks[i] = content.charAt(i);
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN && !content.equals("")){
            x1 = event.getX();
            y1 = event.getY();

            //按钮点击操作，震动30ms，并且颜色变成灰色
            vibrator.vibrate(20);
            switch (content){
                case ".,?!":
                    button.setBackgroundResource(R.drawable.d_mark);
                    break;
                case "abc":
                    button.setBackgroundResource(R.drawable.d_abc);
                    break;
                case "def":
                    button.setBackgroundResource(R.drawable.d_def);
                    break;
                case "ghi":
                    button.setBackgroundResource(R.drawable.d_ghi);
                    break;
                case "jkl":
                    button.setBackgroundResource(R.drawable.d_jkl);
                    break;
                case "mno":
                    button.setBackgroundResource(R.drawable.d_mno);
                    break;
                case "pqrs":
                    button.setBackgroundResource(R.drawable.d_pqrs);
                    break;
                case "tuv":
                    button.setBackgroundResource(R.drawable.d_tuv);
                    break;
                case "wxyz":
                    button.setBackgroundResource(R.drawable.d_wxyz);
                    break;
                case "/…@":
                    button.setBackgroundResource(R.drawable.d_1);
                    break;
                case ":&;\"":
                    button.setBackgroundResource(R.drawable.d_2);
                    break;
                case "(*)":
                    button.setBackgroundResource(R.drawable.d_3);
                    break;
                case "123":
                    button.setBackgroundResource(R.drawable.d_4);
                    break;
                case "4567":
                    button.setBackgroundResource(R.drawable.d_5);
                    break;
                case "890":
                    button.setBackgroundResource(R.drawable.d_6);
                    break;
                case "#=%":
                    button.setBackgroundResource(R.drawable.d_7);
                    break;
                case "+×-÷":
                    button.setBackgroundResource(R.drawable.d_8);
                    break;
                case "_~\\":
                    button.setBackgroundResource(R.drawable.d_9);
                    break;
                case "button_direction":
                    button.setBackgroundResource(R.drawable.d_direction);
                    break;
                case "button_return":
                    button.setBackgroundResource(R.drawable.d_return);
                    break;
                case "button_hide":
                    button.setBackgroundResource(R.drawable.d_hide);
                    break;
                case "button_back":
                    button.setBackgroundResource(R.drawable.d_back);
                    break;
                case "button_marks":
                    button.setBackgroundResource(R.drawable.d_marks);
                    break;
                case "button_markabc":
                    button.setBackgroundResource(R.drawable.d_markabc);
                    break;
                case "button_space":
                    button.setBackgroundResource(R.drawable.d_space);
                    break;
                case "button_enter":
                    button.setBackgroundResource(R.drawable.d_enter);
                    break;
                case "move_up":
                    button.setBackgroundResource(R.drawable.d_up);
                    break;
                case "move_left":
                    button.setBackgroundResource(R.drawable.d_left);
                    break;
                case "move_right":
                    button.setBackgroundResource(R.drawable.d_right);
                    break;
                case "move_down":
                    button.setBackgroundResource(R.drawable.d_down);
                    break;
                default:
                    break;
            }
//            Log.e("x1",""+content+x1);
//            Log.e("y1",""+content+y1);
        }else if(event.getAction() == MotionEvent.ACTION_UP && !content.equals("")) {
            //正则表达式判断是大写英文字母
            String s = "^[A-Z]+$";
            Pattern pattern = Pattern.compile(s);
//            Log.e("content",content);
            //大写英文字母手指离开后恢复界面
            if (pattern.matches(s,content)){
                button.setText(content.toLowerCase());
                content = content.toLowerCase();
                switch (content){
                    case "abc":
                        button.setBackgroundResource(R.drawable.abc);
                        break;
                    case "def":
                        button.setBackgroundResource(R.drawable.def);
                        break;
                    case "ghi":
                        button.setBackgroundResource(R.drawable.ghi);
                        break;
                    case "jkl":
                        button.setBackgroundResource(R.drawable.jkl);
                        break;
                    case "mno":
                        button.setBackgroundResource(R.drawable.mno);
                        break;
                    case "pqrs":
                        button.setBackgroundResource(R.drawable.pqrs);
                        break;
                    case "tuv":
                        button.setBackgroundResource(R.drawable.tuv);
                        break;
                    case "wxyz":
                        button.setBackgroundResource(R.drawable.wxyz);
                        break;
                    default:
                        break;
                }
            }
            //当按键的内容不是大写英文字母，而是其他小写英文字母，字符，以及功能键的情况的时候
            else{
                switch (content){
                    case ".,?!":
                        button.setBackgroundResource(R.drawable.mark);
                        break;
                    case "abc":
                        button.setBackgroundResource(R.drawable.abc);
                        break;
                    case "def":
                        button.setBackgroundResource(R.drawable.def);
                        break;
                    case "ghi":
                        button.setBackgroundResource(R.drawable.ghi);
                        break;
                    case "jkl":
                        button.setBackgroundResource(R.drawable.jkl);
                        break;
                    case "mno":
                        button.setBackgroundResource(R.drawable.mno);
                        break;
                    case "pqrs":
                        button.setBackgroundResource(R.drawable.pqrs);
                        break;
                    case "tuv":
                        button.setBackgroundResource(R.drawable.tuv);
                        break;
                    case "wxyz":
                        button.setBackgroundResource(R.drawable.wxyz);
                        break;
                    case "/…@":
                        button.setBackgroundResource(R.drawable.b1);
                        break;
                    case ":&;\"":
                        button.setBackgroundResource(R.drawable.b2);
                        break;
                    case "(*)":
                        button.setBackgroundResource(R.drawable.b3);
                        break;
                    case "123":
                        button.setBackgroundResource(R.drawable.b4);
                        break;
                    case "4567":
                        button.setBackgroundResource(R.drawable.b5);
                        break;
                    case "890":
                        button.setBackgroundResource(R.drawable.b6);
                        break;
                    case "#=%":
                        button.setBackgroundResource(R.drawable.b7);
                        break;
                    case "+×-÷":
                        button.setBackgroundResource(R.drawable.b8);
                        break;
                    case "_~\\":
                        button.setBackgroundResource(R.drawable.b9);
                        break;
                    case "button_direction":
                        button.setBackgroundResource(R.drawable.direction);
                        break;
                    case "button_return":
                        button.setBackgroundResource(R.drawable.retrun);
                        break;
                    case "button_hide":
                        button.setBackgroundResource(R.drawable.hide);
                        break;
                    case "button_back":
                        button.setBackgroundResource(R.drawable.back);
                        break;
                    case "button_marks":
                        button.setBackgroundResource(R.drawable.marks);
                        break;
                    case "button_markabc":
                        button.setBackgroundResource(R.drawable.markabc);
                        break;
                    case "button_space":
                        button.setBackgroundResource(R.drawable.space);
                        break;
                    case "button_enter":
                        button.setBackgroundResource(R.drawable.enter);
                        break;
                    case "move_up":
                        button.setBackgroundResource(R.drawable.up);
                        break;
                    case "move_left":
                        button.setBackgroundResource(R.drawable.left);
                        break;
                    case "move_right":
                        button.setBackgroundResource(R.drawable.right);
                        break;
                    case "move_down":
                        button.setBackgroundResource(R.drawable.down);
                        break;
                    default:
                        break;
                }

            }

            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
//            Log.e("x2",""+content+x2);
//            Log.e("y2",""+content+y2);
            //水平和垂直高度的绝对距离
            x = Math.abs(x1-x2);
            y = Math.abs(y1-y2);
//            Log.e("x",""+content+x);
//            Log.e("y",""+content+y);

            int index = text.getSelectionStart();
            Editable editable = text.getText();
            //当是四个字符时
            if(content.length() == 4){
                //向左滑
                if(x1 - x2 > 10 && x > y && x != 0 && y != 0) {
                    editable.insert(index, ""+marks[0]);
                }
                //向上滑
                else if(y1 - y2 > 10 && y > x && x != 0 && y != 0) {
                    editable.insert(index, ""+marks[1]);
                }
                //向右滑
                else if(x2 - x1 > 10 && x > y && x != 0 && y != 0) {
                    editable.insert(index, ""+marks[2]);
                }
                //向下滑
                else if(y2 - y1 > 10 && y > x && x != 0 && y != 0) {
                    editable.insert(index, ""+marks[3]);
                }else{
                    editable.insert(index, "");
                }
            }
            //当是三个字符的时候
            else if(content.length() == 3){
                //向左滑
                if(x1 - x2 > 10 && x > y && x != 0 && y != 0) {
                    editable.insert(index, ""+marks[0]);
                }
                //向上滑
                else if(y1 - y2 > 10 && y > x && x != 0 && y != 0) {
                    editable.insert(index, ""+marks[1]);
                }
                //向右滑
                else if(x2 - x1 > 10 && x > y && x != 0 && y != 0) {
                    editable.insert(index, ""+marks[2]);
                }else{
                    editable.insert(index, "");
                }
            }
        }

        return false;
    }

    //初始化
    private void initView(View v){
        button1 = v.findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button1.setOnTouchListener(this);
        button1.setOnLongClickListener(this);
        buttons[0] = button1;
        button2 = v.findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button2.setOnTouchListener(this);
        button2.setOnLongClickListener(this);
        buttons[1] = button2;
        button3 = v.findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button3.setOnTouchListener(this);
        button3.setOnLongClickListener(this);
        buttons[2] = button3;
        button4 = v.findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button4.setOnTouchListener(this);
        button4.setOnLongClickListener(this);
        buttons[3] = button4;
        button5 = v.findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button5.setOnTouchListener(this);
        button5.setOnLongClickListener(this);
        buttons[4] = button5;
        button6 = v.findViewById(R.id.button6);
        button6.setOnClickListener(this);
        button6.setOnTouchListener(this);
        button6.setOnLongClickListener(this);
        buttons[5] = button6;
        button7 = v.findViewById(R.id.button7);
        button7.setOnClickListener(this);
        button7.setOnTouchListener(this);
        button7.setOnLongClickListener(this);
        buttons[6] = button7;
        button8 = v.findViewById(R.id.button8);
        button8.setOnClickListener(this);
        button8.setOnTouchListener(this);
        button8.setOnLongClickListener(this);
        buttons[7] = button8;
        button9 = v.findViewById(R.id.button9);
        button9.setOnClickListener(this);
        button9.setOnTouchListener(this);
        button9.setOnLongClickListener(this);
        buttons[8] = button9;

        direction = v.findViewById(R.id.direction);
        direction.setOnClickListener(this);
        direction.setOnTouchListener(this);
        hide = v.findViewById(R.id.hide);
        hide.setOnClickListener(this);
        hide.setOnTouchListener(this);
        back = v.findViewById(R.id.back);
        back.setOnClickListener(this);
        back.setOnLongClickListener(this);
        back.setOnTouchListener(this);
        change = v.findViewById(R.id.change);
        change.setOnClickListener(this);
        change.setOnTouchListener(this);
        space = v.findViewById(R.id.space);
        space.setOnClickListener(this);
        space.setOnTouchListener(this);
        enter = v.findViewById(R.id.enter);
        enter.setOnClickListener(this);
        enter.setOnTouchListener(this);


    }

    //强制隐藏输入法的键盘
    private void disableShowInput(){
        if (android.os.Build.VERSION.SDK_INT <= 10){
            text.setInputType(InputType.TYPE_NULL);
        }else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus",boolean.class);
                method.setAccessible(true);
                method.invoke(text,false);
            }catch (Exception e) {//TODO: handle exception
            }
            try {
                method = cls.getMethod("setSoftInputShownOnFocus",boolean.class);
                method.setAccessible(true);
                method.invoke(text,false);
            }catch (Exception e) {//TODO: handle exception
            } } }

    //改变键盘界面，切换数字符号与英文界面
    private void change(){
        button1.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button5.setVisibility(View.VISIBLE);
        button7.setVisibility(View.VISIBLE);
        button9.setVisibility(View.VISIBLE);
        if(flag){
            button1.setText("/…@");
            button1.setBackgroundResource(R.drawable.b1);
            button2.setText(":&;\"");
            button2.setBackgroundResource(R.drawable.b2);
            button3.setText("(*)");
            button3.setBackgroundResource(R.drawable.b3);
            button4.setText("123");
            button4.setBackgroundResource(R.drawable.b4);
            button5.setText("4567");
            button5.setBackgroundResource(R.drawable.b5);
            button6.setText("890");
            button6.setBackgroundResource(R.drawable.b6);
            button7.setText("#=%");
            button7.setBackgroundResource(R.drawable.b7);
            button8.setText("+×-÷");
            button8.setBackgroundResource(R.drawable.b8);
            button9.setText("_~\\");
            button9.setBackgroundResource(R.drawable.b9);
            flag = false;
            change.setBackgroundResource(R.drawable.markabc);
            change.setText("button_markabc");
        }else{
            button1.setText(".,?!");
            button1.setBackgroundResource(R.drawable.mark);
            button2.setText("abc");
            button2.setBackgroundResource(R.drawable.abc);
            button3.setText("def");
            button3.setBackgroundResource(R.drawable.def);
            button4.setText("ghi");
            button4.setBackgroundResource(R.drawable.ghi);
            button5.setText("jkl");
            button5.setBackgroundResource(R.drawable.jkl);
            button6.setText("mno");
            button6.setBackgroundResource(R.drawable.mno);
            button7.setText("pqrs");
            button7.setBackgroundResource(R.drawable.pqrs);
            button8.setText("tuv");
            button8.setBackgroundResource(R.drawable.tuv);
            button9.setText("wxyz");
            button9.setBackgroundResource(R.drawable.wxyz);
            flag = true;
            change.setBackgroundResource(R.drawable.marks);
            change.setText("button_marks");
        }

    }

    //将界面改成光标移动的界面
    private void move(){
        if(dir){
            //这里button上的text大小写都使用是为了防止正则匹配的过滤
            button1.setText("");
            button1.setBackgroundResource(R.drawable.blank);
            button2.setText("move_up");
            button2.setBackgroundResource(R.drawable.up);
            button3.setText("");
            button3.setBackgroundResource(R.drawable.blank);
            button4.setText("move_left");
            button4.setBackgroundResource(R.drawable.left);
            button5.setText("");
            button5.setBackgroundResource(R.drawable.blank);
            button6.setText("move_right");
            button6.setBackgroundResource(R.drawable.right);
            button7.setText("");
            button7.setBackgroundResource(R.drawable.blank);
            button8.setText("move_down");
            button8.setBackgroundResource(R.drawable.down);
            button9.setText("");
            button9.setBackgroundResource(R.drawable.blank);
            button1.setVisibility(View.INVISIBLE);
            button3.setVisibility(View.INVISIBLE);
            button5.setVisibility(View.INVISIBLE);
            button7.setVisibility(View.INVISIBLE);
            button9.setVisibility(View.INVISIBLE);
            dir = false;
            direction.setBackgroundResource(R.drawable.retrun);
            direction.setText("button_return");
        }else{
            button1.setText(".,?!");
            button1.setBackgroundResource(R.drawable.mark);
            button2.setText("abc");
            button2.setBackgroundResource(R.drawable.abc);
            button3.setText("def");
            button3.setBackgroundResource(R.drawable.def);
            button4.setText("ghi");
            button4.setBackgroundResource(R.drawable.ghi);
            button5.setText("jkl");
            button5.setBackgroundResource(R.drawable.jkl);
            button6.setText("mno");
            button6.setBackgroundResource(R.drawable.mno);
            button7.setText("pqrs");
            button7.setBackgroundResource(R.drawable.pqrs);
            button8.setText("tuv");
            button8.setBackgroundResource(R.drawable.tuv);
            button9.setText("wxyz");
            button9.setBackgroundResource(R.drawable.wxyz);
            button1.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            button5.setVisibility(View.VISIBLE);
            button7.setVisibility(View.VISIBLE);
            button9.setVisibility(View.VISIBLE);
            dir = true;
            direction.setBackgroundResource(R.drawable.direction);
            direction.setText("button_direction");
        }
    }

    //弹出窗口初始化
    public void initPopupWindowView() {

        // // 获取自定义布局文件pop.xml的视图
        View customView = getLayoutInflater().inflate(R.layout.keyboard, null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupWindow = new PopupWindow(customView, watch.getWidth(), watch.getHeight());

        initView(customView);
    }

    //弹出窗口初始化
    public void initCapitalPopupWindowView(String text) {

        // // 获取自定义布局文件pop.xml的视图
        View customView = getLayoutInflater().inflate(R.layout.capital, null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        capital_popupWindow = new PopupWindow(customView, watch.getWidth(), watch.getHeight());

        c1 = customView.findViewById(R.id.c1);
        c2 = customView.findViewById(R.id.c2);
        c3 = customView.findViewById(R.id.c3);
        c4 = customView.findViewById(R.id.c4);

        //取出文字当中的每个符号存到数组里面
        char[] marks = new char[text.length()];
        for(int i=0;i<text.length();i++){
            marks[i] = text.charAt(i);
        }

        if(text.length() == 4){
            c1.setText(""+marks[0]);
            c2.setText(""+marks[1]);
            c3.setText(""+marks[2]);
            c4.setText(""+marks[3]);
        }else{
            c1.setText(""+marks[0]);
            c2.setText(""+marks[1]);
            c3.setText(""+marks[2]);
            c4.setVisibility(View.GONE);
        }
    }

}
