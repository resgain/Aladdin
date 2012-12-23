package aladdin;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

public class AladdinServer 
{
	public static void main(String[] args) throws Exception {
		Server server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(8080);
		server.addConnector(connector);
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setContextPath("/");
		webAppContext.setDescriptor("/WEB-INF/web.xml");
		webAppContext.setResourceBase("./web");
		webAppContext.setConfigurationDiscovered(true);
		webAppContext.setParentLoaderPriority(true);
		server.setHandler(webAppContext);
		QueuedThreadPool pl = new QueuedThreadPool();
		pl.setMaxThreads(2);
		pl.setMaxQueued(2);
		connector.setThreadPool(pl);
        server.setThreadPool(pl); 
		server.start();
	}
}