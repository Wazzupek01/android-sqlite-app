package com.pedrycz.phonedb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        Button cancelButton = findViewById(R.id.cancelButton);
        Button saveButton = findViewById(R.id.saveButton);
        Button webButton = findViewById(R.id.webButton);

        EditText brandInput = findViewById(R.id.brand_input);
        EditText modelInput = findViewById(R.id.model_input);
        EditText versionInput = findViewById(R.id.version_input);
        EditText urlInput = findViewById(R.id.url_input);

        brandInput.setOnFocusChangeListener((view, isFocused) -> {
            if(!isFocused) {
                if (brandInput.getText().toString().trim().length() < 1) {
                    brandInput.setError("Brand cannot be blank");
                } else {
                    brandInput.setError(null);
                }
            }
        });

        modelInput.setOnFocusChangeListener((view, isFocused) -> {
            if(!isFocused) {
                if (modelInput.getText().toString().trim().length() < 1) {
                    modelInput.setError("Model cannot be blank");
                } else {
                    modelInput.setError(null);
                }
            }
        });

        versionInput.setOnFocusChangeListener((view, isFocused) -> {
            if (!isFocused) {
                if (!(versionInput.getText().toString().trim().length() > 0)) {
                    versionInput.setError("Version cannot be blank");
                } else {
                    versionInput.setError(null);
                }
            }
        });

        urlInput.setOnFocusChangeListener((view, isFocused) -> {
            if(!isFocused) {
                String url = urlInput.getText().toString().trim();
                if (url.length() < 1) {
                    urlInput.setError("Url cannot be blank");
                } else if (!(url.startsWith("http://") || url.startsWith("https://"))) {
                    urlInput.setError("Incomplete address");
                } else {
                    urlInput.setError(null);
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            brandInput.setText(bundle.getString("BRAND"));
            modelInput.setText(bundle.getString("MODEL"));
            versionInput.setText(bundle.getString("VERSION"));
            urlInput.setText(bundle.getString("URL"));
        }

        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });

        webButton.setOnClickListener(view -> {
            Intent browser = new Intent("android.intent.action.VIEW", Uri.parse(urlInput.getText().toString()));
            try {
                startActivity(browser);
            } catch (Exception e) {
                Toast.makeText(this, "Error connection", Toast.LENGTH_SHORT).show();
            }
        });

        saveButton.setOnClickListener(view -> {
            String brand = brandInput.getText().toString().trim();
            String model = modelInput.getText().toString().trim();
            String version = versionInput.getText().toString().trim();
            String url = urlInput.getText().toString().trim();
            if (brand.length() > 0 && model.length() > 0 && version.length() > 0 && url.length() > 0) {
                Intent intent = new Intent();
                if (bundle != null) {
                    intent.putExtra("ID", bundle.getLong("ID"));
                }
                intent.putExtra("BRAND", brand);
                intent.putExtra("MODEL", model);
                intent.putExtra("VERSION", version);
                intent.putExtra("URL", url);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}