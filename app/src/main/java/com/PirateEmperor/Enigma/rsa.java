package com.PirateEmperor.Enigma;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Objects;

import javax.crypto.Cipher;

public class rsa extends Fragment {
    private static final String RSA = "RSA";
    public static PrivateKey rk;
    public static PublicKey uk;
    String copy;
    EditText input_et;
    TextView output_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.rsa, container, false);

        assert getArguments() != null;
        String getArgument = getArguments().getString("data");
        // toast(getArgument);

        return view;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {

        this.input_et = (EditText) view.findViewById(R.id.plaintext_input);
        this.output_tv = (TextView) view.findViewById(R.id.output);
        Button button = (Button) view.findViewById(R.id.button_encryption);
        try {
            generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        button.setOnClickListener(new View.OnClickListener() {
            /*
             * class cryptocalsi.it.cspit.charusat.crypto.Asym_RSA_Fragment.AnonymousClass1
             */

            public void onClick(View view) {
                // try {
                // ((InputMethodManager)
                // rsa.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(rsa.this.getActivity().getCurrentFocus().getWindowToken(),
                // 0);
                // } catch (Exception unused) {
                // }
                if (rsa.this.input_et.getText().toString().equals("")) {
                    Toast.makeText(rsa.this.getContext(), "Please Enter Appropriate Value", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    rsa.this.copy = rsa.encrypt(rsa.this.input_et.getText().toString());
                    TextView textView = rsa.this.output_tv;
                    textView.setText("CipherText : \n" + rsa.this.copy);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ((Button) view.findViewById(R.id.button_decryption)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // try {
                // ((InputMethodManager)
                // rsa.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(rsa.this.getActivity().getCurrentFocus().getWindowToken(),
                // 0);
                // } catch (Exception unused) {
                // }
                if (rsa.this.input_et.getText().toString().equals("")) {
                    Toast.makeText(rsa.this.getContext(), "Please Enter Appropriate Value", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    rsa.this.copy = String.valueOf(rsa.decrypt(rsa.this.input_et.getText().toString()));
                    TextView textView = rsa.this.output_tv;
                    textView.setText("PlainText : \n" + rsa.this.copy);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ((ImageView) view.findViewById(R.id.swap)).setOnClickListener(new View.OnClickListener() {
            /*
             * class cryptocalsi.it.cspit.charusat.crypto.Asym_RSA_Fragment.AnonymousClass3
             */

            public void onClick(View view) {
                // try {
                // ((InputMethodManager)
                // rsa.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(rsa.this.getActivity().getCurrentFocus().getWindowToken(),
                // 0);
                // } catch (Exception unused) {
                // }
                rsa.this.input_et.setText(rsa.this.copy, TextView.BufferType.EDITABLE);
                output_tv.setText("");
            }
        });
    }

    public static void generateKey() throws Exception {
        KeyPairGenerator instance = KeyPairGenerator.getInstance(RSA);
        instance.initialize(512, new SecureRandom());
        KeyPair generateKeyPair = instance.generateKeyPair();
        uk = generateKeyPair.getPublic();
        rk = generateKeyPair.getPrivate();
    }

    private static byte[] encrypt(String str, PublicKey publicKey) throws Exception {
        Cipher instance = Cipher.getInstance(RSA);
        instance.init(1, publicKey);
        return instance.doFinal(str.getBytes());
    }

    public static final String encrypt(String str) {
        try {
            return byte2hex(encrypt(str, uk));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String decrypt(String str) {
        try {
            return new String(decrypt(hex2byte(str.getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] decrypt(byte[] bArr) throws Exception {
        Cipher instance = Cipher.getInstance(RSA);
        instance.init(2, rk);
        return instance.doFinal(bArr);
    }

    public static String byte2hex(byte[] bArr) {
        String str = "";
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            str = hexString.length() == 1 ? str + "0" + hexString : str + hexString;
        }
        return str.toUpperCase();
    }

    public static byte[] hex2byte(byte[] bArr) {
        if (bArr.length % 2 == 0) {
            byte[] bArr2 = new byte[(bArr.length / 2)];
            for (int i = 0; i < bArr.length; i += 2) {
                bArr2[i / 2] = (byte) Integer.parseInt(new String(bArr, i, 2), 16);
            }
            return bArr2;
        }
        throw new IllegalArgumentException("hello");
    }

    void toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

}
