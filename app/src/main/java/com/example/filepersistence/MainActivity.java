package com.example.filepersistence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText etEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEdit = findViewById(R.id.edit);
        String input = loadDateToLocalFile();
        if (!TextUtils.isEmpty(input)){
            etEdit.setText(input);
            etEdit.setSelection(input.length());
            Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = etEdit.getText().toString();
        saveDateToLocalFile(inputText);
    }

    private void saveDateToLocalFile(String inputText){
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            // 通过openFileOutput方法得到一个FileOutputStream对象
            fileOutputStream = openFileOutput("data", Context.MODE_PRIVATE);
            // 使用FileOutputStream对象构建出一个OutputStreamWriter对象
            // 接着使用OutputStreamWriter对象构建一个BufferedWriter对象
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            // 使用BufferedWriter对象将内容写入到文件中
            bufferedWriter.write(inputText);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null){
                    // 关闭BufferedWriter
                    bufferedWriter.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private String loadDateToLocalFile(){
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder content = new StringBuilder();
        try {
            // 通过openFileInput()方法得到一个FileInputStream对象
            fileInputStream = openFileInput("data");
            // 使用FileInputStream对象构建一个InputStreamReader对象
            // 然后使用InputStreamReader对象构建一个BufferedReader对象
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = "";
            // 每次读一行，若该行不为空，则进入循环
            while ((line = bufferedReader.readLine()) != null){
                // 将该行添加至StringBuilder对象中
                content.append(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return content.toString();
    }

}
