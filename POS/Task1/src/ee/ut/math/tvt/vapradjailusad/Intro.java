package ee.ut.math.tvt.vapradjailusad;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ee.ut.math.tvt.vapradjailusad.domain.controller.SalesDomainController;
import ee.ut.math.tvt.vapradjailusad.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.vapradjailusad.ui.ConsoleUI;
import ee.ut.math.tvt.vapradjailusad.ui.SalesSystemUI;




public class Intro {

	private static final Logger log = Logger.getLogger(Intro.class);
	private static final String MODE = "console";

	
	public static void main(String[] args) {
		PropertyConfigurator.configure("etc/log4j.properties");
		final SalesDomainController domainController = new SalesDomainControllerImpl();
		
		
		if (args.length == 1 && args[0].equals(MODE)) {
			log.debug("Mode: " + MODE);

			ConsoleUI cui = new ConsoleUI(domainController);
			cui.run();
		} else {

			IntroUI introUI = new IntroUI();
			introUI.setVisible(true);
			introUI.setAlwaysOnTop(true);

			final SalesSystemUI ui = new SalesSystemUI(domainController);
			ui.setVisible(true);

			introUI.setAlwaysOnTop(false);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			introUI.setVisible(false);
		}
		
        
		log.info("Intro started");

	}

}
