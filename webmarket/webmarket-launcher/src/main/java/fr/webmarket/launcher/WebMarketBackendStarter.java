package fr.webmarket.launcher;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class WebMarketBackendStarter {

	private static final String ROOT_CONTEXT = "/*";
	private static final String REST_ROOT_CONTEXT = "/rest/*";

	private static final int SERVER_PORT = 8080;

	private static final String REST_RESOURCES_PACKAGE = "fr.webmarket.backend.rest";

	private static final String RESOURCES_BASE_PATH = "src/main/resources/frontend";

	public static void main(String[] args) throws Exception {

		// Initialize the server object
		Server server = new Server(SERVER_PORT);

		// Initialize the servlet handler
		ServletContextHandler servletContextHandler = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		ServletHolder servletHolder = new ServletHolder(ServletContainer.class);

		// Jersey dispatcher for REST resources
		servletHolder.setInitParameter(
				"com.sun.jersey.config.property.resourceConfigClass",
				"com.sun.jersey.api.core.PackagesResourceConfig");
		servletHolder.setInitParameter(
				"com.sun.jersey.config.property.packages",
				REST_RESOURCES_PACKAGE);
		servletContextHandler.addServlet(servletHolder, REST_ROOT_CONTEXT);

		// Initialize the resource handler for the frontend
		servletContextHandler.addServlet(
				DefaultServlet.class.getCanonicalName(), ROOT_CONTEXT);
		servletContextHandler.setResourceBase(RESOURCES_BASE_PATH);

		// Start the server
		server.setHandler(servletContextHandler);
		server.setStopAtShutdown(true);
		server.start();
		server.join();
	}
}
