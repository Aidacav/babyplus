package com.aida.babyplus.controlador.filtros;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.servicio.ServicioClientes;

/**
 *
 * @author Aida
 */
@WebFilter(filterName = "UsuarioCliente", urlPatterns = {"/babyplus/jsp/privado/cliente/*"})
public class UsuarioCliente implements Filter {
    
    private static final ServicioClientes SERVICIO_CLIENTES = new ServicioClientes();
    private static final String SUBSCRIPCION_PENDIENTE = "PENDIENTE";
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public UsuarioCliente() {
    }    

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Usuario usuario = ((Usuario) httpServletRequest.getSession().getAttribute("usuario"));


        if (SERVICIO_CLIENTES.esCliente(usuario)) {
            
            if(estaRenovando(httpServletRequest) || SERVICIO_CLIENTES.tieneSubscripcionActiva(usuario.getId())) {
                httpServletRequest.getSession().removeAttribute(SUBSCRIPCION_PENDIENTE);
                chain.doFilter(request, response);
            } else {
                if(httpServletRequest.getSession().getAttribute("error") == null) {
                    httpServletRequest.getSession().setAttribute("error", "subscricion.mensaje");
                }
                httpServletRequest.getSession().setAttribute(SUBSCRIPCION_PENDIENTE, "true");
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/babyplus/jsp/privado/cliente/renovarSubscripcion.jsp");
            }
        } else {
            httpServletRequest.getSession().setAttribute("error", "login.error.privilegios.insuficientes");
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/babyplus/jsp/paginaLogin.jsp");
        }
    }
    
    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("UsuarioLogado:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("UsuarioLogado()");
        }
        StringBuffer sb = new StringBuffer("UsuarioLogado(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }

    private boolean estaRenovando(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getSession().getAttribute(SUBSCRIPCION_PENDIENTE) == "true" || httpServletRequest.getParameter(SUBSCRIPCION_PENDIENTE) != null;
    }

}
