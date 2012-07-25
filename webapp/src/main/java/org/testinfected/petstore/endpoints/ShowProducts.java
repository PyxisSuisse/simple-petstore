package org.testinfected.petstore.endpoints;

import com.pyxis.petstore.domain.product.Product;
import com.pyxis.petstore.domain.product.ProductCatalog;
import org.testinfected.petstore.dispatch.Dispatch;
import org.testinfected.petstore.dispatch.EndPoint;
import org.testinfected.petstore.util.ContextBuilder;

import java.util.List;

import static org.testinfected.petstore.util.ContextBuilder.context;

public class ShowProducts implements EndPoint {

    private final ProductCatalog productCatalog;

    public ShowProducts(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    public void process(Dispatch.Request request, Dispatch.Response response) throws Exception {
        String keyword = request.getParameter("keyword");
        List<Product> matchingProducts = productCatalog.findByKeyword(keyword);

        ContextBuilder context = context().with("keyword", keyword);
        if (matchingProducts.isEmpty()) {
            response.render("no-product", context.asMap());
        } else {
            response.render("products", context.
                    with("products", matchingProducts).
                    and("matchCount", matchingProducts.size()).
                    asMap());
        }
    }
}
