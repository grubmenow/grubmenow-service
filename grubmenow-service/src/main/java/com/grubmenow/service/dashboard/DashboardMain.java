package com.grubmenow.service.dashboard;

import java.io.File;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

public class DashboardMain {

	public static void main(String[] args) throws Exception {

		String webappDirLocation = "src/main/webapp/";
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);

		StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
		System.out.println("configuring app with basedir: "
				+ new File("./" + webappDirLocation).getAbsolutePath());

//		((StandardJarScanner) ctx.getJarScanner()).setScanAllDirectories(true);
//		//declare an alternate location for your "WEB-INF/classes" dir:     
//		File additionWebInfClasses = new File("target/classes");
//		WebResourceRoot resources = new StandardRoot(ctx);
//		resources.setExtraResourcePaths("/WEB-INF/classes=" + additionWebInfClasses);
//		ctx.setResources(resources);
		
		tomcat.start();
		tomcat.getServer().await();
	}
}
