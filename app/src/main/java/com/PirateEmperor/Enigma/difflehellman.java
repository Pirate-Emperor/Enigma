package com.PirateEmperor.Enigma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.math.BigInteger;
import java.util.Objects;

public class difflehellman extends Fragment {
    EditText prime_number_1;
    EditText prime_number_2;
    TextView print;
    EditText secret_RECEIVER;
    EditText secret_SENDER;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.difflehellman, container, false);
        assert getArguments() != null;
        String getArgument = getArguments().getString("data");

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        this.prime_number_1 = (EditText) view.findViewById(R.id.primenumber_input);
        this.prime_number_2 = (EditText) view.findViewById(R.id.primitive_root_input);
        this.secret_SENDER = (EditText) view.findViewById(R.id.value_x_input);
        this.secret_RECEIVER = (EditText) view.findViewById(R.id.value_y_input);
        this.print = (TextView) view.findViewById(R.id.output);
        ((Button) view.findViewById(R.id.button_key)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (difflehellman.this.prime_number_1.getText().toString().equals("")
                        || difflehellman.this.prime_number_2.getText().toString().equals("")
                        || difflehellman.this.secret_SENDER.getText().toString().equals("")
                        || difflehellman.this.secret_RECEIVER.getText().toString().equals("")) {
                    Toast.makeText(difflehellman.this.getContext(), "Please Enter Appropriate Value",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                BigInteger valueOf = BigInteger
                        .valueOf(Long.valueOf(difflehellman.this.prime_number_1.getText().toString()).longValue());
                double doubleValue = Double.valueOf(difflehellman.this.prime_number_2.getText().toString())
                        .doubleValue();
                double doubleValue2 = Double.valueOf(difflehellman.this.secret_SENDER.getText().toString())
                        .doubleValue();
                double doubleValue3 = Double.valueOf(difflehellman.this.secret_RECEIVER.getText().toString())
                        .doubleValue();

                BigInteger power = difflehellman.this.power(doubleValue, doubleValue2, valueOf);
                BigInteger power2 = difflehellman.this.power(
                        difflehellman.this.power(doubleValue, doubleValue3, valueOf).doubleValue(), doubleValue2,
                        valueOf);
                if (power2.equals(difflehellman.this.power(power.doubleValue(), doubleValue3, valueOf))) {
                    TextView textView = difflehellman.this.print;
                    textView.setText("Key Exchanged Successfully \nKey : " + String.valueOf(power2));
                    return;
                }
                difflehellman.this.print.setText("Key Exchange Fails");
            }
        });
    }

    private BigInteger power(double d, double d2, BigInteger bigInteger) {
        return BigInteger.valueOf(Double.valueOf(Math.pow(d, d2)).longValue()).mod(bigInteger);
    }

    void toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

}
