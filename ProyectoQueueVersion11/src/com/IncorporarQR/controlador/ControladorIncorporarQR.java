package com.IncorporarQR.controlador;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import com.incorporar.ProductoMandaUsuario;

import dao.utils.BaseDao;
import dao.utils.JdbcUtils;
import variables.FehcaHoy;
import variables.VariableSQL;

public class ControladorIncorporarQR extends BaseDao{

	
	private String emailUsuario;
	
	private String codigoQR;
	
	
	private BigDecimal id_cola;
	
	private BigDecimal id_usuario;

	public ControladorIncorporarQR(String emailUsuario, String codigoQR) {
		this.emailUsuario = emailUsuario;
		this.codigoQR = codigoQR;
	}
	
	
	public ControladorIncorporarQR() {
		
		
	}
	
	
	/*
	public static void main(String[] args) {
		
		ControladorIncorporarQR qr=new ControladorIncorporarQR("8888","ASDF");
		
		System.out.println(qr.siExisteQR());

		qr.incorporar();
	//	ControladorIncorporarQR qr=new ControladorIncorporarQR();
		/*
		ProductoMandaUsuario productoMandaUsuario=new ProductoMandaUsuario();
		
		productoMandaUsuario.setCantidad(100);
		
		productoMandaUsuario.setId_producto(42);
		
		productoMandaUsuario.setId_cola(1);
		
		productoMandaUsuario.setId_usuario(222);
		
		qr.usuario_anadir_productos(productoMandaUsuario);



		
	}*/
	
    /**
    *
    * @return -1: error QR
    * 1 QR correcto
    * 2 ya esta dentro de la cola
    */
	public int siExisteQR() {
		
		
		id_cola=(BigDecimal)queryForUnValor(VariableSQL.BUSCAR_ID_COLA_CON_QR, codigoQR,FehcaHoy.diaHoy());
		
		if(id_cola==null) {
			
			return -1;
		}else {
			
			buscarIdUsuario();
			
			return buscarSiYaestaDentrodeLacola();
		}
	

	}

	private int buscarSiYaestaDentrodeLacola() {
		
		BigDecimal id=(BigDecimal)queryForUnValor(VariableSQL.BUSCAR_SI_USUARIO_YA_ESTA_EN_COLA,id_cola,id_usuario );
		
		if(id==null) {
			
			return 1;
			
		}else {
			return 2;
		}
		
	}
	
	
	
	private void buscarIdUsuario() {
		
		id_usuario=(BigDecimal)queryForUnValor(VariableSQL.CON_EMAIL_BUSCAR_ID_USUARIO, emailUsuario);
		
	}

	
	public String incorporar() {

		Connection connection= JdbcUtils.getConeection();
		CallableStatement call=null;
		int turnoCola=0;
		try {

			call=connection.prepareCall("begin ElijeUltimoTurno(? ,?) ; end;");

			call.setInt(1,Integer.parseInt(id_cola.toString()));

			call.registerOutParameter(2, Types.INTEGER);// poner salida

			call.execute();

			turnoCola= call.getInt(2);

			call.close();

			connection.close();

		} catch (SQLException throwables) {
			throwables.printStackTrace();

		}
		update(VariableSQL.ANDAIR_USUARIOENCOLA_QR,id_usuario,id_cola,turnoCola,FehcaHoy.horaNow());
		return  String.valueOf(turnoCola);
	}


	
	public synchronized void usuario_anadir_productos(ProductoMandaUsuario productoMandaUsuario) {
		
		
		
		//primero buscar si ya existe datos en incorporar o no 
		// si existe actualizar , pero si cantidad es 0 elimina
		// si cantidad mayor que 0 actualizar
		// si no existe añadir
		
	
		
		BigDecimal usuarioExiste=(BigDecimal)queryForUnValor(VariableSQL.BUSCAR_SI_EXISTE_IDPRODUCTO_CON_IDCOLA_IDUSUARIO,productoMandaUsuario.getId_usuario(),
				productoMandaUsuario.getId_producto(),productoMandaUsuario.getId_cola());
		
		
		if(usuarioExiste==null) {
			
			update(VariableSQL.USUARIO_ANADIR_PRODUCTOS,productoMandaUsuario.getId_producto(),productoMandaUsuario.getId_usuario()
					,productoMandaUsuario.getId_cola(),productoMandaUsuario.getCantidad());
			
		}else {
		
			if(productoMandaUsuario.getCantidad()==0) {
				
				update(VariableSQL.ELIMINAR_PRODUCTO_EN_INCORPORAR, 
						productoMandaUsuario.getId_cola(),productoMandaUsuario.getId_usuario(),productoMandaUsuario.getId_producto());
				
			}else {
				
				
				update(VariableSQL.ACTUALIZAR_TABLA_INCORPORAR, productoMandaUsuario.getCantidad(),productoMandaUsuario.getId_usuario(),
					productoMandaUsuario.getId_producto(),productoMandaUsuario.getId_cola());
			
				}
			}
			
		

		
	}
	
	
	
	public BigDecimal getId_cola() {
		return id_cola;
	}


	public void setId_cola(BigDecimal id_cola) {
		this.id_cola = id_cola;
	}


	public BigDecimal getId_usuario() {
		return id_usuario;
	}


	public void setId_usuario(BigDecimal id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
}
