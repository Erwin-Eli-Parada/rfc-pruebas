package com.example.nfcprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    //Mensaje de error
    public static final String Error_detectado = "No se encontro ninguna etiqueta NFC";

    //Inicio de las variables nfc
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    Tag tag;
    Context context;
    TextView id_message;
    Button boton_escaneo;
    String nfc_id="";

    //Metodo de inicio
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id_message = (TextView) findViewById(R.id.textView2);
        boton_escaneo = findViewById(R.id.button);
        context = this;

        //accion del boton que lleva la selección del id
        boton_escaneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(tag == null){
                        Toast.makeText(context, Error_detectado, Toast.LENGTH_LONG).show();
                    }else{
                        //Aqui puedes meter el metodo para buscar en tu base de datos usando nfc_id
                        Toast.makeText(context, "Se ha ejecutado con exito", Toast.LENGTH_LONG).show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //escaneamos las etiquetas desde el inicio de la aplicación
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Toast.makeText(this, "Este dispositivo no soporta NFC", Toast.LENGTH_SHORT).show();
            finish();
        }
        readfromIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected= new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
    }

    //metodo para leer el Intent
    private void readfromIntent(Intent intent){
        String action = intent.getAction();
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
            || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
            || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){
            //Aqui obtenemos el id tambien puedes obtener los datos con EXTRA_NDEF_MESSAGES
            Parcelable[] rawId = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_ID);
            NdefMessage[] id=null;
            if(rawId != null){
                id= new NdefMessage[rawId.length];
                for (int i=0; i< rawId.length; i++){
                    id[i]= (NdefMessage) rawId[i];
                }
            }
            buildTagViwes(msgs);
        }
    }

    //metodo para decodificar el id y convertirlo en String
    

}