package tdm.ittepic.edu.a11400309_unidad3_32_ws;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    String json_string;
    String JSON_STRING;
    Button consultar;
    TextView tvResultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResultado = (TextView) findViewById(R.id.tresultado);
        consultar = (Button) findViewById(R.id.consultarid);

        consultar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WSIdalumno().execute();
            }
        });
    }
    private class WSIdalumno extends AsyncTask<Void, Void, String> {

        String json_url;

        @Override
        protected String doInBackground(Void... Voids) {
            String cadena="";
            json_url= "http://www.apilayer.net/api/live?access_key=af565281c1da77a1fee879ce1d43a5f9" ;

            //json_url = "http://www.apilayer.net/api/live?access_key=9405515b2614d8f13b71424f8c62b7d3";

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                int respuesta = httpURLConnection.getResponseCode();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                if (respuesta == HttpURLConnection.HTTP_OK) {
                    String abc=json_string;

                    while ((json_string = bufferedReader.readLine()) != null) {

                        stringBuilder.append(json_string + "\n");
                    }

                    JSONObject respuestaJSON = new JSONObject(stringBuilder.toString());
                    String resultJSON = respuestaJSON.getString("quotes");

                    if (resultJSON != null){
                        JSONObject alumnosJSON = respuestaJSON.getJSONObject("quotes");
                        cadena = cadena + "Precio:  " + alumnosJSON.getString("USDMXN") + " Pesos Mexicanos" + "\n" ;

                    }else if (resultJSON== null){
                        cadena = "No hay datos";
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return cadena;
        }
        @Override
        protected void onPostExecute(String result) {
            tvResultado.setText(result);
            JSON_STRING = result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
