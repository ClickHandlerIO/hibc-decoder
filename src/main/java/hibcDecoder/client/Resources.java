package hibcDecoder.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.ui.Tree;

/**
 * Created by admin on 1/19/16.
 */
public interface Resources extends ClientBundle {
    Resources instance = GWT.create(Resources.class);

    @Source("hibc.js")
    TextResource hibc();

    @Source("underscore.js")
    TextResource underscore();
}
