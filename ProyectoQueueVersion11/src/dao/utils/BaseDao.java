package dao.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public abstract class BaseDao {

	// utilizar herramienta Dbutils para gesionar base de datos
	private QueryRunner queryRunner=new QueryRunner();
	
	
	
	// para insert / update y delete
	
	
	/**
	 * 
	 * @param sql
	 * @param args
	 * @return -1 error otro numero las fila que ha afectado
	 */
	public int update(String sql,Object...args) {
		
		Connection connection=JdbcUtils.getConeection();
		
		try {
			
	
		return	 queryRunner.update(connection,sql,args);
		
		} catch (SQLException e) {
	
			e.printStackTrace();
			
		}finally {
			
			JdbcUtils.close(connection);
		}
	
		return -1;
	}
	
	/**
	 * devuelve solo una linea de consulta
	 * @param <T>      el tipo de clase
	 * @param type    clase que va devolver 
	 * @param sql   sql que va ejecutar
	 * @param args      los valore de sql 
	 * @return       si el null significa no hay este usuario
	 */
	
	
// obeject.. es como un array
	public <T>T queryForOne(Class<T> type,String sql,Object...args) {
		
		Connection connection=JdbcUtils.getConeection();
		try {
			return queryRunner.query(connection,sql,new BeanHandler<T>(type),args);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			JdbcUtils.close(connection);
		}
	
		
		return null;
	}
	
	
	// devulve muchas filas
	
	public <T> List<T> queryForList(Class<T> type,String sql,Object...args){
		
		Connection connection=JdbcUtils.getConeection();
		try {
			return queryRunner.query(connection,sql,new BeanListHandler<T>(type),args);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			JdbcUtils.close(connection);
		}
	
		
		return null;
		
	}
	
	
	// devulve solo un valor

	public Object queryForUnValor(String sql,Object...args) {

		Connection connection=JdbcUtils.getConeection();
	
		try {
			
			return 	queryRunner.query(connection,sql, new ScalarHandler(),args);
			
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			
			JdbcUtils.close(connection);
		}

		return null;
	}
	
	
	
	
	
	
}
