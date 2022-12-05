package co.claro.mock.processor;

import static org.springframework.http.MediaType.TEXT_XML_VALUE;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/**
 * mock-consulta-saldo
 * T24BprResponseProcessor.java
 * Sep 4, 2022
 *
 * @author avazquez | Soaint | Claro
 * @version  1.0.0
 * 
 */
@Component
public class ResponseProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(ResponseProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        Boolean mockSuccessResponseFlag = exchange.getIn().getHeader("mockSuccessResponseFlag", Boolean.class);

        if(null == mockSuccessResponseFlag) {
            mockSuccessResponseFlag = true;
        }

        logger.info("Mock Success Response Flag [{}]", mockSuccessResponseFlag);

        if(mockSuccessResponseFlag){
            exchange.getIn().setBody(getSuccessResponse());
        } else {
            exchange.getIn().setBody(getErrorResponse());
        }

        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, TEXT_XML_VALUE);
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);

    }

    private String getSuccessResponse() {

        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" + 
                "   <S:Body>\r\n" + 
                "       <ns3:BalanceEnquiryWebserviceResponse xmlns:ns2=\"http://temenos.com/KCBRWATMBALENQ\" xmlns:ns3=\"http://temenos.com/BAL.ENQATM\">\r\n" + 
                "           <Status>\r\n" + 
                "               <successIndicator>Success</successIndicator>\r\n" + 
                "           </Status>\r\n" + 
                "           <KCBRWATMBALENQType>\r\n" + 
                "               <ns2:gKCBRWATMBALENQDetailType>\r\n" + 
                "                   <ns2:mKCBRWATMBALENQDetailType>\r\n" + 
                "                       <ns2:ACCOUNTNO>9940000057</ns2:ACCOUNTNO>\r\n" + 
                "                       <ns2:SERVICECODE>1011002</ns2:SERVICECODE>\r\n" + 
                "                       <ns2:CHANNELCODE>101</ns2:CHANNELCODE>\r\n" + 
                "                       <ns2:UNIQUEREF>PSAE76420</ns2:UNIQUEREF>\r\n" + 
                "                       <ns2:SESSIONID>20226510</ns2:SESSIONID>\r\n" + 
                "                       <ns2:LedgerBalance>544739.61</ns2:LedgerBalance>\r\n" + 
                "                       <ns2:NetBalance>544739.61</ns2:NetBalance>\r\n" + 
                "                       <ns2:Rrn>YT90932</ns2:Rrn>\r\n" + 
                "                   </ns2:mKCBRWATMBALENQDetailType>\r\n" + 
                "               </ns2:gKCBRWATMBALENQDetailType>\r\n" + 
                "           </KCBRWATMBALENQType>\r\n" + 
                "       </ns3:BalanceEnquiryWebserviceResponse>\r\n" + 
                "   </S:Body>\r\n" + 
                "</S:Envelope>");
        logger.info("Respnse Payload[{}]",xmlBuilder.toString());
        return xmlBuilder.toString();

    }
    private String getErrorResponse() {

        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" + 
                "   <S:Body>\r\n" + 
                "       <ns3:BalanceEnquiryWebserviceResponse xmlns:ns2=\"http://temenos.com/KCBRWATMBALENQ\" xmlns:ns3=\"http://temenos.com/BAL.ENQATM\">\r\n" + 
                "                    <Status>\r\n" + 
                "            <successIndicator>T24Error</successIndicator>\r\n" + 
                "            <application>FUNDS.TRANSFER</application>\r\n" + 
                "            <messages>DEBIT.ACCT.NO:1:1=Bucket Error E-117432</messages>\r\n" + 
                "            <messages>DEBIT.ACCT.NO:1:1=Bucket Error E-117434</messages>\r\n" + 
                "         </Status>\r\n" + 
                "           <KCBRWATMBALENQType>\r\n" + 
                "               <ns2:gKCBRWATMBALENQDetailType>\r\n" + 
                "                   <ns2:mKCBRWATMBALENQDetailType>\r\n" + 
                "                       <ns2:ACCOUNTNO>9940000235</ns2:ACCOUNTNO>\r\n" + 
                "                       <ns2:SERVICECODE>1011002</ns2:SERVICECODE>\r\n" + 
                "                       <ns2:CHANNELCODE>101</ns2:CHANNELCODE>\r\n" + 
                "                       <ns2:UNIQUEREF>PSAE76420</ns2:UNIQUEREF>\r\n" + 
                "                       <ns2:SESSIONID>20226510</ns2:SESSIONID>\r\n" + 
                "                       <ns2:LedgerBalance>Your Balance is Insufficient</ns2:LedgerBalance>\r\n" + 
                "                       <ns2:NetBalance/>\r\n" + 
                "                       <ns2:Rrn>YT90932</ns2:Rrn>\r\n" + 
                "                   </ns2:mKCBRWATMBALENQDetailType>\r\n" + 
                "               </ns2:gKCBRWATMBALENQDetailType>\r\n" + 
                "           </KCBRWATMBALENQType>\r\n" + 
                "       </ns3:BalanceEnquiryWebserviceResponse>\r\n" + 
                "   </S:Body>\r\n" + 
                "</S:Envelope>");
        logger.info("Respnse Payload[{}]",xmlBuilder.toString());
        return xmlBuilder.toString();

    }

}