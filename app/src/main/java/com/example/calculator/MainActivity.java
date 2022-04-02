package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private int[] numericButtons = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};

    private int[] operatorButtons = {R.id.btnPlus, R.id.btnMultiplication, R.id.btnMinus, R.id.btnDivide};
    private TextView textScreen;

    private boolean lastNumeric;
    private boolean stateError;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textScreen = (TextView) findViewById(R.id.textScreen);
        setNumericOnClickListener();
        setOperatorOnClickListener();




    }

    private void setNumericOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                if(stateError) {
                    textScreen.setText(button.getText());
                    stateError = false;
                }
                else {
                    textScreen.append(button.getText());
                }
                lastNumeric = true;
            }
        };
        for (int id : numericButtons ) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lastNumeric && !stateError) {
                    Button button = (Button) v;
                    textScreen.append(button.getText());
                    lastNumeric = false;

                }
            }
        };
        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(listener);
        }

    }

    public void clear(View view) {
        textScreen.setText("");
    }

    public void equal(View view) {
        if(lastNumeric && !stateError) {
            String text = textScreen.getText().toString();
            Expression expression = new ExpressionBuilder(text).build();
            try{
                double result = expression.evaluate();
                textScreen.setText(Double.toString(result));
            }
            catch (ArithmeticException ex) {
                textScreen.setText("Error");
                stateError = true;
                lastNumeric = false;

            }
        }
    }
}