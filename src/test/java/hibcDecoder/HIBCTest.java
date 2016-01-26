package hibcDecoder;

import com.google.gwt.junit.client.GWTTestCase;
import hibcDecoder.client.HIBC;
import junit.framework.TestCase;
import org.junit.Test;
//import hibcDecoder.client.Resources;

public class HIBCTest extends GWTTestCase {  // TestCase  GWTTestCase
    @Override
    public String getModuleName() {
        return "hibcDecoder.HIBCTest";
    }

    @Test
    public void testDecode() throws Exception {

        // test single line barcode
        HIBC scanner = new HIBC();
        scanner.decode("+Z999009993020351 ");
        assertNotNull(scanner);
//        Object itemReturned = scanner.getDecoded();
//        assertNotNull(itemReturned);

        // test double line barcodes and Date time format
        scanner = new HIBC();
        scanner.decode("*+Z999009993020351/05271C");
        assertNotNull(scanner);

//    @Test
//    public void testIsMatch() throws Exception {
//

    }

}
