package com.PirateEmperor.Enigma;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.nio.charset.StandardCharsets;

import static com.yks.secretmessaging.MainActivity.dititaldecryptnencrypt;
import static com.yks.secretmessaging.MainActivity.encryptblowfish;
import static com.yks.secretmessaging.MainActivity.encryptdes;
import static com.yks.secretmessaging.MainActivity.encryptndecryptAes;

public class symmetric extends Fragment {
    String getArgument;
    EditText plaininput, keyinput;
    Button encrypt, decrypt;
    TextView output;
    // Button copy;
    String result = "";
    ImageView swap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.symmetric, container, false);
        assert getArguments() != null;
        getArgument = getArguments().getString("data");
        keyinput = view.findViewById(R.id.key_input);
        swap = view.findViewById(R.id.swap);

        if (getArgument.equals("DSA Cipher")) {
            keyinput.setVisibility(View.GONE);
            swap.setVisibility(View.GONE);
        }

        return view;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {

        plaininput = view.findViewById(R.id.plaintext_input);
        encrypt = view.findViewById(R.id.button_encryption);
        decrypt = view.findViewById(R.id.button_decryption);
        output = view.findViewById(R.id.output);
        // copy=view.findViewById(R.id.copy);

        encrypt.setTag(1);
        decrypt.setTag(2);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int mode;
                    if (v.getTag().equals(1)) {
                        mode = 1;

                        button(mode);

                    } else if (v.getTag().equals(2)) {
                        mode = 2;
                        button(mode);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    output.setText("Output : NULL");
                }
            }

        };

        encrypt.setOnClickListener(onClickListener);
        decrypt.setOnClickListener(onClickListener);

        // copy.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // setClipboard(getContext(),result);
        // }
        // });

        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plaininput.setText(result);
                output.setText("Output : ");
            }
        });

    }

    private void setClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Text copied", text);
        clipboard.setPrimaryClip(clip);
        toast("Text copied to clipboard");
    }

    void button(int mode) throws Exception {
        String s = getArgument;

        byte[] str;
        String pass;

        pass = keyinput.getText().toString().trim();

        str = plaininput.getText().toString().getBytes(StandardCharsets.UTF_8);

        switch (s) {
            case "AES Encryption":
                result = encryptndecryptAes(str, mode, pass);
                break;
            case "Blowfish Encryption":
                result = encryptblowfish(str, mode, pass);
                break;

            case "DSA Cipher":
                byte[] encryptedTextfinal = new byte[0];
                if (mode == 2) {
                    String str2 = result;
                    encryptedTextfinal = Base64.decode(str2, Base64.DEFAULT);
                }
                result = dititaldecryptnencrypt(encryptedTextfinal, str, mode);
                break;

            case "DES Encryption":

                result = encryptdes(str, mode, pass);
                break;

        }

        output.setText("Output : " + result);

    }

    void toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

}
