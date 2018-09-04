package report;

import config.ResourceConfigProvider;
import config.TestCase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: refactor this ugly code!
public class HtmlTestListener implements TestListener {

    private final String templateDirectory;
    private final String templateName;
    private Map<String, Object> root = new HashMap<>();
    private List<TestResult> results = new ArrayList<>();
    private Configuration cfg;
    Template temp;

    public HtmlTestListener(String templateDirectory, String templateName) {
        this.templateName = templateName;
        this.templateDirectory = templateDirectory;
        configure();
        configureTemplate();
    }

    public HtmlTestListener(File templateFile) {
        this.templateName = templateFile.getName();
        this.templateDirectory = templateFile.toPath().getParent().getFileName().toAbsolutePath().toString();
        configure();
        configureTemplate();
    }

    private void configureTemplate() {
        try {
            temp = cfg.getTemplate(templateName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configure() {
        this.cfg = new Configuration(Configuration.VERSION_2_3_27);
        try {
            cfg.setDirectoryForTemplateLoading(new ResourceConfigProvider().getResource("ftl"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
    }

    @Override
    public void onTestSuccess(TestCase testCase) {
        TestResult result = new TestResult();
        result.setStatus(Status.SUCCESS);
        result.setTestCase(testCase);
        results.add(result);
    }

    @Override
    public void onTestFailure(TestCase testCase, String failureMessage) {
        TestResult result = new TestResult();
        result.setStatus(Status.FAILURE);
        result.setTestCase(testCase);
        result.setFailureMessage(failureMessage);
        results.add(result);
    }

    @Override
    public void onTestSkipped(TestCase testCase) {
        TestResult result = new TestResult();
        result.setStatus(Status.SKIPPED);
        result.setTestCase(testCase);
        results.add(result);
    }

    @Override
    public void onTestsFinish() {
        root.put("results", results);
        root.put("name", "TEST NAME");
        root.put("result", results.get(0));
        root.put("results", results);
        Writer out = new OutputStreamWriter(System.out);
        try {
            temp.process(root, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            out = new FileWriter("test-report.html");
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }
}
