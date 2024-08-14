package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;

    private Button btnPlus;
    private Button btnLess;
    private Button btnDevide;
    private Button btnMultiply;

    private Button btnClr;
    private Button btnVirgula;
    private Button btnEnter;
    private Button btnBackspace;

    private Calculadora calculadora = new Calculadora();
    private EditText visor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        btnPlus = findViewById(R.id.btnPlus);
        btnLess = findViewById(R.id.btnLess);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDevide = findViewById(R.id.btnDevide);

        btnClr = findViewById(R.id.btnClr);
        btnEnter = findViewById(R.id.btnEnter);
        btnBackspace = findViewById(R.id.btnBackSpace);
        btnVirgula = findViewById(R.id.btnVirgula);


        visor = findViewById(R.id.editTextNumberDecimal);
        visor.setShowSoftInputOnFocus(false);
        calculadora = new ViewModelProvider(this).get(Calculadora.class);


        btn0.setOnClickListener(botaoClick("0"));
        btn1.setOnClickListener(botaoClick("1"));
        btn2.setOnClickListener(botaoClick("2"));
        btn3.setOnClickListener(botaoClick("3"));
        btn4.setOnClickListener(botaoClick("4"));
        btn5.setOnClickListener(botaoClick("5"));
        btn6.setOnClickListener(botaoClick("6"));
        btn7.setOnClickListener(botaoClick("7"));
        btn8.setOnClickListener(botaoClick("8"));
        btn9.setOnClickListener(botaoClick("9"));



        btnVirgula.setOnClickListener(botaoClick(","));

        btnClr.setOnClickListener((view -> {
            visor.getText().clear();
            calculadora.limpaNumerosPilha();
        }));

        btnBackspace.setOnClickListener((view -> {
            int inicioSelecao = visor.getSelectionStart();
            inicioSelecao = Math.max(inicioSelecao, 0);
            int finalSelecao = visor.getSelectionEnd();

            visor.getText().delete(inicioSelecao, finalSelecao);
        }));

        btnEnter.setOnClickListener(view -> {
            String textoVisor = visor.getText().toString();
            if (!textoVisor.isEmpty()) {
                double valor = Double.valueOf(textoVisor);

                calculadora.setNumero(valor);
                calculadora.enter();
                visor.getText().clear();
            } else {
                Toast.makeText(this, "Por favor, insira um número", Toast.LENGTH_SHORT).show();
            }
        });

        btnPlus.setOnClickListener(view -> {
            if (calculadora.tamanhoPilha() < 2) {
                Toast.makeText(this, "Insira pelo menos dois números antes de somar", Toast.LENGTH_SHORT).show();
                return;
            }

            calculadora.executarOperacao((a, b) -> a + b); // Define a operação de soma

            double resultado = calculadora.getNumero();
            visor.setText(String.valueOf(resultado));
        });

        btnLess.setOnClickListener(view -> {
            if (calculadora.tamanhoPilha() < 2) {
                Toast.makeText(this, "Insira pelo menos dois números antes de subtrair", Toast.LENGTH_SHORT).show();
                return;
            }

            calculadora.executarOperacao((a, b) -> a - b); // Define a operação de soma

            double resultado = calculadora.getNumero();
            visor.setText(String.valueOf(resultado));
        });

        btnMultiply.setOnClickListener(view -> {
            if (calculadora.tamanhoPilha() < 2) {
                Toast.makeText(this, "Insira pelo menos dois números antes de multiplicar", Toast.LENGTH_SHORT).show();
                return;
            }

            calculadora.executarOperacao((a, b) -> a * b); // Define a operação de soma

            double resultado = calculadora.getNumero();
            visor.setText(String.valueOf(resultado));
        });

        btnDevide.setOnClickListener(view -> {
            if (calculadora.tamanhoPilha() < 2) {
                Toast.makeText(this, "Insira pelo menos dois números antes de dividir", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                calculadora.executarOperacao((a, b) -> a / b);
                double resultado = calculadora.getNumero();
                visor.setText(String.valueOf(resultado));
            } catch (ArithmeticException e) {
                Toast.makeText(this, "Erro: Divisão por zero", Toast.LENGTH_SHORT).show();
                calculadora.limpaNumerosPilha();
                visor.setText("");
            }
        });
    }

    public View.OnClickListener botaoClick(final String id) {
        return (v) -> {
            int finalTexto = visor.getText().length();

            visor.setSelection(finalTexto, finalTexto);

            visor.getText().replace(finalTexto, finalTexto, id);
        };
    }

}