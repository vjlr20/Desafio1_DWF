package sv.edu.udb.www.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import sv.edu.udb.www.models.OfertaModel;
import sv.edu.udb.www.models.EmpresaModel;

/**
 * Servlet implementation class OfertaCuponesController
 */
@WebServlet(name = "OfertaCuponesController", urlPatterns = { "/ofertacupon.do" })
public class OfertaCuponesController extends HttpServlet {
	
	OfertaModel modelo= new OfertaModel();
	EmpresaModel modeloEmpresa = new EmpresaModel();
	
	int idEmpresa;
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		try (PrintWriter out = response.getWriter()) {
			
			if (request.getParameter("op") == null) {
				ofertas(request, response);
				return;
			}
			String operacion = request.getParameter("op");

			switch (operacion) {
			case "ofertasEmpresa":
				ofertas(request, response);
				break;
			case "detallesCupones":
				 verDetallesCupones(request, response);
				 break;
			case "asignarCompraCliente":
				out.print("esta entrenado");
				cambiarEstadoCupon(request, response);
				break;
			default:
				break;
			}
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
	
	@Override
	public String getServletInfo() {
		return "Short description";
	}
	
	//VER
	private void ofertas(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setAttribute("ofertasCupones",modelo.obtenerOfertasClientes());
			request.getRequestDispatcher("/clientes/ofertasCupones.jsp").forward(request, response);
		}catch (SQLException | ServletException | IOException ex) {
			Logger.getLogger(OfertaCuponesController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	private void verDetallesCupones(HttpServletRequest request, HttpServletResponse response) {
		
		String idOferta = request.getParameter("idoferta");
		String idEmpresa = request.getParameter("empresa");
		int ofertaID = Integer.parseInt(idOferta);
		int empresaID = Integer.parseInt(idEmpresa);
		
		try {
			String cuponDisponible = modelo.cuponDisponible(ofertaID);
			request.setAttribute("detallesCupones",modelo.mostrarDetallesCupon(ofertaID));
			request.setAttribute("cuponDisponible", cuponDisponible);
			request.setAttribute("cuponId", modelo.cuponId(cuponDisponible));
			request.setAttribute("empresa",modeloEmpresa.obtenerEmpresa(empresaID));
			request.getRequestDispatcher("/clientes/detallesCupones.jsp").forward(request, response);
		}catch (SQLException | ServletException | IOException ex) {
			Logger.getLogger(OfertaCuponesController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}

	private void cambiarEstadoCupon(HttpServletRequest request, HttpServletResponse response) {

		String idCliente = request.getParameter("idCliente");
		String ultimos4 = request.getParameter("ultimos4");
		int cupon = Integer.parseInt(request.getParameter("cupon"));

			
		try {
			modelo.cambiarEstadoCupon(cupon);
			
			modelo.asignarCuponCliente(cupon, Integer.parseInt(idCliente), Integer.parseInt(ultimos4));
			
			
		response.sendRedirect(request.getContextPath() +"/clientes.do?op=perfil&clienteID="+idCliente);
		}catch (SQLException | IOException ex) {
			Logger.getLogger(OfertaCuponesController.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}

	
	
	private void insertar(HttpServletRequest request, HttpServletResponse response) {
		
		
	}

}
