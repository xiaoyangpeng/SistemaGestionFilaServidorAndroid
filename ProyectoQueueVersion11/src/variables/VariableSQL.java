package variables;

public class VariableSQL {

	
	public static final String BUSCA_USUARIO="select * from cuenta_usuario where EMAIL=?";
	
	public static final String BUSCA_EMAIL_USUARIO="select email from cuenta_usuario where EMAIL=?";
	
	public static final String REGITARAR_CUENTA_USUARIO="insert into cuenta_usuario values(?,?,?,default,?,default)";
	
	public static final String ANADAIR_USUARIO="insert into usuario values(null,?,?,?,?)";

	public static final String CON_ROWID_BUSCA_IDUSUARIO="select ID_USUARIO from usuario where ROWID=?";

	
	public static final String USAURIO_ENLINEA="update cuenta_usuario set en_linea=? where email=?";
	
	public static final String ACTIVAR_LA_CUENTA="update cuenta_usuario  set activado=1 where email=?";
	
	
	public static final String BUSCAR_CODIGO_ACTIVACION="select codigo_activacion from cuenta_usuario where email=?";
	
	public static final String ACTUALIZAR_CODIGO_ACTIVACION="update cuenta_usuario  set codigo_activacion=? where email=?";
	
	
	public static final String BUSCAR_ID_COLA_CON_QR="select id_cola from Tienda\r\n"
			+ "join  cola\r\n"
			+ "on cola.id_tienda=tienda.id_tienda\r\n"
			+ "where qr=? and estado='a' and fecha=?";
	
	public static final String CON_EMAIL_BUSCAR_ID_USUARIO="select id_usuario from cuenta_usuario where email=? ";
	
	
	public static final String ANDAIR_USUARIOENCOLA_QR="insert into usuarioEnCola values (?,?,?,'QR',null,'E',?,null)";




	
	public static final String TURNO_ACTUAL_EN_COLA="select turno_Actual from cola where id_cola=?";
	
	
	
	public static final String USUARIO_ANADIR_PRODUCTOS="insert into incorporar values(?,?,?,?)";
	
	
	public static final String BUSCAR_SI_EXISTE_IDPRODUCTO_CON_IDCOLA_IDUSUARIO="select id_usuario from incorporar where id_usuario=? and id_producto=? and id_cola=?";
	
	public static final String ACTUALIZAR_TABLA_INCORPORAR="update incorporar set cantidad_producto=? where id_usuario=? and id_producto=? and id_cola=?";
	
	
	public static final String BUSCAR_SI_USUARIO_YA_ESTA_EN_COLA="select id_usuario from usuarioencola where id_cola=? and id_usuario=? and estado_cliente='E'";

	public static final String BUSCAR_SI_USUARIO_YA_ESTA_EN_COLA_CON_FECHA="select usuarioencola.id_cola from usuarioencola \r\n"
	+ "join cola on usuarioencola.id_cola=cola.id_cola\r\n"
	+ "where fecha=? and id_usuario=? and estado_cliente='E'";
	
	
	public static final String BUSCAR_TURNO_USUARIO="select turno  from usuarioencola \r\n"
			+ "where id_cola=? and id_usuario=? and estado_cliente='E'";
	
	
	public static final String ELIMINAR_PRODUCTO_EN_INCORPORAR="DELETE FROM  incorporar WHERE id_cola=? and id_usuario=? and id_producto=?";
	
	
	public static final String CON_ID_COLA_BUSCA_QR="\r\n"
			+ "select qr from tienda join\r\n"
			+ "cola on cola.id_tienda=tienda.id_tienda\r\n"
			+ "where cola.id_cola=?";
	
}
