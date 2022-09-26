package com.example.registroentrada;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.example.registroentrada.coneccion.ConeccionClass;

public class MainActivity extends AppCompatActivity {
    private EditText correo, contra;
    private Button btnIngresar;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = (EditText)findViewById(R.id.TextEmail);
        contra = (EditText)findViewById(R.id.TextPassword);
        btnIngresar = (Button)findViewById(R.id.buttoningresar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainActivity.checklogin().execute("");
            }
        });
    }

    public class checklogin extends AsyncTask<String, String, String>{

        String z = null;
        Boolean isSucess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings){

            ConnectionClass connectionHelper = new ConnectionClass();
            con = connectionHelper.connectionclass();
            if(con == null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Verificar conexion", Toast.LENGTH_LONG).show();
                    }
                });
                z = "Conexion a internet";
            }else{

                try {
                    String sql = "SELECT * from usuarios where email='"+correo.getText()+"' and password = '"+contra.getText()+"'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);

                    if(rs.next()){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_LONG).show();
                            }
                        });
                        z="Hecho";
                        Intent intent  = new Intent (MainActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Correo o contraseña incorrecta", Toast.LENGTH_LONG).show();
                            }
                        });
                        correo.setText("");
                        contra.setText("");
                    }

                }catch (Exception e){
                    isSucess = false;
                    Log.e("SQL Error : ", e.getMessage());
                }
            }
            return z;
        }
    }
}