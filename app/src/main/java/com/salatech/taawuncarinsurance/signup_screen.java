package com.salatech.taawuncarinsurance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.salatech.taawuncarinsurance.databinding.ActivitySignupScreenBinding;

public class signup_screen extends AppCompatActivity {

    ActivitySignupScreenBinding binding;
    DataBaseInsurance dataBaseInsurance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupScreenBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        dataBaseInsurance = new DataBaseInsurance(this);
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextSignupName.getText().toString();
                String email = binding.editTextSignupEmail.getText().toString();
                String password = binding.editTextSignupPassword.getText().toString();
                String confirmpassword = binding.editTextSignupConfirmPassword.getText().toString();
            if (name.equals("")||email.equals("")||password.equals("")||confirmpassword.equals(""))
                Toast.makeText(signup_screen.this,"All fields are mandatory",Toast.LENGTH_LONG).show();
            else {
                if (password.equals(confirmpassword)){
                    Boolean checkUserEmail = dataBaseInsurance.checkEmail(email);
                    if (checkUserEmail == false){
                        Boolean insert = dataBaseInsurance.inserData(name,email,password);
                        if (insert == true){
                            Toast.makeText(signup_screen.this,"Signup Successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), login_screen.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(signup_screen.this,"Signup Failed",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(signup_screen.this,"User already exists Please login",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(signup_screen.this,"Invalid Password",Toast.LENGTH_LONG).show();
                }
            }

            }
        });
        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login_screen.class);
                startActivity(intent);
            }
        });
    }
}