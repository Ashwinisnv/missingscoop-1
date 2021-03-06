package edu.sjsu.missing.scoop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.sjsu.missing.scoop.authentication.AuthenticationHandler;
import edu.sjsu.missing.scoop.authentication.AuthenticationListener;

public class MainActivity extends AppCompatActivity {

    AuthenticationHandler authenticationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Welcome");

        authenticationHandler = new AuthenticationHandler();

        if (authenticationHandler.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), HomeScreenActivity.class));
            authenticationHandler.sendRegistrationToServer(authenticationHandler.getCurrentUser().getEmail(), getApplicationContext());
            finish();
        }
    }

    public void login(View view) {
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        authenticationHandler.signInWithEmailAndPassword(email, password, this, new AuthenticationListener() {
            @Override
            public void onSuccess(String message) {
                authenticationHandler.sendRegistrationToServer(authenticationHandler.getCurrentUser().getEmail(), getApplicationContext());
                startActivity(new Intent(getApplicationContext(), HomeScreenActivity.class));
                finish();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getApplicationContext(), "Invalid username/password", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    public void signUp(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
        finish();
    }
}
