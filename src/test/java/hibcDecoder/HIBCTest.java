package hibcDecoder;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.junit.client.GWTTestCase;
import hibcDecoder.client.HIBC;
import hibcDecoder.client.Resources;

public class HIBCTest extends GWTTestCase{
    @Override
    public String getModuleName() {
        return "hibcDecoder.HIBCTest";
    }

    @org.junit.Test
    public void testDecode() throws Exception {


        Object scanner = HIBC.decode("+Z999009993020351 ");
        assertNotNull(scanner);


    }

    @org.junit.Test
    public void testIsMatch() throws Exception {



    }
//
//    public class HIBCEntryPoint implements EntryPoint {
//        @Override
//        public void onModuleLoad() {
////            final MomentGwtBundle bundle = MomentGwtBundle.INSTANCE;
//
//
//        }
//    }

}
