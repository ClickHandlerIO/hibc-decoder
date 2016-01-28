package hibcDecoder;

import com.google.gwt.junit.client.GWTTestCase;
import hibcDecoder.client.HIBC;
import org.junit.Test;

public class HIBCTest extends GWTTestCase {

    String labelerId = "Z999";
    String product = "00999302035";
    String lot = "3C001";
    String serial = "0001";
    Character check = 'C';
    Character link = 'L';
    int uom = 1;
    int twoDigitQty = 24;
    int fiveDigitQuantity = 100;

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

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$09053C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue( test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$20928053C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue( test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$30509283C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue( test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$4050928223C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue( test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$5052713C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue( test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$605271223C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue( test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$73C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNull(test3.getDate());
        assertTrue( test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$82409053C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue(test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(twoDigitQty));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$82420928053C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue(test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(twoDigitQty));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$8244050928223C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue(test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(twoDigitQty));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$8244050928223C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue(test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(twoDigitQty));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$8245052713C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue( test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(twoDigitQty));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$824605271223C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue(test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(twoDigitQty));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$82473C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNull(test3.getDate());
        assertTrue(test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(twoDigitQty));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$824C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNull(test3.getDate());
        assertTrue(test3.getPropertyValue().isEmpty());
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(twoDigitQty));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$90010009053C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue(test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(fiveDigitQuantity));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$90010020928053C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue(test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(fiveDigitQuantity));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$90010030509283C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue(test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(fiveDigitQuantity));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$9001004050928223C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue(test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(fiveDigitQuantity));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$9001005052713C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue(test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(fiveDigitQuantity));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$900100605271223C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertTrue(test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(fiveDigitQuantity));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$90010073C001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNull(test3.getDate());
        assertTrue(test3.getPropertyValue().equals(lot));
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(fiveDigitQuantity));

        // null date and empty property value
        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$900100C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNull(test3.getDate());
        assertTrue(test3.getPropertyValue().isEmpty());
        assertTrue(test3.getCheck().equals(check));
        assertTrue(test3.getQuantity().equals(fiveDigitQuantity));

        // serial
        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$+0001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNull(test3.getDate());
        assertEquals(HIBC.PropertyType.SERIAL, test3.getProperty());
        assertTrue(test3.getPropertyValue().equals(serial));
        assertTrue(test3.getCheck().equals(check));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$+09050001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertEquals(HIBC.PropertyType.SERIAL, test3.getProperty());
        assertTrue(test3.getPropertyValue().equals(serial));
        assertTrue(test3.getCheck().equals(check));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$+20928050001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertEquals(HIBC.PropertyType.SERIAL, test3.getProperty());
        assertTrue(test3.getPropertyValue().equals(serial));
        assertTrue(test3.getCheck().equals(check));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$+30509280001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertEquals(HIBC.PropertyType.SERIAL, test3.getProperty());
        assertTrue(test3.getPropertyValue().equals(serial));
        assertTrue(test3.getCheck().equals(check));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$+4050928200001C"); // incorrect hours from hibc
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertEquals(HIBC.PropertyType.SERIAL, test3.getProperty());
        assertTrue(test3.getPropertyValue().equals(serial));
        assertTrue(test3.getCheck().equals(check));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$+5052710001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertEquals(HIBC.PropertyType.SERIAL, test3.getProperty());
        assertTrue(test3.getPropertyValue().equals(serial));
        assertTrue(test3.getCheck().equals(check));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$+605271200001C"); // incorrect hours from hibc
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNotNull(test3.getDate());
        assertEquals(HIBC.PropertyType.SERIAL, test3.getProperty());
        assertTrue(test3.getPropertyValue().equals(serial));
        assertTrue(test3.getCheck().equals(check));

        scanner3 = new HIBC();
        test3 = scanner3.decode("+Z999009993020351/$$+70001C");
        assertNull(test3.getError());
        assertEquals(HIBC.Type.CONCATENATED, test3.getType());
        assertEquals(labelerId, test3.getLabelerId());
        assertEquals(product, test3.getProduct());
        assertTrue(test3.getUom().equals(uom));
        assertNull(test3.getDate());
        assertTrue(test3.getPropertyValue().equals(serial));
        assertTrue(test3.getCheck().equals(check));


        ////////////////////////////////////////////////////////////////////////////////
        // 4. all example data from hibc documentation
        ////////////////////////////////////////////////////////////////////////////////

        HIBC scanner4 = new HIBC();
        HIBC.Decoded test4 = scanner4.decode("+05271LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertNotNull(test4.getDate());

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$3C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$09053C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$20928053C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$30509283C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$4050928223C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$5052713C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$605271223C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$73C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNull(test4.getDate());

        // qty
        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$82409053C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(twoDigitQty));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$82420928053C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(twoDigitQty));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$82430509283C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(twoDigitQty));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$8244050928223C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(twoDigitQty));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$8245052713C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(twoDigitQty));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$824605271223C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(twoDigitQty));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$82473C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(twoDigitQty));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$824LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().isEmpty());  // empty lot
        assertNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(twoDigitQty));

        // 5 digit
        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$90010009053C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(fiveDigitQuantity));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$90010020928053C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(fiveDigitQuantity));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$90010030509283C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(fiveDigitQuantity));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$9001004050928223C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(fiveDigitQuantity));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$9001005052713C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(fiveDigitQuantity));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$900100605271223C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNotNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(fiveDigitQuantity));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$90010073C001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(lot));
        assertNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(fiveDigitQuantity));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$900100LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().isEmpty());
        assertNull(test4.getDate());
        assertTrue(test4.getQuantity().equals(fiveDigitQuantity));

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$+0001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(serial));
        assertNull(test4.getDate());

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$+09050001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(serial));
        assertNotNull(test4.getDate());

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$+20928050001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(serial));
        assertNotNull(test4.getDate());

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$+30509280001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(serial));
        assertNotNull(test4.getDate());

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$+5052710001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(serial));
        assertNotNull(test4.getDate());

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$+605271200001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(serial));
        assertNotNull(test4.getDate());

        scanner4 = new HIBC();
        test4 = scanner4.decode("+$$+70001LC");
        assertNull(test4.getError());
        assertEquals(HIBC.Type.LINE_2, test4.getType());
        assertTrue(test4.getLink().equals(link));
        assertTrue(test4.getCheck().equals(check));
        assertTrue(test4.getPropertyValue().equals(serial));
        assertNull(test4.getDate());


    }

    // boilerplate config for gwt test case
    @Override
    public String getModuleName() {
        return "hibcDecoder.HIBCTest";
    }

}
