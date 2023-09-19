package com.salatech.taawuncarinsurance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.salatech.taawuncarinsurance.R;
import com.salatech.taawuncarinsurance.databinding.ActivityPolicyDetailsScreenBinding;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Policy_details_screen extends AppCompatActivity {

    private ActivityPolicyDetailsScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPolicyDetailsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up spinner adapters
        ArrayAdapter<CharSequence> policyTypeAdapter = ArrayAdapter.createFromResource(
                this, R.array.policy_types, android.R.layout.simple_spinner_item);
        policyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.btnPolicyType.setAdapter(policyTypeAdapter);

        ArrayAdapter<CharSequence> billingFrequencyAdapter = ArrayAdapter.createFromResource(
                this, R.array.billing_frequencies, android.R.layout.simple_spinner_item);
        billingFrequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.btnBillingFrequency.setAdapter(billingFrequencyAdapter);

        // Set a click listener for the Generate PDF button
        binding.btnDownloadPolicyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePDF();
            }
        });
        binding.btnPrintPolicyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePDF();
            }
        });

        binding.btnNextPolicyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Payment_process.class);
                startActivity(intent);
            }
        });

        binding.btnCancelPolicyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Policy_details_screen.class);
                startActivity(intent);
            }
        });

        binding.btnHelpPolicyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), App_settings_screen.class);
                startActivity(intent);
            }
        });
    }

    private void generatePDF() {
        // Get user-entered details
        String policyNumber = binding.btnPolicyNumber.getText().toString();
        String holderName = binding.btnPolicyHolderName.getText().toString();
        String currentAmount = binding.btnCurrentAmount.getText().toString();
        String policyType = binding.btnPolicyType.getSelectedItem().toString();
        String billingFrequency = binding.btnBillingFrequency.getSelectedItem().toString();

        // Create a PDF document
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        // Add text to the PDF
        int startY = 20;
        int lineHeight = 20;

        canvas.drawText("Policy Number: " + policyNumber, 10, startY, paint);
        canvas.drawText("Policy Holder Name: " + holderName, 10, startY + lineHeight, paint);
        canvas.drawText("Current Amount: " + currentAmount, 10, startY + 2 * lineHeight, paint);
        canvas.drawText("Policy Type: " + policyType, 10, startY + 3 * lineHeight, paint);
        canvas.drawText("Billing Frequency: " + billingFrequency, 10, startY + 4 * lineHeight, paint);

        // Finish the page and create the PDF file
        pdfDocument.finishPage(page);
        String pdfFileName = "Policy_Details.pdf";
        File outputFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), pdfFileName);

        try {
            pdfDocument.writeTo(new FileOutputStream(outputFile));
            pdfDocument.close();
            Toast.makeText(getApplicationContext(), "PDF generated: " + outputFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error generating PDF", Toast.LENGTH_SHORT).show();
        }
    }
}
