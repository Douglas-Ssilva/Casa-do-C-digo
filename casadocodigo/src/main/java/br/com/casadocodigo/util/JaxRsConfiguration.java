package br.com.casadocodigo.util;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/services") //JaxRS passará a atender a partir dessa String
public class JaxRsConfiguration extends Application{

}
