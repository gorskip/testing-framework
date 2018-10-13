package pl.pg;

import org.json.JSONObject;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import pl.pg.annotation.Test;
import pl.pg.engine.config.Story;
import pl.pg.engine.config.TestCase;
import pl.pg.exception.TestCaseNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

public class StoryRunner extends Runner {

    public static JSONObject params = new JSONObject();
    private Class testClass;
    private Story story;

    public StoryRunner(Class testClass) {
        super();
        this.testClass = testClass;
        this.story = prepareStoryData();

        Map<String, Object> params = story.getParams();
        if(params != null) {
            params.entrySet().forEach(entrySet -> StoryRunner.params.put(entrySet.getKey(), entrySet.getValue()));
        }
    }

    @Override
    public Description getDescription() {
        return Description.createTestDescription(testClass, story.getName());
    }

    @Override
    public void run(RunNotifier runNotifier) {
        try {
            Object testObject = testClass.newInstance();
            for (Method method : testClass.getMethods()) {
                if (method.isAnnotationPresent(Test.class)) {
                    try {
                        executeTest(runNotifier, testObject, method);
                    }catch(Exception e) {
                        String name = testCaseFromAnnotation(method).getName();
                        runNotifier.fireTestFailure(new Failure(Description.createTestDescription(testClass, name), e));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void executeTest(RunNotifier runNotifier, Object testObject, Method method ) throws InvocationTargetException, IllegalAccessException {
        TestCase testCase =  testCaseFromAnnotation(method);
        runNotifier.fireTestStarted(Description
                .createTestDescription(testClass, testCase.getName()));
        method.invoke(testObject, testCase);
        runNotifier.fireTestFinished(Description
                .createTestDescription(testClass, testCase.getName()));
    }

    private TestCase testCaseFromAnnotation(Method method) {
        Test annotation = method.getAnnotation(Test.class);
        return getTestCase(annotation.value());
    }

    private Story prepareStoryData() {
        pl.pg.annotation.Story annotation = (pl.pg.annotation.Story) testClass.getAnnotation(pl.pg.annotation.Story.class);
        StoryProcessor storyProcessor = new StoryProcessor();
        return storyProcessor.getStory(annotation.value());
    }

    public Story getStory() {
        return story;
    }

    private TestCase getTestCase(String testCaseName) {
        Optional<TestCase> first = this.story.getTests().stream()
                .filter(testCase -> testCaseName.toLowerCase().equals(testCase.getName().toLowerCase()))
                .findFirst();
        if(first.isPresent()) {
            return first.get();
        }
        throw new TestCaseNotFoundException("Cannot find Test case with name: [" + testCaseName +"]");
    }


}
