package com.example.guardardatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLData;

public class MainActivity extends AppCompatActivity {
    private EditText nombre,apellido,control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre=(EditText)findViewById(R.id.et1);
        apellido=(EditText)findViewById(R.id.et2);
        control=(EditText)findViewById(R.id.et3);

    }
    public void registro(View view){
        AdminSQLite admin= new AdminSQLite(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        String nom= nombre.getText().toString();
        String ape= apellido.getText().toString();
        String con= control.getText().toString();

        if (!nom.isEmpty()&&!ape.isEmpty()&&!con.isEmpty()){

            ContentValues registro= new ContentValues();
            registro.put("control",con);
            registro.put("nombre",nom);
            registro.put("apellido",ape);

            BaseDeDatos.insert("alumno",null,registro);
            BaseDeDatos.close();

            Toast.makeText(this,"REGISTRO GUARDADO",Toast.LENGTH_SHORT).show();
            nombre.setText("");
            apellido.setText("");
            control.setText("");

        }else{
            if (nom.isEmpty())
                nombre.setError("Escribe el nombre");
            if (ape.isEmpty())
                apellido.setError("Escribe el apellido");
            if (con.isEmpty())
                control.setError("Escribe tu numero de control");
//            Toast.makeText(this, "INGRESA EL VALOR RESTANTE", Toast.LENGTH_SHORT).show();
        }


    }
    public void buscar(View view){
        AdminSQLite admin= new AdminSQLite(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String con = control.getText().toString();
        if(!con.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select nombre,apellido from alumno where control="+con,null);
            if(fila.moveToFirst()){
                nombre.setText(fila.getString(0));
                apellido.setText(fila.getString(1));
                BaseDeDatos.close();
            }else{
                control.setError("NUMERO DE CONTROL NO REGISTRADO");
                control.setText("");
            }

        }else{
            control.setError("Ingrese numero de control ");
        }

    }
    public void modificar(View view){
        AdminSQLite admin= new AdminSQLite(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String con = control.getText().toString();
        String nom = nombre.getText().toString();
        String ape = apellido.getText().toString();



        if (!nom.isEmpty()&&!ape.isEmpty()&&!con.isEmpty()){

            ContentValues modificar= new ContentValues();

            modificar.put("nombre",nom);
            modificar.put("apellido",ape);

            BaseDeDatos.update("alumno", modificar, "control="+con, null);
            BaseDeDatos.close();
            Toast.makeText(this, "USUARIO MODIFICADO", Toast.LENGTH_SHORT).show();
            nombre.setText("");
            apellido.setText("");
            control.setText("");
        }else{
            if (nom.isEmpty())
                nombre.setError("Escribe el nombre");
            if (ape.isEmpty())
                apellido.setError("Escribe el apellido");
            if (con.isEmpty())
                control.setError("Escribe tu numero de control");
        }

    }

    public void eliminar(View view){
        AdminSQLite admin= new AdminSQLite(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String con = control.getText().toString();
        if(!con.isEmpty()){
            BaseDeDatos.delete("alumno","control="+con,null);
            BaseDeDatos.close();
            Toast.makeText(this,"REGISTRO ELIMINADO",Toast.LENGTH_SHORT).show();
            nombre.setText("");
            apellido.setText("");
            control.setText("");


        }else{
                control.setError("NUMERO DE CONTROL INVALIDO ");
        }


    }

}
