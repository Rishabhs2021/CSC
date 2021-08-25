package utils.ExtentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class ExtentManager {

    private static ExtentSparkReporter sparkReporter;
    private static ExtentReports extent;
    public synchronized static ExtentReports getInstance() throws UnknownHostException {
        if (Objects.isNull(extent)) {
            //extent = new ExtentReports(System.getProperty("user.dir")+"\\target\\surefire-reports\\OLR_AutomationReport.html",true);
            sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/target/reports/Github_AutomationReport.html");
            sparkReporter.config().setDocumentTitle("Automation Report"); // Tile of report
            sparkReporter.config().setReportName("GITHUB Task"); // Name of the report
            sparkReporter.config().setTheme(Theme.STANDARD);
            //htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

            InetAddress ip;
            String hostname;
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();

            extent =new ExtentReports();
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("HOSTNAME", hostname);
            extent.setSystemInfo("IP", ip.getHostAddress());
            extent.setSystemInfo("USERNAME", System.getProperty("user.name"));
            extent.attachReporter(sparkReporter);
        }
        return extent;
    }



//    private static final ExtentReports extentReports = new ExtentReports();
//
//    public synchronized static ExtentReports getExtentReports() {
//        ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/target/failsafe-reports/BIAutomationReport.html");
//        reporter.config().setReportName("SM-UI Automation Report");
//
//        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
//        extentReports.setSystemInfo("USERNAME", System.getProperty("user.name"));
//        extentReports.setSystemInfo("Author", "SM Automation Team");
//        extentReports.attachReporter(reporter);
//        return extentReports;
//    }
}
