package com.pb.projectbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pb.projectbuilder.Activity.Login;

import org.apache.http.Header;


public class SignUp extends AppCompatActivity {
    private EditText email;
    private EditText passwd;
    private EditText passConfText;
    private EditText name;
    private TextView textView;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        //email confirm
        email = (EditText) findViewById(R.id.input_email);
        passwd = (EditText) findViewById(R.id.input_passwd);
        name = (EditText) findViewById(R.id.input_name);

        passConfText = (EditText) findViewById(R.id.confirm_passwd);
        textView = (TextView) findViewById(R.id.TextVIew_PwdProblem);
        textView.setVisibility(View.GONE);
        btn = (Button) findViewById(R.id.new_member_button);
        //intent

        passConfText.addTextChangedListener(passwordWatcher);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String pass1 = passwd.getText().toString();

                if (!isValidPassword(pass1)) {
                    passwd.setError("6자 이상으로 입력하세요");

                    RequestParams params = new RequestParams();
                    params.put("email", email.getText().toString().trim());
                    params.put("passwd", passwd.getText().toString().trim());
                    params.put("name", name.getText().toString().trim());

                    HttpClient.post("signup", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            Intent intetn1;
                            intetn1 = new Intent(SignUp.this, Login.class);
                            startActivity(intetn1);
                            finish();
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                        }
                    });






                }



            }
        });
    }

    //validating email id
  /*  private  boolean isValidEmail(String email){
        String EMAIL_PATTERN = "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+]";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }*/

    //validating password with retype password
    private boolean isValidPassword(String pass){
        if (pass != null && pass.length() > 7) {
            return true;
        }
        return false;
    }

    private boolean isConfirmPassword(String pass_1,String pass_2){
        if(pass_1.equals(pass_2)) {
            return true;
        }
        return false;
    }

    private final TextWatcher passwordWatcher  =  new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            textView.setVisibility(View.VISIBLE);
        }

        public void afterTextChanged (Editable s){
            if(passwd.getText().toString().equals(passConfText.getText().toString())){
                textView.setText("Password correct");
            } else textView.setText("Password Do not Matched");
            // textView.setVisibility(View.GONE);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}