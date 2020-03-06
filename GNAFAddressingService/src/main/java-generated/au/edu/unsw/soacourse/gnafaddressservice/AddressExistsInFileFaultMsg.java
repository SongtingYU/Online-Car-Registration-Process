
package au.edu.unsw.soacourse.gnafaddressservice;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.0.4
 * 2016-10-01T16:58:06.090+10:00
 * Generated source version: 3.0.4
 */

@WebFault(name = "addressExistsInFileFault", targetNamespace = "http://gnafaddressservice.soacourse.unsw.edu.au")
public class AddressExistsInFileFaultMsg extends Exception {
    
    private au.edu.unsw.soacourse.gnafaddressservice.ServiceFaultType addressExistsInFileFault;

    public AddressExistsInFileFaultMsg() {
        super();
    }
    
    public AddressExistsInFileFaultMsg(String message) {
        super(message);
    }
    
    public AddressExistsInFileFaultMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressExistsInFileFaultMsg(String message, au.edu.unsw.soacourse.gnafaddressservice.ServiceFaultType addressExistsInFileFault) {
        super(message);
        this.addressExistsInFileFault = addressExistsInFileFault;
    }

    public AddressExistsInFileFaultMsg(String message, au.edu.unsw.soacourse.gnafaddressservice.ServiceFaultType addressExistsInFileFault, Throwable cause) {
        super(message, cause);
        this.addressExistsInFileFault = addressExistsInFileFault;
    }

    public au.edu.unsw.soacourse.gnafaddressservice.ServiceFaultType getFaultInfo() {
        return this.addressExistsInFileFault;
    }
}