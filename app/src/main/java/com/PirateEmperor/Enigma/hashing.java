package com.PirateEmperor.Enigma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.nio.charset.StandardCharsets;

import static com.yks.secretmessaging.MainActivity.convertToSHA1or512;
import static com.yks.secretmessaging.MainActivity.convertToSHA384or224or256;
import static com.yks.secretmessaging.MainActivity.encryptndecryptAes;
import static com.yks.secretmessaging.MainActivity.hashingmd5;

public class hashing extends Fragment {
    String getArgument;
    EditText plaininput;
    Button encrypt;
    TextView output;
    // Button copy;
    String result = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.hashing, container, false);
        assert getArguments() != null;
        getArgument = getArguments().getString("data");

        return view;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {

        plaininput = view.findViewById(R.id.plaintext_input);
        encrypt = view.findViewById(R.id.button_encryption);
        output = view.findViewById(R.id.output);
        // copy=view.findViewById(R.id.copy);

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    button();
                } catch (Exception e) {
                    e.printStackTrace();
                    output.setText("Output : NULL");
                }
            }
        });

    }

    void button() throws Exception {
        String s = getArgument;

        String str;

        str = plaininput.getText().toString();

        switch (s) {

            case "MD5":
                result = hashingmd5(str);
                break;

            case "SHA-1":
                result = convertToSHA1or512(str, "SHA-1");
                break;
            case "SHA-224":

                result = convertToSHA384or224or256(str, "SHA-224");
                break;
            case "SHA-256":

                result = convertToSHA384or224or256(str, "SHA-256");
                break;
            case "SHA-384":

                result = convertToSHA384or224or256(str, "SHA-384");
                break;
            case "SHA-512":

                result = convertToSHA1or512(str, "SHA-512");
                break;
        }

        output.setText("Output : " + result);

    }

    void toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

}
