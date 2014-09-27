package org.kaufer.matthew.ipfind;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URI;
import java.io.StringWriter;
import java.io.PrintWriter;
import com.loopj.android.http.*;

import org.apache.http.Header;


public class MyActivity extends Activity {

    public String ip = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView ipText = (TextView) findViewById(R.id.ip);
                ipText.setText("Loading....");
//                try {
//                    HttpClient httpclient = new DefaultHttpClient();
//                    HttpResponse response = httpclient.execute(new HttpGet(new URI("http://curlmyip.com/")));
//                    StatusLine statusLine = response.getStatusLine();
//                    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
//                        ByteArrayOutputStream out = new ByteArrayOutputStream();
//                        response.getEntity().writeTo(out);
//                        out.close();
//                        String ip = out.toString();
//                        ipText.setText(ip);
//                    } else{
//                        //Closes the connection.
//                        ipText.setText("Something bad happened. :( \n" + statusLine.getReasonPhrase());
//                        response.getEntity().getContent().close();
////                        throw new IOException(statusLine.getReasonPhrase());
//                    }
//                } catch (Exception e){
//                    ipText.setText("Something bad happened. :( \n" + exceptionToString(e));
//                }
                AsyncHttpClient client = new AsyncHttpClient();
                RequestHandle requestHandle = client.get("http://curlmyip.com/", new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        String s = new String(response);
                        ipText.setText(s);
                        ip = s;
                        IPFind.setIP(ip);
                        Intent intent = new Intent(getApplicationContext(), TweetIP.class);//go to the IP activity
                        startActivity(intent);//go to the tweet page

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        String s = new String(errorResponse);
                        ipText.setText(s);
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    public String exceptionToString(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString(); // stack trace as a string
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return R.id.action_settings == id || super.onOptionsItemSelected(item);
    }

}
