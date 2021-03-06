package test.unit.org.testinfected.petstore.controllers;

import org.junit.Test;
import org.testinfected.petstore.controllers.StaticPage;
import test.support.org.testinfected.molecule.unit.MockRequest;
import test.support.org.testinfected.molecule.unit.MockResponse;
import test.support.org.testinfected.petstore.web.MockPage;

import static org.hamcrest.CoreMatchers.nullValue;

public class StaticPageTest {

    MockPage page = new MockPage();
    StaticPage staticPage = new StaticPage(page);

    MockRequest request = new MockRequest();
    MockResponse response = new MockResponse();

    @Test public void
    rendersPageWithEmptyContext() throws Exception {
        staticPage.handle(request, response);
        page.assertRenderedTo(response);
        page.assertRenderedWith(nullValue());
    }
}

