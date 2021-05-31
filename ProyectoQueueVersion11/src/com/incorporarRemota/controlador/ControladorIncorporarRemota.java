package com.incorporarRemota.controlador;

import dao.utils.BaseDao;
import dao.utils.JdbcUtils;
import token.JwtUtil;
import variables.FehcaHoy;
import variables.VariableSQL;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControladorIncorporarRemota extends BaseDao {

        private  int tiempoQuiere;

        private  int id_cola;

        private int id_usuario;

        private int turnoEstima;

        private String token;

        public ControladorIncorporarRemota(int tiempoQuiere, int id_cola,String token) {
                this.tiempoQuiere = tiempoQuiere;
                this.id_cola = id_cola;
                this.id_usuario= JwtUtil.vertificar(token);


        }


   /*    public static void main(String[] args) {


            ControladorIncorporarRemota re=new ControladorIncorporarRemota(60,361,"");

            System.out.println(re.incorporarRemota());

       }*/




        public int buscarSiYaestaDentrodeLacola() {

                BigDecimal id=(BigDecimal)queryForUnValor(VariableSQL.BUSCAR_SI_USUARIO_YA_ESTA_EN_COLA,id_cola,id_usuario );

                if(id==null) {

                        return 1;

                }else {
                        return 2;
                }

        }



   public int incorporarRemota(){

                Connection connection= JdbcUtils.getConeection();
                CallableStatement call=null;
                try {

                        call=connection.prepareCall("begin EligeTurnoRemota(? ,?,?) ; end;");

                        call.setInt(1,id_cola);

                        call.setInt(2, tiempoQuiere);

                        call.registerOutParameter(3, Types.INTEGER);// poner salida

                        call.execute();

                        turnoEstima= call.getInt(3);

                        call.close();

                        connection.close();

                } catch (SQLException throwables) {
                        throwables.printStackTrace();

                }

       update(VariableSQL.ANDAIR_USUARIOENCOLA_REMOTA,id_usuario,id_cola, turnoEstima, tiempoCancelar(),FehcaHoy.horaNow());

                return  turnoEstima;

        }


        private  String tiempoCancelar(){
            Date date=new Date();

            Long now=date.getTime();

            Long tiempoestima=(Long.parseLong(String.valueOf(tiempoQuiere)))*60000 ;


            Date tiempoCancelar=new Date(tiempoestima+now);

            SimpleDateFormat sdf= new SimpleDateFormat("HH:mm:ss");


          return  sdf.format(tiempoCancelar);

        }


        public int getId_usuario() {
                return id_usuario;
        }
}
