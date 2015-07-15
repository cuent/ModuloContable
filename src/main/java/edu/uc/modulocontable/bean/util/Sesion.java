/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.bean.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author miguesaca
 */
public class Sesion {

    public static void creaSesion() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession(true);
    }

    public static void tiempoInactividad(int tiempo) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession().setMaxInactiveInterval(tiempo);
       

    }

    public static void setVariable(String nombre, Object dato) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession().putValue(nombre, dato);
    }

    public static Object getVariable(String nombre) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Object dato = request.getSession().getAttribute(nombre);
        return dato;
    }

    public static void redireccionaPagina(String url)  {

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(Sesion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    public static void validaSesion() throws IOException {
//        //System.out.println("ActivacionUsuario.isCambiarContrasena() "+ActivacionUsuario.isCambiarContrasena());
//        if (Sesion.getVariable("usuario") == null && Sesion.getVariable("empresa") == null ) {
//            String url = ResourceBundle.getBundle("/BundleObjetosES").getString("index");
//            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
//        } 
//        else if (Sesion.getVariable("usuario") != null && ActivacionUsuario.isCambiarContrasena()) {
//           // String url = ResourceBundle.getBundle("/propiedadesObjetosEC").getString("UrlLoginMKS");
//           // FacesContext.getCurrentInstance().getExternalContext().redirect(url);
//        }
//        
//    }
//    public static void cerrarSesion() throws ServletException {
//        try {
//            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            HttpSession sesion = request.getSession();
//            if (sesion != null) {
//                //new ActivacionUsuarioMKP().limpiaActivacion();
//                ActivacionUsuario.setUsuario(null);
//                sesion.invalidate();
//                
//            }
//            String url = ResourceBundle.getBundle("/BundleObjetosES").getString("index");
//            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
//        } catch (IOException ex) {
//            Logger.getLogger(Sesion.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }



    public static void invalidaSesion() throws ServletException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession sesion = request.getSession();
        if (sesion != null) {
            sesion.invalidate();
            //System.out.println("Invalidando Sesion ");
        }
    }

    public static String MD5(String clave) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(clave.getBytes("UTF-8"), 0, clave.length());
            byte[] bt = md.digest();
            BigInteger bi = new BigInteger(1, bt);
            String md5 = bi.toString(16);
            ////System.out.println("clave " + md5);
            return md5;
        } catch (UnsupportedEncodingException ex) {

        }
        return null;

    }

    public static Date obtieneFechaCaducidad(Date fecha, int meses,int dias) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe	
        calendar.add(Calendar.MONTH, meses);  // numero de días a añadir, o restar en caso de días<0
        calendar.add(Calendar.DATE, dias);
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos

    }
}
