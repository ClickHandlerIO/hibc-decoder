package hibcDecoder.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.ScriptInjector;
import elemental.client.Browser;

/**
 * Created by admin on 1/19/16.
 */
public class Main implements EntryPoint {

    @Override
    public void onModuleLoad() {
        ScriptInjector.fromString(Resources.instance.underscore().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
        ScriptInjector.fromString(Resources.instance.hibc().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();

        Object scanner = HIBC.decode("+Z999009993020351");
        Browser.getWindow().getConsole().log(scanner);
    }
}
