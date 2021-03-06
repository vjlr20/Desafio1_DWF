package sv.edu.udb.www.models;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.HeaderTokenizer.Token;

import java.sql.SQLException;
import java.security.NoSuchAlgorithmException;
import sv.edu.udb.www.db.Conexion;
import sv.edu.udb.www.utils.*;
import sv.edu.udb.www.beans.Empresa;
import sv.edu.udb.www.beans.Rubro;

public class EmpresaModel extends Conexion {

	// Ciclo registro de empresa
//	public int verificarTokenExistente(String tokenString) throws SQLException {
//		try {
//			int tokenExistente = 0;
//			String sqlString = "CALL obtenerTokenExistente(?)";
//			int idAverificar = 0;
//			this.conectar();
//			cs = conexion.prepareCall(sqlString);
//			cs.setString(1, tokenString);
//			rs = cs.executeQuery();
//			if (rs.next()) {
//				idAverificar = rs.getInt("empresa");
//				tokenExistente++;
//			}
//			this.desconectar();
//			activarEmpresa(idAverificar);
//			return tokenExistente;
//		} catch (Exception ex) {
//			// TODO: handle exception
//			Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
//			return 0;
//		} finally {
//			this.desconectar();
//		}
//	}
	public int verificarCorreoNoExistente(String correoEmpresa) throws SQLException{
		try {
			int verificado = 1;
			String sqlString = "CALL validarCorreoExistenteEmpresa(?)";
			this.conectar();
			cs = conexion.prepareCall(sqlString);
			cs.setString(1, correoEmpresa);
            rs = cs.executeQuery();			
			if(rs.next()) {
				verificado = 0;
			}
			this.desconectar();
			return verificado;
		} catch (Exception e) {
			// TODO: handle exception
			Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, e);
			this.desconectar();
			return 0;
		}
	}
	

	public List<Rubro> listarRubros() throws SQLException {
		try {
			List<Rubro> rubrosList = new ArrayList<Rubro>();
			String slqString = "SELECT * FROM `rubros` WHERE rubros.estado = 1";
			this.conectar();
			cs = conexion.prepareCall(slqString);
			rs = cs.executeQuery();

			while (rs.next()) {
				Rubro rubro = new Rubro();
				rubro.setRubro(rs.getString("rubro"));
				rubro.setRubroID(rs.getInt("rubro_id"));
				rubrosList.add(rubro);
			}

			this.desconectar();
			return rubrosList;
		} catch (Exception ex) {
			// TODO: handle exception
			Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
			this.desconectar();
			return null;
		}

	}

//	private void activarEmpresa(int idEmpresa) throws SQLException {
//		try {
//			String sqlString = "CALL activarEmpresa(?,?)";
//			this.conectar();
//			cs = conexion.prepareCall(sqlString);
//			cs.setInt(1, idEmpresa);
//			cs.setInt(2, 1);
//			cs.executeUpdate();
//			this.desconectar();
//		} catch (SQLException ex) {
//			// TODO: handle exception
//			Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
//			this.desconectar();
//		}
//
//	}
    
	public int actualPasswordExists(String actualPassword, int empresaID) throws SQLException {
		
		try {
			Sha1 getSha1 = new Sha1();
			int passwordActualCorrecto = 0;	
			String sqlString = "CALL actualPassword(?,?)";
			this.conectar();
			cs = conexion.prepareCall(sqlString);
			cs.setString(1, getSha1.sha1Hash(actualPassword));
			cs.setInt(2, empresaID);
			rs = cs.executeQuery();
			if(rs.next()) {
				passwordActualCorrecto ++;
			}
			this.desconectar();
			return passwordActualCorrecto;	
		} catch (SQLException |  NoSuchAlgorithmException ex) {
			// TODO: handle exception
			Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
			this.desconectar();
			return 0;
		}					
	}
	
	public int recuperarPassword(String emailEmpresa, int empresaID) throws SQLException {
		try {
			int filasAfectadas = 0;
			Sha1 sha1 = new Sha1();
			SendEmail mEmail = new SendEmail();
			String sqlString = "CALL recuperarPassword(?,?)";
			this.conectar();		
			cs = conexion.prepareCall(sqlString);
			String newPasswordString = mEmail.recuperarPasswordMail(emailEmpresa);
			cs.setString(1, sha1.sha1Hash(newPasswordString));
			cs.setInt(2, empresaID);
			filasAfectadas = cs.executeUpdate();
			this.desconectar();
			return filasAfectadas;
		} catch (SQLException | MessagingException | NoSuchAlgorithmException ex) {
			// TODO: handle exception
			Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
			this.desconectar();
			return 0;
		}
	}
	
	
	public int actualMailExist(String emailString) throws SQLException{
		try {
			int correoEncontrado = 0;
			String sqlString = "CALL correoExistente(?)";
			this.conectar();
			cs = conexion.prepareCall(sqlString);
			cs.setString(1, emailString);
			rs = cs.executeQuery();
			if(rs.next()) {
				correoEncontrado = rs.getInt("empresa_id");
			}
			this.desconectar();
			return correoEncontrado;
		} catch (SQLException ex) {
			// TODO: handle exception
			Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
			this.desconectar();
			return 0;
		}
	}
	
	public int updatePassword(String newPassword, int empresaID) throws SQLException {
		try {
			Sha1 getSha1 = new Sha1();
			int filasAfectadas= 0;
			String sqlString = "CALL updatePassword(?,?)";
			this.conectar();
			cs = conexion.prepareCall(sqlString);
			cs.setString(1, getSha1.sha1Hash(newPassword));
			cs.setInt(2, empresaID);
			filasAfectadas = cs.executeUpdate();
			this.desconectar();
			return filasAfectadas;
		} catch (SQLException | NoSuchAlgorithmException ex) {
			// TODO: handle exception
			Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
			this.desconectar();
			return 0;
		}
	}
	
	public int actualizarEmpresa(Empresa empresa) throws SQLException {
		try {
			int filasAfectadas = 0;
			String sqlString = "CALL updateEmpresa(?,?,?,?,?,?,?)";
			this.conectar();
			cs = conexion.prepareCall(sqlString);
			cs.setString(1, empresa.getNombreEmpresa());
			cs.setString(2, empresa.getDireccionEmpresa());
			cs.setString(3, empresa.getContactoEmpresa());
			cs.setString(4, empresa.getTelefonoEmpresa());
			cs.setString(5, empresa.getCorreoEmpresa());
			cs.setInt(6, empresa.getRubro_id());
			cs.setInt(7, empresa.getEmpresa_id());
			filasAfectadas = cs.executeUpdate();
			this.desconectar();
			return filasAfectadas;
		} catch (SQLException ex) {
			// TODO: handle exception
			Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
			this.desconectar();
			return 0;
		}
	}
	
	public int registrarEmpresaPendienteVerificaion(Empresa empresa) throws SQLException, NoSuchAlgorithmException, MessagingException {
		try {
			SendEmail mEmail = new SendEmail();
			Sha1 sha1 = new Sha1();
			int filasAfectadas = 0;
			String sql = "CALL insertarEmpresa(?,?,?,?,?,?,?,?,?,?)";
			this.conectar();
			cs = conexion.prepareCall(sql);
			cs.setString(1, empresa.getCodigo_empresa());
			cs.setString(2, empresa.getNombreEmpresa());
			cs.setString(3, empresa.getDireccionEmpresa());
			cs.setString(4, empresa.getContactoEmpresa());
			cs.setString(5, empresa.getTelefonoEmpresa());
			cs.setString(6, empresa.getCorreoEmpresa());
			String passwor = mEmail.senPasswordEmpresa(empresa.getCorreoEmpresa(), empresa.getNombreEmpresa());
			cs.setString(7, sha1.sha1Hash(passwor));
			cs.setInt(8, empresa.getRubro_id());
			cs.setDouble(9, empresa.getComisionEmpresa());
			cs.setInt(10,1);
			filasAfectadas = cs.executeUpdate();
			this.desconectar();
//			
			return filasAfectadas;
		} catch (SQLException ex) {
			// TODO: handle exception
			Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
			this.desconectar();
			return 0;
		}
	}

//	private void asignarToken(String destinatarioEmail, String nombreEmpresa) throws SQLException{
//		try {
//			CodigoEmpresa codigo = new CodigoEmpresa();
//			SendEmail email = new SendEmail();
//			String sql = "CALL insertarToken(?,?)";
//			this.conectar();
//			cs = conexion.prepareCall(sql); 
//			cs.setInt(1, codigo.codigoEmpresaToken());
//			cs.setString(2, email.sendEmpresaVerificationEmail(destinatarioEmail, nombreEmpresa));
//			cs.executeUpdate();
//			this.desconectar();
//		} catch (SQLException | MessagingException ex) {
//			// TODO: handle exception
//			Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
//			this.desconectar();
//		}
//	}

	public Empresa iniciarSesion(String correoEmpresa, String passwordEmpresa) throws SQLException {
		try {
			Sha1 getSha1 = new Sha1();
			Empresa logEmpresa = new Empresa();
			String sqlString = "CALL loginEmpresa(?,?)";
			this.conectar();
			cs = conexion.prepareCall(sqlString);
			cs.setString(1, correoEmpresa);
			cs.setString(2, getSha1.sha1Hash(passwordEmpresa));
			rs = cs.executeQuery();
			if (rs.next()) {
				logEmpresa.setEmpresa_id(rs.getInt("empresa_id"));
				logEmpresa.setNombreEmpresa(rs.getString("nombre"));
				logEmpresa.setContactoEmpresa(rs.getString("contacto"));
				logEmpresa.setTelefonoEmpresa(rs.getString("telefono"));
				logEmpresa.setDireccionEmpresa(rs.getString("direccion"));
				logEmpresa.setCorreoEmpresa(rs.getString("correo"));
				logEmpresa.setRubro_id(rs.getInt("rubro_id"));
				this.desconectar();
				return logEmpresa;
			}

			this.desconectar();
			return null;
		} catch (SQLException | NoSuchAlgorithmException ex) {
			// TODO: handle exception

			Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
			this.desconectar();
			return null;
		}
	}

	public Empresa recuperarSesion(Object idEmpresa) throws SQLException {
		try {
			Empresa logEmpresa = new Empresa();
			String sqlString = "CALL obtenerEmpresa(?)";
			this.conectar();
			cs = conexion.prepareCall(sqlString);
			cs.setObject(1, idEmpresa);
			rs = cs.executeQuery();
			if (rs.next()) {
				logEmpresa.setEmpresa_id(rs.getInt("empresa_id"));
				logEmpresa.setNombreEmpresa(rs.getString("nombre"));
				logEmpresa.setContactoEmpresa(rs.getString("contacto"));
				logEmpresa.setTelefonoEmpresa(rs.getString("telefono"));
				logEmpresa.setDireccionEmpresa(rs.getString("direccion"));
				logEmpresa.setCorreoEmpresa(rs.getString("correo"));
				logEmpresa.setRubro_id(rs.getInt("rubro_id"));
				this.desconectar();
				return logEmpresa;
			}

			this.desconectar();
			return null;
		} catch (SQLException ex) {
			// TODO: handle exception

			Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
			this.desconectar();
			return null;
		}
	}

	// Obtenemos la empresa
	public Empresa obtenerEmpresa(int idEmpresa) throws SQLException {
		try {
			String sql = "SELECT * FROM empresas WHERE empresa_id = ?";

			Empresa empresa = new Empresa();

			this.conectar();

			cs = conexion.prepareCall(sql);

			cs.setInt(1, idEmpresa);

			rs = cs.executeQuery();

			if (rs.next()) {
				RubroModel rubro = new RubroModel();
				String rubroNombre = rubro.obtenerRubro(rs.getInt("rubro_id")).getRubro();
				empresa.setEmpresa_id(rs.getInt("empresa_id"));
				empresa.setCodigo_empresa(rs.getString("codigo"));
				empresa.setNombreEmpresa(rs.getString("nombre"));
				empresa.setDireccionEmpresa(rs.getString("direccion"));
				empresa.setContactoEmpresa(rs.getString("contacto"));
				empresa.setTelefonoEmpresa(rs.getString("telefono"));
				empresa.setComisionEmpresa(rs.getDouble("comision"));
				empresa.setCorreoEmpresa(rs.getString("correo"));
				empresa.setRubroNombre(rubroNombre);

				this.desconectar();

				return empresa;
			}

			this.desconectar();

			return null;
		} catch (SQLException ex) {
			Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);

			this.desconectar();

			return null;
		}
	}
	
	// Listado de empresas
	public List<Empresa> listarEmpresa() throws SQLException {
		ArrayList<Empresa> empresas = new ArrayList<Empresa>();
		
		String sql = "SELECT * FROM empresas WHERE estado = 1";
		
		try {
			this.conectar();

			cs = conexion.prepareCall(sql);

			rs = cs.executeQuery();

			while (rs.next()) {
				Empresa empresa = new Empresa();
				
				empresa.setEmpresa_id(rs.getInt("empresa_id"));
				empresa.setCodigo_empresa(rs.getString("codigo"));
				empresa.setNombreEmpresa(rs.getString("nombre"));
				empresa.setContactoEmpresa(rs.getString("contacto"));
				empresa.setTelefonoEmpresa(rs.getString("telefono"));
				empresa.setComisionEmpresa(rs.getDouble("comision"));
				
				empresas.add(empresa);
			}

			this.desconectar();

			return empresas;
		} catch (SQLException ex) {
			Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);

			this.desconectar();

			return null;
		}
	}
}
