package com.incorporarRemota.controlador;

import dao.utils.BaseDao;
import dao.utils.JdbcUtils;
import token.JwtUtil;
import variables.FehcaHoy;
import variables.VariableSQL;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

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


     /*   public static void main(String[] args) {


                ControladorIncorporarRemota re=new ControladorIncorporarRemota(65,2);

                re.buscarTurno();

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

       update(VariableSQL.ANDAIR_USUARIOENCOLA_REMOTA,id_usuario,id_cola, turnoEstima, FehcaHoy.horaNow());

                return  turnoEstima;

        }


        public int getId_usuario() {
                return id_usuario;
        }
}
