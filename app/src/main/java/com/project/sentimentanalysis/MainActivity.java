package com.project.sentimentanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.cloud.sdk.core.http.ServiceCallback;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.tone_analyzer.v3.model.ToneOptions;
import com.ibm.watson.tone_analyzer.v3.model.ToneScore;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView txt1 ;
    EditText edtTxt1;
    Button btn1;
    String sentiment;
    boolean check = true;


    private class AskWatsonTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... TextToAnalyse) {
            System.out.println(edtTxt1.getText());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt1.setText("What is happening inside the thread");
                }
            });
            Authenticator authenticator = new IamAuthenticator("qOQvdMRiNw5sn9dNH6h0aJXrpRi0nMdzLzhzJYtKvdTs");
            ToneAnalyzer service = new ToneAnalyzer("2017-09-21", authenticator);

            ToneOptions toneOptions = new ToneOptions.Builder().text(String.valueOf(edtTxt1.getText())).build();
            ToneAnalysis tone = service.tone(toneOptions).execute().getResult();
            System.out.println(tone);


            sentiment = "Test Sentiment";
            System.out.println(tone);


            try {


                return tone.getDocumentTone().getTones().get(0).getToneId().toString();
            } catch (Exception exception) {

                check= false;
             //   Toast.makeText(getApplicationContext(),"Try Again Robot is confuse",Toast.LENGTH_SHORT).show();
                return null;
            }

        }

       @Override
       protected void onPostExecute(String result) {
         // txt1.setText("The message sentiment is "+ result);
           if(check ) {
               Intent intent = new Intent(MainActivity.this, ShowActivity.class);
               intent.putExtra("sentiment", result);

               startActivity(intent);
           }

           else
           {
               Toast.makeText(getApplicationContext(),"Try Again Robot is confuse",Toast.LENGTH_SHORT).show();
           }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txt1 =  (TextView) findViewById(R.id.txt1);
        edtTxt1 =  (EditText) findViewById(R.id.edttxt1);
        btn1 =  (Button) findViewById(R.id.btn1);


     btn1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             System.out.println("logging to the console that the button pressed for the test" + edtTxt1.getText());
             txt1.setText("Display at UI the sentiment to be checked for" +edtTxt1.getText());

             AskWatsonTask task = new AskWatsonTask();
             task.execute(new String[]{});
         }
     }

     );
    }


}