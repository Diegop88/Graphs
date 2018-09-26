package mx.diegop.graficas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Lineas lineas;
    Lineas2 lineas2;
    List<Integer> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineas = (Lineas) findViewById(R.id.lineas);
        lineas2 = (Lineas2) findViewById(R.id.lineas2);
    }

    public void crearDatos() {
        datos = new ArrayList<>();
        datos.add(3);
        datos.add(8);
        datos.add(1);
        datos.add(5);
        datos.add(7);
        datos.add(0);
    }

    public void crearDatos2() {
        datos = new ArrayList<>();
        datos.add(6);
        datos.add(1);
        datos.add(7);
        datos.add(3);
        datos.add(15);
        datos.add(2);
        datos.add(8);
        datos.add(1);
        datos.add(20);
    }

    public void crearDatos3() {
        datos = new ArrayList<>();
        datos.add(1);
        datos.add(2);
        datos.add(3);
        datos.add(4);
        datos.add(5);
        datos.add(6);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.dato1) {
            lineas.limpiar();
            lineas2.limpiar();

            crearDatos();
            lineas.addDatos(datos);
            lineas2.addDatos(datos);

            lineas.setDataComplete();
            lineas2.setDataComplete();
            return true;
        }
        if (id == R.id.dato2) {
            lineas.limpiar();
            lineas2.limpiar();

            crearDatos2();
            lineas.addDatos(datos);
            lineas2.addDatos(datos);

            lineas.setDataComplete();
            lineas2.setDataComplete();
            return true;
        }
        if (id == R.id.dato3) {
            lineas.limpiar();
            lineas2.limpiar();

            crearDatos3();
            lineas.addDatos(datos);
            lineas2.addDatos(datos);

            lineas.setDataComplete();
            lineas2.setDataComplete();
            return true;
        }
        if (id == R.id.datos) {
            lineas.limpiar();
            lineas2.limpiar();

            crearDatos();
            lineas.addDatos(datos);
            lineas2.addDatos(datos);
            crearDatos2();
            lineas.addDatos(datos);
            lineas2.addDatos(datos);
            crearDatos3();
            lineas.addDatos(datos);
            lineas2.addDatos(datos);

            lineas.setDataComplete();
            lineas2.setDataComplete();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}