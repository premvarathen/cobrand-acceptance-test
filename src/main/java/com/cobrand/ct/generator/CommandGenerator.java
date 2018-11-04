package com.cobrand.ct.generator;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cobrand.ct.controller.ReservationWrapper;
import com.cobrand.ct.domain.Reservation;
import com.cobrand.ct.sabre.VelocityTemplateDeterminer;

import aa.ct.fly.bdd.pnr.compromisedException.SabreCommandsCouldNotBeCreated;

public class CommandGenerator {
  private static final Logger LOGGER = LoggerFactory.getLogger(CommandGenerator.class);

  private VelocityEngine ve;
  private VelocityTemplateDeterminer templateDeterminer;

  public CommandGenerator() {
    ve = new VelocityEngine();
    ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
    ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    ve.init();
    templateDeterminer = new VelocityTemplateDeterminer();
  }

  public String generator(Reservation reservation) {

    VelocityContext context = setVelocityContext(reservation);
    Template template = getTemplate(reservation);

    StringWriter sw = new StringWriter();
    template.merge(context, sw);

    return sw.toString();
  }
  
  public String generator(ReservationWrapper reservation) {

	    VelocityContext context = setVelocityContext(reservation);
	    Template template = getTemplate(reservation);

	    StringWriter sw = new StringWriter();
	    template.merge(context, sw);

	    return sw.toString();
	  }
  

  private VelocityContext setVelocityContext(Reservation reservation) {
    VelocityContext context = new VelocityContext();
    context.put("pnr", reservation);
    return context;
  }

  private VelocityContext setVelocityContext(ReservationWrapper reservation) {
	    VelocityContext context = new VelocityContext();
	    context.put("pnr", reservation);
	    return context;
	  }
  
  private Template getTemplate(Reservation reservation) {
    String templateFile = templateDeterminer.determine(reservation);
    Template template;

    try {
      template = ve.getTemplate(templateFile);
    }
    catch(ResourceNotFoundException rnfe) {
      LOGGER.error("couldn't find the template {}", templateFile);
      throw new SabreCommandsCouldNotBeCreated("couldn't find the template " + templateFile);
    }
    catch(ParseErrorException pee) {
      LOGGER.error("syntax error: problem parsing the template");
      throw new SabreCommandsCouldNotBeCreated("syntax error: problem parsing the template");

    }
    catch(MethodInvocationException mie) {
      LOGGER.error("something invoked in the template threw an exception");
      throw new SabreCommandsCouldNotBeCreated("something invoked in the template threw an exception");

    }
    catch(Exception e) {
      LOGGER.error(e.getMessage());
      throw new SabreCommandsCouldNotBeCreated("something invoked in the template threw an exception");

    }
    return template;
  }
  private Template getTemplate(ReservationWrapper reservation) {
	    String templateFile = templateDeterminer.determine(reservation);
	    Template template;

	    try {
	      template = ve.getTemplate(templateFile);
	    }
	    catch(ResourceNotFoundException rnfe) {
	      LOGGER.error("couldn't find the template {}", templateFile);
	      throw new SabreCommandsCouldNotBeCreated("couldn't find the template " + templateFile);
	    }
	    catch(ParseErrorException pee) {
	      LOGGER.error("syntax error: problem parsing the template");
	      throw new SabreCommandsCouldNotBeCreated("syntax error: problem parsing the template");

	    }
	    catch(MethodInvocationException mie) {
	      LOGGER.error("something invoked in the template threw an exception");
	      throw new SabreCommandsCouldNotBeCreated("something invoked in the template threw an exception");

	    }
	    catch(Exception e) {
	      LOGGER.error(e.getMessage());
	      throw new SabreCommandsCouldNotBeCreated("something invoked in the template threw an exception");

	    }
	    return template;
	  }

}

