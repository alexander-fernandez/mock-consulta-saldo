package co.claro.mock.routes;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;


import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Dispatcher Route Class
 *
 * @author Andrés Vazquez
 * @version 1.0.0
 * @implNote This class configures all routes to dispatch events from rest services
 */
@Component
public class AppRouteBuilder extends RouteBuilder {
	
	public static final String SERVICE_SECURITY_DEFINITION_BASIC_VALUE = "Basic";	
	public static final String SERVICE_AUTHORIZATION_HEADER_NAME = "Authorization";
	
    @Override
    public void configure() throws Exception {
    	
        // Rest Endpoints        
        rest()
        	.id("mock")
        	.description("Rest Service Mock")
			.securityDefinitions()
				.basicAuth(SERVICE_SECURITY_DEFINITION_BASIC_VALUE)
			.end()
            .post("/be/consultaSaldoAtomic")
            .description("Mock Adapter Post REST Service")
                .security(SERVICE_SECURITY_DEFINITION_BASIC_VALUE)
                .consumes(APPLICATION_JSON_VALUE)
                .produces(APPLICATION_JSON_VALUE)
                .to("direct:loanAdapter")
            .post("/CON.SALTV/services")
            .description("Mock Consulta Saldo Post REST Service")
                .consumes(TEXT_PLAIN_VALUE)
                .produces(TEXT_PLAIN_VALUE)
                .to("direct:dispatchPost");
        /* Routes Configuration */
        from("direct:dispatchPost").routeId("consultasaldo.request.dispatchPost")
              .log(LoggingLevel.INFO, "Consulta Saldo Mock:: Payload: \n${body}")
              .removeHeaders("*","mockSuccessResponseFlag")
              .process("responseProcessor");
        
        from("direct:loanAdapter").routeId("consultasaldo.request.loadadapter")
            .log(LoggingLevel.INFO, "loan_application:: Payload: [${headers}]\n")
            .removeHeaders("*","mockSuccessResponseFlag")
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant("200"))
            .setBody(constant("{\n"
                    + "  \"header\": {\n"
                    + "    \"messageID\": \"KCB_MESSAGEID\",\n"
                    + "    \"conversationID\": \"50697c9c-e129-4071-b323-a6640296fee1\",\n"
                    + "    \"targetSystemID\": \"not available\",\n"
                    + "    \"channelCode\": \"101\",\n"
                    + "    \"channelName\": \"atm\",\n"
                    + "    \"routeCode\": \"001\",\n"
                    + "    \"routeName\": \"t24\",\n"
                    + "    \"serviceCode\": \"1011002\",\n"
                    + "    \"processingCode\": \"1002\",\n"
                    + "    \"statusCode\": \"0\",\n"
                    + "    \"statusDescription\": \"Success\",\n"
                    + "    \"ehfInfo\": {\n"
                    + "      \"item\": [\n"
                    + "        {\n"
                    + "          \"ehfRef\": \"OSP-1002\",\n"
                    + "          \"ehfDesc\": \"Processed Successfully\"\n"
                    + "        }\n"
                    + "      ]\n"
                    + "    }\n"
                    + "  },\n"
                    + "  \"responsePayload\": {\n"
                    + "    \"primaryData\": {\n"
                    + "      \"businessKey\": \"9940000057\",\n"
                    + "      \"businessKeyType\": \"AccountNumber\"\n"
                    + "    },\n"
                    + "    \"additionalData\": {\n"
                    + "      \"accountNumber\": \"9940000057\",\n"
                    + "      \"uniqueRef\": \"PSAE76420\",\n"
                    + "      \"sessionID\": \"20226510\",\n"
                    + "      \"ledgerBalance\": \"544739.61\",\n"
                    + "      \"netBalance\": \"544739.61\",\n"
                    + "      \"rrn\": \"YT90932\"\n"
                    + "    }\n"
                    + "  }\n"
                    + "}"));
        
        
        from("{{kafka.uri}}").routeId("consumer-route")
        	.log("Read from topic:: --------->")        
	        .log("Headers : ${headers}")
	        .log("Body : ${body}")
	        .process(new Processor() {
	            @Override
	            public void process(Exchange exchange) throws Exception {
	                log.info("My thread is : " + Thread.currentThread().getName());
	            }
	        });
    }
}