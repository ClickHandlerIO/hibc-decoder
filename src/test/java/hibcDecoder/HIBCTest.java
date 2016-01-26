package hibcDecoder;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.ScriptInjector;
//import com.google.gwt.junit.client.GWTTestCase;
import hibcDecoder.client.HIBC;
import junit.framework.TestCase;
import org.junit.Test;
//import hibcDecoder.client.Resources;

public class HIBCTest extends TestCase {  // TestCase  GWTTestCase
//    @Override
//    public String getModuleName() {
//        return "hibcDecoder.HIBC";
//    }

    @Test
    public void testDecode() throws Exception {

        // test double line barcode
        HIBC scanner = new HIBC();
//        Decoded decodeTest = new Decoded();
        scanner.decode("*+Z999009993020351/05271C");
        assertNotNull(scanner);


        // test single line barcode
        scanner = new HIBC();
        scanner.decode("+Z999009993020351 ");
        assertNotNull(scanner);


    }

    @Test
    public void testIsMatch() throws Exception {



    }
//    private class Decoded{
//        Type type;
//        Error error;
//        String barcode;
//        String labelerId;
//        Character check;
//        Character link;
//        String lot;
//        PropertyType property; // used to determine the type of code it seems, lot, serial, link
//        String propertyValue;
//        Integer quantity;
//        Integer uom;
//        String date;
//        String product;
//    }

//
//    public class HIBCEntryPoint implements EntryPoint {
//        @Override
//        public void onModuleLoad() {
////            final MomentGwtBundle bundle = MomentGwtBundle.INSTANCE;
//
//
//        }
//    }
    // action config is related to the hysterix configuration,
    // remoteaction means we;re going to have some context on it, like a remote ocntext

}
