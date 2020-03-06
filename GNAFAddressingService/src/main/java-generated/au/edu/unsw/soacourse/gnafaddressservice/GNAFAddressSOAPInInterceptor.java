package au.edu.unsw.soacourse.gnafaddressservice;

import javax.xml.namespace.QName;

import org.apache.cxf.phase.Phase;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.w3c.dom.Element;

public class GNAFAddressSOAPInInterceptor extends AbstractSoapInterceptor {

	public GNAFAddressSOAPInInterceptor() {
		super(Phase.USER_PROTOCOL);
	}

	@Override
	public void handleMessage(SoapMessage message) {
		QName qname = new QName("http://soacourse.cse.unsw.edu.au", "api-key");
//		if (message.hasHeaders()) {
//
//			SoapHeader header = (SoapHeader) message.getHeader(qname);
//			String apikey = ((Element) header.getObject()).getTextContent()
//					.trim();
//
//			String[] split = apikey.split(" +");
//			String keyVal = split[0].trim();
//			String keyUser = split[1].trim();
//
//			if (!(keyVal.equals("soacourse-key-pass") && keyUser
//					.equals("soacourse"))) {
//				throw new Fault(new Throwable("Incorrect API key"));
//			}
//
//		} else {
//			throw new Fault(new Throwable("No API key provided"));
//		}
	}

}
