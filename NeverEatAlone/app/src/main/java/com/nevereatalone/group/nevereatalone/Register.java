package com.nevereatalone.group.nevereatalone;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private String name = null, emailAddress = null, phoneNumber = null, userAge = null;

    private ImageButton defaultPicButton = null;
    private Button registerButton = null, cancelButton = null;
    private EditText etName = null, etEmail = null, etPhone = null, etAge = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_register);

        /* Get EditText views */
        etName = (EditText)(findViewById(R.id.name));
        etEmail = (EditText)(findViewById(R.id.emailAddress));
        etPhone = (EditText)(findViewById(R.id.phoneNumber));
        etAge = (EditText)(findViewById(R.id.userAge));

        /* Get profile picture button */
        defaultPicButton = (ImageButton)(findViewById(R.id.defaultPicButton));

        /* Get Button views */
        registerButton = (Button)(findViewById(R.id.registerButton));
        cancelButton = (Button)(findViewById(R.id.cancelButton));

        /* Set listeners */
        registerButton.setOnClickListener(new View.OnClickListener(){
            /* Called when the register button is clicked to validate the input */
            public void onClick(View v){
                register();
            }
        });
    }

    /* Registration method */
    private void register(){
        name = etFormatValue(etName);
        emailAddress = etFormatValue(etEmail);
        phoneNumber = etFormatValue(etPhone);
        userAge = etFormatValue(etAge);

        if(validateInput()){
            /* Display legal notice dialog */
            AlertDialog legalAlert = new AlertDialog.Builder(this).create();
            legalAlert.setMessage(getResources().getString(R.string.legal_notice));

            legalAlert.setOnDismissListener(
                    new AlertDialog.OnDismissListener(){
                        public void onDismiss(final DialogInterface dialog){
                            /* Start the main activity */
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }
            );

            legalAlert.show();
        }else{
            Toast.makeText(this, "Registration has failed", Toast.LENGTH_SHORT).show();
        }
    }

    /* Valid user input */
    private boolean validateInput(){
        if(name.isEmpty() || name.length() > 32){
            etName.setError("Please enter a valid name");
            return (false);
        }

        if(emailAddress.isEmpty() || !(Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches())){
            etEmail.setError("Please enter a valid E-Mail address");
            return (false);
        }

        if(phoneNumber.isEmpty() || !(Patterns.PHONE.matcher(phoneNumber).matches())){
            etPhone.setError("Please enter a valid phone number");
            return (false);
        }

        if(userAge.isEmpty()){
            etAge.setError("Please enter a valid age");
            return (false);
        }

        return (true);
    }

    /* Format string for validation */
    private String etFormatValue(EditText et){ return (et.getText().toString().trim()); }
}
