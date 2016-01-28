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
        assertNotNull(test);
        assertEquals(test.getType(), HIBC.Type.LINE_2);
        assertTrue(test.getPropertyValue().equals(lot));
        assertTrue(test.getLink().equals(' '));
        assertTrue(test.getCheck().equals(' '));
        assertNotNull(test.getDate());


        ////////////////////////////////////////////////////////////////////////////////
        // 1. test double line barcodes with leading and trailing *
        ////////////////////////////////////////////////////////////////////////////////

        HIBC scanner1 = new HIBC();
        HIBC.Decoded test1 = scanner1.decode("*+Z999009993020351/05271C");
        assertNotNull(test1);
        assertNull(test1.getError());
        assertEquals(test1.getType(), HIBC.Type.CONCATENATED);
        assertEquals(test1.getLabelerId(), labelerId);
        assertEquals(test1.getProduct(), product);
        assertTrue(test1.getUom().equals(uom));
        assertTrue(test1.getCheck().equals(check));
        assertNotNull(test1.getDate());


        scanner1 = new HIBC();
        test1 = scanner1.decode("+Z999009993020351/05271C*");
        assertNotNull(test1);
        assertNull(test1.getError());
        assertEquals(test1.getType(), HIBC.Type.CONCATENATED);
        assertEquals(test1.getLabelerId(), labelerId);
        assertEquals(test1.getProduct(), product);
        assertTrue(test1.getUom().equals(uom));
        assertTrue(test1.getCheck().equals(check));
        assertNotNull(test1.getDate());

        scanner1 = new HIBC();
        test1 = scanner1.decode("*+Z999009993020351/05271C*");
        assertNotNull(test1);
        assertNull(test1.getError());
        assertEquals(test1.getType(), HIBC.Type.CONCATENATED);
        assertEquals(test1.getLabelerId(), labelerId);
        assertEquals(test1.getProduct(), product);
        assertTrue(test1.getUom().equals(uom));
        assertTrue(test1.getCheck().equals(check));
        assertNotNull(test1.getDate());


        ////////////////////////////////////////////////////////////////////////////////
        // 2. test for line 1 and line 2 linking and handle, check and link characters
        ////////////////////////////////////////////////////////////////////////////////

        HIBC scanner2 = new HIBC();
        HIBC.Decoded test2 = scanner2.decode("+Z999009993020351/");
        assertNotNull(test2);
        assertNull(test2.getError());
        assertEquals(test2.getType(), HIBC.Type.LINE_1);
        assertEquals(test2.getLabelerId(), labelerId);
        assertEquals(test2.getProduct(), product);
        assertTrue(test2.getUom().equals(uom));
        assertTrue(test2.getCheck().equals('/'));

        scanner2 = new HIBC();
        test2 = scanner2.decode("+$$09053C001L/");
        assertNotNull(test2);
        assertNull(test2.getError());
        assertEquals(test2.getType(), HIBC.Type.LINE_2);
        assertNull(test2.getLabelerId());
        assertNull(test2.getProduct());
        assertTrue(test2.getPropertyValue().equals(lot));
        assertTrue(test2.getLink().equals(link));
        assertTrue(test2.getCheck().equals('/'));
        assertNotNull(test2.getDate());

        scanner2 = new HIBC();
        test2 = scanner2.decode("+$$09053C001//");
        assertNotNull(test2);
        assertNull(test2.getError());
        assertEquals(test2.getType(), HIBC.Type.LINE_2);
        assertTrue(test2.getPropertyValue().equals(lot));
        assertTrue(test2.getLink().equals('/'));
        assertTrue(test2.getCheck().equals('/'));
        assertNotNull(test2.getDate());


        ////////////////////////////////////////////////////////////////////////////////
        // 3. more concatenated barcodes
        ////////////////////////////////////////////////////////////////////////////////

        HIBC scanner3 = new HIBC();
        HIBC.Decoded test3 = scanner3.decode("+Z999009993020351/05271C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue(test3.getCheck().equals(check));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$3C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNull(test3.getDate());
        assertTrue( test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));



    }

    // boilerplate config for gwt test case
    @Override
    public String getModuleName() {
        return "hibcDecoder.HIBCTest";
    }

}
