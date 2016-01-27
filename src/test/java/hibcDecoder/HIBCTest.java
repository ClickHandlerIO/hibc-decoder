package hibcDecoder;

import com.google.gwt.junit.client.GWTTestCase;
import hibcDecoder.client.HIBC;
import junit.framework.TestCase;
import org.junit.Test;
//import hibcDecoder.client.Resources;

public class HIBCTest extends GWTTestCase {

    String labelerId = "Z999";
    String product = "00999302035";
    String lot = "3C001";
    Character check = 'C';
    Character link = 'L';
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

        scanner = new HIBC();
        test = scanner.decode("+$$09053C001 C");
        assertNotNull(test);
        assertEquals(test.getType(), HIBC.Type.LINE_2);
        assertTrue(test.getPropertyValue().equals(lot));
        assertTrue(test.getLink().equals(' '));
        assertTrue(test.getCheck().equals(check));
        assertNotNull(test.getDate());

        scanner = new HIBC();
        test = scanner.decode("+$$09053C001  ");
        assertEquals(test.getType(), HIBC.Type.LINE_2);
        assertTrue(test.getPropertyValue().equals(lot));
        assertTrue(test.getLink().equals(' '));
        assertTrue(test.getCheck().equals(' '));
        assertNotNull(test.getDate());


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
