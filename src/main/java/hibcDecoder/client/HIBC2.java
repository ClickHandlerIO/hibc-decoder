package hibcDecoder.client;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

/**
 * Created by admin on 1/19/16.
 */
@JsType(isNative = true)
public class HIBC2 {


    @JsMethod(namespace = "HIBC")
    public static native Object decode(String barcode);

    @JsMethod(namespace = "HIBC")
    public static native HIBC2 isMatch(String line1, String line2);

}
