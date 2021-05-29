package com.incorporarRemota;

import com.google.gson.Gson;
import com.incorporar.MandaInformacionCola;
import com.incorporarRemota.controlador.ControladorIncorporarRemota;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;

public class IncorporarRemota {


    private Socket misocket;


    private String token;

    private Tiendaremota tiendaremota;

    private ControladorIncorporarRemota remota;
    private DataInputStream reader;



    public IncorporarRemota(Socket misocket) {
        this.misocket = misocket;
    }



    public void actuar(){

        try {
            reader = new DataInputStream( misocket.getInputStream());

            Gson gson=new Gson();


            String json=reader.readUTF();

            tiendaremota=gson.fromJson(json, Tiendaremota.class);

            token=reader.readUTF();

            enviarRepuestaRemota();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    private void enviarRepuestaRemota() throws IOException {

        remota=new ControladorIncorporarRemota(tiendaremota.getTiempomedia(),tiendaremota.getId_cola(),token);
        DataOutputStream out=new DataOutputStream(misocket.getOutputStream());

        int respuesta=remota.buscarSiYaestaDentrodeLacola();

        out.writeInt(respuesta);

        if(respuesta==1){

            // coger el turno
            String miturno=String.valueOf(remota.incorporarRemota());

            // entra en bucle para mandar informacion acerca de cola
            //siempre y cuando el socket esta conectado
            MandaInformacionCola informacion=new MandaInformacionCola(misocket,new BigDecimal(tiendaremota.getId_cola()), new BigDecimal(remota.getId_usuario()),miturno);

            informacion.mandarJsonCola();

        }else{

            out.close();
            misocket.close();

        }

    }

}
