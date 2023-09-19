package com.salatech.taawuncarinsurance;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.salatech.taawuncarinsurance.databinding.ActivityPaymentProcessBinding;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Payment_process extends AppCompatActivity {
    ActivityPaymentProcessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentProcessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.Credit_Debit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.btnCreditDebit.setAdapter(adapter);

        ArrayAdapter<CharSequence> banktranfer = ArrayAdapter.createFromResource(
                this, R.array.Bank_transfers, android.R.layout.simple_spinner_item);
        banktranfer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.btnBankTransfers.setAdapter(banktranfer);

        ArrayAdapter<CharSequence> onlinepayment = ArrayAdapter.createFromResource(
                this, R.array.Online_Payment, android.R.layout.simple_spinner_item);
        onlinepayment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.btnOnlinePayment.setAdapter(onlinepayment);

        ArrayAdapter<CharSequence> prepaid = ArrayAdapter.createFromResource(
                this, R.array.Prepaid_cards, android.R.layout.simple_spinner_item);
        prepaid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.btnPrepaidCards.setAdapter(prepaid);

        binding.btnNextPaymentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Thank_You_Screen.class);
                startActivity(intent);
            }
        });

        binding.btnCancelPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Payment_process.class);
                startActivity(intent);
            }
        });

        binding.btnConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Payment_process.this, "Please wait for 24 hours while we verify your payment", Toast.LENGTH_LONG).show();

                // Generate and save the payment details PDF
                generatePaymentPDF();
            }
        });
    }

    private void generatePaymentPDF() {
        // Get user-entered payment details
        String paymentMethod = binding.btnOnlinePayment.getSelectedItem().toString();
        String creditCardNumber = binding.editTextCreditCardNumber.getText().toString();
        String expirationDate = binding.editTextExpirationDatePayment.getText().toString();
        String billingAddress = binding.editTextBillingAddress.getText().toString();
        String bank = binding.btnBankTransfers.getSelectedItem().toString();
        String prepaidcards = binding.btnPrepaidCards.getSelectedItem().toString();
        String credit = binding.btnCreditDebit.getSelectedItem().toString();


        // Create a PDF document
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        // Add text to the PDF
        int startY = 20;
        int lineHeight = 20;

        canvas.drawText("Online Payment: " + paymentMethod, 10, startY, paint);
        canvas.drawText("Credit Card Number: " + creditCardNumber, 10, startY + lineHeight, paint);
        canvas.drawText("Expiration Date: " + expirationDate, 10, startY + 2 * lineHeight, paint);
        canvas.drawText("Billing Address: " + billingAddress, 10, startY + 3 * lineHeight, paint);
        canvas.drawText("Bank Transfer: " + bank, 10, startY +4 * lineHeight, paint);
        canvas.drawText("Credit and Debit Cards: " + credit, 10, startY +5 * lineHeight, paint);
        canvas.drawText("Prepaid Card: " + prepaidcards, 10, startY +6 * lineHeight, paint);
        // Finish the page and create the PDF file
        pdfDocument.finishPage(page);
        String pdfFileName = "Payment_Details.pdf";

        // Get the directory for saving PDF
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
