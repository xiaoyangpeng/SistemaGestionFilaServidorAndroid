package dao.controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mandarEmail.Email;

import dao.utils.BaseDao;
import dao.utils.ConectarBBDD;
import variables.GeneraCodigo;
import variables.VariableSQL;

public class ControladorUsuarioSign extends BaseDao{

	private UsuarioJson usuarioJson;
	private PreparedStatement preparedStatement;
	private Connection conecion;
	private ResultSet resultado = null;
	
	private int id_usuario;
	private String codigoActivacion ;
	
	public ControladorUsuarioSign(UsuarioJson usuarioJson) {
	
		this.usuarioJson=usuarioJson;
	
	}
	
	/*public static void main(String[] args) {
	
	
	Usuario u=new Usuario();
	
	u.contrasena=1234;
	
	u.email="vanela8760@naymeo.com";
	
	u.nombre="jaja";
	
	u.sexo="H";
	
	u.telefono=123456789;
	
	u.fecha_nacimiento="12/12/12";
	
	ControladorSign controladorSign=new ControladorSign(u);
	

	controladorSign.NoexisteUsuario();

	
}*/

	public boolean siExisteEmail() {
		
		
		String emailAux=(String)queryForUnValor(VariableSQL.BUSCA_EMAIL_USUARIO, usuarioJson.email);
			
		
		if(emailAux==null) {
			
			
			try {
				anadirUsuario();
				// primero anadir el usuario en bbdd
 				// como el id usuario es autoincrementa
				// hace falta un metodo para obtener su id


			    anadirCuentaUsuario(); // como en tabla cuenta_usuario 
			    				// hace falta el id_usuario como calve ajena 
			    				// por eso antes de ejecutar hay que obetener su id_usuario
	
			    
			    
			    // una vez creado usuario y cuenta_usuario manta el codigo de activacion
			    // al email del usuario
			    Email mandaemail=new Email(usuarioJson.email,codigoActivacion);
			    
			    mandaemail.mantar();
			    
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		    
			
			return false;
			
			
		}else return true;
		
	}
	

	private  synchronized void anadirUsuario() throws SQLException{
	
			ConectarBBDD conectar=new ConectarBBDD();
			conecion=conectar.conectarOracle();
			
			preparedStatement=conecion.prepareStatement(VariableSQL.ANADAIR_USUARIO,PreparedStatement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, usuarioJson.nombre);
			
			preparedStatement.setString(2, usuarioJson.sexo);
			
			if(usuarioJson.telefono==0) {
				
				preparedStatement.setString(3, null);
				
			}else {
				preparedStatement.setInt(3, usuarioJson.telefono);
			}
			
			
			preparedStatement.setString(4, usuarioJson.fecha_nacimiento);
			
			preparedStatement.executeUpdate();
			
			// coge rowid de esta nueva fila, con el rowid busca el id del usuario
			resultado=preparedStatement.getGeneratedKeys();
			
			if(resultado.next()) {
				
				String ROWID = resultado.getString(1); 
		
				conRowidBuscaIDUsuario(ROWID);
			}
			
		
	}
	
	
	private void  conRowidBuscaIDUsuario(String ROWID) throws SQLException {
		
		preparedStatement=conecion.prepareStatement(VariableSQL.CON_ROWID_BUSCA_IDUSUARIO);
	
		preparedStatement.setString(1, ROWID);
		
		resultado=preparedStatement.executeQuery();
	
		
		if(resultado.next()) {
			
			id_usuario=resultado.getInt("ID_USUARIO");
			
		

		}
	
	}
	
	private synchronized void anadirCuentaUsuario() throws SQLException {
		
		preparedStatement=conecion.prepareStatement(VariableSQL.REGITARAR_CUENTA_USUARIO);
		
		
		preparedStatement.setString(1, usuarioJson.email);
		
		preparedStatement.setInt(2, id_usuario);
		
		preparedStatement.setInt(3, usuarioJson.contrasena);
		
		
		codigoActivacion=GeneraCodigo.LetraAleatoria();
		
		preparedStatement.setString(4,codigoActivacion);
		
		preparedStatement.execute();
		

		
	}
	
	public void cerrarConexion() {
		
		try {
			
		if(preparedStatement!=null) preparedStatement.close();
		
		if(resultado!=null) resultado.close();
		
		if(conecion!=null) 	conecion.close();
		
		} catch (SQLException e) {
			
				e.printStackTrace();
			
		}
		
	}
	
	
}
