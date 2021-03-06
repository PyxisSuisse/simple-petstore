package org.testinfected.molecule.middlewares;

import org.testinfected.molecule.Request;
import org.testinfected.molecule.Response;
import org.testinfected.molecule.decoration.Decorator;
import org.testinfected.molecule.decoration.HtmlDocumentProcessor;
import org.testinfected.molecule.decoration.HtmlPageSelector;
import org.testinfected.molecule.decoration.Layout;
import org.testinfected.molecule.decoration.PageCompositor;
import org.testinfected.molecule.decoration.Selector;
import org.testinfected.molecule.util.BufferedResponse;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class SiteMesh extends AbstractMiddleware {

    private final Selector selector;
    private final Decorator decorator;

    public static SiteMesh html(Layout layout) {
        return new SiteMesh(new HtmlPageSelector(), new PageCompositor(new HtmlDocumentProcessor(), layout));
    }

    public SiteMesh(Selector selector, Decorator decorator) {
        this.selector = selector;
        this.decorator = decorator;
    }

    public void handle(Request request, Response response) throws Exception {
        BufferedResponse capture = captureResponse(request, response);
        if (shouldDecorate(capture)) {
            decorate(response, capture);
        } else {
            write(response, capture);
        }
    }

    private void decorate(Response response, BufferedResponse buffer) throws IOException {
        response.removeHeader("Content-Length");
        Writer out = new BufferedWriter(response.writer());
        decorator.decorate(out, buffer.body());
        out.flush();
    }

    private void write(Response response, BufferedResponse buffer) throws IOException {
        response.body(buffer.body());
    }

    private BufferedResponse captureResponse(Request request, Response response) throws Exception {
        BufferedResponse capture = new BufferedResponse(response);
        forward(request, capture);
        return capture;
    }

    private boolean shouldDecorate(Response response) {
        return selector.select(response);
    }
}
