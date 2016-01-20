package hibcDecoder.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import elemental.client.Browser;

/**
 * Created by admin on 1/19/16.
 */
public class Main implements EntryPoint {

    @Override
    public void onModuleLoad() {
        // save for future reference
//        ScriptInjector.fromString(Resources.instance.underscore().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
//        ScriptInjector.fromString(Resources.instance.hibc().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
//
//        // old js interop way of doing it
//        Object scanner = HIBC2.decode("+Z999009993020351");
//        Browser.getWindow().getConsole().log(scanner);

        // resources interface
//        public interface Resources extends ClientBundle {
//            Resources instance = GWT.create(Resources.class);
//
//            @Source("hibc.js")
//            TextResource hibc();
//
//            @Source("underscore.js")
//            TextResource underscore();
//        }

    }
}
