package hibcDecoder.client;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

@JsType(isNative = true)
public class HIBC {

    @JsMethod(namespace = "HIBC")
    public static native Object decode(String barcode);

    @JsMethod(namespace = "HIBC")
    public static native HIBC isMatch(String line1, String line2);

}
