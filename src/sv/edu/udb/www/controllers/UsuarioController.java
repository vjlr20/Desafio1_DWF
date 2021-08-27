package sv.edu.udb.www.controllers;

import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.security.NoSuchAlgorithmException;

import sv.edu.udb.www.models.*;

/**
 * Servlet implementation class UsuarioController
 */
@WebServlet(name = "UsuarioController", urlPatterns = { "/admin.do" })
public class UsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static int sesion = 0;
    
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException, SQLException {
		HttpSession session = request.getSession();
		Cookie cookie = null;
		
		ArrayList<String> mensajes = new ArrayList<>();
		 
		try (PrintWriter out = response.getWriter()) {
			if (request.getParameter("op") == null) {
				if (loginRequired(request, response) > 0) {
					response.sendRedirect("admin.do?op=home");
				} else {
					request.getRequestDispatcher("/admin/Loginadmin.jsp").forward(request, response);
				}
				
				return;
			}
			
			UsuarioModel model = new UsuarioModel();
			OfertaModel ofertaModel = new OfertaModel();
			RubroModel rubroModel = new RubroModel();

			String op = request.getParameter("op");
			// System.out.println(op);
			switch (op) {
				case "login":
					String user = request.getParameter("usuario");
					String password = request.getParameter("password");

					if (model.loginAdmin(user, password) > 0) {
						sesion = 1;
						
						cookie = new Cookie("usuario", user);
						
						response.addCookie(cookie);
						response.sendRedirect("admin.do?op=home");
					} else {						
						request.getRequestDispatcher("/admin/Loginadmin.jsp").forward(request, response);
					}
					
					break;
				
				case "logout":
					sesion = 0;
					
					cookie = new Cookie("usuario", null);
					cookie.setMaxAge(0); 

					response.sendRedirect("admin.do");
					break;
				
				case "home":
					if (loginRequired(request, response) > 0) {
						request.setAttribute("ofertas", ofertaModel.ofertas());
						request.getRequestDispatcher("/admin/inicio.jsp").forward(request, response);
					} else {
						response.sendRedirect("admin.do");
					}
					
					break;
					
				case "standby":
					if (loginRequired(request, response) > 0) {
						request.setAttribute("ofertas", ofertaModel.ofertasEstado(3));
						request.getRequestDispatcher("/admin/pendientes.jsp").forward(request, response);
					} else {
						response.sendRedirect("admin.do");
					}
					
					break;
				
				case "details":
					if (loginRequired(request, response) > 0) {
						String id = request.getParameter("codigo");
						
						request.setAttribute("oferta", ofertaModel.obtenerOferta(Integer.parseInt(id)));
						request.getRequestDispatcher("/admin/detalles.jsp").forward(request, response);
					} else {
						response.sendRedirect("admin.do");
					}					
					break;
				
				case "sale":
					if (loginRequired(request, response) > 0) {
						String id = request.getParameter("codigo");
						
						request.setAttribute("oferta", ofertaModel.obtenerOferta(Integer.parseInt(id)));
						request.getRequestDispatcher("/admin/formulario.jsp").forward(request, response);
					} else {
						response.sendRedirect("admin.do");
					}					
					break;
				
				case "sale-status":
					if (loginRequired(request, response) > 0) {
						String id = request.getParameter("codigo");
						String status_a = request.getParameter("estado-aprobar");
						String status_r = request.getParameter("estado-rechazar");
						String observaciones = request.getParameter("observaciones");

						int result = 0; 
						int mensaje = 0;
						
						if (observaciones.equals("")) {
							result = ofertaModel.validarOferta(id, status_a, "");
						} else {
							result = ofertaModel.validarOferta(id, status_r, observaciones);
						}
						
						if (result > 0) {
							if (observaciones.equals("")) {
								mensaje = 1;
							} else {
								mensaje = 2;	
							}
						}

						response.sendRedirect("admin.do?op=details&codigo=" + id + "&message=" + mensaje);
					} else {
						response.sendRedirect("admin.do");
					}					
					break;
					
				case "headings":
					if (loginRequired(request, response) > 0) {
						request.setAttribute("rubros", rubroModel.rubros());
						request.getRequestDispatcher("/admin/rubros.jsp").forward(request, response);
					} else {
						response.sendRedirect("admin.do");
					}
					break;
				
				case "details-heading":
					if (loginRequired(request, response) > 0) {
						String id = request.getParameter("codigo");
						
						request.setAttribute("rubro", rubroModel.obtenerRubro(Integer.parseInt(id)));
						request.getRequestDispatcher("/admin/verRubro.jsp").forward(request, response);
					} else {
						response.sendRedirect("admin.do");
					}					
					break;
					
				case "new-heading":
					if (loginRequired(request, response) > 0) {
						request.getRequestDispatcher("/admin/nuevoRubro.jsp").forward(request, response);
					} else {
						response.sendRedirect("admin.do");
					}					
					break;
				
				case "insert-heading":
					if (loginRequired(request, response) > 0) {
						String rubro = request.getParameter("rubro");

						int result = 0; 
						int mensaje = 0;

						result = rubroModel.nuevoRubro(rubro);
						
						if (result > 0) {
							mensaje = 1;
						}
						
						int id = rubroModel.buscarRubro(rubro);
						
						response.sendRedirect("admin.do?op=details-heading&codigo=" + id + "&message=" + mensaje);
					} else {
						response.sendRedirect("admin.do");
					}					
					break;
				
				case "edit-heading":
					if (loginRequired(request, response) > 0) {
						String id = request.getParameter("codigo");
						
						request.setAttribute("rubro", rubroModel.obtenerRubro(Integer.parseInt(id)));
						request.getRequestDispatcher("/admin/editarRubro.jsp").forward(request, response);
					} else {
						response.sendRedirect("admin.do");
					}					
					break;
				
				case "update-heading":
					if (loginRequired(request, response) > 0) {
						String codigo = request.getParameter("codigo");
						String rubro = request.getParameter("rubro");
						
						int result = 0; 
						int mensaje = 0;

						result = rubroModel.editarRubro(rubro, Integer.parseInt(codigo));
						
						if (result > 0) {
							mensaje = 2;
						}
						
						int id = rubroModel.buscarRubro(rubro);
						
						response.sendRedirect("admin.do?op=details-heading&codigo=" + id + "&message=" + mensaje);
					} else {
						response.sendRedirect("admin.do");
					}					
					break;
				
				case "delete-heading":
					if (loginRequired(request, response) > 0) {
						String codigo = request.getParameter("codigo");
						
						int result = 0; 
						int mensaje = 0;

						result = rubroModel.borrarRubro(Integer.parseInt(codigo));
						
						if (result > 0) {
							mensaje = 1;
						}
						
						response.sendRedirect("admin.do?op=headings&message=" + mensaje);
					} else {
						response.sendRedirect("admin.do");
					}					
					break;
					
				default:
					
					break;
			}
		}
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (NoSuchAlgorithmException | ServletException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (NoSuchAlgorithmException | ServletException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected int loginRequired(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int res = 0;
		System.out.println(sesion);
		if (sesion > 0) {
			res = 1;
		} 
		
		return res;
	}
}
