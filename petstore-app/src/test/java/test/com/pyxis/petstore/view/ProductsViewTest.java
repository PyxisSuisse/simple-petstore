package test.com.pyxis.petstore.view;

import static com.google.common.collect.Iterables.size;
import static com.threelevers.css.DocumentBuilder.doc;
import static com.threelevers.css.DocumentBuilder.dom;
import static com.threelevers.css.Selector.from;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static test.support.com.pyxis.petstore.builders.Entities.entities;
import static test.support.com.pyxis.petstore.builders.ProductBuilder.aProduct;
import static test.support.com.pyxis.petstore.matchers.DomMatchers.hasUniqueSelector;
import static test.support.com.pyxis.petstore.matchers.DomMatchers.withText;
import static test.support.com.pyxis.petstore.velocity.VelocityRendering.render;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;

import test.support.com.pyxis.petstore.builders.EntityBuilder;

public class ProductsViewTest {
    private static final String PRODUCTS_VIEW = "products";
	private String searchResultsPage;

	@Before
	public void setUp() {
		searchResultsPage = render(PRODUCTS_VIEW).using(aModelWith(
				aProduct().withName("Dalmatian"),
				aProduct().withName("Labrador")));
	}
	
    @Test
    public void displaysNumberOfProductsFound() {
        assertThat(dom(searchResultsPage), hasUniqueSelector("#match-count", withText("2")));
    }
    
    @Test
    public void displaysASummaryOfEachMatchingProducts() {
    	assertThat(size(from(doc(searchResultsPage)).select("#product")), is(equalTo(2)));
    }

	private static Map<String, ?> aModelWith(EntityBuilder<?>... entityBuilders) {
        ModelMap model = new ModelMap();
        model.addAttribute(entities(entityBuilders));
        return model;
    }
}
