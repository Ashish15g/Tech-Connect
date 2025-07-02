package com.tech_connect.listenersclass;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.tech_connect.baseclass.BaseClass;

public class ListenersClass extends BaseClass implements ITestListener {

	private ExtentReports report;
    private ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportName = "TechConnect_Report_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter("./Reports/" + reportName);
        spark.config().setDocumentTitle("Tech-Connect Testing Report");
        spark.config().setReportName("Test Execution Summary");
        spark.config().setTheme(Theme.STANDARD);

     // Load custom configuration (logo, theme, etc.)
        try {
			spark.loadJSONConfig(new File("src/test/resources/spark-config.json"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        report = new ExtentReports();
        report.attachReporter(spark);
        report.setSystemInfo("OS", System.getProperty("os.name"));
        report.setSystemInfo("Browser", "Chrome Latest");
        report.setSystemInfo("Test Engineer", "Ashish");
        report.setSystemInfo("Execution Start Time", timestamp);

        Reporter.log("🚀 Test Execution Started at: " + timestamp, true);
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        test = report.createTest(testName)
                .assignCategory(result.getMethod().getGroups())
                .assignAuthor("QA Team")
                .info("🟢 Test Started: " + testName);

        Reporter.log("🟢 Executing Test: " + testName, true);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass(MarkupHelper.createLabel("✅ Test Passed: " + result.getMethod().getMethodName(), ExtentColor.GREEN));
        test.info("Execution Time: " + getExecutionTime(result) + " seconds");

        Reporter.log("✅ Test Passed: " + result.getMethod().getMethodName(), true);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable failureCause = result.getThrowable();

        test.fail(MarkupHelper.createLabel("❌ Test Failed: " + testName, ExtentColor.RED));
        test.fail("❗ Reason: " + (failureCause != null ? failureCause.getMessage() : "No exception message"));
        if (failureCause != null) {
            test.fail(failureCause); // Adds full stack trace
        }

        captureScreenshot(test, testName);

        Reporter.log("❌ Test Failed: " + testName + " | Reason: " + (failureCause != null ? failureCause.getMessage() : "Unknown"), true);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable skipReason = result.getThrowable();

        test.skip(MarkupHelper.createLabel("⚠ Test Skipped: " + testName, ExtentColor.ORANGE));
        test.skip("Reason: " + (skipReason != null ? skipReason.getMessage() : "No reason provided"));

        captureScreenshot(test, testName);

        Reporter.log("⚠ Test Skipped: " + testName, true);
    }

    @Override
    public void onFinish(ITestContext context) {
        String endTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        report.setSystemInfo("Execution End Time", endTime);
        report.flush();

        Reporter.log("🏁 Test Execution Completed at: " + endTime, true);
    }

    // Capture screenshot and attach to the report
    private void captureScreenshot(ExtentTest test, String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            String screenshot = ts.getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(screenshot, "Screenshot for: " + testName);
        } catch (Exception e) {
            test.warning("⚠ Screenshot capture failed: " + e.getMessage());
        }
    }

    // Calculate execution time for a test
    private String getExecutionTime(ITestResult result) {
        long duration = (result.getEndMillis() - result.getStartMillis()) / 1000;
        return String.valueOf(duration);
    }

}
