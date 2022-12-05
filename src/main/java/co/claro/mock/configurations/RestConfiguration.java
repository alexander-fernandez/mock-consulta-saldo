package co.claro.mock.configurations;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Rest Route Builder Class
 *
 * @author munenedk
 * @version 1.0.0
 * @since June 22, 2020
 * @implNote This class configures the rest component for all endpoints
 */
@Component
public class RestConfiguration extends RouteBuilder {

    @Value("${camelComponent}")
    private String camelComponent;

    @Value("${mock.api.path}")
    private String apiPath;

    @Value("${mock.api.enableCors}")
    private Boolean apiEnableCors;

    @Value("${mock.api-docs.path}")
    private String apiDocsPath;

    @Value("${mock.api-docs.version}")
    private String apiDocsVersion;

    @Value("${mock.api-docs.title}")
    private String apiDocsTitle;

    @Override
    public void configure() throws Exception {

        // Rest Configuration
		// Rest Configuration
		restConfiguration()
			.component(camelComponent)
			.contextPath(apiPath)
			.enableCORS(apiEnableCors)
			.corsHeaderProperty("Access-Control-Allow-Methods", "POST")
				.apiContextPath(apiDocsPath)
				.apiProperty("api.title", apiDocsTitle)
				.apiProperty("api.version", apiDocsVersion)
				.apiProperty("cors", apiEnableCors.toString())
				.bindingMode(RestBindingMode.off)
				.dataFormatProperty("prettyPrint","true")
				.apiVendorExtension(true);
    }
}