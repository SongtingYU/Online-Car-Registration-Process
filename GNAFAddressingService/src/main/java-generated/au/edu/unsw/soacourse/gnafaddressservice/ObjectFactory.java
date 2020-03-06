
package au.edu.unsw.soacourse.gnafaddressservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the au.edu.unsw.soacourse.gnafaddressservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddressExistsInFileFault_QNAME = new QName("http://gnafaddressservice.soacourse.unsw.edu.au", "addressExistsInFileFault");
    private final static QName _SimilarAddressesInFileFault_QNAME = new QName("http://gnafaddressservice.soacourse.unsw.edu.au", "similarAddressesInFileFault");
    private final static QName _ReturnPostcodeInfoFault_QNAME = new QName("http://gnafaddressservice.soacourse.unsw.edu.au", "returnPostcodeInfoFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: au.edu.unsw.soacourse.gnafaddressservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddressExistsInFileRequest }
     * 
     */
    public AddressExistsInFileRequest createAddressExistsInFileRequest() {
        return new AddressExistsInFileRequest();
    }

    /**
     * Create an instance of {@link AddressExistsInFileResponse }
     * 
     */
    public AddressExistsInFileResponse createAddressExistsInFileResponse() {
        return new AddressExistsInFileResponse();
    }

    /**
     * Create an instance of {@link SimilarAddressesInFileRequest }
     * 
     */
    public SimilarAddressesInFileRequest createSimilarAddressesInFileRequest() {
        return new SimilarAddressesInFileRequest();
    }

    /**
     * Create an instance of {@link SimilarAddressesInFileResponse }
     * 
     */
    public SimilarAddressesInFileResponse createSimilarAddressesInFileResponse() {
        return new SimilarAddressesInFileResponse();
    }

    /**
     * Create an instance of {@link ReturnPostcodeInfoRequest }
     * 
     */
    public ReturnPostcodeInfoRequest createReturnPostcodeInfoRequest() {
        return new ReturnPostcodeInfoRequest();
    }

    /**
     * Create an instance of {@link ReturnPostcodeInfoResponse }
     * 
     */
    public ReturnPostcodeInfoResponse createReturnPostcodeInfoResponse() {
        return new ReturnPostcodeInfoResponse();
    }

    /**
     * Create an instance of {@link ServiceFaultType }
     * 
     */
    public ServiceFaultType createServiceFaultType() {
        return new ServiceFaultType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gnafaddressservice.soacourse.unsw.edu.au", name = "addressExistsInFileFault")
    public JAXBElement<ServiceFaultType> createAddressExistsInFileFault(ServiceFaultType value) {
        return new JAXBElement<ServiceFaultType>(_AddressExistsInFileFault_QNAME, ServiceFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gnafaddressservice.soacourse.unsw.edu.au", name = "similarAddressesInFileFault")
    public JAXBElement<ServiceFaultType> createSimilarAddressesInFileFault(ServiceFaultType value) {
        return new JAXBElement<ServiceFaultType>(_SimilarAddressesInFileFault_QNAME, ServiceFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gnafaddressservice.soacourse.unsw.edu.au", name = "returnPostcodeInfoFault")
    public JAXBElement<ServiceFaultType> createReturnPostcodeInfoFault(ServiceFaultType value) {
        return new JAXBElement<ServiceFaultType>(_ReturnPostcodeInfoFault_QNAME, ServiceFaultType.class, null, value);
    }

}
