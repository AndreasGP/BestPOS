package ee.ut.math.tvt.vapradjailusad;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;




public class Intro {

	private static final Logger log = Logger.getLogger(Intro.class);
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("etc/log4j.properties");
		
		
		IntroUI ui = new IntroUI();
        
		log.info("Intro started");

	}

}
