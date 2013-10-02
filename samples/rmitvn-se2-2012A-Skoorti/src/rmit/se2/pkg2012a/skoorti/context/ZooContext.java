
package rmit.se2.pkg2012a.skoorti.context;

import org.springframework.context.ApplicationContext;

public class ZooContext {
    private static ApplicationContext appContext;
    
    public static void setAppContext(ApplicationContext ctx) {
        appContext = ctx;
    }
    
    public static ApplicationContext getAppContext() {
        return appContext;
    }
}
