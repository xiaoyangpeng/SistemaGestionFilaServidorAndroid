package com.incorporarRemota;

import com.incorporar.EscuchaOpcionProductos;
import com.incorporar.MandaInformacionCola;
import com.incorporarRemota.controlador.ControladorIncorporarRemota;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;

public class IncorporarRemota {


    private Socket misocket;
    private DataInputStream entrada;

    private String token;

    private  int id_cola;


    private  int tiempoQuiere;

    private ControladorIncorporarRemota remota;
    public IncorporarRemota(Socket misocket) {
        this.misocket = misocket;
    }


    private void enviarRepuestaRemota() throws IOException {

        remota=new ControladorIncorporarRemota(tiempoQuiere,id_cola,token);
        DataOutputStream out=new DataOutputStream(misocket.getOutputStream());


        int respuesta=remota.incorporarRemota();

        if(respuesta==1){


            // coger el turno
            String miturno=String.valueOf(remota.incorporarRemota());

            // arranca un hilo para esuchar los productos que eligra por el cliente
            EscuchaOpcionProductos escuchaOpcionProductos=new EscuchaOpcionProductos(misocket);

            escuchaOpcionProductos.start();

            // entra en bucle para mandar informacion acerca de cola
            //siempre y cuando el socket esta conectado
            MandaInformacionCola informacion=new MandaInformacionCola(misocket,new BigDecimal(id_cola), new BigDecimal(remota.getId_usuario()),miturno);

            informacion.mandarJsonCola();

        }else{

            out.close();
            misocket.close();

        }


    }

}
