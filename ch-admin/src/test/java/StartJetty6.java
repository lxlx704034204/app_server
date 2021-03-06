/*
 * $Id: Start.java,v 1.3 2006/06/12 04:53:34 hillenius Exp $
 * $Revision: 1.3 $
 * $Date: 2006/06/12 04:53:34 $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.management.MBeanContainer;
import org.mortbay.resource.Resource;
import org.mortbay.resource.ResourceCollection;

/**
 * Seperate startup class for people that want to run the examples directly.
 */
public class StartJetty6 {

	/**
	 * Main function, starts the jetty server.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		Server server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(8889);
		server.addConnector(connector);

		WebAppContext web = new WebAppContext();
		web.setContextPath("/");
		web.setClassLoader(Thread.currentThread().getContextClassLoader());
		
		Resource[] resources = new Resource[] {Resource.newResource("src/main/webapp")};
		web.setBaseResource(new ResourceCollection(resources));

		// prevent jetty6 locking static files
		web.getInitParams().put("org.mortbay.jetty.servlet.Default.useFileMappedBuffer", "false");

		server.addHandler(web);
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
		server.getContainer().addEventListener(mBeanContainer);
		mBeanContainer.start();

		try {
			System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
			server.start();
			while (System.in.available() == 0) {
				Thread.sleep(5000);
			}
			server.stop();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}
}
