package hibcDecoder;

import com.google.gwt.junit.client.GWTTestCase;
import hibcDecoder.client.HIBC;
import junit.framework.TestCase;
import org.junit.Test;
//import hibcDecoder.client.Resources;

public class HIBCTest extends GWTTestCase {

    String labelerId = "Z999";
    String product = "00999302035";
    Character check = 'C';
    int uom = 1;

    @Test
    public void testDecode() throws Exception {

        ////////////////////////////////////////////////////////////////////////////////
        // 0. test single line barcodes with spaces and check link characters
        ////////////////////////////////////////////////////////////////////////////////

        HIBC scanner = new HIBC();
        HIBC.Decoded test = scanner.decode("+Z999009993020351 ");
        assertNotNull(test);
        assertEquals(test.getType(), HIBC.Type.LINE_1);
        assertEquals(test.getLabelerId(), labelerId);
        assertEquals(test.getProduct(), product);
        assertTrue(test.getUom().equals(uom));
        assertTrue(test.getCheck().equals(' '));



        ////////////////////////////////////////////////////////////////////////////////
        // 1. test double line barcodes with leading and trailing *
        ////////////////////////////////////////////////////////////////////////////////

        scanner = new HIBC();
        HIBC.Decoded test1 = scanner.decode("*+Z999009993020351/05271C");
        assertNotNull(test1);
        assertNull(test1.getError());
        assertEquals(test1.getType(), HIBC.Type.CONCATENATED);
        assertEquals(test1.getLabelerId(), labelerId);
        assertEquals(test1.getProduct(), product);







    }

    // boilerplate config for gwt test case
    @Override
    public String getModuleName() {
        return "hibcDecoder.HIBCTest";
    }

}
